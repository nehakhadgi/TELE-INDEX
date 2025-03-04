"use client"

import { useState } from "react"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { Calendar } from "@/components/ui/calendar"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { format } from "date-fns"
import { CalendarIcon, Trash2, Edit, Search } from "lucide-react"
import { Textarea } from "@/components/ui/textarea"
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog"

// Mock data for demonstration
const initialDepartments = [
  { department_id: 1, name: "Cardiology", location: "Building A, 2nd Floor" },
  { department_id: 2, name: "Neurology", location: "Building B, 1st Floor" },
  { department_id: 3, name: "Pediatrics", location: "Building C, 3rd Floor" },
  { department_id: 4, name: "Orthopedics", location: "Building A, 1st Floor" },
  { department_id: 5, name: "Emergency", location: "Building D, Ground Floor" },
]

const initialDoctors = [
  { doctor_id: 1, name: "Dr. John Smith", specialization: "Cardiologist", contact: "555-1234", department_id: 1 },
  { doctor_id: 2, name: "Dr. Sarah Johnson", specialization: "Neurologist", contact: "555-2345", department_id: 2 },
  { doctor_id: 3, name: "Dr. Michael Brown", specialization: "Pediatrician", contact: "555-3456", department_id: 3 },
  {
    doctor_id: 4,
    name: "Dr. Emily Davis",
    specialization: "Orthopedic Surgeon",
    contact: "555-4567",
    department_id: 4,
  },
  {
    doctor_id: 5,
    name: "Dr. Robert Wilson",
    specialization: "Emergency Physician",
    contact: "555-5678",
    department_id: 5,
  },
]

const initialPatients = [
  {
    patient_id: 1,
    name: "Alice Johnson",
    age: 45,
    gender: "Female",
    address: "123 Main St",
    contact: "555-9876",
    medical_history: "Hypertension",
  },
  {
    patient_id: 2,
    name: "Bob Williams",
    age: 62,
    gender: "Male",
    address: "456 Oak Ave",
    contact: "555-8765",
    medical_history: "Diabetes Type 2",
  },
  {
    patient_id: 3,
    name: "Carol Martinez",
    age: 35,
    gender: "Female",
    address: "789 Pine Rd",
    contact: "555-7654",
    medical_history: "Asthma",
  },
  {
    patient_id: 4,
    name: "David Thompson",
    age: 28,
    gender: "Male",
    address: "101 Elm St",
    contact: "555-6543",
    medical_history: "None",
  },
  {
    patient_id: 5,
    name: "Eva Garcia",
    age: 50,
    gender: "Female",
    address: "202 Maple Dr",
    contact: "555-5432",
    medical_history: "Arthritis",
  },
]

const initialAppointments = [
  { appointment_id: 1, date: new Date("2023-11-15"), time: "09:00", status: "Completed", doctor_id: 1, patient_id: 1 },
  { appointment_id: 2, date: new Date("2023-11-16"), time: "10:30", status: "Scheduled", doctor_id: 2, patient_id: 2 },
  { appointment_id: 3, date: new Date("2023-11-17"), time: "14:00", status: "Scheduled", doctor_id: 3, patient_id: 3 },
  { appointment_id: 4, date: new Date("2023-11-18"), time: "11:15", status: "Cancelled", doctor_id: 4, patient_id: 4 },
  { appointment_id: 5, date: new Date("2023-11-19"), time: "16:30", status: "Scheduled", doctor_id: 5, patient_id: 5 },
]

const initialMedications = [
  { medication_id: 1, name: "Aspirin", description: "Pain reliever", dosage: "100mg" },
  { medication_id: 2, name: "Amoxicillin", description: "Antibiotic", dosage: "500mg" },
  { medication_id: 3, name: "Lisinopril", description: "Blood pressure medication", dosage: "10mg" },
  { medication_id: 4, name: "Metformin", description: "Diabetes medication", dosage: "850mg" },
  { medication_id: 5, name: "Albuterol", description: "Asthma inhaler", dosage: "90mcg/actuation" },
]

const initialPrescriptions = [
  { prescription_id: 1, date: new Date("2023-11-15"), appointment_id: 1 },
  { prescription_id: 2, date: new Date("2023-11-16"), appointment_id: 2 },
]

const initialPrescriptionMedications = [
  { prescription_id: 1, medication_id: 1, instructions: "Take once daily with food" },
  { prescription_id: 1, medication_id: 3, instructions: "Take twice daily" },
  { prescription_id: 2, medication_id: 2, instructions: "Take three times daily for 7 days" },
]

const initialRooms = [
  { room_id: 1, room_number: "101", type: "General Ward", status: "Available" },
  { room_id: 2, room_number: "102", type: "General Ward", status: "Occupied" },
  { room_id: 3, room_number: "201", type: "Private", status: "Available" },
  { room_id: 4, room_number: "301", type: "ICU", status: "Occupied" },
  { room_id: 5, room_number: "302", type: "ICU", status: "Available" },
]

const initialAdmissions = [
  {
    admission_id: 1,
    admission_date: new Date("2023-11-10"),
    discharge_date: new Date("2023-11-15"),
    patient_id: 1,
    room_id: 2,
  },
  { admission_id: 2, admission_date: new Date("2023-11-12"), discharge_date: null, patient_id: 5, room_id: 4 },
]

