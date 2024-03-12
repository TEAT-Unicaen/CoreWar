package coreWar;

import coreWar.vmcore.virtualMachine.Vm;
import java.util.List;


public class demo {
    
    public static void main(String[] args) throws InterruptedException {
        Supervisor supervisor = new Supervisor();
        String template = "MOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\nMOV 0, 15\n";
        String template2 = "JMP 4 0\nMOV 2 -1\nJMP -1 0\nDAT 9 0\nSPL -2 0\nSPL 4 0\nADD #-16 -3\nMOV -4 @-16\nJMP -4 0\nSPL 2 0\nJMP -1 0\nMOV 0 1\n";
        System.out.println("coucou");
        supervisor.createVm(1058,template,template2);
        System.out.println("coucou2");
        supervisor.createVm(1058,template2,template);
        System.out.println("attente ici");
        List<Vm> res = supervisor.getValues();
        System.out.println("fin attente ici\n" + res.size());
        for (Vm current : res) {
            System.out.println(current.tick + " | " + current.winner + "\nInstances : " + current.playersInstance[0] + " | " + current.playersInstance[1] + " | " + current.playersInstance[2] + "\nMVP : " + current.getMVP());
        }
    }
}

