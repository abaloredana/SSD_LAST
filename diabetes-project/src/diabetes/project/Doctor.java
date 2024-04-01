/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;


/**
 *
 * @author loredana
 */

public class Doctor {
    public String username;
    public String password;
    public int id;
    //public static Set<Patient> patients = new HashSet<>();

    public void setId(int id) {
        this.id = id;
    }
    
    public Doctor(String username, String password) {
        this.username = username;
        setPassword(password);
    }

    private void setPassword(String password) {
        this.password = Utils.hashPassword(password);
    }

  
    
    // Getter for username
    public String getUsername() {
        return username;
    }

    // This is a demonstration purpose method.
    // In a real-world scenario, you would not have a method to get the password hash directly.
    public String getPassword() {
        return password; 
    }
    
    public int getId() {
        return id;
    }

    // You can add other methods here as needed, for example, a method to verify a password input.
}

