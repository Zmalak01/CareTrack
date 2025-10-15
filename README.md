# CareTrack

A simple desktop app that manages core hospital workflows with a friendly Swing UI and a lightweight SQLite database for persistence. It supports staff management, patient registry, billing, medical records, and appointments everything saved to `hospital.db` so data survives restarts.

## What it does

* **Login flow**

  * **Admin** (password: `Admin324`) gets tabs for **Staff**, **Patients**, **Billings**, **Medical Records**, and **Appointments**.
  * **Patient** gets **Medical Records** and **Appointments** view.
 


    
* **Staff**
  Add employees with role (**Doctor / Nurse / Cleaner / Receptionist / Manager**).
  Doctors store a specific **specialty** (e.g., “Cardiology”). Loads from DB on startup.


  
* **Patients**
  Register patients with **ID, Name, Age, Type** (“Ambulatory” / “Outpatient”), and **Gender**. Loads from DB on startup.

  
* **Billing**
  Create **Customerbill** or **Supplierbill** entries, then generate a quick **summary report** (totals by type). Loads from DB on startup.


  
* **Medical Records**
  Admin can add records (**patient id, date, notes**); both roles can view them in the UI.


  
* **Appointments**
  Book **patient ↔ doctor** appointments with a date; list loads from DB on startup.


* **Java (Swing)** for the GUI
* **SQLite** for storage (embedded, file-based)
* **sqlite-jdbc** driver (JAR in `lib/`)
* `Database.java` handles table creation and all inserts/queries



## How data is stored

On first run, `Database.init()` creates `hospital.db` and these tables (if missing):
`employees`, `patients`, `appointments`, `bills`, `medical_records`.
All tabs read from the DB at startup and write new entries on “Add”.





**Windows (PowerShell)**

```powershell
javac -cp ".;lib\sqlite-jdbc-3.44.1.0.jar" *.java
java  -cp ".;lib\sqlite-jdbc-3.44.1.0.jar" Hospitalsystem
```



**macOS/Linux**

```bash
javac -cp ".:lib/sqlite-jdbc-3.44.1.0.jar" *.java
java  -cp ".:lib/sqlite-jdbc-3.44.1.0.jar" Hospitalsystem
```



> The app creates/uses `hospital.db` in the project root.
> Change the jar version in the commands to match the file you have.



## Project structure 

```
Hospital/
├─ lib/
│  └─ sqlite-jdbc-3.44.1.0.jar
├─ Database.java
├─ Hospitalsystem.java
├─ domain classes: Employee.java, Doctor.java, Patient.java, Bill.java,
│    Customerbill.java, Supplierbill.java, Appointment.java, Medicalrecord.java, ...)
└─ hospital.db  (generated at runtime)
```

## Notes / limitations

* Intended for learning/demo use; not production-ready (no auth for patients, no encryption, no audit trails).
* Basic input validation only.
* Single-user desktop app; no concurrent/multi-user safety.


