package vmcore;

//TODO : Implement instructions 

/**
 * This class represents a virtual machine. It contains a memory of instructions
 * The memory is the tab with Instructions where the players will moove
 */

public class VirtualMachine {
    public Instruction[] memory; //?
	public boolean active;
    public String name;

    // Cloning an existing VM, allowing taking memory snapshots or execution frames at a given time
	public VirtualMachine(VirtualMachine vm) {
        VirtualMachine cl = vm.clone();
        this.memory = cl.memory;
        this.name = cl.name;
        cl.UnregisterAndDestroyVm();
        this.active = true;
	}

    // Creates a new VM
    public VirtualMachine(String name, int memLength) {
        this.name = name;
        memory = new Instruction[memLength];
		for (int i = 0; i < memLength; i++) {
			memory[i] = new Instruction();
		}
        this.active = true;
	}

	public VirtualMachine clone() {
		VirtualMachine clone = new VirtualMachine(this.name+" clone", memory.length);
		Instruction[] temp = new Instruction[memory.length];
		for (int i = 0; i < memory.length; i++) {
			temp[i] = new Instruction();
			//TODO : add instructions
		}
		clone.memory = temp;
		return clone;
	}

    // Mark the VM as active and ready to be simulated for the supervisor
    public void RegisterVm() {
        this.active = true;
    }

    // Mark the VM as inactive and ignored by the supervisor
    public void UnregisterVm() {
        this.active = true;
    }

    // Mark the VM as inactive and empty for the supervisor, which will drop it, making the GC wiping it
    public void UnregisterAndDestroyVm() {
        this.memory = null;
        this.active = false;
    }
}