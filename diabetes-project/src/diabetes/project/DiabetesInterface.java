/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.util.AbstractMap;
import java.util.Map;

/**
*
* @author loredana
*/

public class DiabetesInterface {

	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_BLUE = "\u001B[94m";
	private static final String ANSI_RESET = "\u001B[0m";
	public static Set<Patient> patients = new HashSet<>();
	private static Scanner scanner = new Scanner(System.in);

// DOCTOR-RELATED METHODS
	public static void menu(Doctor doctor) {
		
		KieContext kieContext = null;
		while (true) {

			String input;
			do {
				System.out.println("\nDiabetes Management System");
				System.out.println("1. Add Patient");
				System.out.println("2. Evaluate Patients.");
				System.out.println("3. Exit.");

				System.out.print("Choose an option: ");
				input = scanner.nextLine().trim();

				if (!Utils.choiceIsValid(input)) {
					System.out.println("Invalid choice. Please enter a non-negative integer.");
				}
			} while (!Utils.choiceIsValid(input));

			int choice = Integer.parseInt(input);

			switch (choice) {
			case 1:
				addNewPatient(scanner, doctor);
				break;
			case 2:
				if (kieContext == null) {
					kieContext = evaluatePatients();
				}
				performEvaluation(kieContext, doctor);

				break;
			case 3:
				System.exit(0);
			default:
				invalidChoicePrompt();
			}
		}
	}

	private static Set<Patient> addNewPatient(Scanner scanner, Doctor doctor) {
		System.out.println("Adding a new patient...");

		String name;

		do {
			System.out.println("Enter patient name:");
			name = scanner.nextLine().trim();

			if (!Utils.nameIsValid(name)) {
				System.out.println("Invalid name. Please enter a valid patient name: ");
			}
		} while (!Utils.nameIsValid(name));

		String input;

		do {
			System.out.println("Enter type of diabetes (1 for Type 1, 2 for Type 2):");
			input = scanner.nextLine().trim();

			if (!Utils.typeOfDiabetesIsValid(input)) {
				System.out.println("Invalid type of diabetes. Please enter 1 for Type 1 or 2 for Type 2 diabetes.");
			}
		} while (!Utils.typeOfDiabetesIsValid(input));

		int typeOfDiabetes = Integer.parseInt(input);

		do {
			System.out.println("Enter BMI:");
			input = scanner.nextLine().trim();

			if (!Utils.bmiIsValid(input)) {
				System.out.println("Invalid BMI. Please enter a positive double number.");
			}
		} while (!Utils.bmiIsValid(input));

		double bmi = Double.parseDouble(input);

		do {
			System.out.println("Enter age:");
			input = scanner.nextLine().trim();

			if (!Utils.ageIsValid(input)) {
				System.out.println("Invalid age. Please enter a positive integer number.");
			}
		} while (!Utils.ageIsValid(input));

		int age = Integer.parseInt(input);

		do {
			System.out.println("Enter insulin production (1 = NO INSULIN PRODUCTION, "
					+ "2 = HYPOINSULINEMIA, 3 = NORMOINSULINEMIA, 4 = HYPERINSULINEMIA):");
			input = scanner.nextLine().trim();
			if (!Utils.insulinProdIsValid(input)) {
				System.out.println("Invalid input. Please enter a number between 1 and 4.");
			}
		} while (!Utils.insulinProdIsValid(input));

		int insulinProd = Integer.parseInt(input);

		boolean insulinRes = promptYesNo("Is insulin resistant? (y/n):");
		boolean hypotension = promptYesNo("Has hypotension? (y/n):");
		boolean dyslipidemia = promptYesNo("Has dyslipidemia? (y/n):");
		boolean pad = promptYesNo("Has peripheral artery disease (PAD)? (y/n):");
		boolean nafld = promptYesNo("Has non-alcoholic fatty liver disease (NAFLD)? (y/n):");
		boolean osteoporosis = promptYesNo("Has osteoporosis? (y/n):");

		Patient patient = new Patient(name, typeOfDiabetes, bmi, age, insulinProd, insulinRes, hypotension,
				dyslipidemia, pad, nafld, osteoporosis);

		patients.add(patient);

		DBManager.insertPatient(patient.getName(), patient.getTypeOfDiabetes(), patient.getBmi(), patient.getAge(),
				patient.getInsulinProd(), patient.isInsulinRes(), patient.isHypotension(), patient.isDyslipidemia(),
				patient.isPad(), patient.isNafld(), patient.isOsteoporosis(), doctor);

		System.out.println(ANSI_GREEN + "Patient added successfully." + ANSI_RESET);
		return patients;
	}

