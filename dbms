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
import {
  CalendarIcon,
  Trash2,
  Edit,
  Search,
  FileText,
  Shield,
  Database,
  Tag,
  Clock,
  Building,
  Briefcase,
} from "lucide-react"
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
import { Badge } from "@/components/ui/badge"

// Mock data for demonstration
const initialDepartments = [
  { department_id: 1, name: "Criminal Investigation", location: "Headquarters, 1st Floor" },
  { department_id: 2, name: "Cyber Crime Unit", location: "Tech Building, 3rd Floor" },
  { department_id: 3, name: "Forensics", location: "Science Wing, 2nd Floor" },
  { department_id: 4, name: "Narcotics", location: "West Precinct, 1st Floor" },
  { department_id: 5, name: "Homicide", location: "Headquarters, 2nd Floor" },
]

const initialOfficers = [
  {
    officer_id: 1,
    badge_number: "B1234",
    name: "John Smith",
    rank: "Detective",
    contact: "555-1234",
    department_id: 1,
  },
  {
    officer_id: 2,
    badge_number: "B2345",
    name: "Sarah Johnson",
    rank: "Sergeant",
    contact: "555-2345",
    department_id: 2,
  },
  {
    officer_id: 3,
    badge_number: "B3456",
    name: "Michael Brown",
    rank: "Officer",
    contact: "555-3456",
    department_id: 3,
  },
  {
    officer_id: 4,
    badge_number: "B4567",
    name: "Emily Davis",
    rank: "Lieutenant",
    contact: "555-4567",
    department_id: 4,
  },
  {
    officer_id: 5,
    badge_number: "B5678",
    name: "Robert Wilson",
    rank: "Detective",
    contact: "555-5678",
    department_id: 5,
  },
]

const initialCases = [
  {
    case_id: 1,
    case_number: "C2023-001",
    title: "Downtown Robbery",
    description: "Armed robbery at First National Bank",
    status: "Active",
    creation_date: new Date("2023-01-15"),
  },
  {
    case_id: 2,
    case_number: "C2023-002",
    title: "Cyber Attack",
    description: "Ransomware attack on city systems",
    status: "Active",
    creation_date: new Date("2023-02-20"),
  },
  {
    case_id: 3,
    case_number: "C2023-003",
    title: "Missing Person",
    description: "Jane Doe reported missing from residence",
    status: "Closed",
    creation_date: new Date("2023-03-10"),
  },
  {
    case_id: 4,
    case_number: "C2023-004",
    title: "Drug Trafficking",
    description: "Suspected drug distribution network",
    status: "Active",
    creation_date: new Date("2023-04-05"),
  },
  {
    case_id: 5,
    case_number: "C2023-005",
    title: "Homicide Investigation",
    description: "Victim found at riverside park",
    status: "Active",
    creation_date: new Date("2023-05-12"),
  },
]

const initialOfficerCases = [
  { officer_id: 1, case_id: 1 },
  { officer_id: 1, case_id: 5 },
  { officer_id: 2, case_id: 2 },
  { officer_id: 3, case_id: 3 },
  { officer_id: 4, case_id: 4 },
  { officer_id: 5, case_id: 5 },
]

const initialEvidenceTypes = [
  { evidence_type_id: 1, name: "Image", description: "Photographic evidence" },
  { evidence_type_id: 2, name: "Video", description: "Video recordings" },
  { evidence_type_id: 3, name: "Audio", description: "Audio recordings" },
  { evidence_type_id: 4, name: "Document", description: "Written or printed materials" },
  { evidence_type_id: 5, name: "Digital", description: "Computer files, emails, etc." },
]

const initialEvidence = [
  {
    evidence_id: 1,
    name: "Security Camera Footage",
    description: "Bank security camera showing robbery",
    file_path: "/evidence/video001.mp4",
    hash_value: "a1b2c3d4e5f6",
    size: 256.5,
    collection_date: new Date("2023-01-15"),
    case_id: 1,
    evidence_type_id: 2,
  },
  {
    evidence_id: 2,
    name: "Ransom Note",
    description: "Digital ransom note from attackers",
    file_path: "/evidence/note001.pdf",
    hash_value: "f6e5d4c3b2a1",
    size: 1.2,
    collection_date: new Date("2023-02-21"),
    case_id: 2,
    evidence_type_id: 4,
  },
  {
    evidence_id: 3,
    name: "Last Known Photo",
    description: "Last photo of missing person",
    file_path: "/evidence/photo001.jpg",
    hash_value: "1a2b3c4d5e6f",
    size: 3.5,
    collection_date: new Date("2023-03-11"),
    case_id: 3,
    evidence_type_id: 1,
  },
  {
    evidence_id: 4,
    name: "Phone Conversation",
    description: "Recorded call between suspects",
    file_path: "/evidence/audio001.mp3",
    hash_value: "6f5e4d3c2b1a",
    size: 45.8,
    collection_date: new Date("2023-04-06"),
    case_id: 4,
    evidence_type_id: 3,
  },
  {
    evidence_id: 5,
    name: "Crime Scene Photos",
    description: "Photos from the murder scene",
    file_path: "/evidence/photos002.zip",
    hash_value: "abcdef123456",
    size: 128.7,
    collection_date: new Date("2023-05-13"),
    case_id: 5,
    evidence_type_id: 1,
  },
]

