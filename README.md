# HospitalER: Hospital Emergency Room management system
## Team Members
- Nazla Chy, 20240004973, Lead Developer & Project Coordinator
- Elham Abbas Abbasi, 20240005699, Data Structures & Integration
- Fedora Fernandes, 20240005748, Resource Management & Statistics
- Negin Mirzajani, 20240004675, UI & Supporting Components

## Project

This project was created using Java and is a Hospital Emergency Room Management System that is created for real ER environment using data structures and algorithms that handle important tasks. The system handles the patient triaging which is based on severity and manages patient records as well. Along with that the system allocates teh patients on top of the triage with doctors and rooms, and provides the user with statistical analysis of all the waiting times of each patient and these are all displayed through JavaFX as the GUI.

### Data Structures
1. Max-Heap(priority queue): Triage by severity
2. Hash Table (hashmap): Patient record managing, searching through lookup
3. FIFO Queue (linkedlists): Registration queue (arrival order in FIFO)
4. Adjacency List Graph (HashMap<String, List<String>>): Doctor/room allocation using BFS

## File Structure

```
hospital-er-management/
│
├── Main.java         # For GUI: full UI (5 scrollpane pages : Dashboard, Queue, Registry, Registration, Statistics and other internal mini cards)
├── ERSystem.java     # Controller: it connects all data structures and has getters
├── Patient.java      # Patient class with getters and setters along with attribute fields
├── Triage.java       # Max heap used for triage
├── Registry.java     # HashMap for patient registry
├── RegQueue.java          # Queue - FIFO
├── ResourceGraph.java     # Adjacency list graph for resource finding and assigning too (BFS)
├── Stats.java        # Analysis of waiting periods
├── ERException.java  # Exception class
└── README.md
```


## System Requirements

Requirement Details: 
- Java Version: JDK 17 or higher
- JavaFX Version: JavaFX SDK 17+
- RAM: 256 MB minimum
- OS: Windows 10/11, macOS 12+
- IDE: IntelliJ or VSCode with the Java Extension Package and Batch runner extensions


## Setup and Running Instructions

### Step 1: Install Java JDK

Download from: https://adoptium.net

Verify installation:
```bash
java -version
```
You should see `openjdk version "17.x.x"` or higher.

### Step 2: Download JavaFX SDK
Download the JavaFX SDK for your OS and check here for all: https://gluonhq.com/products/javafx/
After you have downloaded it extract it and change the file path according to your OS
1. **Windows:** `C:\javafx-sdk-17\`
2. **macOS/Linux:** `~/javafx-sdk-17/`


### Step 3: Download This Repository
Download and extract the ZIP file and move it to HospitalER folder where you also have your JavaFX SDK exported

### Step 4: Compile
After making sure you have all the necessary extensions compile it

### Step 5: Run these
**Windows:**
```bash
java --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml Main
```

**macOS / Linux:**
```bash
java --module-path ~/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml Main
```

You will see the applications window launch and you must enter your name

## Pages description
1. Dashboard: This page is an overview and it will show you all the critical patients here, any available doctors and rooms, a small queue overview, and mini stats overview and a calendar
2. Queue: Triage patients are here and sorted by severity with coloured badge (C/H/M/L) which have critical, high, moderate, low
3. Patient Registry: Registered patients are all here and you can search by name or ID and discharge when treatment time is over
4. Registration: Form to add patient
5. Statistics: Mean, standard deviation, max wait times, minimum wait, severity breakdown table

## Main features

- Triage by severity of the patient: highest severity patients are always served first
- Patient lookup: O(1), you can instantly get the records if you search by ID
- Automatic resource assignment: BFS on resource graph gives registered patient a doctor and a room whenever both available
- Discharge: Releases patient and frees your doctor and room
- Statistics: track the mean, min, max, deviation and severity breakdown
- Input validation: all fields validated with error dialogs

## Severity Scale

| Score | Label | Color |
|-------|-------|-------|
| 9–10 | Critical (C) | Red |
| 7–8 | High (H) | Orange |
| 4–6 | Moderate (M) | Yellow |
| 1–3 | Low (L) | Green |

## How to Use

1. Launch the app and enter your name.
2. Go to Registration and fill in patient details then click Register Patient
3. System automatically registers your patient and assigns room and doctor if available, or they are put in the waiting list
4. If doctors and rooms are available, the patient is assigned automatically
5. Go to **Queue** to see the triage which is arranged in descending order by severity
6. Go to **Patient Registry** to see all patients and their records and you can search patients and discharge them when treatment is done
7. Check **Statistics** for wait time analysis.
