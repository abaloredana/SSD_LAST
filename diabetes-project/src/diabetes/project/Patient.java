/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;


import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import diabetes.project.Treatment;

/**
 *
 * @author loredana
 */
public class Patient {
    private String name;
    private int typeOfDiabetes;
    private double bmi;
    private int age;
    private int insulinProd; //1= NO PROD; 2= HYPO; 3= NORMAL; 4=HYPER
    private boolean insulinRes;
    private boolean hypotension;
    private boolean dyslipidemia;
    private boolean pad;
    private boolean nafld;
    private boolean osteoporosis;
    public Set<Treatment> treatments = new HashSet<>();
    private int doctorId;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public String toString() {
        return "Patient{" + "name=" + name + ", treatments=" + treatments + '}';
    }
    
    public void applyTreatment(String name, boolean shouldBeApplied, double priority){
       Treatment treatment = new Treatment(name, shouldBeApplied, priority);
       this.treatments.add(treatment);
    }
    
    public void addTreatment(Treatment treatment) {
        this.treatments.add(treatment);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTypeOfDiabetes(int typeOfDiabetes) {
        this.typeOfDiabetes = typeOfDiabetes;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setInsulinProd(int insulinProd) {
        this.insulinProd = insulinProd;
    }

    public void setInsulinRes(boolean insulinRes) {
        this.insulinRes = insulinRes;
    }

    public void setHypotension(boolean hypotension) {
        this.hypotension = hypotension;
    }

    public void setDyslipidemia(boolean dyslipidemia) {
        this.dyslipidemia = dyslipidemia;
    }

    public void setPad(boolean pad) {
        this.pad = pad;
    }

    public void setNafld(boolean nafld) {
        this.nafld = nafld;
    }

    public void setOsteoporosis(boolean osteoporosis) {
        this.osteoporosis = osteoporosis;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public String getName() {
        return name;
    }

    public int getTypeOfDiabetes() {
        return typeOfDiabetes;
    }

    public double getBmi() {
        return bmi;
    }

    public int getAge() {
        return age;
    }

    public int getInsulinProd() {
        return insulinProd;
    }

    public boolean isInsulinRes() {
        return insulinRes;
    }

    public boolean isHypotension() {
        return hypotension;
    }

    public boolean isDyslipidemia() {
        return dyslipidemia;
    }

    public boolean isPad() {
        return pad;
    }

    public boolean isNafld() {
        return nafld;
    }

    public boolean isOsteoporosis() {
        return osteoporosis;
    }

    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public Patient(String name, int typeOfDiabetes, double bmi, int age, int insulinProd, boolean insulinRes, boolean hypotension, boolean dyslipidemia, boolean pad, boolean nafld, boolean osteoporosis) {
        this.name = name;
        this.typeOfDiabetes = typeOfDiabetes;
        this.bmi = bmi;
        this.age = age;
        this.insulinProd = insulinProd;
        this.insulinRes = insulinRes;
        this.hypotension = hypotension;
        this.dyslipidemia = dyslipidemia;
        this.pad = pad;
        this.nafld = nafld;
        this.osteoporosis = osteoporosis;
        
    }
    

   

}

    

