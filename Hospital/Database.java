import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class Database {
    private static final String DB_URL = "jdbc:sqlite:hospital.db";

    // This will oad the SQlite Driver

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found. Makes sure that the JAR is on classpath.");
        }
    }

    private static Connection getConnection() throws SQLException {
        Connection c = DriverManager.getConnection(DB_URL);

        // Ensures that the connection is valid
        try (Statement st = c.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
        }
        return c;
    }

    // gets called when system starts up, Creates DB & Tables if they dont
    public static void init() {
        try (Connection c = getConnection(); Statement st = c.createStatement()) {
            st.execute(
                    "CREATE TABLE IF NOT EXISTS employees(" +
                            "id TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "specialty TEXT NOT NULL," +
                            "salary REAL NOT NULL)");

            st.execute(
                    "CREATE TABLE IF NOT EXISTS patients(" +
                            "id TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "age INTEGER NOT NULL," +
                            "type TEXT NOT NULL," +
                            "gender TEXT NOT NULL)");

            st.execute(
                    "CREATE TABLE IF NOT EXISTS appointments(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "patient_id TEXT NOT NULL," +
                            "doctor_id TEXT NOT NULL," +
                            "date TEXT NOT NULL," +
                            "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE," +
                            "FOREIGN KEY (doctor_id) REFERENCES employees(id) ON DELETE CASCADE)");

            st.execute(
                    "CREATE TABLE IF NOT EXISTS bills(" +
                            "type TEXT NOT NULL," +
                            "id TEXT NOT NULL," +
                            "amount REAL NOT NULL," +
                            "PRIMARY KEY (type,id))");

            st.execute(
                    "CREATE TABLE IF NOT EXISTS medicalrecords(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "patient_id TEXT NOT NULL," +
                            "description TEXT NOT NULL," +
                            "date TEXT NOT NULL," +
                            "FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // EMPLOYEES
    public static void insertEmployee(String id, String name, String specialty, double salary) {
        String sql = "INSERT INTO employees(id,name,specialty,salary) VALUES(?,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, specialty);
            ps.setDouble(4, salary);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB insert Employee error:" + e.getMessage());
        }
    }

    public static List<EmployeeRow> getAllEmployees() {
        ArrayList<EmployeeRow> list = new ArrayList<>();
        String sql = "SELECT id,name,specialty,salary FROM employees  ORDER BY name";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new EmployeeRow(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("specialty"),
                        rs.getDouble("salary")

                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static final class EmployeeRow {
        public final String id, name, specialty;
        public final double salary;

        public EmployeeRow(String id, String name, String specialty, double salary) {
            this.id = id;
            this.name = name;
            this.specialty = specialty;
            this.salary = salary;
        }
    }

    // PATIENTS
    public static void insertPatient(String id, String name, int age, String type, String gender) {
        String sql = "INSERT INTO patients(id,name,age,type,gender) VALUES(?, ?, ?, ?, ?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, type);
            ps.setString(5, gender);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB insert Patient error:" + e.getMessage());
        }
    }

    public static List<PatientRow> getAllPatients() {
        ArrayList<PatientRow> list = new ArrayList<>();
        String sql = "SELECT id,name,age,type,gender FROM patients ORDER By name";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new PatientRow(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("type"),
                        rs.getString("gender")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static final class PatientRow {
        public final String id, name, type, gender;
        public final int age;

        public PatientRow(String id, String name, int age, String type, String gender) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.type = type;
            this.gender = gender;
        }
    }

    // APPOINTMENTS
    public static void insertAppointment(String patientId, String doctorId, String date) {
        String sql = "INSERT INTO appointments(patient_id, doctor_id, date) VALUES(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, patientId);
            ps.setString(2, doctorId);
            ps.setString(3, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB insert Appointment error:" + e.getMessage());
        }
    }

    public static List<AppointmentRow> getAllAppointments() {
        ArrayList<AppointmentRow> list = new ArrayList<>();
        String sql = "SELECT patient_id, doctor_id, date FROM appointments ORDER BY date";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new AppointmentRow(
                        rs.getString("patient_id"),
                        rs.getString("doctor_id"),
                        rs.getString("date")

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static final class AppointmentRow {
        public final String patientId, doctorId, date;

        public AppointmentRow(String patient_id, String doctor_id, String date) {
            this.patientId = patient_id;
            this.doctorId = doctor_id;
            this.date = date;
        }
    }

    // BILLS
    public static void insertBill(String type, String id, double amount) {
        String sql = "INSERT INTO bills(type,id,amount) VALUES(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setString(2, id);
            ps.setDouble(3, amount);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("DB INSERT BILL ERROR:" + e.getMessage());
        }
    }

    public static List<BillRow> getAllBills() {
        ArrayList<BillRow> list = new ArrayList<>();
        String sql = "SELECT type,id,amount FROM bills ORDER BY amount";
        try (Connection x = getConnection(); Statement st = x.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new BillRow(
                        rs.getString("type"),
                        rs.getString("id"),
                        rs.getDouble("amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static final class BillRow {
        public final String type, id;
        public final double amount;

        public BillRow(String type, String id, double amount) {
            this.type = type;
            this.id = id;
            this.amount = amount;
        }
    }

    // MEDICAL RECORDS
    public static void insertMedicalRecord(String patientId, String description, String date) {
        String sql = "INSERT INTO medicalrecords(patient_id,description,date) VALUES(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, patientId);
            ps.setString(2, description);
            ps.setString(3, date);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB insertMedicalRecord error: " + e.getMessage());
        }
    }

    public static List<MedicalRecordRow> getAllMedicalRecords() {
        ArrayList<MedicalRecordRow> list = new ArrayList<>();
        String sql = "SELECT patient_id, description, date FROM medical_records ORDER BY date";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new MedicalRecordRow(
                        rs.getString("patient_id"),
                        rs.getString("description"),
                        rs.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static final class MedicalRecordRow {
        public final String patientId, description, date;

        public MedicalRecordRow(String patientId, String description, String date) {
            this.patientId = patientId;
            this.description = description;
            this.date = date;
        }
    }

}