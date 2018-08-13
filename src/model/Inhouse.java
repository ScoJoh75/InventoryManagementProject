package model;

public class Inhouse extends Part {
    private int machineID;

    public Inhouse(int partID, String name, double price, int inStock, int min, int max, int machineID) {
        setPartID(partID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);
    }

    public int getMachineID() {
        return machineID;
    } // end getMachineID

    private void setMachineID(int machineID) {
        this.machineID = machineID;
    } // end setMachineID
} // end Inhouse
