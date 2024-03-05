
#include <iostream>
#include<algorithm>   // for str
#include<iomanip>     // for setw

using namespace std;
char ch;

//using the concept of double linear linked list
struct contact{
	string first_name,last_name,address,mob_num,email;
	contact* next,*prev;
}*pfirst=NULL,*plast=NULL,*pthis,*pnew,*ptemp;

void create_user();
void search_user();
void delete_user();
void all_contact();

void us_email() {
    bool valid = false;
    do {
        cin>>pnew->email;
        size_t position = pnew->email.find("@");
        if (position != std::string::npos && pnew->email.substr(position + 1) == "gmail.com") {
            valid = true;
        } else {
            cout << "\n\t\tInvalid Email. Please enter a valid Gmail address.\n\n\t\t";
        }
    } while (!valid);
}

bool isNumeric(const string &str) {
    return !str.empty() && all_of(str.begin(), str.end(), ::isdigit);
}

void us_mob() {
    bool validInput = false;
    while (!validInput){
        cin >> pnew->mob_num;
        if (pnew->mob_num.length() != 10 || !isNumeric(pnew->mob_num)) {
            cout << "\n\t\tInvalid mobile number. Please enter a 10-digit numeric mobile number.\n\t\t";
        } else {
            validInput = true;
        }
    }
    ptemp = pfirst;
    while (ptemp != NULL){
        if (ptemp->mob_num == pnew->mob_num) {
            cout << "\n\t\tNumber already exists. Please enter a different number:\n\t\t";
            validInput = false; // Set flag to re-prompt for input
            cin >> pnew->mob_num; // Receiving input again
            ptemp = pfirst; // Resetting pointer to check duplicates
        }
        else {
            ptemp = ptemp->next; // Move to the next node
        }
    }
}

void front_page(){
    cout<<"\n\n\t\t"<<char(201);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(187)<<endl;
    for(int i=0;i<1;i++){
        cout<<"\t\t"<<char(186)<<"\t\t\t\t\t\t\t\t\t\t "<<char(186)<<endl;
    }
    cout<<"\t\t"<<char(186)<<"\t\t\t        Phone Directory                 \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                        \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t      Select Task                       \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                        \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        [1] Create new contact          \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                        \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        [2] Search Contact              \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                        \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        [3] All Contacts                \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                        \t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        [4] Delete Contact              \t\t "<<char(186)<<endl;

    for(int i=0;i<11;i++)
    {
        cout<<"\t\t"<<char(186)<<"\t\t\t\t\t\t\t\t\t\t "<<char(186)<<endl;
    }
    cout<<"\t\t"<<char(200);
    for(int i=0;i<80;i++)
    {
        cout<<char(205);
    }
    cout<<char(188)<<endl<<endl;
    cout<<char(205);
    cin>>ch;
    switch(ch){
        case '1':{
            create_user();
            break;
        }
        case '2':{
            search_user();
            break;
        }
        case '3':{
            all_contact();
            break;
        }
        case '4':{
            delete_user();
        }
        default:{
            cout<<"\n\n\t\tInvalid Input. Try Again ";
            cout<<"\n\t\t( Press any key to continue )";
            cin>>ch;
            front_page();
        }
    }
}

void new_contact(){
    cout<<"\n\n\t\t";
    for(int i=0;i<90;i++)
    {
        cout<<char(205);
    }
    cout<<"\t\t\t\t\t\t\t                 New Contact:               ";
    cout<<"\n\t\t";
    for(int i=0;i<90;i++)
    {
        cout<<char(205);
    }
	pnew=new contact;//pnew=(struct contact*)malloc(sizeof(struct contact));
	pnew->next=NULL;
	pnew->prev=NULL;
	cout<<"\n\t\t Enter first name : ";
	cin>>pnew->first_name;
	cout<<"\n\t\t Enter last name : ";
	cin>>pnew->last_name;
	cout<<"\n\t\t Mobile number : ";
	us_mob();
	cout<<"\n\t\t Email address : ";
	us_email();
	cout<<"\n\t\t Address : ";
	cin>>pnew->address;
	// Add the new contact to the linked list
    if (pfirst == NULL) {
        pfirst = pnew;
        plast = pnew;
        pfirst->prev = NULL;
        pfirst->next = NULL;
        plast->prev = NULL;
        plast->next = NULL;
    }
    else {
        plast->next = pnew;
        pnew->prev = plast;
        plast = pnew;
        plast->next=NULL;
    }
    cout<<"\n\t\t Contact created Successfully. ";
}

