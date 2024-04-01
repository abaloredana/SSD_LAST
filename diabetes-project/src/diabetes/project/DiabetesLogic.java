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
		if(!doctorTableExists && !patientTableExists && !treatmentTableExists) {
			DBManager.createTables();
		}
		
		int choice = DiabetesInterface.welcomePrompt();

		switch (choice) {
		case 1:
			DiabetesInterface.addDoctor();
		case 2:
			Map.Entry<Boolean, Doctor> validateLogin = DiabetesInterface.isUserCorrect();
			boolean authorized = validateLogin.getKey();
			Doctor logedInDoctor = validateLogin.getValue();

			if (authorized && logedInDoctor != null) {
				DiabetesInterface.logInVeredict(authorized);
				DiabetesInterface.menu(logedInDoctor);
			}
			
		default:
			DiabetesInterface.invalidChoicePrompt();

		}

	}
}
