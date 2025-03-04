import streamlit as st
import psycopg2
import pandas as pd
from datetime import datetime
import hashlib
import os

# Database connection function
def get_connection():
    conn = psycopg2.connect(
        host=st.secrets["db_host"],
        database=st.secrets["db_name"],
        user=st.secrets["db_user"],
        password=st.secrets["db_password"]
    )
    return conn

# Initialize database
def init_db():
    conn = get_connection()
    cur = conn.cursor()
    
    # Create tables
    cur.execute('''
    CREATE TABLE IF NOT EXISTS Department (
        DepartmentID SERIAL PRIMARY KEY,
        DepartmentName VARCHAR(100) NOT NULL,
        Location VARCHAR(100),
        ContactInfo VARCHAR(100)
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS Role (
        RoleID SERIAL PRIMARY KEY,
        RoleName VARCHAR(50) NOT NULL,
        Description TEXT,
        PermissionLevel INTEGER NOT NULL
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS "User" (
        UserID SERIAL PRIMARY KEY,
        Username VARCHAR(50) UNIQUE NOT NULL,
        PasswordHash VARCHAR(128) NOT NULL,
        FirstName VARCHAR(50) NOT NULL,
        LastName VARCHAR(50) NOT NULL,
        BadgeNumber VARCHAR(20) UNIQUE,
        Email VARCHAR(100) UNIQUE,
        Phone VARCHAR(20),
        RoleID INTEGER REFERENCES Role(RoleID),
        DepartmentID INTEGER REFERENCES Department(DepartmentID),
        Status VARCHAR(20) DEFAULT 'Active'
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS Case (
        CaseID SERIAL PRIMARY KEY,
        CaseTitle VARCHAR(100) NOT NULL,
        Description TEXT,
        DateOpened DATE NOT NULL,
        Status VARCHAR(20) CHECK (Status IN ('Open', 'Closed', 'Pending', 'Suspended')),
        Priority VARCHAR(20)
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS EvidenceType (
        EvidenceTypeID SERIAL PRIMARY KEY,
        TypeName VARCHAR(50) NOT NULL,
        Description TEXT
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS Evidence (
        EvidenceID SERIAL PRIMARY KEY,
        CaseID INTEGER REFERENCES Case(CaseID),
        EvidenceTypeID INTEGER REFERENCES EvidenceType(EvidenceTypeID),
        Description TEXT,
        DateCollected DATE NOT NULL,
        LocationCollected VARCHAR(100),
        CollectedBy INTEGER REFERENCES "User"(UserID),
        FileHash VARCHAR(128) UNIQUE,
        FileSize BIGINT,
        FileFormat VARCHAR(20),
        Status VARCHAR(20) CHECK (Status IN ('Active', 'Archived', 'Destroyed', 'Released'))
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS AccessLog (
        LogID SERIAL PRIMARY KEY,
        UserID INTEGER REFERENCES "User"(UserID),
        EvidenceID INTEGER REFERENCES Evidence(EvidenceID),
        AccessTimestamp TIMESTAMP NOT NULL,
        ActionType VARCHAR(50) NOT NULL,
        IPAddress VARCHAR(50),
        Notes TEXT
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS ChainOfCustody (
        CustodyID SERIAL PRIMARY KEY,
        EvidenceID INTEGER REFERENCES Evidence(EvidenceID),
        UserID INTEGER REFERENCES "User"(UserID),
        ActionType VARCHAR(50) NOT NULL,
        Timestamp TIMESTAMP NOT NULL,
        Notes TEXT,
        PreviousCustodianID INTEGER REFERENCES "User"(UserID)
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS Tag (
        TagID SERIAL PRIMARY KEY,
        TagName VARCHAR(50) UNIQUE NOT NULL,
        Description TEXT
    );
    ''')
    
    cur.execute('''
    CREATE TABLE IF NOT EXISTS EvidenceTag (
        EvidenceID INTEGER REFERENCES Evidence(EvidenceID),
        TagID INTEGER REFERENCES Tag(TagID),
        PRIMARY KEY (EvidenceID, TagID)
    );
    ''')
    
    # Insert sample data if tables are empty
    cur.execute("SELECT COUNT(*) FROM Department")
    if cur.fetchone()[0] == 0:
        insert_sample_data(cur)
    
    conn.commit()
    cur.close()
    conn.close()

