/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.Logger;

/**
 *
 * @author loredana
 */
public class Utils {

	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean usernameIsValid(String username) {
		String pattern = "^\\p{Alpha}[a-zA-Z0-9_-]{5,19}$";
		return Pattern.matches(pattern, username);
	}

	public static boolean passwordIsValid(String password) {
		String pattern = "^(?=.*\\d)(?=.*\\p{Lower})(?=.*\\p{Upper})(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";
		return Pattern.matches(pattern, password);
	}

	public static boolean choiceIsValid(String choice) {
		String pattern = "^[0-9]+$";
		return Pattern.matches(pattern, choice);
	}

	public static boolean nameIsValid(String name) {
		String pattern = "^[a-zA-ZñÑ][a-zA-ZñÑ\\s'-]+$";
		return Pattern.matches(pattern, name);
	}

	public static boolean typeOfDiabetesIsValid(String typeOfDiabetes) {
		String pattern = "^[12]$";
		return Pattern.matches(pattern, typeOfDiabetes);
	}

	public static boolean bmiIsValid(String bmi) {
		String pattern = "^\\d+(\\.\\d+)?$";
		return Pattern.matches(pattern, bmi);
	}

	public static boolean ageIsValid(String age) {
		String pattern = "^[1-9]\\d*$";
		return Pattern.matches(pattern, age);
	}

	public static boolean insulinProdIsValid(String insulinProd) {
		String pattern = "^[1234]$";
		return Pattern.matches(pattern, insulinProd);
	}

	public static boolean yesNoIsValid(String input) {
		String pattern = "^[ynYN]$";
		return Pattern.matches(pattern, input);
	}

	public static void suppressDroolsLogMessages() {
		
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
		rootLogger.setLevel(Level.OFF); // Set to WARN to suppress DEBUG and INFO messages

		Logger droolsLogger = loggerContext.getLogger("org.drools");
		droolsLogger.setLevel(Level.OFF); 


	}
	
	



}