const initialChainOfCustody = [
  {
    custody_id: 1,
    timestamp: new Date("2023-01-15T14:30:00"),
    action: "Collected",
    notes: "Retrieved from bank security office",
    evidence_id: 1,
    officer_id: 1,
  },
  {
    custody_id: 2,
    timestamp: new Date("2023-01-16T09:15:00"),
    action: "Transferred",
    notes: "Sent to digital forensics lab",
    evidence_id: 1,
    officer_id: 3,
  },
  {
    custody_id: 3,
    timestamp: new Date("2023-02-21T16:45:00"),
    action: "Collected",
    notes: "Extracted from infected system",
    evidence_id: 2,
    officer_id: 2,
  },
  {
    custody_id: 4,
    timestamp: new Date("2023-03-11T11:20:00"),
    action: "Collected",
    notes: "Provided by family member",
    evidence_id: 3,
    officer_id: 3,
  },
  {
    custody_id: 5,
    timestamp: new Date("2023-04-06T20:10:00"),
    action: "Collected",
    notes: "Obtained with warrant",
    evidence_id: 4,
    officer_id: 4,
  },
  {
    custody_id: 6,
    timestamp: new Date("2023-05-13T08:45:00"),
    action: "Collected",
    notes: "Taken at crime scene",
    evidence_id: 5,
    officer_id: 5,
  },
  {
    custody_id: 7,
    timestamp: new Date("2023-05-14T10:30:00"),
    action: "Analyzed",
    notes: "Initial forensic analysis completed",
    evidence_id: 5,
    officer_id: 3,
  },
]

const initialTags = [
  { tag_id: 1, name: "Urgent" },
  { tag_id: 2, name: "Homicide" },
  { tag_id: 3, name: "Robbery" },
  { tag_id: 4, name: "Cyber" },
  { tag_id: 5, name: "Narcotics" },
]

const initialEvidenceTags = [
  { evidence_id: 1, tag_id: 3 },
  { evidence_id: 2, tag_id: 4 },
  { evidence_id: 3, tag_id: 1 },
  { evidence_id: 4, tag_id: 5 },
  { evidence_id: 5, tag_id: 1 },
  { evidence_id: 5, tag_id: 2 },
]