# Insert sample data
def insert_sample_data(cur):
    # Insert departments
    departments = [
        ('Cybercrime Unit', 'Headquarters', 'cyber@police.gov'),
        ('Forensics Department', 'Lab Building', 'forensics@police.gov'),
        ('Homicide Division', 'North Precinct', 'homicide@police.gov')
    ]
    for dept in departments:
        cur.execute(
            "INSERT INTO Department (DepartmentName, Location, ContactInfo) VALUES (%s, %s, %s)",
            dept
        )
    
    # Insert roles
    roles = [
        ('Administrator', 'Full system access', 100),
        ('Detective', 'Case management and evidence access', 80),
        ('Forensic Analyst', 'Evidence analysis and documentation', 70),
        ('Officer', 'Basic evidence submission', 50),
        ('Legal Counsel', 'Read-only access to case files', 40)
    ]
    for role in roles:
        cur.execute(
            "INSERT INTO Role (RoleName, Description, PermissionLevel) VALUES (%s, %s, %s)",
            role
        )
    
    # Insert users
    users = [
        ('admin', hashlib.sha256('admin123'.encode()).hexdigest(), 'System', 'Administrator', 'ADMIN001', 'admin@police.gov', '555-0100', 1, 1),
        ('jsmith', hashlib.sha256('password123'.encode()).hexdigest(), 'John', 'Smith', 'DET123', 'jsmith@police.gov', '555-0101', 2, 1),
        ('agarcia', hashlib.sha256('secure456'.encode()).hexdigest(), 'Ana', 'Garcia', 'FOR456', 'agarcia@police.gov', '555-0102', 3, 2),
        ('mwilson', hashlib.sha256('police789'.encode()).hexdigest(), 'Mike', 'Wilson', 'OFF789', 'mwilson@police.gov', '555-0103', 4, 3),
        ('jdoe', hashlib.sha256('legal321'.encode()).hexdigest(), 'Jane', 'Doe', 'LGL321', 'jdoe@police.gov', '555-0104', 5, 1)
    ]
    for user in users:
        cur.execute(
            'INSERT INTO "User" (Username, PasswordHash, FirstName, LastName, BadgeNumber, Email, Phone, RoleID, DepartmentID) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)',
            user
        )
    
    # Insert cases
    cases = [
        ('Ransomware Attack on City Hall', 'Investigation of ransomware attack affecting city systems', '2023-01-15', 'Open', 'High'),
        ('Corporate Data Breach', 'Investigation of data breach at TechCorp', '2023-02-20', 'Open', 'High'),
        ('Identity Theft Ring', 'Investigation of organized identity theft operation', '2023-03-10', 'Pending', 'Medium'),
        ('Email Phishing Campaign', 'Investigation of widespread phishing emails targeting seniors', '2023-04-05', 'Closed', 'Low'),
        ('Social Media Harassment', 'Investigation of online harassment case', '2023-05-12', 'Open', 'Medium')
    ]
    for case in cases:
        cur.execute(
            "INSERT INTO Case (CaseTitle, Description, DateOpened, Status, Priority) VALUES (%s, %s, %s, %s, %s)",
            case
        )
    
    # Insert evidence types
    evidence_types = [
        ('Hard Drive', 'Physical storage device'),
        ('Email', 'Electronic mail communication'),
        ('Image', 'Digital photograph or image file'),
        ('Video', 'Digital video recording'),
        ('Document', 'Text document or PDF'),
        ('Mobile Device', 'Smartphone or tablet'),
        ('Network Log', 'Server or network device logs'),
        ('Social Media', 'Content from social media platforms')
    ]
    for etype in evidence_types:
        cur.execute(
            "INSERT INTO EvidenceType (TypeName, Description) VALUES (%s, %s)",
            etype
        )
    
    # Insert tags
    tags = [
        ('Urgent', 'Requires immediate attention'),
        ('Sensitive', 'Contains sensitive information'),
        ('Encrypted', 'Data is encrypted'),
        ('Corrupted', 'File is partially corrupted'),
        ('Key Evidence', 'Critical to the case')
    ]
    for tag in tags:
        cur.execute(
            "INSERT INTO Tag (TagName, Description) VALUES (%s, %s)",
            tag
        )
    
    # Insert evidence
    evidence = [
        (1, 1, 'City Hall server backup', '2023-01-16', 'Server Room', 3, 'a1b2c3d4e5f6', 5000000, 'IMG', 'Active'),
        (1, 7, 'Firewall logs from attack period', '2023-01-17', 'Network Operations Center', 3, 'f6e5d4c3b2a1', 2500000, 'LOG', 'Active'),
        (2, 6, 'CEO\'s smartphone', '2023-02-21', 'TechCorp Office', 2, '1a2b3c4d5e6f', 64000000, 'IMG', 'Active'),
        (3, 5, 'Forged identification documents', '2023-03-12', 'Suspect\'s residence', 4, '6f5e4d3c2b1a', 15000000, 'PDF', 'Active'),
        (4, 2, 'Phishing email samples', '2023-04-06', 'Victim\'s computer', 2, 'abcdef123456', 500000, 'EML', 'Archived')
    ]
    for e in evidence:
        cur.execute(
            "INSERT INTO Evidence (CaseID, EvidenceTypeID, Description, DateCollected, LocationCollected, CollectedBy, FileHash, FileSize, FileFormat, Status) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
            e
        )
    
    # Insert evidence tags
    evidence_tags = [
        (1, 5),  # City Hall server backup - Key Evidence
        (1, 1),  # City Hall server backup - Urgent
        (2, 3),  # Firewall logs - Encrypted
        (3, 2),  # CEO's smartphone - Sensitive
        (4, 5),  # Forged documents - Key Evidence
        (5, 4)   # Phishing email - Corrupted
    ]
    for et in evidence_tags:
        cur.execute(
            "INSERT INTO EvidenceTag (EvidenceID, TagID) VALUES (%s, %s)",
            et
        )
    
    # Insert chain of custody records
    custody_records = [
        (1, 3, 'Collected', '2023-01-16 10:30:00', 'Initial seizure', None),
        (1, 2, 'Transferred', '2023-01-17 09:15:00', 'For analysis', 3),
        (2, 3, 'Collected', '2023-01-17 14:45:00', 'Initial seizure', None),
        (3, 2, 'Collected', '2023-02-21 11:20:00', 'Initial seizure', None),
        (4, 4, 'Collected', '2023-03-12 16:10:00', 'Initial seizure', None),
        (5, 2, 'Collected', '2023-04-06 13:30:00', 'Initial seizure', None),
        (5, 3, 'Transferred', '2023-04-07 10:00:00', 'For analysis', 2),
        (5, 1, 'Archived', '2023-04-20 15:45:00', 'Case closed', 3)
    ]
    for cr in custody_records:
        cur.execute(
            "INSERT INTO ChainOfCustody (EvidenceID, UserID, ActionType, Timestamp, Notes, PreviousCustodianID) VALUES (%s, %s, %s, %s, %s, %s)",
            cr
        )
    
    # Insert access logs
    access_logs = [
        (1, 1, '2023-01-16 11:00:00', 'View', '192.168.1.10', 'Initial review'),
        (2, 1, '2023-01-17 10:00:00', 'View', '192.168.1.10', 'Case assignment'),
        (3, 2, '2023-01-17 15:00:00', 'Download', '192.168.1.11', 'Analysis preparation'),
        (1, 3, '2023-01-18 09:30:00', 'Analyze', '192.168.1.12', 'Forensic analysis'),
        (2, 3, '2023-01-18 14:15:00', 'Analyze', '192.168.1.12', 'Log analysis'),
        (3, 2, '2023-02-22 10:45:00', 'View', '192.168.1.11', 'Follow-up review'),
        (4, 4, '2023-03-13 09:00:00', 'Upload', '192.168.1.13', 'Evidence submission'),
        (5, 2, '2023-04-07 11:30:00', 'View', '192.168.1.11', 'Initial review'),
        (5, 3, '2023-04-08 13:20:00', 'Analyze', '192.168.1.12', 'Email header analysis')
    ]
    for al in access_logs:
        cur.execute(
            "INSERT INTO AccessLog (UserID, EvidenceID, AccessTimestamp, ActionType, IPAddress, Notes) VALUES (%s, %s, %s, %s, %s, %s)",
            al
        )

