package model;

public class Inhouse extends Part {
    private int machineID;

    public Inhouse(int partID, String name, double price, int instock, int min, int max, int machineID) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(instock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }

    public int getMachineID() {
        return machineID;
    } // end getMachineID

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    } // end setMachineID
} // end Inhouse
