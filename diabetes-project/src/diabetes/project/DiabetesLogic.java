/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diabetes.project;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import static org.apache.tools.ant.dispatch.DispatchUtils.execute;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class DiabetesLogic {
    /**
     * @param args the command line arguments
     */
   
     public static void main(String[] args) {
        //DBManager.createTables();
        int choice = DiabetesInterface.welcomePrompt();
        
        switch(choice){
         case 1:  
            Map.Entry<Boolean, Doctor> validateLogin = DiabetesInterface.isUserCorrect();
            boolean authorized = validateLogin.getKey();
            Doctor logedInDoctor = validateLogin.getValue();
            
            if (authorized && logedInDoctor != null){
                DiabetesInterface.logInVeredict(authorized);
                DiabetesInterface.menu(logedInDoctor); 
            }



            case 2:  
                Map.Entry<Boolean, Doctor> validateSignup = DiabetesInterface.addDoctor();
                boolean authorized2 = validateSignup.getKey();
                Doctor signedUpDoctor = validateSignup.getValue();
            if (authorized2){
                DiabetesInterface.menu(signedUpDoctor); 
            }


            
            
     
     
        }
     
    }
}

    