	private static boolean promptYesNo(String message) {
		String input;
		do {
			System.out.print(message);
			input = scanner.nextLine().trim();
			if (!Utils.yesNoIsValid(input)) {
				System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
			}
		} while (!Utils.yesNoIsValid(input));
		return input.equals("y") || input.equals("Y");
	}

	public static int welcomePrompt() {
		String input;
		do {
			System.out.println("Welcome to our DSS!\n");
			System.out.println("1. Sign Up\n");
			System.out.println("2. Log In\n");

			System.out.print("Choose an option: ");
			input = scanner.nextLine().trim();

			if (!Utils.choiceIsValid(input)) {
				System.out.println("Invalid choice. Please enter a non-negative integer.");
			}
		} while (!Utils.choiceIsValid(input));

		int choice = Integer.parseInt(input);

		return choice;
	}

	public static void logInVeredict(boolean authorized) {

		if (authorized) {
			System.out.println(ANSI_GREEN + "Login successful" + ANSI_RESET);

		} else {
			System.out.println(ANSI_RED + "Login failed" + ANSI_RESET);

		}

	}
//DECISION MAKING

	public static KieContext evaluatePatients() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		KieContext kieContext = new KieContext(ks, kc);
		return kieContext;
	}

	public static void performEvaluation(KieContext kieContext, Doctor doctor) {

		KieSession ksession = kieContext.getKieContainer().newKieSession("diabetesSession");
		Set<Treatment> treatments;
		patients = DBManager.getPatientsByDoctorId(doctor.getId());

		for (Patient patient : patients) {

			ksession.insert(patient);
		}

		ksession.fireAllRules();

		for (Patient patient : patients) {

			treatments = patient.getTreatments();

			for (Treatment treatment : treatments) {
				DBManager.insertTreatmentForPatient(patient.getId(), treatment);

			}
		}

		ksession.dispose();

		System.out.println("Evaluation completed for patients:");
		int id = displayPatients();
		System.out.println(
				"Treatments that can be used to treat patient " + DBManager.getPatientNameById(id).toUpperCase() + ":");
		displayTreatments(id);
		System.out.println("Press enter to return to the main menu...");
		scanner.nextLine();
	}

	public static int displayPatients() {

		String input;
		int id;
		Set<Integer> ids = new HashSet<>();
		for (Patient patient : patients) {

			System.out.println(patient.getId() + "-" + patient.getName());
			ids.add(patient.getId());

		}

		do {
			System.out.println("Select the number of the patient you want to see the treatments of: ");
			input = scanner.nextLine().trim();

			if (!Utils.choiceIsValid(input)) {
				System.out.println("Invalid number. Please enter a non-negative integer.");
			} else {
				if (!ids.contains(Integer.parseInt(input))) {
					System.out.println("Invalid number. Please enter one of the listed numbers.");
				}
			}
		} while (!Utils.choiceIsValid(input) || !ids.contains(Integer.parseInt(input)));

		id = Integer.parseInt(input);
		// scanner.nextLine();
		return id;
	}

	public static void displayTreatments(int patientId) {

		Set<Treatment> treatments = DBManager.getTreatmentsByPatientId(patientId);
		if (treatments.isEmpty()) {
			System.out.println(ANSI_BLUE + "No treatments were added.");
			System.out.println(
					"Patient´s characteristics are too rare to be analyzed by this system. Consider personalized medical evaluation and monitoring for their condition."
							+ ANSI_RESET);
		}

		for (Treatment treatment : treatments) {

			System.out.println(treatment);

		}

	}

	public static boolean addDoctor() {
		System.out.println("Signing up new doctor.\n");
		String username;
		String password;
		String input;
		boolean invalidChoice = false;
		boolean exists = false;
		Doctor doc;

		do {
			System.out.println("Type the username:\n" + "Usernames must start with a letter,"
					+ "and can only contain letters, numbers, underscores, or dashes."
					+ "Length must be between 6 and 20 characters.");
			username = scanner.next();
			scanner.nextLine();

			if (!Utils.usernameIsValid(username)) {
				System.out.println("Invalid username.");
			} else {
				exists = DBManager.doesDoctorExist(username);
				if (exists) {
					do {
						if (!invalidChoice) {
							System.out.println("Username already exists.");
						}
						System.out.println("1. Retry with another username");
						System.out.println("2. Log in");

						System.out.print("Choose an option: ");
						input = scanner.nextLine().trim();
						if (!Utils.choiceIsValid(input)) {
							System.out.println("Invalid choice. Please enter a non-negative integer.");
							invalidChoice = true;

						} else if (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2) {
							System.out.println("Invalid choice. Please enter 1 or 2.");
							invalidChoice = true;
						} else {
							invalidChoice = false;
						}
					} while (!Utils.choiceIsValid(input)
							|| (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2));
					int choice = Integer.parseInt(input);
					if (choice == 2) {
						System.out.println("Redirecting to login...");
						return false;

					}

				}
			}
		} while (!Utils.usernameIsValid(username) || exists);

		do {
			System.out.println("Type the password:\n" + "Passwords must be at least 8 characters long, "
					+ "and include at least one uppercase letter, one lowercase letter, "
					+ "one digit, and one special character (!@#$%^&*).");
			password = scanner.next();
			scanner.nextLine();
			if (!Utils.passwordIsValid(password)) {
				System.out.println("Invalid password.");
			}
		} while (!Utils.passwordIsValid(password));

		doc = new Doctor(username, password);
		DBManager.insertDoctor(doc.getUsername(), doc.getPassword());
		int doctorId = DBManager.getDoctorIdByUsername(username);
		doc.setId(doctorId);
		System.out.println(ANSI_GREEN + "Signup successful" + ANSI_RESET);
		System.out.println("Redirecting to login...");
		return true;
	}