void create_user(){
    new_contact();
    front_page();
}

void search_user(){
    int number=0;
    cout<<"\n\n\t\t"<<char(201);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(187)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        Search Contact                \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(204);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(185)<<endl;
    // if no contacts exist
    if(pfirst==NULL){
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t    No Contacts saved.                \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t  ( Press any key to continue )       \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(200);
        for(int i=0;i<80;i++){
            cout<<char(205);
        }
        cout<<char(188)<<endl;
        cin>>ch;
        front_page();
    }
    // if contact exist
    cout<<"\t\t"<<char(186)<<"\t\t\t  Search by:                          \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t  [1] Name                            \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t  [2] Mobile Number                   \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(200);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(188)<<endl;
    cout<<"\t\t\t\t\t Enter your choice: ";
    char searchChoice;
    cin >> searchChoice;

    if (searchChoice == '1') {
        cout<<"\t\t\t\t\t Enter first name to search: ";
        string searchName;
        cin>>searchName;
        ptemp = pfirst;
        while (ptemp != NULL) {
            if (ptemp->first_name == searchName) {
                number++;
            }
            ptemp = ptemp->next;
        }
        if (number == 0) {
            cout << "\n\n\t\t\t\t\t Contact not found.( Press any key to continue )"<<endl;
        }
        else if(number=1){
            ptemp=pfirst;
            while (ptemp != NULL) {
                if (ptemp->first_name == searchName) {
                    // Display the details of the found contact
                    cout <<"\n\n\n";
                    cout << "\t\t\t\t Name     : " << ptemp->first_name<<" "<<ptemp->last_name <<endl;
                    cout << "\t\t\t\t Mobile   : " << ptemp->mob_num <<endl;
                    cout << "\t\t\t\t Email    : " << ptemp->email <<endl;
                    cout << "\t\t\t\t Address  : " << ptemp->address <<endl;
                }
                ptemp = ptemp->next;
            }
        }
        //if(number>=2){
        else{
            ptemp=pfirst;
            while (ptemp != NULL) {
                if (ptemp->first_name == searchName) {
                    // Display the details of the found contact
                    cout <<"\n\n\n";
                    cout << "\t\t\t\t Name     : " << ptemp->first_name<<" "<<ptemp->last_name <<endl;
                    cout << "\t\t\t\t Mobile   : " << ptemp->mob_num <<endl;
                }
                ptemp = ptemp->next;
            }
        }

    }
    else if (searchChoice == '2') {
        cout<<"\t\t\t\t\t Enter the mobile number to search: ";
        string searchmobile;
        cin>>searchmobile;
        ptemp = pfirst;
        while (ptemp != NULL) {
            if (ptemp->mob_num == searchmobile) {
                // Display the details of the found contact
                cout <<"\n\n\n";
                cout << "\t\t\t\t Name     : " << ptemp->first_name<<" "<<ptemp->last_name <<endl;
                cout << "\t\t\t\t Mobile   : " << ptemp->mob_num <<endl;
                cout << "\t\t\t\t Email    : " << ptemp->email <<endl;
                cout << "\t\t\t\t Address  : " << ptemp->address <<endl;
                break;
            }
            ptemp = ptemp->next;
        }

        if (ptemp == NULL) {
            cout << "\t\t\t\t\t Contact not found.\t\t\t\t "<<endl;
        }
    }
    else {
        cout << "\t\t\t\t\t Invalid choice."<<endl;
    }
    cout<<"\t\t\t\t ( Press any key to continue ) "<<endl;
    cin>>ch;
    front_page();
}

