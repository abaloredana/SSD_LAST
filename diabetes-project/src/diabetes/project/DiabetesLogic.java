/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diabetes.project;

import java.sql.SQLException;
import java.util.Map;

public class DiabetesLogic {
	/**
	 * @param args the command line arguments
	 */

	public static void main(String[] args) {

		Utils.suppressDroolsLogMessages();
		DBManager.generateTablesIfNeeded();

		int choice = DiabetesInterface.welcomePrompt();

		switch (choice) {
		case 1:
			signUpFlow();
			break;
		case 2:
			logInFlow();
			break;
		default:
			DiabetesInterface.invalidChoicePrompt();

		}

	}

	public static void signUpFlow() {
		DiabetesInterface.addDoctor();
		logInFlow();
	}

	public static void logInFlow() {
		Map.Entry<Boolean, Doctor> validateLogin = DiabetesInterface.isUserCorrect();
		boolean authorized = validateLogin.getKey();
		Doctor loggedInDoctor = validateLogin.getValue();

		if (authorized && loggedInDoctor != null) {
			DiabetesInterface.logInVeredict(authorized);
			DiabetesInterface.menu(loggedInDoctor);
		} else {
			// If authorization fails, call the addDoctorFlow again
			signUpFlow();
		}
	}
}
