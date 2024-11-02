package DataObject.PharmacyObjects;

public class MedicineData {
    public int ID;
    public String name;
    public int amount;
    public int minStock;

    public static final int IDLength = 8;
    public static final String IDPrefix = "MED";

    public MedicineData(int ID, String name, int amount, int minStock) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.minStock = minStock;
    }


    public int getID() {return this.ID;}
    public String getName() {return this.name;}
    public int getAmount() {return this.amount;}
    public int getMinStock() {return this.minStock;}

    public String getIDString() {
        return getStrID(this.ID);
    }

    public boolean getLevelStatus() {
        return this.amount < this.minStock;
    }
//    public void print() {
//        //System.out.printf("%-8s:%-16s Amt:%-14d\n",getStrID(this.ID), this.name, this.amount);
//    }

    public static String getStrID(int ID) {
        StringBuilder str = new StringBuilder();
        String IDStr = String.valueOf(ID);

        str.append(IDPrefix);
        for (int i = 0; i < IDLength-IDPrefix.length()-IDStr.length(); i++) {
            str.append('0');
        }
        str.append(IDStr);
        return str.toString();
    }

}
