package coreWar;

import coreWar.vmcore.virtualMachine.Vm;
import coreWar.vmcore.virtualMachine.ThreadVm;

import java.util.ArrayList;
import java.util.List;


public class demo {
    
    public static void main(String[] args) throws InterruptedException {
        Supervisor supervisor = new Supervisor();
        String template = "MOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\n";
        System.out.println("coucou");
        supervisor.createVm(1058,template,template);
        System.out.println("attente ici");
        List<Vm> res = supervisor.getValues();
        System.out.println("fin attente ici");
    }

}

