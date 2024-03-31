/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diabetes.project;

import java.util.Map;


    
   
public class DiabetesLogic {
    public static void main(String[] args) {
        int choice = DiabetesInterface.welcomePrompt();

        switch(choice) {
            case 1:
                Map.Entry<Boolean, Doctor> validateLogin = DiabetesInterface.isUserCorrect();
                if (!validateLogin.getKey() && validateLogin.getValue() == null) {
                    // User opted to sign up
                    choice = 2; // Explicitly chosen signup
                    // No 'break' to allow fall-through to case 2
                } else {
                    // Proceed with the login process
                    if (validateLogin.getKey()) {
                        // Successful login
                        DiabetesInterface.logInVeredict(true);
                        DiabetesInterface.menu(validateLogin.getValue());
                    }
                    break; // Ensure we break here to not fall through to case 2 after a successful login
                }

            case 2:
                if (choice == 2) { // Check if we've fallen through from case 1 or directly chosen option 2
                    Map.Entry<Boolean, Doctor> validateSignup = DiabetesInterface.addDoctor();
                    if (validateSignup.getKey()) {
                        DiabetesInterface.menu(validateSignup.getValue());
                    }
                }
                break;

            // Other cases if necessary
        }
    }
}