# Function to log access
def log_access(user_id, evidence_id, action_type, notes=""):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute(
        "INSERT INTO AccessLog (UserID, EvidenceID, AccessTimestamp, ActionType, IPAddress, Notes) VALUES (%s, %s, %s, %s, %s, %s)",
        (user_id, evidence_id, datetime.now(), action_type, "127.0.0.1", notes)
    )
    conn.commit()
    cur.close()
    conn.close()

# Function to add chain of custody record
def add_custody_record(evidence_id, user_id, action_type, notes, previous_custodian_id=None):
    conn = get_connection()
    cur = conn.cursor()
    cur.execute(
        "INSERT INTO ChainOfCustody (EvidenceID, UserID, ActionType, Timestamp, Notes, PreviousCustodianID) VALUES (%s, %s, %s, %s, %s, %s)",
        (evidence_id, user_id, action_type, datetime.now(), notes, previous_custodian_id)
    )
    conn.commit()
    cur.close()
    conn.close()

# Function to calculate file hash
def calculate_file_hash(file_bytes):
    return hashlib.sha256(file_bytes).hexdigest()

# Streamlit UI
def main():
    st.set_page_config(page_title="Digital Evidence Management System", layout="wide")
    
    # Initialize database
    try:
        init_db()
    except Exception as e:
        st.error(f"Database initialization error: {e}")
        return
    
    # Sidebar for navigation
    st.sidebar.title("DEMS Navigation")
    page = st.sidebar.selectbox(
        "Select a page", 
        ["Login", "Cases", "Evidence", "Users", "Reports", "Chain of Custody"]
    )
    
    # Session state for login
    if 'logged_in' not in st.session_state:
        st.session_state.logged_in = False
        st.session_state.user_id = None
        st.session_state.username = None
        st.session_state.role = None
    
    # Login page
    if page == "Login" or not st.session_state.logged_in:
        st.title("Digital Evidence Management System")
        st.subheader("Login")
        
        username = st.text_input("Username")
        password = st.text_input("Password", type="password")
        
        if st.button("Login"):
            if username and password:
                conn = get_connection()
                cur = conn.cursor()
                
                # Hash the password
                hashed_password = hashlib.sha256(password.encode()).hexdigest()
                
                # Check credentials
                cur.execute(
                    'SELECT u.UserID, u.Username, r.RoleName FROM "User" u JOIN Role r ON u.RoleID = r.RoleID WHERE u.Username = %s AND u.PasswordHash = %s AND u.Status = %s',
                    (username, hashed_password, 'Active')
                )
                user = cur.fetchone()
                
                if user:
                    st.session_state.logged_in = True
                    st.session_state.user_id = user[0]
                    st.session_state.username = user[1]
                    st.session_state.role = user[2]
                    st.success(f"Welcome, {user[1]}!")
                    st.experimental_rerun()
                else:
                    st.error("Invalid username or password")
                
                cur.close()
                conn.close()
            else:
                st.warning("Please enter both username and password")
        
        # Sample login credentials
        st.info("Sample credentials: username: admin, password: admin123")
        return
    
    # Logout button in sidebar
    if st.sidebar.button("Logout"):
        st.session_state.logged_in = False
        st.session_state.user_id = None
        st.session_state.username = None
        st.session_state.role = None
        st.experimental_rerun()
    
    st.sidebar.write(f"Logged in as: {st.session_state.username}")
    st.sidebar.write(f"Role: {st.session_state.role}")
    
    # Cases page
    if page == "Cases":
        st.title("Case Management")
        
        tab1, tab2 = st.tabs(["View Cases", "Add Case"])
        
        with tab1:
            st.subheader("All Cases")
            conn = get_connection()
            cases_df = pd.read_sql("SELECT * FROM Case ORDER BY CaseID", conn)
            st.dataframe(cases_df)
            
            # Case details
            st.subheader("Case Details")
            case_id = st.number_input("Enter Case ID", min_value=1, step=1)
            if st.button("View Case Details"):
                case_df = pd.read_sql(f"SELECT * FROM Case WHERE CaseID = {case_id}", conn)
                if not case_df.empty:
                    st.write(case_df)
                    
                    # Show evidence related to this case
                    evidence_df = pd.read_sql(f"""
                        SELECT e.EvidenceID, e.Description, e.DateCollected, e.Status, 
                               et.TypeName as EvidenceType, 
                               CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                        FROM Evidence e
                        JOIN EvidenceType et ON e.EvidenceTypeID = et.EvidenceTypeID
                        JOIN "User" u ON e.CollectedBy = u.UserID
                        WHERE e.CaseID = {case_id}
                    """, conn)
                    
                    st.subheader(f"Evidence for Case #{case_id}")
                    if not evidence_df.empty:
                        st.dataframe(evidence_df)
                    else:
                        st.info("No evidence found for this case")
                else:
                    st.error("Case not found")
            
            conn.close()
        
        with tab2:
            st.subheader("Add New Case")
            case_title = st.text_input("Case Title")
            description = st.text_area("Description")
            date_opened = st.date_input("Date Opened")
            status = st.selectbox("Status", ["Open", "Closed", "Pending", "Suspended"])
            priority = st.selectbox("Priority", ["High", "Medium", "Low"])
            
            if st.button("Add Case"):
                if case_title:
                    conn = get_connection()
                    cur = conn.cursor()
                    try:
                        cur.execute(
                            "INSERT INTO Case (CaseTitle, Description, DateOpened, Status, Priority) VALUES (%s, %s, %s, %s, %s) RETURNING CaseID",
                            (case_title, description, date_opened, status, priority)
                        )
                        new_case_id = cur.fetchone()[0]
                        conn.commit()
                        st.success(f"Case added successfully with ID: {new_case_id}")
                    except Exception as e:
                        conn.rollback()
                        st.error(f"Error adding case: {e}")
                    finally:
                        cur.close()
                        conn.close()
                else:
                    st.warning("Case title is required")
    
    # Evidence page
    elif page == "Evidence":
        st.title("Evidence Management")
        
        tab1, tab2, tab3 = st.tabs(["View Evidence", "Add Evidence", "Search Evidence"])
        
        with tab1:
            st.subheader("All Evidence")
            conn = get_connection()
            evidence_df = pd.read_sql("""
                SELECT e.EvidenceID, c.CaseTitle, e.Description, e.DateCollected, e.Status, 
                       et.TypeName as EvidenceType, 
                       CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                FROM Evidence e
                JOIN Case c ON e.CaseID = c.CaseID
                JOIN EvidenceType et ON e.EvidenceTypeID = et.EvidenceTypeID
                JOIN "User" u ON e.CollectedBy = u.UserID
                ORDER BY e.EvidenceID
            """, conn)
            st.dataframe(evidence_df)
            
            # Evidence details
            st.subheader("Evidence Details")
            evidence_id = st.number_input("Enter Evidence ID", min_value=1, step=1)
            if st.button("View Evidence Details"):
                evidence_df = pd.read_sql(f"""
                    SELECT e.*, c.CaseTitle, et.TypeName as EvidenceType, 
                           CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                    FROM Evidence e
                    JOIN Case c ON e.CaseID = c.CaseID
                    JOIN EvidenceType et ON e.EvidenceTypeID = et.EvidenceTypeID
                    JOIN "User" u ON e.CollectedBy = u.UserID
                    WHERE e.EvidenceID = {evidence_id}
                """, conn)
                
                if not evidence_df.empty:
                    st.write(evidence_df)
                    
                    # Show chain of custody for this evidence
                    custody_df = pd.read_sql(f"""
                        SELECT coc.CustodyID, coc.ActionType, coc.Timestamp, coc.Notes,
                               CONCAT(u.FirstName, ' ', u.LastName) as Custodian,
                               CONCAT(p.FirstName, ' ', p.LastName) as PreviousCustodian
                        FROM ChainOfCustody coc
                        JOIN "User" u ON coc.UserID = u.UserID
                        LEFT JOIN "User" p ON coc.PreviousCustodianID = p.UserID
                        WHERE coc.EvidenceID = {evidence_id}
                        ORDER BY coc.Timestamp
                    """, conn)
                    
                    st.subheader("Chain of Custody")
                    if not custody_df.empty:
                        st.dataframe(custody_df)
                    else:
                        st.info("No chain of custody records found")
                    
                    # Show tags for this evidence
                    tags_df = pd.read_sql(f"""
                        SELECT t.TagName, t.Description
                        FROM EvidenceTag et
                        JOIN Tag t ON et.TagID = t.TagID
                        WHERE et.EvidenceID = {evidence_id}
                    """, conn)
                    
                    st.subheader("Tags")
                    if not tags_df.empty:
                        st.dataframe(tags_df)
                    else:
                        st.info("No tags found")
                else:
                    st.error("Evidence not found")
            
            conn.close()
        
        with tab2:
            st.subheader("Add New Evidence")
            conn = get_connection()
            
            # Get cases for dropdown
            cases_df = pd.read_sql("SELECT CaseID, CaseTitle FROM Case WHERE Status != 'Closed' ORDER BY CaseID", conn)
            case_options = {row['CaseTitle']: row['CaseID'] for _, row in cases_df.iterrows()}
            
            # Get evidence types for dropdown
            types_df = pd.read_sql("SELECT EvidenceTypeID, TypeName FROM EvidenceType ORDER BY TypeName", conn)
            type_options = {row['TypeName']: row['EvidenceTypeID'] for _, row in types_df.iterrows()}
            
            # Form fields
            selected_case = st.selectbox("Select Case", list(case_options.keys()))
            selected_type = st.selectbox("Evidence Type", list(type_options.keys()))
            description = st.text_area("Description")
            date_collected = st.date_input("Date Collected")
            location = st.text_input("Location Collected")
            status = st.selectbox("Status", ["Active", "Archived", "Destroyed", "Released"])
            
            # File upload
            uploaded_file = st.file_uploader("Upload Evidence File", type=["jpg", "jpeg", "png", "pdf", "txt", "zip", "mp4"])
            
            if st.button("Add Evidence"):
                if selected_case and selected_type and description:
                    cur = conn.cursor()
                    try:
                        file_hash = None
                        file_size = None
                        file_format = None
                        
                        if uploaded_file is not None:
                            file_bytes = uploaded_file.getvalue()
                            file_hash = calculate_file_hash(file_bytes)
                            file_size = len(file_bytes)
                            file_format = uploaded_file.name.split('.')[-1].upper()
                        
                        # Insert evidence
                        cur.execute(
                            """INSERT INTO Evidence 
                               (CaseID, EvidenceTypeID, Description, DateCollected, LocationCollected, 
                                CollectedBy, FileHash, FileSize, FileFormat, Status) 
                               VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) RETURNING EvidenceID""",
                            (case_options[selected_case], type_options[selected_type], description, 
                             date_collected, location, st.session_state.user_id, 
                             file_hash, file_size, file_format, status)
                        )
                        
                        new_evidence_id = cur.fetchone()[0]
                        
                        # Add initial chain of custody record
                        cur.execute(
                            """INSERT INTO ChainOfCustody 
                               (EvidenceID, UserID, ActionType, Timestamp, Notes, PreviousCustodianID) 
                               VALUES (%s, %s, %s, %s, %s, %s)""",
                            (new_evidence_id, st.session_state.user_id, 'Collected', 
                             datetime.now(), 'Initial collection', None)
                        )
                        
                        conn.commit()
                        st.success(f"Evidence added successfully with ID: {new_evidence_id}")
                    except Exception as e:
                        conn.rollback()
                        st.error(f"Error adding evidence: {e}")
                    finally:
                        cur.close()
                else:
                    st.warning("Please fill in all required fields")
            
            conn.close()
        
        with tab3:
            st.subheader("Search Evidence")
            search_term = st.text_input("Search by description or case title")
            
            if search_term:
                conn = get_connection()
                search_results = pd.read_sql(f"""
                    SELECT e.EvidenceID, c.CaseTitle, e.Description, e.DateCollected, e.Status, 
                           et.TypeName as EvidenceType, 
                           CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                    FROM Evidence e
                    JOIN Case c ON e.CaseID = c.CaseID
                    JOIN EvidenceType et ON e.EvidenceTypeID = et.EvidenceTypeID
                    JOIN "User" u ON e.CollectedBy = u.UserID
                    WHERE e.Description ILIKE '%{search_term}%' OR c.CaseTitle ILIKE '%{search_term}%'
                    ORDER BY e.EvidenceID
                """, conn)
                
                if not search_results.empty:
                    st.dataframe(search_results)
                else:
                    st.info("No results found")
                
                conn.close()
    
    # Users page
    elif page == "Users":
        st.title("User Management")
        
        # Check if admin
        if st.session_state.role != 'Administrator':
            st.warning("You don't have permission to access this page")
            return
        
        tab1, tab2 = st.tabs(["View Users", "Add User"])
        
        with tab1:
            st.subheader("All Users")
            conn = get_connection()
            users_df = pd.read_sql("""
                SELECT u.UserID, u.Username, u.FirstName, u.LastName, u.BadgeNumber, 
                       u.Email, u.Phone, r.RoleName, d.DepartmentName, u.Status
                FROM "User" u
                JOIN Role r ON u.RoleID = r.RoleID
                JOIN Department d ON u.DepartmentID = d.DepartmentID
                ORDER BY u.UserID
            """, conn)
            st.dataframe(users_df)
            
            # User details and edit
            st.subheader("Edit User")
            user_id = st.number_input("Enter User ID to edit", min_value=1, step=1)
            
            if st.button("Load User"):
                user_df = pd.read_sql(f'SELECT * FROM "User" WHERE UserID = {user_id}', conn)
                if not user_df.empty:
                    st.session_state.edit_user = user_df.iloc[0].to_dict()
                    st.success("User loaded. Edit below.")
                else:
                    st.error("User not found")
            
            if 'edit_user' in st.session_state:
                user = st.session_state.edit_user
                
                # Get roles and departments for dropdowns
                roles_df = pd.read_sql("SELECT RoleID, RoleName FROM Role ORDER BY RoleName", conn)
                role_options = {row['RoleName']: row['RoleID'] for _, row in roles_df.iterrows()}
                
                depts_df = pd.read_sql("SELECT DepartmentID, DepartmentName FROM Department ORDER BY DepartmentName", conn)
                dept_options = {row['DepartmentName']: row['DepartmentID'] for _, row in depts_df.iterrows()}
                
                # Get current role and department names
                cur_role = pd.read_sql(f"SELECT RoleName FROM Role WHERE RoleID = {user['RoleID']}", conn).iloc[0]['RoleName']
                cur_dept = pd.read_sql(f"SELECT DepartmentName FROM Department WHERE DepartmentID = {user['DepartmentID']}", conn).iloc[0]['DepartmentName']
                
                # Edit form
                first_name = st.text_input("First Name", user['FirstName'])
                last_name = st.text_input("Last Name", user['LastName'])
                badge = st.text_input("Badge Number", user['BadgeNumber'])
                email = st.text_input("Email", user['Email'])
                phone = st.text_input("Phone", user['Phone'])
                selected_role = st.selectbox("Role", list(role_options.keys()), index=list(role_options.keys()).index(cur_role))
                selected_dept = st.selectbox("Department", list(dept_options.keys()), index=list(dept_options.keys()).index(cur_dept))
                status = st.selectbox("Status", ["Active", "Inactive", "Suspended"], index=["Active", "Inactive", "Suspended"].index(user['Status']))
                
                if st.button("Update User"):
                    cur = conn.cursor()
                    try:
                        cur.execute(
                            """UPDATE "User" SET 
                               FirstName = %s, LastName = %s, BadgeNumber = %s, 
                               Email = %s, Phone = %s, RoleID = %s, 
                               DepartmentID = %s, Status = %s
                               WHERE UserID = %s""",
                            (first_name, last_name, badge, email, phone, 
                             role_options[selected_role], dept_options[selected_dept], 
                             status, user_id)
                        )
                        conn.commit()
                        st.success("User updated successfully")
                        # Clear the session state
                        del st.session_state.edit_user
                    except Exception as e:
                        conn.rollback()
                        st.error(f"Error updating user: {e}")
                    finally:
                        cur.close()
            
            conn.close()
        
        with tab2:
            st.subheader("Add New User")
            conn = get_connection()
            
            # Get roles and departments for dropdowns
            roles_df = pd.read_sql("SELECT RoleID, RoleName FROM Role ORDER BY RoleName", conn)
            role_options = {row['RoleName']: row['RoleID'] for _, row in roles_df.iterrows()}
            
            depts_df = pd.read_sql("SELECT DepartmentID, DepartmentName FROM Department ORDER BY DepartmentName", conn)
            dept_options = {row['DepartmentName']: row['DepartmentID'] for _, row in depts_df.iterrows()}
            
            # Form fields
            username = st.text_input("Username")
            password = st.text_input("Password", type="password")
            confirm_password = st.text_input("Confirm Password", type="password")
            first_name = st.text_input("First Name")
            last_name = st.text_input("Last Name")
            badge = st.text_input("Badge Number")
            email = st.text_input("Email")
            phone = st.text_input("Phone")
            selected_role = st.selectbox("Role", list(role_options.keys()))
            selected_dept = st.selectbox("Department", list(dept_options.keys()))
            
            if st.button("Add User"):
                if username and password and first_name and last_name and email:
                    if password != confirm_password:
                        st.error("Passwords do not match")
                    else:
                        cur = conn.cursor()
                        try:
                            # Hash the password
                            hashed_password = hashlib.sha256(password.encode()).hexdigest()
                            
                            cur.execute(
                                """INSERT INTO "User" 
                                   (Username, PasswordHash, FirstName, LastName, BadgeNumber, 
                                    Email, Phone, RoleID, DepartmentID, Status) 
                                   VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) RETURNING UserID""",
                                (username, hashed_password, first_name, last_name, badge, 
                                 email, phone, role_options[selected_role], 
                                 dept_options[selected_dept], 'Active')
                            )
                            
                            new_user_id = cur.fetchone()[0]
                            conn.commit()
                            st.success(f"User added successfully with ID: {new_user_id}")
                        except Exception as e:
                            conn.rollback()
                            st.error(f"Error adding user: {e}")
                        finally:
                            cur.close()
                else:
                    st.warning("Please fill in all required fields")
            
            conn.close()
    
    # Reports page
    elif page == "Reports":
        st.title("Reports")
        
        report_type = st.selectbox(
            "Select Report Type", 
            ["Evidence by Case", "Evidence by Type", "User Activity", "Chain of Custody Timeline"]
        )
        
        conn = get_connection()
        
        if report_type == "Evidence by Case":
            st.subheader("Evidence by Case")
            
            cases_df = pd.read_sql("SELECT CaseID, CaseTitle FROM Case ORDER BY CaseTitle", conn)
            case_options = {row['CaseTitle']: row['CaseID'] for _, row in cases_df.iterrows()}
            
            selected_case = st.selectbox("Select Case", list(case_options.keys()))
            
            if st.button("Generate Report"):
                report_df = pd.read_sql(f"""
                    SELECT e.EvidenceID, e.Description, e.DateCollected, e.Status, 
                           et.TypeName as EvidenceType, 
                           CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                    FROM Evidence e
                    JOIN EvidenceType et ON e.EvidenceTypeID = et.EvidenceTypeID
                    JOIN "User" u ON e.CollectedBy = u.UserID
                    WHERE e.CaseID = {case_options[selected_case]}
                    ORDER BY e.DateCollected
                """, conn)
                
                st.write(f"### Evidence for Case: {selected_case}")
                if not report_df.empty:
                    st.dataframe(report_df)
                    
                    # Create a bar chart of evidence types
                    type_counts = report_df['EvidenceType'].value_counts().reset_index()
                    type_counts.columns = ['Evidence Type', 'Count']
                    
                    st.write("### Evidence Types Distribution")
                    st.bar_chart(type_counts.set_index('Evidence Type'))
                else:
                    st.info("No evidence found for this case")
        
        elif report_type == "Evidence by Type":
            st.subheader("Evidence by Type")
            
            types_df = pd.read_sql("SELECT EvidenceTypeID, TypeName FROM EvidenceType ORDER BY TypeName", conn)
            type_options = {row['TypeName']: row['EvidenceTypeID'] for _, row in types_df.iterrows()}
            
            selected_type = st.selectbox("Select Evidence Type", list(type_options.keys()))
            
            if st.button("Generate Report"):
                report_df = pd.read_sql(f"""
                    SELECT e.EvidenceID, c.CaseTitle, e.Description, e.DateCollected, e.Status, 
                           CONCAT(u.FirstName, ' ', u.LastName) as CollectedBy
                    FROM Evidence e
                    JOIN Case c ON e.CaseID = c.CaseID
                    JOIN "User" u ON e.CollectedBy = u.UserID
                    WHERE e.EvidenceTypeID = {type_options[selected_type]}
                    ORDER BY e.DateCollected
                """, conn)
                
                st.write(f"### Evidence of Type: {selected_type}")
                if not report_df.empty:
                    st.dataframe(report_df)
                    
                    # Create a bar chart of cases
                    case_counts = report_df['CaseTitle'].value_counts().reset_index()
                    case_counts.columns = ['Case', 'Count']
                    
                    st.write("### Cases Distribution")
                    st.bar_chart(case_counts.set_index('Case'))
                else:
                    st.info("No evidence found for this type")
        
        elif report_type == "User Activity":
            st.subheader("User Activity Report")
            
            users_df = pd.read_sql('SELECT UserID, CONCAT(FirstName, \' \', LastName) as Name FROM "User" ORDER BY Name', conn)
            user_options = {row['Name']: row['UserID'] for _, row in users_df.iterrows()}
            
            selected_user = st.selectbox("Select User", list(user_options.keys()))
            date_range = st.date_input("Select Date Range", value=[datetime.now() - pd.Timedelta(days=30), datetime.now()])
            
            if st.button("Generate Report"):
                start_date, end_date = date_range
                end_date = end_date + pd.Timedelta(days=1)  # Include the end date
                
                report_df = pd.read_sql(f"""
                    SELECT al.LogID, al.AccessTimestamp, al.ActionType, al.Notes,
                           e.Description as Evidence, c.CaseTitle as Case
                    FROM AccessLog al
                    JOIN Evidence e ON al.EvidenceID = e.EvidenceID
                    JOIN Case c ON e.CaseID = c.CaseID
                    WHERE al.UserID = {user_options[selected_user]}
                    AND al.AccessTimestamp BETWEEN '{start_date}' AND '{end_date}'
                    ORDER BY al.AccessTimestamp DESC
                """, conn)
                
                st.write(f"### Activity for User: {selected_user}")
                if not report_df.empty:
                    st.dataframe(report_df)
                    
                    # Create a bar chart of action types
                    action_counts = report_df['ActionType'].value_counts().reset_index()
                    action_counts.columns = ['Action Type', 'Count']
                    
                    st.write("### Action Types Distribution")
                    st.bar_chart(action_counts.set_index('Action Type'))
                else:
                    st.info("No activity found for this user in the selected date range")
        
        elif report_type == "Chain of Custody Timeline":
            st.subheader("Chain of Custody Timeline")
            
            evidence_df = pd.read_sql("""
                SELECT e.EvidenceID, CONCAT(e.EvidenceID, ': ', e.Description) as EvidenceDesc
                FROM Evidence e
                ORDER BY e.EvidenceID
            """, conn)
            evidence_options = {row['EvidenceDesc']: row['EvidenceID'] for _, row in evidence_df.iterrows()}
            
            selected_evidence = st.selectbox("Select Evidence", list(evidence_options.keys()))
            
            if st.button("Generate Report"):
                report_df = pd.read_sql(f"""
                    SELECT coc.CustodyID, coc.ActionType, coc.Timestamp, coc.Notes,
                           CONCAT(u.FirstName, ' ', u.LastName) as Custodian,
                           CONCAT(p.FirstName, ' ', p.LastName) as PreviousCustodian
                    FROM ChainOfCustody coc
                    JOIN "User" u ON coc.UserID = u.UserID
                    LEFT JOIN "User" p ON coc.PreviousCustodianID = p.UserID
                    WHERE coc.EvidenceID = {evidence_options[selected_evidence]}
                    ORDER BY coc.Timestamp
                """, conn)
                
                st.write(f"### Chain of Custody for Evidence: {selected_evidence}")
                if not report_df.empty:
                    st.dataframe(report_df)
                    
                    # Create a timeline visualization
                    st.write("### Timeline Visualization")
                    
                    for i, row in report_df.iterrows():
                        col1, col2 = st.columns([1, 3])
                        with col1:
                            st.write(f"**{row['Timestamp']}**")
                        with col2:
                            st.write(f"**{row['ActionType']}** by {row['Custodian']}")
                            if row['PreviousCustodian']:
                                st.write(f"From: {row['PreviousCustodian']}")
                            if row['Notes']:
                                st.write(f"Notes: {row['Notes']}")
                        st.markdown("---")
                else:
                    st.info("No chain of custody records found for this evidence")
        
        conn.close()
    
    # Chain of Custody page
    elif page == "Chain of Custody":
        st.title("Chain of Custody Management")
        
        tab1, tab2 = st.tabs(["View Chain of Custody", "Add Custody Record"])
        
        with tab1:
            st.subheader("Chain of Custody Records")
            conn = get_connection()
            
            evidence_df = pd.read_sql("""
                SELECT e.EvidenceID, CONCAT(e.EvidenceID, ': ', e.Description) as EvidenceDesc
                FROM Evidence e
                ORDER BY e.EvidenceID
            """, conn)
            evidence_options = {row['EvidenceDesc']: row['EvidenceID'] for _, row in evidence_df.iterrows()}
            
            selected_evidence = st.selectbox("Select Evidence", list(evidence_options.keys()))
            
            if st.button("View Chain of Custody"):
                custody_df = pd.read_sql(f"""
                    SELECT coc.CustodyID, coc.ActionType, coc.Timestamp, coc.Notes,
                           CONCAT(u.FirstName, ' ', u.LastName) as Custodian,
                           CONCAT(p.FirstName, ' ', p.LastName) as PreviousCustodian
                    FROM ChainOfCustody coc
                    JOIN "User" u ON coc.UserID = u.UserID
                    LEFT JOIN "User" p ON coc.PreviousCustodianID = p.UserID
                    WHERE coc.EvidenceID = {evidence_options[selected_evidence]}
                    ORDER BY coc.Timestamp
                """, conn)
                
                if not custody_df.empty:
                    st.dataframe(custody_df)
                else:
                    st.info("No chain of custody records found for this evidence")
            
            conn.close()
        
        with tab2:
            st.subheader("Add Custody Record")
            conn = get_connection()
            
            # Get evidence for dropdown
            evidence_df = pd.read_sql("""
                SELECT e.EvidenceID, CONCAT(e.EvidenceID, ': ', e.Description) as EvidenceDesc
                FROM Evidence e
                WHERE e.Status = 'Active'
                ORDER BY e.EvidenceID
            """, conn)
            evidence_options = {row['EvidenceDesc']: row['EvidenceID'] for _, row in evidence_df.iterrows()}
            
            # Get users for dropdown
            users_df = pd.read_sql('SELECT UserID, CONCAT(FirstName, \' \', LastName) as Name FROM "User" WHERE Status = \'Active\' ORDER BY Name', conn)
            user_options = {row['Name']: row['UserID'] for _, row in users_df.iterrows()}
            
            # Form fields
            selected_evidence = st.selectbox("Select Evidence", list(evidence_options.keys()), key="add_custody_evidence")
            action_type = st.selectbox("Action Type", ["Collected", "Transferred", "Analyzed", "Stored", "Retrieved", "Returned", "Destroyed", "Archived"])
            notes = st.text_area("Notes")
            
            # Get the last custodian
            if selected_evidence:
                last_custody = pd.read_sql(f"""
                    SELECT coc.UserID, CONCAT(u.FirstName, ' ', u.LastName) as Name
                    FROM ChainOfCustody coc
                    JOIN "User" u ON coc.UserID = u.UserID
                    WHERE coc.EvidenceID = {evidence_options[selected_evidence]}
                    ORDER BY coc.Timestamp DESC
                    LIMIT 1
                """, conn)
                
                if not last_custody.empty:
                    last_custodian_id = last_custody.iloc[0]['UserID']
                    last_custodian_name = last_custody.iloc[0]['Name']
                    st.write(f"Current custodian: **{last_custodian_name}**")
                else:
                    last_custodian_id = None
                    st.write("No previous custodian")
            
            if st.button("Add Custody Record"):
                if selected_evidence and action_type:
                    cur = conn.cursor()
                    try:
                        cur.execute(
                            """INSERT INTO ChainOfCustody 
                               (EvidenceID, UserID, ActionType, Timestamp, Notes, PreviousCustodianID) 
                               VALUES (%s, %s, %s, %s, %s, %s) RETURNING CustodyID""",
                            (evidence_options[selected_evidence], st.session_state.user_id, 
                             action_type, datetime.now(), notes, 
                             last_custodian_id if 'last_custodian_id' in locals() else None)
                        )
                        
                        new_custody_id = cur.fetchone()[0]
                        
                        # Log the action
                        cur.execute(
                            """INSERT INTO AccessLog 
                               (UserID, EvidenceID, AccessTimestamp, ActionType, IPAddress, Notes) 
                               VALUES (%s, %s, %s, %s, %s, %s)""",
                            (st.session_state.user_id, evidence_options[selected_evidence], 
                             datetime.now(), f"Chain of Custody: {action_type}", 
                             "127.0.0.1", f"Added custody record #{new_custody_id}")
                        )
                        
                        conn.commit()
                        st.success(f"Custody record added successfully with ID: {new_custody_id}")
                    except Exception as e:
                        conn.rollback()
                        st.error(f"Error adding custody record: {e}")
                    finally:
                        cur.close()
                else:
                    st.warning("Please fill in all required fields")
            
            conn.close()

if __name__ == "__main__":
    main()


