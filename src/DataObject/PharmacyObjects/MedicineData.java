package DataObject.PharmacyObjects;

public class MedicineData {
    public int ID;
    public String name;
    public int amount;

    private static final int IDLength = 8;
    private static final String IDPrefix = "MED";

    public MedicineData(int ID, String name, int amount) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
    }

    public void print() {
        System.out.printf("ID:%-6d Med:%-16s Amt:%-7d\n",this.ID, this.name, this.amount);
    }

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
