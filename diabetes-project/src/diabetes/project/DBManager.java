/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author carlo
 */
public class DBManager {
	

	public static void generateTablesIfNeeded() {

		boolean doctorTableExists = false;
		try {
			doctorTableExists = DBManager.doesTableExist("Doctor");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean patientTableExists = false;
		try {
			patientTableExists = DBManager.doesTableExist("Patient");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean treatmentTableExists = false;
		try {
			treatmentTableExists = DBManager.doesTableExist("Treatment");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!doctorTableExists && !patientTableExists && !treatmentTableExists) {
			DBManager.createTables();
		}
	}

	public static void createTables() {
		try {

			// Open database connection
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:./db/treatments.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			

			// Create tables: begin
			Statement stmt3 = c.createStatement();
			String sql3 = "CREATE TABLE  Doctor (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "username TEXT NOT NULL,"
					+ "passwordHash TEXT NOT NULL" + ")";

			stmt3.executeUpdate(sql3);
			stmt3.close();
			Statement stmt1 = c.createStatement();
			String sql1 = "CREATE TABLE Patient (" + "    id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ "    name TEXT NOT NULL," + "    typeOfDiabetes INTEGER," + "    bmi DOUBLE," + "    age INT,"
					+ "    insulinProd INTEGER," + "    insulinRes BOOLEAN," + "    hypotension BOOLEAN,"
					+ "    dyslipidemia BOOLEAN," + "    pad BOOLEAN," + "    nafld BOOLEAN,"
					+ "    osteoporosis BOOLEAN," + "    doctor_id INTEGER,"
					+ "    FOREIGN KEY (doctor_id) REFERENCES Doctor(id)" +

					")";
			stmt1.executeUpdate(sql1);
			stmt1.close();

			Statement stmt2 = c.createStatement();
			String sql2 = "CREATE TABLE Treatment (" + "    id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ "    patient_id INTEGER," + "    name TEXT NOT NULL," + "    shouldBeApplied BOOLEAN,"
					+ "    priority DOUBLE," + "    FOREIGN KEY (patient_id) REFERENCES Patient(id)" + ")";

			stmt2.executeUpdate(sql2);
			stmt2.close();
			//tables are now created

			c.close();
	
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("inside catch");
		}
	}

	public static boolean doesTableExist(String tableName) throws SQLException {
		String url = "jdbc:sqlite:./db/treatments.db";
		Connection connection = DriverManager.getConnection(url);
		DatabaseMetaData dbMetaData = connection.getMetaData();
		try (ResultSet rs = dbMetaData.getTables(null, null, tableName, null)) {
			while (rs.next()) {
				String name = rs.getString("TABLE_NAME");
				if (tableName.equalsIgnoreCase(name)) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

// PATIENT
	public static Set<Patient> getPatientsFromDatabase() {
		Set<Patient> patients = new HashSet<>();
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT * FROM Patient";

		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				String name = rs.getString("name");
				int typeOfDiabetes = rs.getInt("typeOfDiabetes");
				double bmi = rs.getDouble("bmi");
				int age = rs.getInt("age");
				int insulinProd = rs.getInt("insulinProd");
				boolean insulinRes = rs.getBoolean("insulinRes");
				boolean hypotension = rs.getBoolean("hypotension");
				boolean dyslipidemia = rs.getBoolean("dyslipidemia");
				boolean pad = rs.getBoolean("pad");
				boolean nafld = rs.getBoolean("nafld");
				boolean osteoporosis = rs.getBoolean("osteoporosis");

				Patient patient = new Patient(name, typeOfDiabetes, bmi, age, insulinProd, insulinRes, hypotension,
						dyslipidemia, pad, nafld, osteoporosis);
				patients.add(patient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	public static Patient searchPatient(String nombre) {
		String nameToFind = nombre;

		String sql = "SELECT * FROM Patient WHERE doctor_id = ?";

		String url = "jdbc:sqlite:./db/treatments.db";
		Patient pat = null;

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, nameToFind);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {

					String name = rs.getString("name");
					int typeOfDiabetes = rs.getInt("typeOfDiabetes");
					double bmi = rs.getDouble("bmi");
					int age = rs.getInt("age");
					int insulinProd = rs.getInt("insulinProd");
					boolean insulinRes = rs.getBoolean("insulinRes");
					boolean hypotension = rs.getBoolean("hypotension");
					boolean dyslipidemia = rs.getBoolean("dyslipidemia");
					boolean pad = rs.getBoolean("pad");
					boolean nafld = rs.getBoolean("nafld");
					boolean osteoporosis = rs.getBoolean("osteoporosis");

					// Mostrar los datos del paciente
					System.out.println("Paciente encontrado:");
					System.out.println("Nombre: " + name);
					System.out.println("Tipo de diabetes: " + typeOfDiabetes);
					System.out.println("BMI: " + bmi);
					System.out.println("Edad: " + age);
					System.out.println("Producción de insulina: " + insulinProd);
					System.out.println("Resistencia a la insulina: " + insulinRes);
					System.out.println("Hipotensión: " + hypotension);
					System.out.println("Dislipidemia: " + dyslipidemia);
					System.out.println("PAD: " + pad);
					System.out.println("NAFLD: " + nafld);
					System.out.println("Osteoporosis: " + osteoporosis);
					pat = new Patient(name, typeOfDiabetes, bmi, age, insulinProd, insulinRes, hypotension,
							dyslipidemia, pad, nafld, osteoporosis);
				} else {
					System.out.println("No se encontró ningún paciente con el nombre '" + nameToFind + "'.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pat;

	}

	public static void insertPatient(String name, int typeOfDiabetes, double bmi, int age, int insulinProd,
			boolean insulinRes, boolean hypotension, boolean dyslipidemia, boolean pad, boolean nafld,
			boolean osteoporosis, Doctor doctor) {
		String sql = "INSERT INTO Patient (name, typeOfDiabetes, bmi, age, insulinProd, insulinRes, hypotension, dyslipidemia, pad, nafld, osteoporosis, doctor_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

		// Conexión a la base de datos
		String url = "jdbc:sqlite:./db/treatments.db";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// Establecer los parámetros de la sentencia SQL
			pstmt.setString(1, name);
			pstmt.setInt(2, typeOfDiabetes);
			pstmt.setDouble(3, bmi);
			pstmt.setInt(4, age);
			pstmt.setInt(5, insulinProd);
			pstmt.setBoolean(6, insulinRes);
			pstmt.setBoolean(7, hypotension);
			pstmt.setBoolean(8, dyslipidemia);
			pstmt.setBoolean(9, pad);
			pstmt.setBoolean(10, nafld);
			pstmt.setBoolean(11, osteoporosis);
			pstmt.setInt(12, doctor.getId()); // Set the doctor_id foreign key
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getPatientNameById(int patientId) {
		String patientName = null;
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT name FROM Patient WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, patientId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					patientName = rs.getString("name");
				} else {
					System.out.println("No patient found with ID: " + patientId);
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error occurred while retrieving the name for patient ID: " + patientId);
			e.printStackTrace();
		}

		return patientName;
	}

	public static Set<Patient> getPatientsByDoctorId(int doctorId) {
		Set<Patient> patients = new HashSet<>();
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT * FROM Patient WHERE doctor_id = ?";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, doctorId);

			try (ResultSet rs = pstmt.executeQuery()) {

				while (rs.next()) {

					Patient patient = new Patient(rs.getString("name"), rs.getInt("typeOfDiabetes"),
							rs.getDouble("bmi"), rs.getInt("age"), rs.getInt("insulinProd"),
							rs.getBoolean("insulinRes"), rs.getBoolean("hypotension"), rs.getBoolean("dyslipidemia"),
							rs.getBoolean("pad"), rs.getBoolean("nafld"), rs.getBoolean("osteoporosis"));
					int patientId = rs.getInt("id");
					patient.setId(patientId);
					patient.setDoctorId(doctorId);
					patients.add(patient);
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error occurred while retrieving patients for Doctor ID: " + doctorId);
			e.printStackTrace();
		}

		return patients;
	}

//DOCTOR

	public static void insertDoctor(String username, String passwordHash) {
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "INSERT INTO Doctor (username, passwordHash) VALUES (?, ?)";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, passwordHash);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean doesDoctorExist(String username) {
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT COUNT(*) AS count FROM Doctor WHERE username = ?";
		boolean exists = false;

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt("count");
				exists = count > 0;
				// if(!exists){
				// DiabetesInterface.incorrectUsername(1);
				// }
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	public static boolean isPasswordCorrect(String username, String password) {
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT id, passwordHash FROM Doctor WHERE username = ?";
		boolean correct = false;

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String passwordHashFromDB = rs.getString("passwordHash");
				String inputPasswordHash = password;
				correct = passwordHashFromDB.equals(inputPasswordHash);
				// if(!correct){
				// DiabetesInterface.incorrectPassword();
				// }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return correct;
	}

	public static int getDoctorIdByUsername(String username) {
		// Initialize the doctor ID to an invalid value to indicate not found
		int doctorId = -1;
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT id FROM Doctor WHERE username = ?";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					// Retrieve the doctor's ID from the query result
					doctorId = rs.getInt("id");

				} else {
					System.out.println("No doctor found with the username: " + username);
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error occurred");
			e.printStackTrace();
		}

		return doctorId;
	}

// TREATMENTS
	public static void insertTreatmentForPatient(int patientId, Treatment treatment) {

		if (treatmentExists(patientId, treatment)) {
			return; // Exit the method early if the treatment already exists
		}

		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "INSERT INTO Treatment (patient_id, name, shouldBeApplied, priority) VALUES (?, ?, ?, ?)";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, patientId);
			pstmt.setString(2, treatment.getName());
			pstmt.setBoolean(3, treatment.isShouldBeApplied());
			pstmt.setDouble(4, treatment.getPriority());

			int rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Database error occurred while inserting a new treatment");
			e.printStackTrace();
		}
	}

	public static boolean treatmentExists(int patientId, Treatment treatment) {
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT COUNT(*) FROM Treatment WHERE patient_id = ? AND name = ? AND shouldBeApplied = ? AND priority = ?";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, patientId);
			pstmt.setString(2, treatment.getName());
			pstmt.setBoolean(3, treatment.isShouldBeApplied());
			pstmt.setDouble(4, treatment.getPriority());

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Default to false if an error occurs or no match found
	}

	public static Set<Treatment> getTreatmentsByPatientId(int patientId) {
		Set<Treatment> treatments = new HashSet<>();
		String url = "jdbc:sqlite:./db/treatments.db";
		String sql = "SELECT * FROM Treatment WHERE patient_id = ?";

		try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, patientId);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					boolean shouldBeApplied = rs.getBoolean("shouldBeApplied");
					double priority = rs.getDouble("priority");

					Treatment treatment = new Treatment(name, shouldBeApplied, priority);
					treatment.setId(id);
					treatments.add(treatment);
				}
			}
		} catch (SQLException e) {
			System.out.println("Database error occurred while retrieving treatments for patient ID: " + patientId);
			e.printStackTrace();
		}

		return treatments;
	}
}