void delete_user(){
    cout<<"\n\n\t\t"<<char(201);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(187)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t        Delete Contact                \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(204);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(185)<<endl;
    if(pfirst==NULL){
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t    No Contacts saved.                \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t  ( Press any key to continue )       \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(200);
        for(int i=0;i<80;i++){
            cout<<char(205);
        }
        cout<<char(188)<<endl;
        cin>>ch;
        front_page();
    }
    else{
        cout<<"\n\n\t\t\t\t\t Enter the first name to delete: ";
        string first_deletename,last_deletename;
        cin>>first_deletename;
        cout<<"\n\t\t\t\t\t Enter the last name to delete: ";
        cin>>last_deletename;
        pthis = pfirst;
        ptemp=NULL;
        int same_contact=0;
        contact* contacttodelete;
        while (pthis != NULL) {
            if (pthis->first_name == first_deletename&&pthis->last_name == last_deletename ) {
                same_contact++;
                contacttodelete=pthis;
            }
            pthis=pthis->next;
        }
        if(same_contact == 0){
            cout<<"\n\t\t\t\t\t No Contacts found.";
            cout<<"\n\n\t\t\t\t\t ( Press any key to continue )";
            cin>>ch;
            front_page();
        }
        else if(same_contact==1){
            if(contacttodelete==pfirst){
                pfirst=pfirst->next;
                if(pfirst!=NULL){
                    pfirst->prev=NULL;
                }
                free(contacttodelete);
            }
            else if(contacttodelete==plast){
                plast=plast->prev;
                if(plast!=NULL){
                    plast->next=NULL;
                }
                free(contacttodelete);
            }
            else{
                // It's an intermediate node
            contacttodelete->prev->next = contacttodelete->next;
            contacttodelete->next->prev = contacttodelete->prev;
            free(contacttodelete);
            }
        }
        if(same_contact>1){
            cout<<"\n\t\t More than one contacts are found with same name ";
            cout<<"\n\t\t Please enter mobile number ";
            string num;
            cin>>num;
            pthis=pfirst;
            bool gotit=false;
            while(pthis!=NULL){
                if(pthis->mob_num==num){
                    gotit=true;
                    contacttodelete=pthis;
                }
                pthis=pthis->next;
            }
            if(!gotit){
                cout<<"\n\t\t\t\t\t No contacts found ";
                cout<<"\n\t\t\t\t ( Press any key to continue ) ";
                cin>>ch;
                front_page();
            }
            if(contacttodelete==pfirst){
                pfirst=pfirst->next;
                if(pfirst!=NULL){
                    pfirst->prev=NULL;
                }
                free(contacttodelete);
            }
            else if(contacttodelete==plast){
                plast=plast->prev;
                if(plast!=NULL){
                    plast->next=NULL;
                }
                free(contacttodelete);
            }
            else{
                // It's an intermediate node
                contacttodelete->prev->next = contacttodelete->next;
                contacttodelete->next->prev = contacttodelete->prev;
                free(contacttodelete);
            }
        }
            cout<<"\n\t\t Contact deleted Successfully. ";
            cout<<"\n\t\t ( Press any key to continue )";
            cin>>ch;
            front_page();
    }
}

void all_contact() {
    cout << "\n\n\t\t" << char(201);
    for (int i = 0; i < 80; i++) {
        cout << char(205);
    }
    cout<<char(187)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t           ALL CONTACTS               \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(204);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(185)<<endl;
    if (pfirst == NULL) {
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t    No Contacts saved.                \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t  ( Press any key to continue )       \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
        cout<<"\t\t"<<char(200);
        for(int i=0;i<80;i++){
            cout<<char(205);
        }
        cout<<char(188)<<endl;
        cin>>ch;
        front_page();
    }

    ptemp = pfirst;
    cout <<"\t\t"<<char(186)<< setw(20) << "\tName" <<right<<setw(20)<<" "<< setw(10) << "Mobile Number\t\t\t "<<right<<char(186) << std::endl;
    cout << "\t\t" << char(186) << "\t\t\t                                      \t\t\t " << char(186) << std::endl;
    while (ptemp != NULL) {
        cout << "\t\t"<<char(186)<< setw(20) << ptemp->first_name<<left<<setw(4)<<" "<< setw(15) << ptemp->last_name<<left << setw(10)<<" " << ptemp->mob_num<<right<<"\t\t\t "<<char(186) << std::endl;
        ptemp = ptemp->next;
    }
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t  ( Press any key to continue )       \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(186)<<"\t\t\t                                      \t\t\t "<<char(186)<<endl;
    cout<<"\t\t"<<char(200);
    for(int i=0;i<80;i++){
        cout<<char(205);
    }
    cout<<char(188)<<endl;
    cin>>ch;
    front_page();
}


int main(){
    front_page();
    return 0;
}