//CREDENTIAL VALIDATION

	public static Map.Entry<Boolean, Doctor> isUserCorrect() {
		boolean doctorExists = false;
		String username = "";
		Doctor doc = null;
		boolean invalidChoice = false;
		String input;

		while (!doctorExists) {
			System.out.println("Please type the username:");
			username = scanner.next();
			scanner.nextLine();

			if (!DBManager.doesDoctorExist(username)) {

				do {
					if (!invalidChoice) {
						System.out.println("No doctor registered under this username.");
					}
					System.out.println("1. Retry typing the username");
					System.out.println("2. Sign up");

					System.out.print("Choose an option: ");
					input = scanner.nextLine().trim();

					if (!Utils.choiceIsValid(input)) {
						System.out.println("Invalid choice. Please enter a non-negative integer.");
						invalidChoice = true;

					} else if (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2) {
						System.out.println("Invalid choice. Please enter 1 or 2.");
						invalidChoice = true;
					} else {
						invalidChoice = false;
					}
				} while (!Utils.choiceIsValid(input) || (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2));

				int choice = Integer.parseInt(input);

				switch (choice) {
				case 1:
					continue;

				case 2:
					return new AbstractMap.SimpleEntry<>(false, null);
				}

			} else {
				doctorExists = true; // Doctor exists
				// Proceed with password verification
				boolean passwordCorrect = false;
				while (!passwordCorrect) {
					System.out.println("Please type the password:");
					String password = scanner.next();
					scanner.nextLine();
					passwordCorrect = DBManager.isPasswordCorrect(username, Utils.hashPassword(password));
					if (passwordCorrect) {
						// Password is correct, create Doctor object
						int doctorId = DBManager.getDoctorIdByUsername(username);
						doc = new Doctor(username, password);
						doc.setId(doctorId); // Assuming Doctor class has an setId method

						return new AbstractMap.SimpleEntry<>(true, doc);
					} else {
						invalidChoice = false;
						do {
							if (!invalidChoice) {
								System.out.println("Incorrect password. Please try again.");
							}
							System.out.println("1. Retry typing the password");
							System.out.println("2. Return to username input");

							System.out.print("Choose an option: ");
							input = scanner.nextLine().trim();

							if (!Utils.choiceIsValid(input)) {
								System.out.println("Invalid choice. Please enter a non-negative integer.");
								invalidChoice = true;

							} else if (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2) {
								System.out.println("Invalid choice. Please enter 1 or 2.");
								invalidChoice = true;
							} else {
								invalidChoice = false;
							}
						} while (!Utils.choiceIsValid(input)
								|| (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2));

						int choice = Integer.parseInt(input);

						if (choice == 2) {
							// Break out to the outer loop to re-enter the username
							doctorExists = false;
							break;
						}
						// If the user chooses to retry, the loop will continue to prompt for the
						// password
					}
				}
			}
		}
		// This return statement is a fallback and should never be reached due to the
		// loops, but is necessary to compile
		return new AbstractMap.SimpleEntry<>(false, null);
	}

	public static void incorrectPassword() {
		System.out.println("Incorrect Password. Try again\n");
	}

	public static void incorrectUsername(int prompt) {
		if (prompt == 1) {
			System.out.println("No doctor registered under that username. Try again\n");
		} else if (prompt == 2) {
			System.out.println("Username already in use. Choose another one.\n ");
		}

	}

	public static void invalidChoicePrompt() {
		System.out.println("Invalid choice. Please try again.");
	}

}
