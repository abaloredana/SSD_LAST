/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 *
 * @author beatr
 */

public class PatientTest {

    @Test
    public void testPatient1InsulinRule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient1", 1, 25, 23, 1, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(1, firedRules);
        assertEquals(1, patient.getTreatments().size());
        assertEquals("Insulin", patient.getTreatments().iterator().next().getName());
        
        System.out.println("Test testPatient1InsulinRule is correct.");
    }

    @Test
    public void testPatient2InsulinRule() {  
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");
      
        Patient patient = new Patient("Patient2", 1, 25, 23, 2, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(1, firedRules);
        assertEquals(1, patient.getTreatments().size());
        assertEquals("Insulin", patient.getTreatments().iterator().next().getName());
        
        System.out.println("Test testPatient2InsulinRule is correct.");
    }
    
    @Test
    public void testPatient3Metformin1Rule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient3", 1, 25, 23, 3, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(1, firedRules);
        assertEquals(1, patient.getTreatments().size());
        assertEquals("Metformin", patient.getTreatments().iterator().next().getName());
        
        System.out.println("Test testPatient3Metformin1Rule is correct.");
    }

@Test
    public void testPatient4Metformin2Rule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient4", 1, 25, 23, 4, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(1, firedRules);
        assertEquals(1, patient.getTreatments().size());
        assertEquals("Metformin", patient.getTreatments().iterator().next().getName());
        
        System.out.println("Test testPatient4Metformin2Rule is correct.");
    }

@Test
    public void testPatient5() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient5", 2, 25, 23, 3, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(11, firedRules);
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Metformin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Sitagliptin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Saxagliptin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Linagliptin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dulaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Exenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Lixisenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Semaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glimepiride")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glipizide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glyburide")));

        System.out.println("Test testPatient5 is correct.");
    }

    @Test
    public void testPatient6Rule() { 
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient6", 2, 25, 23, 4, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(8, firedRules);
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Metformin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dulaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Exenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Lixisenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Semaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Bexagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Canagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dapagliflozin")));
        
        System.out.println("Test testPatient6Rule is correct.");
    }
    
    @Test
    public void testPatient7Rule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient7", 2, 25, 23, 2, false, false, true, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(4, firedRules);
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Insulin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glimepiride")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glipizide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Glyburide")));
        
        System.out.println("Test testPatient7Rule is correct.");
    }
    
    @Test
    public void testPatient8Rule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient8", 2, 24, 23, 4, false, false, false, true, false, false);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(9, firedRules);
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Metformin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dulaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Exenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Lixisenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Bexagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Canagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dapagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Rosiglitazone")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Pioglitazone")));
        
        System.out.println("Test testPatient8Rule is correct.");
    }
    
     @Test
    public void testPatient9Rule() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.getKieClasspathContainer();
        KieSession ksession = kc.newKieSession("diabetesSession");

        Patient patient = new Patient("Patient9", 2, 24, 23, 4, true, false, false, true, true, true);
        ksession.insert(patient);
        int firedRules = ksession.fireAllRules();
        ksession.dispose();

        assertEquals(7, firedRules);
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Metformin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dulaglutide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Exenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Lixisenatide")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Bexagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Canagliflozin")));
        assertTrue(patient.getTreatments().stream().anyMatch(t -> t.getName().equals("Dapagliflozin")));
        
        System.out.println("Test testPatient9Rule is correct.");
    }
    
    
}