export default function HospitalManagementSystem() {
  // State for each entity
  const [departments, setDepartments] = useState(initialDepartments)
  const [doctors, setDoctors] = useState(initialDoctors)
  const [patients, setPatients] = useState(initialPatients)
  const [appointments, setAppointments] = useState(initialAppointments)
  const [medications, setMedications] = useState(initialMedications)
  const [prescriptions, setPrescriptions] = useState(initialPrescriptions)
  const [prescriptionMedications, setPrescriptionMedications] = useState(initialPrescriptionMedications)
  const [rooms, setRooms] = useState(initialRooms)
  const [admissions, setAdmissions] = useState(initialAdmissions)

  // State for form inputs
  const [newDepartment, setNewDepartment] = useState({ name: "", location: "" })
  const [newDoctor, setNewDoctor] = useState({ name: "", specialization: "", contact: "", department_id: "" })
  const [newPatient, setNewPatient] = useState({
    name: "",
    age: "",
    gender: "",
    address: "",
    contact: "",
    medical_history: "",
  })
  const [newAppointment, setNewAppointment] = useState({
    date: new Date(),
    time: "",
    status: "Scheduled",
    doctor_id: "",
    patient_id: "",
  })
  const [newMedication, setNewMedication] = useState({ name: "", description: "", dosage: "" })
  const [newPrescription, setNewPrescription] = useState({ date: new Date(), appointment_id: "" })
  const [newPrescriptionMedication, setNewPrescriptionMedication] = useState({
    prescription_id: "",
    medication_id: "",
    instructions: "",
  })
  const [newRoom, setNewRoom] = useState({ room_number: "", type: "", status: "Available" })
  const [newAdmission, setNewAdmission] = useState({
    admission_date: new Date(),
    discharge_date: null,
    patient_id: "",
    room_id: "",
  })

  // State for edit mode
  const [editMode, setEditMode] = useState(false)
  const [editId, setEditId] = useState(null)

  // State for search
  const [searchTerm, setSearchTerm] = useState("")

  // Function to handle adding a new department
  const handleAddDepartment = () => {
    if (newDepartment.name && newDepartment.location) {
      if (editMode) {
        setDepartments(
          departments.map((dept) =>
            dept.department_id === editId
              ? { ...dept, name: newDepartment.name, location: newDepartment.location }
              : dept,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = departments.length > 0 ? Math.max(...departments.map((dept) => dept.department_id)) + 1 : 1
        setDepartments([...departments, { department_id: newId, ...newDepartment }])
      }
      setNewDepartment({ name: "", location: "" })
    }
  }

  // Function to handle adding a new doctor
  const handleAddDoctor = () => {
    if (newDoctor.name && newDoctor.specialization && newDoctor.contact && newDoctor.department_id) {
      if (editMode) {
        setDoctors(
          doctors.map((doc) =>
            doc.doctor_id === editId
              ? {
                  ...doc,
                  name: newDoctor.name,
                  specialization: newDoctor.specialization,
                  contact: newDoctor.contact,
                  department_id: Number.parseInt(newDoctor.department_id),
                }
              : doc,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = doctors.length > 0 ? Math.max(...doctors.map((doc) => doc.doctor_id)) + 1 : 1
        setDoctors([
          ...doctors,
          { doctor_id: newId, ...newDoctor, department_id: Number.parseInt(newDoctor.department_id) },
        ])
      }
      setNewDoctor({ name: "", specialization: "", contact: "", department_id: "" })
    }
  }

  // Function to handle adding a new patient
  const handleAddPatient = () => {
    if (newPatient.name && newPatient.age && newPatient.gender) {
      if (editMode) {
        setPatients(
          patients.map((pat) =>
            pat.patient_id === editId
              ? {
                  ...pat,
                  name: newPatient.name,
                  age: Number.parseInt(newPatient.age),
                  gender: newPatient.gender,
                  address: newPatient.address,
                  contact: newPatient.contact,
                  medical_history: newPatient.medical_history,
                }
              : pat,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = patients.length > 0 ? Math.max(...patients.map((pat) => pat.patient_id)) + 1 : 1
        setPatients([
          ...patients,
          {
            patient_id: newId,
            ...newPatient,
            age: Number.parseInt(newPatient.age),
          },
        ])
      }
      setNewPatient({ name: "", age: "", gender: "", address: "", contact: "", medical_history: "" })
    }
  }

  // Function to handle adding a new appointment
  const handleAddAppointment = () => {
    if (newAppointment.date && newAppointment.time && newAppointment.doctor_id && newAppointment.patient_id) {
      if (editMode) {
        setAppointments(
          appointments.map((apt) =>
            apt.appointment_id === editId
              ? {
                  ...apt,
                  date: newAppointment.date,
                  time: newAppointment.time,
                  status: newAppointment.status,
                  doctor_id: Number.parseInt(newAppointment.doctor_id),
                  patient_id: Number.parseInt(newAppointment.patient_id),
                }
              : apt,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = appointments.length > 0 ? Math.max(...appointments.map((apt) => apt.appointment_id)) + 1 : 1
        setAppointments([
          ...appointments,
          {
            appointment_id: newId,
            ...newAppointment,
            doctor_id: Number.parseInt(newAppointment.doctor_id),
            patient_id: Number.parseInt(newAppointment.patient_id),
          },
        ])
      }
      setNewAppointment({ date: new Date(), time: "", status: "Scheduled", doctor_id: "", patient_id: "" })
    }
  }

  // Function to handle adding a new medication
  const handleAddMedication = () => {
    if (newMedication.name && newMedication.dosage) {
      if (editMode) {
        setMedications(
          medications.map((med) =>
            med.medication_id === editId
              ? {
                  ...med,
                  name: newMedication.name,
                  description: newMedication.description,
                  dosage: newMedication.dosage,
                }
              : med,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = medications.length > 0 ? Math.max(...medications.map((med) => med.medication_id)) + 1 : 1
        setMedications([...medications, { medication_id: newId, ...newMedication }])
      }
      setNewMedication({ name: "", description: "", dosage: "" })
    }
  }

  // Function to handle adding a new prescription
  const handleAddPrescription = () => {
    if (newPrescription.date && newPrescription.appointment_id) {
      if (editMode) {
        setPrescriptions(
          prescriptions.map((pres) =>
            pres.prescription_id === editId
              ? {
                  ...pres,
                  date: newPrescription.date,
                  appointment_id: Number.parseInt(newPrescription.appointment_id),
                }
              : pres,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = prescriptions.length > 0 ? Math.max(...prescriptions.map((pres) => pres.prescription_id)) + 1 : 1
        setPrescriptions([
          ...prescriptions,
          {
            prescription_id: newId,
            ...newPrescription,
            appointment_id: Number.parseInt(newPrescription.appointment_id),
          },
        ])
      }
      setNewPrescription({ date: new Date(), appointment_id: "" })
    }
  }

  // Function to handle adding a new prescription medication
  const handleAddPrescriptionMedication = () => {
    if (
      newPrescriptionMedication.prescription_id &&
      newPrescriptionMedication.medication_id &&
      newPrescriptionMedication.instructions
    ) {
      // Check if this prescription-medication combination already exists
      const exists = prescriptionMedications.some(
        (pm) =>
          pm.prescription_id === Number.parseInt(newPrescriptionMedication.prescription_id) &&
          pm.medication_id === Number.parseInt(newPrescriptionMedication.medication_id),
      )

      if (!exists) {
        setPrescriptionMedications([
          ...prescriptionMedications,
          {
            prescription_id: Number.parseInt(newPrescriptionMedication.prescription_id),
            medication_id: Number.parseInt(newPrescriptionMedication.medication_id),
            instructions: newPrescriptionMedication.instructions,
          },
        ])
        setNewPrescriptionMedication({ prescription_id: "", medication_id: "", instructions: "" })
      }
    }
  }

  // Function to handle adding a new room
  const handleAddRoom = () => {
    if (newRoom.room_number && newRoom.type) {
      if (editMode) {
        setRooms(
          rooms.map((room) =>
            room.room_id === editId
              ? {
                  ...room,
                  room_number: newRoom.room_number,
                  type: newRoom.type,
                  status: newRoom.status,
                }
              : room,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = rooms.length > 0 ? Math.max(...rooms.map((room) => room.room_id)) + 1 : 1
        setRooms([...rooms, { room_id: newId, ...newRoom }])
      }
      setNewRoom({ room_number: "", type: "", status: "Available" })
    }
  }

  // Function to handle adding a new admission
  const handleAddAdmission = () => {
    if (newAdmission.admission_date && newAdmission.patient_id && newAdmission.room_id) {
      if (editMode) {
        setAdmissions(
          admissions.map((adm) =>
            adm.admission_id === editId
              ? {
                  ...adm,
                  admission_date: newAdmission.admission_date,
                  discharge_date: newAdmission.discharge_date,
                  patient_id: Number.parseInt(newAdmission.patient_id),
                  room_id: Number.parseInt(newAdmission.room_id),
                }
              : adm,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = admissions.length > 0 ? Math.max(...admissions.map((adm) => adm.admission_id)) + 1 : 1
        setAdmissions([
          ...admissions,
          {
            admission_id: newId,
            ...newAdmission,
            patient_id: Number.parseInt(newAdmission.patient_id),
            room_id: Number.parseInt(newAdmission.room_id),
          },
        ])

        // Update room status to Occupied
        setRooms(
          rooms.map((room) =>
            room.room_id === Number.parseInt(newAdmission.room_id) ? { ...room, status: "Occupied" } : room,
          ),
        )
      }
      setNewAdmission({ admission_date: new Date(), discharge_date: null, patient_id: "", room_id: "" })
    }
  }

  // Function to handle discharge
  const handleDischarge = (admissionId) => {
    const admission = admissions.find((adm) => adm.admission_id === admissionId)
    if (admission) {
      // Update admission with discharge date
      setAdmissions(
        admissions.map((adm) => (adm.admission_id === admissionId ? { ...adm, discharge_date: new Date() } : adm)),
      )

      // Update room status to Available
      setRooms(rooms.map((room) => (room.room_id === admission.room_id ? { ...room, status: "Available" } : room)))
    }
  }

  // Function to handle edit
  const handleEdit = (id, type) => {
    setEditMode(true)
    setEditId(id)

    switch (type) {
      case "department":
        const dept = departments.find((d) => d.department_id === id)
        setNewDepartment({ name: dept.name, location: dept.location })
        break
      case "doctor":
        const doc = doctors.find((d) => d.doctor_id === id)
        setNewDoctor({
          name: doc.name,
          specialization: doc.specialization,
          contact: doc.contact,
          department_id: doc.department_id.toString(),
        })
        break
      case "patient":
        const pat = patients.find((p) => p.patient_id === id)
        setNewPatient({
          name: pat.name,
          age: pat.age.toString(),
          gender: pat.gender,
          address: pat.address,
          contact: pat.contact,
          medical_history: pat.medical_history,
        })
        break
      case "appointment":
        const apt = appointments.find((a) => a.appointment_id === id)
        setNewAppointment({
          date: apt.date,
          time: apt.time,
          status: apt.status,
          doctor_id: apt.doctor_id.toString(),
          patient_id: apt.patient_id.toString(),
        })
        break
      case "medication":
        const med = medications.find((m) => m.medication_id === id)
        setNewMedication({
          name: med.name,
          description: med.description,
          dosage: med.dosage,
        })
        break
      case "prescription":
        const pres = prescriptions.find((p) => p.prescription_id === id)
        setNewPrescription({
          date: pres.date,
          appointment_id: pres.appointment_id.toString(),
        })
        break
      case "room":
        const room = rooms.find((r) => r.room_id === id)
        setNewRoom({
          room_number: room.room_number,
          type: room.type,
          status: room.status,
        })
        break
      case "admission":
        const adm = admissions.find((a) => a.admission_id === id)
        setNewAdmission({
          admission_date: adm.admission_date,
          discharge_date: adm.discharge_date,
          patient_id: adm.patient_id.toString(),
          room_id: adm.room_id.toString(),
        })
        break
      default:
        break
    }
  }

  // Function to handle delete
  const handleDelete = (id, type) => {
    switch (type) {
      case "department":
        setDepartments(departments.filter((d) => d.department_id !== id))
        break
      case "doctor":
        setDoctors(doctors.filter((d) => d.doctor_id !== id))
        break
      case "patient":
        setPatients(patients.filter((p) => p.patient_id !== id))
        break
      case "appointment":
        setAppointments(appointments.filter((a) => a.appointment_id !== id))
        break
      case "medication":
        setMedications(medications.filter((m) => m.medication_id !== id))
        break
      case "prescription":
        setPrescriptions(prescriptions.filter((p) => p.prescription_id !== id))
        // Also delete associated prescription medications
        setPrescriptionMedications(prescriptionMedications.filter((pm) => pm.prescription_id !== id))
        break
      case "prescription_medication":
        setPrescriptionMedications(
          prescriptionMedications.filter(
            (pm) => !(pm.prescription_id === id.prescription_id && pm.medication_id === id.medication_id),
          ),
        )
        break
      case "room":
        setRooms(rooms.filter((r) => r.room_id !== id))
        break
      case "admission":
        setAdmissions(admissions.filter((a) => a.admission_id !== id))
        break
      default:
        break
    }
  }

  // Helper function to get department name by ID
  const getDepartmentName = (id) => {
    const department = departments.find((dept) => dept.department_id === id)
    return department ? department.name : "Unknown"
  }

  // Helper function to get doctor name by ID
  const getDoctorName = (id) => {
    const doctor = doctors.find((doc) => doc.doctor_id === id)
    return doctor ? doctor.name : "Unknown"
  }

  // Helper function to get patient name by ID
  const getPatientName = (id) => {
    const patient = patients.find((pat) => pat.patient_id === id)
    return patient ? patient.name : "Unknown"
  }

  // Helper function to get medication name by ID
  const getMedicationName = (id) => {
    const medication = medications.find((med) => med.medication_id === id)
    return medication ? medication.name : "Unknown"
  }

  // Helper function to get room number by ID
  const getRoomNumber = (id) => {
    const room = rooms.find((r) => r.room_id === id)
    return room ? room.room_number : "Unknown"
  }

  // Filter function for search
  const filterBySearchTerm = (item, type) => {
    if (!searchTerm) return true

    const term = searchTerm.toLowerCase()

    switch (type) {
      case "department":
        return item.name.toLowerCase().includes(term) || item.location.toLowerCase().includes(term)
      case "doctor":
        return item.name.toLowerCase().includes(term) || item.specialization.toLowerCase().includes(term)
      case "patient":
        return (
          item.name.toLowerCase().includes(term) ||
          (item.medical_history && item.medical_history.toLowerCase().includes(term))
        )
      case "appointment":
        return (
          getDoctorName(item.doctor_id).toLowerCase().includes(term) ||
          getPatientName(item.patient_id).toLowerCase().includes(term)
        )
      case "medication":
        return item.name.toLowerCase().includes(term) || item.description.toLowerCase().includes(term)
      case "room":
        return item.room_number.toLowerCase().includes(term) || item.type.toLowerCase().includes(term)
      default:
        return true
    }
  }

  return (
    <div className="container mx-auto py-6">
      <h1 className="text-3xl font-bold mb-6 text-center">Hospital Management System</h1>
      
      <div className="mb-4">
        <div className="relative">
          <Search className="absolute left-2.5 top-2.5 h-4 w-4 text-muted-foreground" />
          <Input
            type="search"
            placeholder="Search..."
            className="pl-8"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
      </div>
      
      <Tabs defaultValue="patients">
        <TabsList className="grid grid-cols-4 md:grid-cols-8 mb-4">
          <TabsTrigger value="patients">Patients</TabsTrigger>
          <TabsTrigger value="doctors">Doctors</TabsTrigger>
          <TabsTrigger value="appointments">Appointments</TabsTrigger>
          <TabsTrigger value="departments">Departments</TabsTrigger>
          <TabsTrigger value="medications">Medications</TabsTrigger>
          <TabsTrigger value="prescriptions">Prescriptions</TabsTrigger>
          <TabsTrigger value="rooms">Rooms</TabsTrigger>
          <TabsTrigger value="admissions">Admissions</TabsTrigger>
        </TabsList>
        
        {/* Patients Tab */}
        <TabsContent value="patients">
          <Card>
            <CardHeader>
              <CardTitle>Patients</CardTitle>
              <CardDescription>Manage patient records</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="patient-name">Name</Label>
                    <Input
                      id="patient-name"
                      value={newPatient.name}
                      onChange={(e) => setNewPatient({ ...newPatient, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="patient-age">Age</Label>
                    <Input
                      id="patient-age"
                      type="number"
                      value={newPatient.age}
                      onChange={(e) => setNewPatient({ ...newPatient, age: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="patient-gender">Gender</Label>
                    <Select
                      value={newPatient.gender}
                      onValueChange={(value) => setNewPatient({ ...newPatient, gender: value })}
                    >
                      <SelectTrigger id="patient-gender">
                        <SelectValue placeholder="Select gender" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Male">Male</SelectItem>
                        <SelectItem value="Female">Female</SelectItem>
                        <SelectItem value="Other">Other</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="patient-address">Address</Label>
                    <Input
                      id="patient-address"
                      value={newPatient.address}
                      onChange={(e) => setNewPatient({ ...newPatient, address: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="patient-contact">Contact</Label>
                    <Input
                      id="patient-contact"
                      value={newPatient.contact}
                      onChange={(e) => setNewPatient({ ...newPatient, contact: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="patient-history">Medical History</Label>
                    <Textarea
                      id="patient-history"
                      value={newPatient.medical_history}
                      onChange={(e) => setNewPatient({ ...newPatient, medical_history: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddPatient}>
                  {editMode ? "Update Patient" : "Add Patient"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Age</TableHead>
                      <TableHead>Gender</TableHead>
                      <TableHead>Contact</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {patients.filter(patient => filterBySearchTerm(patient, "patient")).map((patient) => (
                      <TableRow key={patient.patient_id}>
                        <TableCell>{patient.patient_id}</TableCell>
                        <TableCell>{patient.name}</TableCell>
                        <TableCell>{patient.age}</TableCell>
                        <TableCell>{patient.gender}</TableCell>
                        <TableCell>{patient.contact}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(patient.patient_id, "patient")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the patient record.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(patient.patient_id, "patient")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Doctors Tab */}
        <TabsContent value="doctors">
          <Card>
            <CardHeader>
              <CardTitle>Doctors</CardTitle>
              <CardDescription>Manage doctor records</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="doctor-name">Name</Label>
                    <Input
                      id="doctor-name"
                      value={newDoctor.name}
                      onChange={(e) => setNewDoctor({ ...newDoctor, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="doctor-specialization">Specialization</Label>
                    <Input
                      id="doctor-specialization"
                      value={newDoctor.specialization}
                      onChange={(e) => setNewDoctor({ ...newDoctor, specialization: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="doctor-contact">Contact</Label>
                    <Input
                      id="doctor-contact"
                      value={newDoctor.contact}
                      onChange={(e) => setNewDoctor({ ...newDoctor, contact: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="doctor-department">Department</Label>
                    <Select
                      value={newDoctor.department_id}
                      onValueChange={(value) => setNewDoctor({ ...newDoctor, department_id: value })}
                    >
                      <SelectTrigger id="doctor-department">
                        <SelectValue placeholder="Select department" />
                      </SelectTrigger>
                      <SelectContent>
                        {departments.map((dept) => (
                          <SelectItem key={dept.department_id} value={dept.department_id.toString()}>
                            {dept.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddDoctor}>
                  {editMode ? "Update Doctor" : "Add Doctor"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Specialization</TableHead>
                      <TableHead>Contact</TableHead>
                      <TableHead>Department</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {doctors.filter(doctor => filterBySearchTerm(doctor, "doctor")).map((doctor) => (
                      <TableRow key={doctor.doctor_id}>
                        <TableCell>{doctor.doctor_id}</TableCell>
                        <TableCell>{doctor.name}</TableCell>
                        <TableCell>{doctor.specialization}</TableCell>
                        <TableCell>{doctor.contact}</TableCell>
                        <TableCell>{getDepartmentName(doctor.department_id)}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(doctor.doctor_id, "doctor")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the doctor record.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(doctor.doctor_id, "doctor")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Appointments Tab */}
        <TabsContent value="appointments">
          <Card>
            <CardHeader>
              <CardTitle>Appointments</CardTitle>
              <CardDescription>Manage patient appointments</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label>Date</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button
                          variant="outline"
                          className="w-full justify-start text-left font-normal"
                        >
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newAppointment.date ? format(newAppointment.date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newAppointment.date}
                          onSelect={(date) => setNewAppointment({ ...newAppointment, date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="appointment-time">Time</Label>
                    <Input
                      id="appointment-time"
                      type="time"
                      value={newAppointment.time}
                      onChange={(e) => setNewAppointment({ ...newAppointment, time: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="appointment-status">Status</Label>
                    <Select
                      value={newAppointment.status}
                      onValueChange={(value) => setNewAppointment({ ...newAppointment, status: value })}
                    >
                      <SelectTrigger id="appointment-status">
                        <SelectValue placeholder="Select status" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Scheduled">Scheduled</SelectItem>
                        <SelectItem value="Completed">Completed</SelectItem>
                        <SelectItem value="Cancelled">Cancelled</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="appointment-doctor">Doctor</Label>
                    <Select
                      value={newAppointment.doctor_id}
                      onValueChange={(value) => setNewAppointment({ ...newAppointment, doctor_id: value })}
                    >
                      <SelectTrigger id="appointment-doctor">
                        <SelectValue placeholder="Select doctor" />
                      </SelectTrigger>
                      <SelectContent>
                        {doctors.map((doctor) => (
                          <SelectItem key={doctor.doctor_id} value={doctor.doctor_id.toString()}>
                            {doctor.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="appointment-patient">Patient</Label>
                    <Select
                      value={newAppointment.patient_id}
                      onValueChange={(value) => setNewAppointment({ ...newAppointment, patient_id: value })}
                    >
                      <SelectTrigger id="appointment-patient">
                        <SelectValue placeholder="Select patient" />
                      </SelectTrigger>
                      <SelectContent>
                        {patients.map((patient) => (
                          <SelectItem key={patient.patient_id} value={patient.patient_id.toString()}>
                            {patient.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddAppointment}>
                  {editMode ? "Update Appointment" : "Add Appointment"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Time</TableHead>
                      <TableHead>Doctor</TableHead>
                      <TableHead>Patient</TableHead>
                      <TableHead>Status</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {appointments.filter(appointment => filterBySearchTerm(appointment, "appointment")).map((appointment) => (
                      <TableRow key={appointment.appointment_id}>
                        <TableCell>{appointment.appointment_id}</TableCell>
                        <TableCell>{format(appointment.date, "PP")}</TableCell>
                        <TableCell>{appointment.time}</TableCell>
                        <TableCell>{getDoctorName(appointment.doctor_id)}</TableCell>
                        <TableCell>{getPatientName(appointment.patient_id)}</TableCell>
                        <TableCell>{appointment.status}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(appointment.appointment_id, "appointment")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the appointment.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(appointment.appointment_id, "appointment")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Departments Tab */}
        <TabsContent value="departments">
          <Card>
            <CardHeader>
              <CardTitle>Departments</CardTitle>
              <CardDescription>Manage hospital departments</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="department-name">Name</Label>
                    <Input
                      id="department-name"
                      value={newDepartment.name}
                      onChange={(e) => setNewDepartment({ ...newDepartment, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="department-location">Location</Label>
                    <Input
                      id="department-location"
                      value={newDepartment.location}
                      onChange={(e) => setNewDepartment({ ...newDepartment, location: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddDepartment}>
                  {editMode ? "Update Department" : "Add Department"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Location</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {departments.filter(department => filterBySearchTerm(department, "department")).map((department) => (
                      <TableRow key={department.department_id}>
                        <TableCell>{department.department_id}</TableCell>
                        <TableCell>{department.name}</TableCell>
                        <TableCell>{department.location}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(department.department_id, "department")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the department.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(department.department_id, "department")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Medications Tab */}
        <TabsContent value="medications">
          <Card>
            <CardHeader>
              <CardTitle>Medications</CardTitle>
              <CardDescription>Manage medications</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="medication-name">Name</Label>
                    <Input
                      id="medication-name"
                      value={newMedication.name}
                      onChange={(e) => setNewMedication({ ...newMedication, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="medication-description">Description</Label>
                    <Input
                      id="medication-description"
                      value={newMedication.description}
                      onChange={(e) => setNewMedication({ ...newMedication, description: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="medication-dosage">Dosage</Label>
                    <Input
                      id="medication-dosage"
                      value={newMedication.dosage}
                      onChange={(e) => setNewMedication({ ...newMedication, dosage: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddMedication}>
                  {editMode ? "Update Medication" : "Add Medication"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Description</TableHead>
                      <TableHead>Dosage</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {medications.filter(medication => filterBySearchTerm(medication, "medication")).map((medication) => (
                      <TableRow key={medication.medication_id}>
                        <TableCell>{medication.medication_id}</TableCell>
                        <TableCell>{medication.name}</TableCell>
                        <TableCell>{medication.description}</TableCell>
                        <TableCell>{medication.dosage}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(medication.medication_id, "medication")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the medication.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(medication.medication_id, "medication")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Prescriptions Tab */}
        <TabsContent value="prescriptions">
          <Card>
            <CardHeader>
              <CardTitle>Prescriptions</CardTitle>
              <CardDescription>Manage prescriptions and medications</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label>Date</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button
                          variant="outline"
                          className="w-full justify-start text-left font-normal"
                        >
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newPrescription.date ? format(newPrescription.date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newPrescription.date}
                          onSelect={(date) => setNewPrescription({ ...newPrescription, date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="prescription-appointment">Appointment</Label>
                    <Select
                      value={newPrescription.appointment_id}
                      onValueChange={(value) => setNewPrescription({ ...newPrescription, appointment_id: value })}
                    >
                      <SelectTrigger id="prescription-appointment">
                        <SelectValue placeholder="Select appointment" />
                      </SelectTrigger>
                      <SelectContent>
                        {appointments.map((appointment) => (
                          <SelectItem key={appointment.appointment_id} value={appointment.appointment_id.toString()}>
                            {format(appointment.date, "PP")} - {getDoctorName(appointment.doctor_id)} - {getPatientName(appointment.patient_id)}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddPrescription}>
                  {editMode ? "Update Prescription" : "Add Prescription"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Date</TableHead>
                      <TableHead>Appointment</TableHead>
                      <TableHead>Patient</TableHead>
                      <TableHead>Doctor</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {prescriptions.map((prescription) => {\
                      const appointment = appointments.find(a => a.appointment_id === prescription.appointment  => {
                      const appointment = appointments.find(a => a.appointment_id === prescription.appointment_id)
                      const patient = appointment ? patients.find(p => p.patient_id === appointment.patient_id) : null
                      const doctor = appointment ? doctors.find(d => d.doctor_id === appointment.doctor_id) : null
                      
                      return (
                        <TableRow key={prescription.prescription_id}>
                          <TableCell>{prescription.prescription_id}</TableCell>
                          <TableCell>{format(prescription.date, "PP")}</TableCell>
                          <TableCell>{appointment ? format(appointment.date, "PP") + " " + appointment.time : "Unknown"}</TableCell>
                          <TableCell>{patient ? patient.name : "Unknown"}</TableCell>
                          <TableCell>{doctor ? doctor.name : "Unknown"}</TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button
                                variant="outline"
                                size="icon"
                                onClick={() => handleEdit(prescription.prescription_id, "prescription")}
                              >
                                <Edit className="h-4 w-4" />
                              </Button>
                              <AlertDialog>
                                <AlertDialogTrigger asChild>
                                  <Button variant="outline" size="icon" className="text-red-500">
                                    <Trash2 className="h-4 w-4" />
                                  </Button>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                  <AlertDialogHeader>
                                    <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                    <AlertDialogDescription>
                                      This action cannot be undone. This will permanently delete the prescription.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete(prescription.prescription_id, "prescription")}>
                                      Delete
                                    </AlertDialogAction>
                                  </AlertDialogFooter>
                                </AlertDialogContent>
                              </AlertDialog>
                            </div>
                          </TableCell>
                        </TableRow>
                      )
                    })}
                  </TableBody>
                </Table>
              </div>
              
              <div className="mt-6">
                <h3 className="text-lg font-medium mb-4">Add Medications to Prescription</h3>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="pm-prescription">Prescription</Label>
                    <Select
                      value={newPrescriptionMedication.prescription_id}
                      onValueChange={(value) => setNewPrescriptionMedication({ ...newPrescriptionMedication, prescription_id: value })}
                    >
                      <SelectTrigger id="pm-prescription">
                        <SelectValue placeholder="Select prescription" />
                      </SelectTrigger>
                      <SelectContent>
                        {prescriptions.map((prescription) => {
                          const appointment = appointments.find(a => a.appointment_id === prescription.appointment_id)
                          const patient = appointment ? patients.find(p => p.patient_id === appointment.patient_id) : null
                          
                          return (
                            <SelectItem key={prescription.prescription_id} value={prescription.prescription_id.toString()}>
                              {format(prescription.date, "PP")} - {patient ? patient.name : "Unknown"}
                            </SelectItem>
                          )
                        })}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="pm-medication">Medication</Label>
                    <Select
                      value={newPrescriptionMedication.medication_id}
                      onValueChange={(value) => setNewPrescriptionMedication({ ...newPrescriptionMedication, medication_id: value })}
                    >
                      <SelectTrigger id="pm-medication">
                        <SelectValue placeholder="Select medication" />
                      </SelectTrigger>
                      <SelectContent>
                        {medications.map((medication) => (
                          <SelectItem key={medication.medication_id} value={medication.medication_id.toString()}>
                            {medication.name} ({medication.dosage})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="pm-instructions">Instructions</Label>
                    <Input
                      id="pm-instructions"
                      value={newPrescriptionMedication.instructions}
                      onChange={(e) => setNewPrescriptionMedication({ ...newPrescriptionMedication, instructions: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddPrescriptionMedication} className="mt-4">
                  Add Medication to Prescription
                </Button>
                
                <div className="mt-6">
                  <h3 className="text-lg font-medium mb-4">Prescription Medications</h3>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Prescription</TableHead>
                        <TableHead>Patient</TableHead>
                        <TableHead>Medication</TableHead>
                        <TableHead>Instructions</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {prescriptionMedications.map((pm) => {
                        const prescription = prescriptions.find(p => p.prescription_id === pm.prescription_id)
                        const appointment = prescription ? appointments.find(a => a.appointment_id === prescription.appointment_id) : null
                        const patient = appointment ? patients.find(p => p.patient_id === appointment.patient_id) : null
                        
                        return (
                          <TableRow key={`${pm.prescription_id}-${pm.medication_id}`}>
                            <TableCell>{pm.prescription_id}</TableCell>
                            <TableCell>{patient ? patient.name : "Unknown"}</TableCell>
                            <TableCell>{getMedicationName(pm.medication_id)}</TableCell>
                            <TableCell>{pm.instructions}</TableCell>
                            <TableCell>
                              <AlertDialog>
                                <AlertDialogTrigger asChild>
                                  <Button variant="outline" size="icon" className="text-red-500">
                                    <Trash2 className="h-4 w-4" />
                                  </Button>
                                </AlertDialogTrigger>
                                <AlertDialogContent>
                                  <AlertDialogHeader>
                                    <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                    <AlertDialogDescription>
                                      This action cannot be undone. This will permanently delete this medication from the prescription.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete({ prescription_id: pm.prescription_id, medication_id: pm.medication_id }, "prescription_medication")}>
                                      Delete
                                    </AlertDialogAction>
                                  </AlertDialogFooter>
                                </AlertDialogContent>
                              </AlertDialog>
                            </TableCell>
                          </TableRow>
                        )
                      })}
                    </TableBody>
                  </Table>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Rooms Tab */}
        <TabsContent value="rooms">
          <Card>
            <CardHeader>
              <CardTitle>Rooms</CardTitle>
              <CardDescription>Manage hospital rooms</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="room-number">Room Number</Label>
                    <Input
                      id="room-number"
                      value={newRoom.room_number}
                      onChange={(e) => setNewRoom({ ...newRoom, room_number: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="room-type">Type</Label>
                    <Select
                      value={newRoom.type}
                      onValueChange={(value) => setNewRoom({ ...newRoom, type: value })}
                    >
                      <SelectTrigger id="room-type">
                        <SelectValue placeholder="Select type" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="General Ward">General Ward</SelectItem>
                        <SelectItem value="Private">Private</SelectItem>
                        <SelectItem value="ICU">ICU</SelectItem>
                        <SelectItem value="Operating Room">Operating Room</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="room-status">Status</Label>
                    <Select
                      value={newRoom.status}
                      onValueChange={(value) => setNewRoom({ ...newRoom, status: value })}
                    >
                      <SelectTrigger id="room-status">
                        <SelectValue placeholder="Select status" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Available">Available</SelectItem>
                        <SelectItem value="Occupied">Occupied</SelectItem>
                        <SelectItem value="Under Maintenance">Under Maintenance</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddRoom}>
                  {editMode ? "Update Room" : "Add Room"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Room Number</TableHead>
                      <TableHead>Type</TableHead>
                      <TableHead>Status</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {rooms.filter(room => filterBySearchTerm(room, "room")).map((room) => (
                      <TableRow key={room.room_id}>
                        <TableCell>{room.room_id}</TableCell>
                        <TableCell>{room.room_number}</TableCell>
                        <TableCell>{room.type}</TableCell>
                        <TableCell>{room.status}</TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(room.room_id, "room")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the room.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(room.room_id, "room")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        {/* Admissions Tab */}
        <TabsContent value="admissions">
          <Card>
            <CardHeader>
              <CardTitle>Admissions</CardTitle>
              <CardDescription>Manage patient admissions</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                  <div className="space-y-2">
                    <Label>Admission Date</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button
                          variant="outline"
                          className="w-full justify-start text-left font-normal"
                        >
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newAdmission.admission_date ? format(newAdmission.admission_date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newAdmission.admission_date}
                          onSelect={(date) => setNewAdmission({ ...newAdmission, admission_date: date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2">
                    <Label>Discharge Date (Optional)</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button
                          variant="outline"
                          className="w-full justify-start text-left font-normal"
                        >
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newAdmission.discharge_date ? format(newAdmission.discharge_date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newAdmission.discharge_date}
                          onSelect={(date) => setNewAdmission({ ...newAdmission, discharge_date: date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="admission-patient">Patient</Label>
                    <Select
                      value={newAdmission.patient_id}
                      onValueChange={(value) => setNewAdmission({ ...newAdmission, patient_id: value })}
                    >
                      <SelectTrigger id="admission-patient">
                        <SelectValue placeholder="Select patient" />
                      </SelectTrigger>
                      <SelectContent>
                        {patients.map((patient) => (
                          <SelectItem key={patient.patient_id} value={patient.patient_id.toString()}>
                            {patient.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="admission-room">Room</Label>
                    <Select
                      value={newAdmission.room_id}
                      onValueChange={(value) => setNewAdmission({ ...newAdmission, room_id: value })}
                    >
                      <SelectTrigger id="admission-room">
                        <SelectValue placeholder="Select room" />
                      </SelectTrigger>
                      <SelectContent>
                        {rooms.filter(room => room.status === "Available").map((room) => (
                          <SelectItem key={room.room_id} value={room.room_id.toString()}>
                            {room.room_number} ({room.type})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddAdmission}>
                  {editMode ? "Update Admission" : "Add Admission"}
                </Button>
              </div>
              
              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Patient</TableHead>
                      <TableHead>Room</TableHead>
                      <TableHead>Admission Date</TableHead>
                      <TableHead>Discharge Date</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {admissions.map((admission) => (
                      <TableRow key={admission.admission_id}>
                        <TableCell>{admission.admission_id}</TableCell>
                        <TableCell>{getPatientName(admission.patient_id)}</TableCell>
                        <TableCell>{getRoomNumber(admission.room_id)}</TableCell>
                        <TableCell>{format(admission.admission_date, "PP")}</TableCell>
                        <TableCell>
                          {admission.discharge_date ? format(admission.discharge_date, "PP") : "Not discharged"}
                        </TableCell>
                        <TableCell>
                          <div className="flex space-x-2">
                            {!admission.discharge_date && (
                              <Button
                                variant="outline"
                                size="sm"
                                onClick={() => handleDischarge(admission.admission_id)}
                              >
                                Discharge
                              </Button>
                            )}
                            <Button
                              variant="outline"
                              size="icon"
                              onClick={() => handleEdit(admission.admission_id, "admission")}
                            >
                              <Edit className="h-4 w-4" />
                            </Button>
                            <AlertDialog>
                              <AlertDialogTrigger asChild>
                                <Button variant="outline" size="icon" className="text-red-500">
                                  <Trash2 className="h-4 w-4" />
                                </Button>
                              </AlertDialogTrigger>
                              <AlertDialogContent>
                                <AlertDialogHeader>
                                  <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                                  <AlertDialogDescription>
                                    This action cannot be undone. This will permanently delete the admission record.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction onClick={() => handleDelete(admission.admission_id, "admission")}>
                                    Delete
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </div>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  )
}


