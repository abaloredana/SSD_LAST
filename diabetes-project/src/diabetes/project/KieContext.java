/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package diabetes.project;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;

/**
 *
 * @author loredana
 */
public class KieContext {
    
    private KieServices ks;
    private KieContainer kc;

    public KieContext(KieServices kieServices, KieContainer kieContainer) {
        this.ks = kieServices;
        this.kc = kieContainer;
    }

    // Getters
    public KieServices getKieServices() {
        return ks;
    }

    public KieContainer getKieContainer() {
        return kc;
    }
}
