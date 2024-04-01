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
    public String name;
    public boolean shouldBeApplied;
    public double priority;
    public  int id;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

   

    @Override
	public String toString() {
		return "Treatment name = " + name ;
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
