/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

/**
 *
 * @author loredana
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

public class DiabetesInterface {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";
    public static Set<Patient> patients = new HashSet<>();
    private static Scanner scanner = new Scanner(System.in);
    
public static void menu(Doctor doctor) {
    KieContext kieContext = null; // Initialize outside the loop to persist the context
    //DBMannager.createTables();
    while (true) {
        System.out.println("\nDiabetes Management System");
        System.out.println("1. Add Patient");
        System.out.println("2. Evaluate Patients.");
        System.out.println("3. Exit.");

        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addNewPatient(scanner, doctor);
                break;
            case 2:
                if (kieContext == null) {
                    kieContext = evaluatePatients(); // Only initialize if not already done
                }
                performEvaluation(kieContext,doctor); // This will handle the evaluation logic
                break;
            case 3:
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

    

private static Set<Patient> addNewPatient(Scanner scanner, Doctor doctor) {
    System.out.println("Adding a new patient...");

    System.out.println("Enter patient name:");
    String name = scanner.nextLine();

    int typeOfDiabetes;
    do {
        System.out.println("Enter type of diabetes (1 for Type 1, 2 for Type 2):");
        typeOfDiabetes = scanner.nextInt();
        if (typeOfDiabetes < 1 || typeOfDiabetes > 2) {
            System.out.println("Invalid input. Please enter 1 for Type 1 or 2 for Type 2 diabetes.");
        }
    } while (typeOfDiabetes < 1 || typeOfDiabetes > 2);

    System.out.println("Enter BMI:");
    double bmi = scanner.nextDouble();

    System.out.println("Enter age:");
    int age = scanner.nextInt();

    int insulinProd;
    do {
        System.out.println("Enter insulin production (1=NO PROD, 2=HYPO, 3=NORMAL, 4=HYPER):");
        insulinProd = scanner.nextInt();
        if (insulinProd < 1 || insulinProd > 4) {
            System.out.println("Invalid input. Please enter a number between 1 and 4.");
        }
    } while (insulinProd < 1 || insulinProd > 4);

    scanner.nextLine(); 

    boolean insulinRes = promptYesNo("Is insulin resistant? (y/n):");
    boolean hypotension = promptYesNo("Has hypotension? (y/n):");
    boolean dyslipidemia = promptYesNo("Has dyslipidemia? (y/n):");
    boolean pad = promptYesNo("Has peripheral artery disease (PAD)? (y/n):");
    boolean nafld = promptYesNo("Has non-alcoholic fatty liver disease (NAFLD)? (y/n):");
    boolean osteoporosis = promptYesNo("Has osteoporosis? (y/n):");

    Patient patient = new Patient(name, typeOfDiabetes, bmi, age, insulinProd, insulinRes, hypotension, dyslipidemia, pad, nafld, osteoporosis);
    
    patients.add(patient);
    
    DBManager.insertPatient(patient.getName(), patient.getTypeOfDiabetes(), patient.getBmi(), patient.getAge(), patient.getInsulinProd(), patient.isInsulinRes(), patient.isHypotension(), patient.isDyslipidemia(), patient.isPad(), patient.isNafld(), patient.isOsteoporosis(), doctor);

    System.out.println("Patient added successfully.");
return patients;   
}

private static boolean promptYesNo(String message) {
    String input;
    do {
        System.out.print(message);
        input = scanner.nextLine().trim().toLowerCase();
        if (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
        }
    } while (!input.equals("y") && !input.equals("n"));
    return input.equals("y");
}

 // DOCTOR METHODS 
    public static Doctor promptForDoctorCredentials(){
        
        System.out.println("Input your username:\n");
        String username = scanner.nextLine();
        System.out.println("Input yor password:\n");
        String password = scanner.nextLine();
        
        return new Doctor(username, password);
    }
   
    public static int welcomePrompt(){
        System.out.println("Welcome to our DSS!\n");
        System.out.println("1.Login\n");
        System.out.println("2.Sign Up \n");
        int option = scanner.nextInt();
        return option;
    }
    
    public static boolean logInVeredict(boolean authorized) {
        try {
            
            if (authorized) {
                System.out.println(ANSI_GREEN + "Login successful" + ANSI_RESET);
                return true;
            } else {
                System.out.println(ANSI_RED + "Login failed" + ANSI_RESET);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }
//DECISION MAKING
    
public static KieContext evaluatePatients() {
    KieServices ks = KieServices.Factory.get();
    KieContainer kc = ks.getKieClasspathContainer();
    KieContext kieContext = new KieContext(ks,kc);
    return kieContext;
}
   
public static void performEvaluation(KieContext kieContext, Doctor doctor) {
    KieSession ksession = kieContext.getKieContainer().newKieSession("diabetesSession");

    patients = DBManager.getPatientsByDoctorId(doctor.getId());
    System.out.println(patients);
    

    for(Patient patient : patients) {
        System.out.println("Doctor id for patients is: " + patient.getDoctorId());
        System.out.println("Patient id for treatments is: " + patient.getId());
        ksession.insert(patient);
    }
    
    
    ksession.fireAllRules();
    
    
    for(Patient patient : patients) {
        
        Set<Treatment> treatments = getTreatmentsForPatient(patient); 
        System.out.println("Patient id for treatments is: " + patient.getId());
       
        for(Treatment treatment : treatments) {
            DBManager.insertTreatmentForPatient(patient.getId(), treatment);
            
        }
    }
    
    ksession.dispose();
    System.out.println("Evaluation for Doctor ID " + doctor.getId() + " completed.");
    

}

 
public static Map.Entry<Boolean, Doctor> addDoctor(){
       System.out.println("Signing up new doctor.\n");
       System.out.println("Type the username:\n");
       String user = scanner.next();
       System.out.println("Type the password:\n");
       String password = scanner.next();
       Doctor doc = new Doctor(user, password);
       boolean exists = DBManager.doesDoctorExist(user);
       if (!exists){
           DBManager.insertDoctor(doc.getUsername(), doc.getPassword()); 
           int doctorId = DBManager.getDoctorIdByUsername(user);
           doc.setId(doctorId);
           return new AbstractMap.SimpleEntry<>(true, doc);
       }else{
       DiabetesInterface.incorrectUsername(2);
       return new AbstractMap.SimpleEntry<>(false, doc);
       }
      
    }

//CREDENTIAL VALIDATION


public static Map.Entry<Boolean, Doctor> isUserCorrect() {
    Scanner scanner = new Scanner(System.in);
    boolean doctorExists = false;
    String username = "";
    Doctor doc = null;

    while (!doctorExists) {
        System.out.println("Please type the username:");
        username = scanner.next();

        if (!DBManager.doesDoctorExist(username)) {
            System.out.println("No doctor registered under this username.");
            System.out.println("1. Retry typing the username");
            System.out.println("2. Sign up");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 2) {
                // Redirect to signup
                return new AbstractMap.SimpleEntry<>(false, null);
            } // If the user chooses to retry, the loop will continue
        } else {
            doctorExists = true; // Doctor exists
            // Proceed with password verification
            boolean passwordCorrect = false;
            while (!passwordCorrect) {
                System.out.println("Please type the password:");
                String password = scanner.next();
                passwordCorrect = DBManager.isPasswordCorrect(username, Utils.hashPassword(password));
                if (passwordCorrect) {
                    // Password is correct, create Doctor object
                    int doctorId = DBManager.getDoctorIdByUsername(username);
                    doc = new Doctor(username, password);
                    doc.setId(doctorId); // Assuming Doctor class has an setId method
                    System.out.println("Login successful.");
                    return new AbstractMap.SimpleEntry<>(true, doc);
                } else {
                    System.out.println("Incorrect password. Please try again.");
                    System.out.println("1. Retry typing the password");
                    System.out.println("2. Return to username input");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    if (choice == 2) {
                        // Break out to the outer loop to re-enter the username
                        doctorExists = false; 
                        break;
                    }
                    // If the user chooses to retry, the loop will continue to prompt for the password
                }
            }
        }
    }
    // This return statement is a fallback and should never be reached due to the loops, but is necessary to compile
    return new AbstractMap.SimpleEntry<>(false, null);
}





 
public static void incorrectPassword(){
    System.out.println("Incorrect Password. Try again\n");    
}

public static void incorrectUsername(int prompt){
    if(prompt == 1){
        System.out.println("No doctor registered under that username. Try again\n");
    } else if(prompt ==2){ 
        System.out.println("Username already in use. Choose another one.\n ");
}
        
}
public static Set<Treatment> getTreatmentsForPatient(Patient patient) {
    return patient.getTreatments();
}

}

