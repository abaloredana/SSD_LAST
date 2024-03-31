/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

/**
 *
 * @author loredana
 */
public class Treatment {
    private String name;
    private boolean shouldBeApplied;
    private double priority;

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Treatment{" + "name=" + name + ", shouldBeApplied=" + shouldBeApplied + ", priority=" + priority + '}';
    }

    public Treatment(String name, boolean shouldBeApplied, double priority) {
        this.name = name;
        this.shouldBeApplied = shouldBeApplied;
        this.priority = priority;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShouldBeApplied() {
        return shouldBeApplied;
    }

    public void setShouldBeApplied(boolean shouldBeApplied) {
        this.shouldBeApplied = shouldBeApplied;
    }

    
}