export default function DigitalEvidenceManagementSystem() {
  // State for each entity
  const [departments, setDepartments] = useState(initialDepartments)
  const [officers, setOfficers] = useState(initialOfficers)
  const [cases, setCases] = useState(initialCases)
  const [officerCases, setOfficerCases] = useState(initialOfficerCases)
  const [evidenceTypes, setEvidenceTypes] = useState(initialEvidenceTypes)
  const [evidence, setEvidence] = useState(initialEvidence)
  const [chainOfCustody, setChainOfCustody] = useState(initialChainOfCustody)
  const [tags, setTags] = useState(initialTags)
  const [evidenceTags, setEvidenceTags] = useState(initialEvidenceTags)

  // State for form inputs
  const [newDepartment, setNewDepartment] = useState({ name: "", location: "" })
  const [newOfficer, setNewOfficer] = useState({ badge_number: "", name: "", rank: "", contact: "", department_id: "" })
  const [newCase, setNewCase] = useState({
    case_number: "",
    title: "",
    description: "",
    status: "Active",
    creation_date: new Date(),
  })
  const [newOfficerCase, setNewOfficerCase] = useState({ officer_id: "", case_id: "" })
  const [newEvidenceType, setNewEvidenceType] = useState({ name: "", description: "" })
  const [newEvidence, setNewEvidence] = useState({
    name: "",
    description: "",
    file_path: "",
    hash_value: "",
    size: "",
    collection_date: new Date(),
    case_id: "",
    evidence_type_id: "",
  })
  const [newChainOfCustody, setNewChainOfCustody] = useState({
    timestamp: new Date(),
    action: "",
    notes: "",
    evidence_id: "",
    officer_id: "",
  })
  const [newTag, setNewTag] = useState({ name: "" })
  const [newEvidenceTag, setNewEvidenceTag] = useState({ evidence_id: "", tag_id: "" })

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

  // Function to handle adding a new officer
  const handleAddOfficer = () => {
    if (newOfficer.badge_number && newOfficer.name && newOfficer.rank && newOfficer.department_id) {
      if (editMode) {
        setOfficers(
          officers.map((officer) =>
            officer.officer_id === editId
              ? {
                  ...officer,
                  badge_number: newOfficer.badge_number,
                  name: newOfficer.name,
                  rank: newOfficer.rank,
                  contact: newOfficer.contact,
                  department_id: Number.parseInt(newOfficer.department_id),
                }
              : officer,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = officers.length > 0 ? Math.max(...officers.map((officer) => officer.officer_id)) + 1 : 1
        setOfficers([
          ...officers,
          { officer_id: newId, ...newOfficer, department_id: Number.parseInt(newOfficer.department_id) },
        ])
      }
      setNewOfficer({ badge_number: "", name: "", rank: "", contact: "", department_id: "" })
    }
  }

  // Function to handle adding a new case
  const handleAddCase = () => {
    if (newCase.case_number && newCase.title && newCase.status) {
      if (editMode) {
        setCases(
          cases.map((c) =>
            c.case_id === editId
              ? {
                  ...c,
                  case_number: newCase.case_number,
                  title: newCase.title,
                  description: newCase.description,
                  status: newCase.status,
                  creation_date: newCase.creation_date,
                }
              : c,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = cases.length > 0 ? Math.max(...cases.map((c) => c.case_id)) + 1 : 1
        setCases([
          ...cases,
          {
            case_id: newId,
            ...newCase,
          },
        ])
      }
      setNewCase({ case_number: "", title: "", description: "", status: "Active", creation_date: new Date() })
    }
  }

  // Function to handle adding a new officer-case assignment
  const handleAddOfficerCase = () => {
    if (newOfficerCase.officer_id && newOfficerCase.case_id) {
      // Check if this assignment already exists
      const exists = officerCases.some(
        (oc) =>
          oc.officer_id === Number.parseInt(newOfficerCase.officer_id) &&
          oc.case_id === Number.parseInt(newOfficerCase.case_id),
      )

      if (!exists) {
        setOfficerCases([
          ...officerCases,
          {
            officer_id: Number.parseInt(newOfficerCase.officer_id),
            case_id: Number.parseInt(newOfficerCase.case_id),
          },
        ])
        setNewOfficerCase({ officer_id: "", case_id: "" })
      }
    }
  }

  // Function to handle adding a new evidence type
  const handleAddEvidenceType = () => {
    if (newEvidenceType.name) {
      if (editMode) {
        setEvidenceTypes(
          evidenceTypes.map((et) =>
            et.evidence_type_id === editId
              ? {
                  ...et,
                  name: newEvidenceType.name,
                  description: newEvidenceType.description,
                }
              : et,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = evidenceTypes.length > 0 ? Math.max(...evidenceTypes.map((et) => et.evidence_type_id)) + 1 : 1
        setEvidenceTypes([...evidenceTypes, { evidence_type_id: newId, ...newEvidenceType }])
      }
      setNewEvidenceType({ name: "", description: "" })
    }
  }

  // Function to handle adding new evidence
  const handleAddEvidence = () => {
    if (
      newEvidence.name &&
      newEvidence.file_path &&
      newEvidence.hash_value &&
      newEvidence.case_id &&
      newEvidence.evidence_type_id
    ) {
      if (editMode) {
        setEvidence(
          evidence.map((e) =>
            e.evidence_id === editId
              ? {
                  ...e,
                  name: newEvidence.name,
                  description: newEvidence.description,
                  file_path: newEvidence.file_path,
                  hash_value: newEvidence.hash_value,
                  size: Number.parseFloat(newEvidence.size),
                  collection_date: newEvidence.collection_date,
                  case_id: Number.parseInt(newEvidence.case_id),
                  evidence_type_id: Number.parseInt(newEvidence.evidence_type_id),
                }
              : e,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = evidence.length > 0 ? Math.max(...evidence.map((e) => e.evidence_id)) + 1 : 1
        setEvidence([
          ...evidence,
          {
            evidence_id: newId,
            ...newEvidence,
            size: Number.parseFloat(newEvidence.size),
            case_id: Number.parseInt(newEvidence.case_id),
            evidence_type_id: Number.parseInt(newEvidence.evidence_type_id),
          },
        ])

        // Automatically create a "Collected" chain of custody record
        const newCustodyId = chainOfCustody.length > 0 ? Math.max(...chainOfCustody.map((c) => c.custody_id)) + 1 : 1
        setChainOfCustody([
          ...chainOfCustody,
          {
            custody_id: newCustodyId,
            timestamp: new Date(),
            action: "Collected",
            notes: "Initial collection",
            evidence_id: newId,
            officer_id: Number.parseInt(newEvidence.officer_id || "1"), // Default to first officer if not specified
          },
        ])
      }
      setNewEvidence({
        name: "",
        description: "",
        file_path: "",
        hash_value: "",
        size: "",
        collection_date: new Date(),
        case_id: "",
        evidence_type_id: "",
      })
    }
  }

  // Function to handle adding a new chain of custody record
  const handleAddChainOfCustody = () => {
    if (newChainOfCustody.action && newChainOfCustody.evidence_id && newChainOfCustody.officer_id) {
      const newId = chainOfCustody.length > 0 ? Math.max(...chainOfCustody.map((c) => c.custody_id)) + 1 : 1
      setChainOfCustody([
        ...chainOfCustody,
        {
          custody_id: newId,
          ...newChainOfCustody,
          timestamp: newChainOfCustody.timestamp || new Date(),
          evidence_id: Number.parseInt(newChainOfCustody.evidence_id),
          officer_id: Number.parseInt(newChainOfCustody.officer_id),
        },
      ])
      setNewChainOfCustody({ timestamp: new Date(), action: "", notes: "", evidence_id: "", officer_id: "" })
    }
  }

  // Function to handle adding a new tag
  const handleAddTag = () => {
    if (newTag.name) {
      if (editMode) {
        setTags(
          tags.map((t) =>
            t.tag_id === editId
              ? {
                  ...t,
                  name: newTag.name,
                }
              : t,
          ),
        )
        setEditMode(false)
        setEditId(null)
      } else {
        const newId = tags.length > 0 ? Math.max(...tags.map((t) => t.tag_id)) + 1 : 1
        setTags([...tags, { tag_id: newId, ...newTag }])
      }
      setNewTag({ name: "" })
    }
  }

  // Function to handle adding a new evidence-tag association
  const handleAddEvidenceTag = () => {
    if (newEvidenceTag.evidence_id && newEvidenceTag.tag_id) {
      // Check if this association already exists
      const exists = evidenceTags.some(
        (et) =>
          et.evidence_id === Number.parseInt(newEvidenceTag.evidence_id) &&
          et.tag_id === Number.parseInt(newEvidenceTag.tag_id),
      )

      if (!exists) {
        setEvidenceTags([
          ...evidenceTags,
          {
            evidence_id: Number.parseInt(newEvidenceTag.evidence_id),
            tag_id: Number.parseInt(newEvidenceTag.tag_id),
          },
        ])
        setNewEvidenceTag({ evidence_id: "", tag_id: "" })
      }
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
      case "officer":
        const officer = officers.find((o) => o.officer_id === id)
        setNewOfficer({
          badge_number: officer.badge_number,
          name: officer.name,
          rank: officer.rank,
          contact: officer.contact,
          department_id: officer.department_id.toString(),
        })
        break
      case "case":
        const c = cases.find((c) => c.case_id === id)
        setNewCase({
          case_number: c.case_number,
          title: c.title,
          description: c.description,
          status: c.status,
          creation_date: c.creation_date,
        })
        break
      case "evidence_type":
        const et = evidenceTypes.find((et) => et.evidence_type_id === id)
        setNewEvidenceType({
          name: et.name,
          description: et.description,
        })
        break
      case "evidence":
        const e = evidence.find((e) => e.evidence_id === id)
        setNewEvidence({
          name: e.name,
          description: e.description,
          file_path: e.file_path,
          hash_value: e.hash_value,
          size: e.size.toString(),
          collection_date: e.collection_date,
          case_id: e.case_id.toString(),
          evidence_type_id: e.evidence_type_id.toString(),
        })
        break
      case "tag":
        const t = tags.find((t) => t.tag_id === id)
        setNewTag({
          name: t.name,
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
      case "officer":
        setOfficers(officers.filter((o) => o.officer_id !== id))
        // Also delete officer-case assignments
        setOfficerCases(officerCases.filter((oc) => oc.officer_id !== id))
        break
      case "case":
        setCases(cases.filter((c) => c.case_id !== id))
        // Also delete evidence and officer-case assignments related to this case
        setEvidence(evidence.filter((e) => e.case_id !== id))
        setOfficerCases(officerCases.filter((oc) => oc.case_id !== id))
        break
      case "officer_case":
        setOfficerCases(officerCases.filter((oc) => !(oc.officer_id === id.officer_id && oc.case_id === id.case_id)))
        break
      case "evidence_type":
        setEvidenceTypes(evidenceTypes.filter((et) => et.evidence_type_id !== id))
        break
      case "evidence":
        setEvidence(evidence.filter((e) => e.evidence_id !== id))
        // Also delete chain of custody records and evidence-tag associations
        setChainOfCustody(chainOfCustody.filter((c) => c.evidence_id !== id))
        setEvidenceTags(evidenceTags.filter((et) => et.evidence_id !== id))
        break
      case "chain_of_custody":
        setChainOfCustody(chainOfCustody.filter((c) => c.custody_id !== id))
        break
      case "tag":
        setTags(tags.filter((t) => t.tag_id !== id))
        // Also delete evidence-tag associations
        setEvidenceTags(evidenceTags.filter((et) => et.tag_id !== id))
        break
      case "evidence_tag":
        setEvidenceTags(evidenceTags.filter((et) => !(et.evidence_id === id.evidence_id && et.tag_id === id.tag_id)))
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

  // Helper function to get officer name by ID
  const getOfficerName = (id) => {
    const officer = officers.find((o) => o.officer_id === id)
    return officer ? officer.name : "Unknown"
  }

  // Helper function to get case title by ID
  const getCaseTitle = (id) => {
    const c = cases.find((c) => c.case_id === id)
    return c ? c.title : "Unknown"
  }

  // Helper function to get evidence type name by ID
  const getEvidenceTypeName = (id) => {
    const et = evidenceTypes.find((et) => et.evidence_type_id === id)
    return et ? et.name : "Unknown"
  }

  // Helper function to get evidence name by ID
  const getEvidenceName = (id) => {
    const e = evidence.find((e) => e.evidence_id === id)
    return e ? e.name : "Unknown"
  }

  // Helper function to get tag name by ID
  const getTagName = (id) => {
    const t = tags.find((t) => t.tag_id === id)
    return t ? t.name : "Unknown"
  }

  // Helper function to get tags for an evidence item
  const getEvidenceTags = (evidenceId) => {
    return evidenceTags.filter((et) => et.evidence_id === evidenceId).map((et) => getTagName(et.tag_id))
  }

  // Helper function to get officers assigned to a case
  const getCaseOfficers = (caseId) => {
    return officerCases.filter((oc) => oc.case_id === caseId).map((oc) => getOfficerName(oc.officer_id))
  }

  // Filter function for search
  const filterBySearchTerm = (item, type) => {
    if (!searchTerm) return true

    const term = searchTerm.toLowerCase()

    switch (type) {
      case "department":
        return item.name.toLowerCase().includes(term) || item.location.toLowerCase().includes(term)
      case "officer":
        return (
          item.name.toLowerCase().includes(term) ||
          item.badge_number.toLowerCase().includes(term) ||
          item.rank.toLowerCase().includes(term)
        )
      case "case":
        return (
          item.title.toLowerCase().includes(term) ||
          item.case_number.toLowerCase().includes(term) ||
          (item.description && item.description.toLowerCase().includes(term))
        )
      case "evidence":
        return (
          item.name.toLowerCase().includes(term) || (item.description && item.description.toLowerCase().includes(term))
        )
      case "evidence_type":
        return (
          item.name.toLowerCase().includes(term) || (item.description && item.description.toLowerCase().includes(term))
        )
      case "tag":
        return item.name.toLowerCase().includes(term)
      default:
        return true
    }
  }

  return (
    <div className="container mx-auto py-6">
      <h1 className="text-3xl font-bold mb-6 text-center">Digital Evidence Management System</h1>

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

      <Tabs defaultValue="cases">
        <TabsList className="grid grid-cols-4 md:grid-cols-7 mb-4">
          <TabsTrigger value="cases">
            <Briefcase className="h-4 w-4 mr-2" />
            Cases
          </TabsTrigger>
          <TabsTrigger value="evidence">
            <FileText className="h-4 w-4 mr-2" />
            Evidence
          </TabsTrigger>
          <TabsTrigger value="custody">
            <Clock className="h-4 w-4 mr-2" />
            Chain of Custody
          </TabsTrigger>
          <TabsTrigger value="officers">
            <Shield className="h-4 w-4 mr-2" />
            Officers
          </TabsTrigger>
          <TabsTrigger value="departments">
            <Building className="h-4 w-4 mr-2" />
            Departments
          </TabsTrigger>
          <TabsTrigger value="evidence_types">
            <Database className="h-4 w-4 mr-2" />
            Evidence Types
          </TabsTrigger>
          <TabsTrigger value="tags">
            <Tag className="h-4 w-4 mr-2" />
            Tags
          </TabsTrigger>
        </TabsList>

        {/* Cases Tab */}
        <TabsContent value="cases">
          <Card>
            <CardHeader>
              <CardTitle>Cases</CardTitle>
              <CardDescription>Manage case records</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="case-number">Case Number</Label>
                    <Input
                      id="case-number"
                      value={newCase.case_number}
                      onChange={(e) => setNewCase({ ...newCase, case_number: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="case-title">Title</Label>
                    <Input
                      id="case-title"
                      value={newCase.title}
                      onChange={(e) => setNewCase({ ...newCase, title: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="case-status">Status</Label>
                    <Select value={newCase.status} onValueChange={(value) => setNewCase({ ...newCase, status: value })}>
                      <SelectTrigger id="case-status">
                        <SelectValue placeholder="Select status" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Active">Active</SelectItem>
                        <SelectItem value="Pending">Pending</SelectItem>
                        <SelectItem value="Closed">Closed</SelectItem>
                        <SelectItem value="Archived">Archived</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label>Creation Date</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button variant="outline" className="w-full justify-start text-left font-normal">
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newCase.creation_date ? format(newCase.creation_date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newCase.creation_date}
                          onSelect={(date) => setNewCase({ ...newCase, creation_date: date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="case-description">Description</Label>
                    <Textarea
                      id="case-description"
                      value={newCase.description}
                      onChange={(e) => setNewCase({ ...newCase, description: e.target.value })}
                      className="min-h-[100px]"
                    />
                  </div>
                </div>
                <Button onClick={handleAddCase}>{editMode ? "Update Case" : "Add Case"}</Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Case Number</TableHead>
                      <TableHead>Title</TableHead>
                      <TableHead>Status</TableHead>
                      <TableHead>Creation Date</TableHead>
                      <TableHead>Assigned Officers</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {cases
                      .filter((c) => filterBySearchTerm(c, "case"))
                      .map((c) => (
                        <TableRow key={c.case_id}>
                          <TableCell>{c.case_number}</TableCell>
                          <TableCell>{c.title}</TableCell>
                          <TableCell>
                            <Badge
                              variant={
                                c.status === "Active" ? "default" : c.status === "Closed" ? "secondary" : "outline"
                              }
                            >
                              {c.status}
                            </Badge>
                          </TableCell>
                          <TableCell>{format(c.creation_date, "PP")}</TableCell>
                          <TableCell>{getCaseOfficers(c.case_id).join(", ") || "None"}</TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button variant="outline" size="icon" onClick={() => handleEdit(c.case_id, "case")}>
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
                                      This action cannot be undone. This will permanently delete the case and all
                                      associated evidence.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete(c.case_id, "case")}>
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

              <div className="mt-6">
                <h3 className="text-lg font-medium mb-4">Assign Officer to Case</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="officer-case-officer">Officer</Label>
                    <Select
                      value={newOfficerCase.officer_id}
                      onValueChange={(value) => setNewOfficerCase({ ...newOfficerCase, officer_id: value })}
                    >
                      <SelectTrigger id="officer-case-officer">
                        <SelectValue placeholder="Select officer" />
                      </SelectTrigger>
                      <SelectContent>
                        {officers.map((officer) => (
                          <SelectItem key={officer.officer_id} value={officer.officer_id.toString()}>
                            {officer.name} ({officer.badge_number})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="officer-case-case">Case</Label>
                    <Select
                      value={newOfficerCase.case_id}
                      onValueChange={(value) => setNewOfficerCase({ ...newOfficerCase, case_id: value })}
                    >
                      <SelectTrigger id="officer-case-case">
                        <SelectValue placeholder="Select case" />
                      </SelectTrigger>
                      <SelectContent>
                        {cases.map((c) => (
                          <SelectItem key={c.case_id} value={c.case_id.toString()}>
                            {c.case_number} - {c.title}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddOfficerCase} className="mt-4">
                  Assign Officer to Case
                </Button>

                <div className="mt-6">
                  <h3 className="text-lg font-medium mb-4">Case Assignments</h3>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Officer</TableHead>
                        <TableHead>Case</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {officerCases.map((oc) => (
                        <TableRow key={`${oc.officer_id}-${oc.case_id}`}>
                          <TableCell>{getOfficerName(oc.officer_id)}</TableCell>
                          <TableCell>{getCaseTitle(oc.case_id)}</TableCell>
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
                                    This action cannot be undone. This will remove the officer from this case.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction
                                    onClick={() =>
                                      handleDelete({ officer_id: oc.officer_id, case_id: oc.case_id }, "officer_case")
                                    }
                                  >
                                    Remove
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Evidence Tab */}
        <TabsContent value="evidence">
          <Card>
            <CardHeader>
              <CardTitle>Evidence</CardTitle>
              <CardDescription>Manage digital evidence</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="evidence-name">Name</Label>
                    <Input
                      id="evidence-name"
                      value={newEvidence.name}
                      onChange={(e) => setNewEvidence({ ...newEvidence, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-file-path">File Path</Label>
                    <Input
                      id="evidence-file-path"
                      value={newEvidence.file_path}
                      onChange={(e) => setNewEvidence({ ...newEvidence, file_path: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-hash">Hash Value</Label>
                    <Input
                      id="evidence-hash"
                      value={newEvidence.hash_value}
                      onChange={(e) => setNewEvidence({ ...newEvidence, hash_value: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-size">Size (MB)</Label>
                    <Input
                      id="evidence-size"
                      type="number"
                      step="0.1"
                      value={newEvidence.size}
                      onChange={(e) => setNewEvidence({ ...newEvidence, size: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label>Collection Date</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button variant="outline" className="w-full justify-start text-left font-normal">
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newEvidence.collection_date ? format(newEvidence.collection_date, "PPP") : "Select date"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newEvidence.collection_date}
                          onSelect={(date) => setNewEvidence({ ...newEvidence, collection_date: date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-case">Case</Label>
                    <Select
                      value={newEvidence.case_id}
                      onValueChange={(value) => setNewEvidence({ ...newEvidence, case_id: value })}
                    >
                      <SelectTrigger id="evidence-case">
                        <SelectValue placeholder="Select case" />
                      </SelectTrigger>
                      <SelectContent>
                        {cases.map((c) => (
                          <SelectItem key={c.case_id} value={c.case_id.toString()}>
                            {c.case_number} - {c.title}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-type">Evidence Type</Label>
                    <Select
                      value={newEvidence.evidence_type_id}
                      onValueChange={(value) => setNewEvidence({ ...newEvidence, evidence_type_id: value })}
                    >
                      <SelectTrigger id="evidence-type">
                        <SelectValue placeholder="Select type" />
                      </SelectTrigger>
                      <SelectContent>
                        {evidenceTypes.map((et) => (
                          <SelectItem key={et.evidence_type_id} value={et.evidence_type_id.toString()}>
                            {et.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="evidence-description">Description</Label>
                    <Textarea
                      id="evidence-description"
                      value={newEvidence.description}
                      onChange={(e) => setNewEvidence({ ...newEvidence, description: e.target.value })}
                      className="min-h-[100px]"
                    />
                  </div>
                </div>
                <Button onClick={handleAddEvidence}>{editMode ? "Update Evidence" : "Add Evidence"}</Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Name</TableHead>
                      <TableHead>Type</TableHead>
                      <TableHead>Case</TableHead>
                      <TableHead>Collection Date</TableHead>
                      <TableHead>Size (MB)</TableHead>
                      <TableHead>Tags</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {evidence
                      .filter((e) => filterBySearchTerm(e, "evidence"))
                      .map((e) => (
                        <TableRow key={e.evidence_id}>
                          <TableCell>{e.name}</TableCell>
                          <TableCell>{getEvidenceTypeName(e.evidence_type_id)}</TableCell>
                          <TableCell>{getCaseTitle(e.case_id)}</TableCell>
                          <TableCell>{format(e.collection_date, "PP")}</TableCell>
                          <TableCell>{e.size.toFixed(1)}</TableCell>
                          <TableCell>
                            <div className="flex flex-wrap gap-1">
                              {getEvidenceTags(e.evidence_id).map((tag, index) => (
                                <Badge key={index} variant="outline">
                                  {tag}
                                </Badge>
                              ))}
                            </div>
                          </TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button
                                variant="outline"
                                size="icon"
                                onClick={() => handleEdit(e.evidence_id, "evidence")}
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
                                      This action cannot be undone. This will permanently delete the evidence and all
                                      associated custody records.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete(e.evidence_id, "evidence")}>
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

              <div className="mt-6">
                <h3 className="text-lg font-medium mb-4">Add Tag to Evidence</h3>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="evidence-tag-evidence">Evidence</Label>
                    <Select
                      value={newEvidenceTag.evidence_id}
                      onValueChange={(value) => setNewEvidenceTag({ ...newEvidenceTag, evidence_id: value })}
                    >
                      <SelectTrigger id="evidence-tag-evidence">
                        <SelectValue placeholder="Select evidence" />
                      </SelectTrigger>
                      <SelectContent>
                        {evidence.map((e) => (
                          <SelectItem key={e.evidence_id} value={e.evidence_id.toString()}>
                            {e.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-tag-tag">Tag</Label>
                    <Select
                      value={newEvidenceTag.tag_id}
                      onValueChange={(value) => setNewEvidenceTag({ ...newEvidenceTag, tag_id: value })}
                    >
                      <SelectTrigger id="evidence-tag-tag">
                        <SelectValue placeholder="Select tag" />
                      </SelectTrigger>
                      <SelectContent>
                        {tags.map((t) => (
                          <SelectItem key={t.tag_id} value={t.tag_id.toString()}>
                            {t.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                </div>
                <Button onClick={handleAddEvidenceTag} className="mt-4">
                  Add Tag to Evidence
                </Button>

                <div className="mt-6">
                  <h3 className="text-lg font-medium mb-4">Evidence Tags</h3>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Evidence</TableHead>
                        <TableHead>Tag</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {evidenceTags.map((et) => (
                        <TableRow key={`${et.evidence_id}-${et.tag_id}`}>
                          <TableCell>{getEvidenceName(et.evidence_id)}</TableCell>
                          <TableCell>{getTagName(et.tag_id)}</TableCell>
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
                                    This action cannot be undone. This will remove this tag from the evidence.
                                  </AlertDialogDescription>
                                </AlertDialogHeader>
                                <AlertDialogFooter>
                                  <AlertDialogCancel>Cancel</AlertDialogCancel>
                                  <AlertDialogAction
                                    onClick={() =>
                                      handleDelete({ evidence_id: et.evidence_id, tag_id: et.tag_id }, "evidence_tag")
                                    }
                                  >
                                    Remove
                                  </AlertDialogAction>
                                </AlertDialogFooter>
                              </AlertDialogContent>
                            </AlertDialog>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </div>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Chain of Custody Tab */}
        <TabsContent value="custody">
          <Card>
            <CardHeader>
              <CardTitle>Chain of Custody</CardTitle>
              <CardDescription>Track evidence handling and transfers</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="custody-evidence">Evidence</Label>
                    <Select
                      value={newChainOfCustody.evidence_id}
                      onValueChange={(value) => setNewChainOfCustody({ ...newChainOfCustody, evidence_id: value })}
                    >
                      <SelectTrigger id="custody-evidence">
                        <SelectValue placeholder="Select evidence" />
                      </SelectTrigger>
                      <SelectContent>
                        {evidence.map((e) => (
                          <SelectItem key={e.evidence_id} value={e.evidence_id.toString()}>
                            {e.name}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="custody-officer">Officer</Label>
                    <Select
                      value={newChainOfCustody.officer_id}
                      onValueChange={(value) => setNewChainOfCustody({ ...newChainOfCustody, officer_id: value })}
                    >
                      <SelectTrigger id="custody-officer">
                        <SelectValue placeholder="Select officer" />
                      </SelectTrigger>
                      <SelectContent>
                        {officers.map((o) => (
                          <SelectItem key={o.officer_id} value={o.officer_id.toString()}>
                            {o.name} ({o.badge_number})
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="custody-action">Action</Label>
                    <Select
                      value={newChainOfCustody.action}
                      onValueChange={(value) => setNewChainOfCustody({ ...newChainOfCustody, action: value })}
                    >
                      <SelectTrigger id="custody-action">
                        <SelectValue placeholder="Select action" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Collected">Collected</SelectItem>
                        <SelectItem value="Transferred">Transferred</SelectItem>
                        <SelectItem value="Analyzed">Analyzed</SelectItem>
                        <SelectItem value="Stored">Stored</SelectItem>
                        <SelectItem value="Retrieved">Retrieved</SelectItem>
                        <SelectItem value="Returned">Returned</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label>Timestamp</Label>
                    <Popover>
                      <PopoverTrigger asChild>
                        <Button variant="outline" className="w-full justify-start text-left font-normal">
                          <CalendarIcon className="mr-2 h-4 w-4" />
                          {newChainOfCustody.timestamp
                            ? format(newChainOfCustody.timestamp, "PPP HH:mm")
                            : "Select date and time"}
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent className="w-auto p-0">
                        <Calendar
                          mode="single"
                          selected={newChainOfCustody.timestamp}
                          onSelect={(date) => setNewChainOfCustody({ ...newChainOfCustody, timestamp: date })}
                          initialFocus
                        />
                      </PopoverContent>
                    </Popover>
                  </div>
                  <div className="space-y-2 md:col-span-2">
                    <Label htmlFor="custody-notes">Notes</Label>
                    <Textarea
                      id="custody-notes"
                      value={newChainOfCustody.notes}
                      onChange={(e) => setNewChainOfCustody({ ...newChainOfCustody, notes: e.target.value })}
                      className="min-h-[100px]"
                    />
                  </div>
                </div>
                <Button onClick={handleAddChainOfCustody}>Add Custody Record</Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Timestamp</TableHead>
                      <TableHead>Evidence</TableHead>
                      <TableHead>Officer</TableHead>
                      <TableHead>Action</TableHead>
                      <TableHead>Notes</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {chainOfCustody.map((c) => (
                      <TableRow key={c.custody_id}>
                        <TableCell>{format(c.timestamp, "PPP HH:mm")}</TableCell>
                        <TableCell>{getEvidenceName(c.evidence_id)}</TableCell>
                        <TableCell>{getOfficerName(c.officer_id)}</TableCell>
                        <TableCell>{c.action}</TableCell>
                        <TableCell className="max-w-xs truncate">{c.notes}</TableCell>
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
                                  This action cannot be undone. This will permanently delete this custody record.
                                </AlertDialogDescription>
                              </AlertDialogHeader>
                              <AlertDialogFooter>
                                <AlertDialogCancel>Cancel</AlertDialogCancel>
                                <AlertDialogAction onClick={() => handleDelete(c.custody_id, "chain_of_custody")}>
                                  Delete
                                </AlertDialogAction>
                              </AlertDialogFooter>
                            </AlertDialogContent>
                          </AlertDialog>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        {/* Officers Tab */}
        <TabsContent value="officers">
          <Card>
            <CardHeader>
              <CardTitle>Officers</CardTitle>
              <CardDescription>Manage officer records</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="officer-badge">Badge Number</Label>
                    <Input
                      id="officer-badge"
                      value={newOfficer.badge_number}
                      onChange={(e) => setNewOfficer({ ...newOfficer, badge_number: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="officer-name">Name</Label>
                    <Input
                      id="officer-name"
                      value={newOfficer.name}
                      onChange={(e) => setNewOfficer({ ...newOfficer, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="officer-rank">Rank</Label>
                    <Select
                      value={newOfficer.rank}
                      onValueChange={(value) => setNewOfficer({ ...newOfficer, rank: value })}
                    >
                      <SelectTrigger id="officer-rank">
                        <SelectValue placeholder="Select rank" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="Officer">Officer</SelectItem>
                        <SelectItem value="Detective">Detective</SelectItem>
                        <SelectItem value="Sergeant">Sergeant</SelectItem>
                        <SelectItem value="Lieutenant">Lieutenant</SelectItem>
                        <SelectItem value="Captain">Captain</SelectItem>
                        <SelectItem value="Chief">Chief</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="officer-contact">Contact</Label>
                    <Input
                      id="officer-contact"
                      value={newOfficer.contact}
                      onChange={(e) => setNewOfficer({ ...newOfficer, contact: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="officer-department">Department</Label>
                    <Select
                      value={newOfficer.department_id}
                      onValueChange={(value) => setNewOfficer({ ...newOfficer, department_id: value })}
                    >
                      <SelectTrigger id="officer-department">
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
                <Button onClick={handleAddOfficer}>{editMode ? "Update Officer" : "Add Officer"}</Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Badge Number</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Rank</TableHead>
                      <TableHead>Contact</TableHead>
                      <TableHead>Department</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {officers
                      .filter((officer) => filterBySearchTerm(officer, "officer"))
                      .map((officer) => (
                        <TableRow key={officer.officer_id}>
                          <TableCell>{officer.badge_number}</TableCell>
                          <TableCell>{officer.name}</TableCell>
                          <TableCell>{officer.rank}</TableCell>
                          <TableCell>{officer.contact}</TableCell>
                          <TableCell>{getDepartmentName(officer.department_id)}</TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button
                                variant="outline"
                                size="icon"
                                onClick={() => handleEdit(officer.officer_id, "officer")}
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
                                      This action cannot be undone. This will permanently delete the officer record.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete(officer.officer_id, "officer")}>
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
              <CardDescription>Manage law enforcement departments</CardDescription>
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
                <Button onClick={handleAddDepartment}>{editMode ? "Update Department" : "Add Department"}</Button>
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
                    {departments
                      .filter((department) => filterBySearchTerm(department, "department"))
                      .map((department) => (
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
                                    <AlertDialogAction
                                      onClick={() => handleDelete(department.department_id, "department")}
                                    >
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

        {/* Evidence Types Tab */}
        <TabsContent value="evidence_types">
          <Card>
            <CardHeader>
              <CardTitle>Evidence Types</CardTitle>
              <CardDescription>Manage evidence type categories</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="evidence-type-name">Name</Label>
                    <Input
                      id="evidence-type-name"
                      value={newEvidenceType.name}
                      onChange={(e) => setNewEvidenceType({ ...newEvidenceType, name: e.target.value })}
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="evidence-type-description">Description</Label>
                    <Input
                      id="evidence-type-description"
                      value={newEvidenceType.description}
                      onChange={(e) => setNewEvidenceType({ ...newEvidenceType, description: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddEvidenceType}>
                  {editMode ? "Update Evidence Type" : "Add Evidence Type"}
                </Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Description</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {evidenceTypes
                      .filter((et) => filterBySearchTerm(et, "evidence_type"))
                      .map((et) => (
                        <TableRow key={et.evidence_type_id}>
                          <TableCell>{et.evidence_type_id}</TableCell>
                          <TableCell>{et.name}</TableCell>
                          <TableCell>{et.description}</TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button
                                variant="outline"
                                size="icon"
                                onClick={() => handleEdit(et.evidence_type_id, "evidence_type")}
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
                                      This action cannot be undone. This will permanently delete the evidence type.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction
                                      onClick={() => handleDelete(et.evidence_type_id, "evidence_type")}
                                    >
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

        {/* Tags Tab */}
        <TabsContent value="tags">
          <Card>
            <CardHeader>
              <CardTitle>Tags</CardTitle>
              <CardDescription>Manage evidence tags</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div className="space-y-2">
                    <Label htmlFor="tag-name">Name</Label>
                    <Input
                      id="tag-name"
                      value={newTag.name}
                      onChange={(e) => setNewTag({ ...newTag, name: e.target.value })}
                    />
                  </div>
                </div>
                <Button onClick={handleAddTag}>{editMode ? "Update Tag" : "Add Tag"}</Button>
              </div>

              <div className="mt-6">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Name</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {tags
                      .filter((tag) => filterBySearchTerm(tag, "tag"))
                      .map((tag) => (
                        <TableRow key={tag.tag_id}>
                          <TableCell>{tag.tag_id}</TableCell>
                          <TableCell>{tag.name}</TableCell>
                          <TableCell>
                            <div className="flex space-x-2">
                              <Button variant="outline" size="icon" onClick={() => handleEdit(tag.tag_id, "tag")}>
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
                                      This action cannot be undone. This will permanently delete the tag.
                                    </AlertDialogDescription>
                                  </AlertDialogHeader>
                                  <AlertDialogFooter>
                                    <AlertDialogCancel>Cancel</AlertDialogCancel>
                                    <AlertDialogAction onClick={() => handleDelete(tag.tag_id, "tag")}>
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



