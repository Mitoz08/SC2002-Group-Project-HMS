package DataObject.PharmacyObjects;

/**
 * The MedicineData class represents data about a medicine in a pharmacy system.
 * It includes details such as the medicine's unique ID, name, quantity, and minimum stock level.
 */
public class MedicineData {

    /** The ID of the medicine. */
    public int ID;

    /** The name of the medicine. */
    public String name;

    /** The amount in stock. */
    public int amount;

    /** The minimum amount required in stock. */
    public int minStock;

    /** The length of the formatted ID string. */
    public static final int IDLength = 8;

    /** The prefix of the formatted ID string. */
    public static final String IDPrefix = "MED";

    // Constructor

      /**
     * Constructs a new MedicineData object with the specified attributes.
     *
     * @param ID      the unique ID of the medicine
     * @param name    the name of the medicine
     * @param amount  the current amount in stock
     * @param minStock the minimum stock level required
     */
    public MedicineData(int ID, String name, int amount, int minStock) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.minStock = minStock;
    }

    // Getters

    /**
     * Gets the ID of the medicine
     * @return the ID of the medicine
     */
    public int getID() {return this.ID;}

    /**
     * Gets the medicine name
     * @return the medicine name
     */
    public String getName() {return this.name;}

    /**
     * Gets the amount of that medicine
     * @return the amount of that medicine
     */
    public int getAmount() {return this.amount;}

    /**
     * Gets the minimum stock requirement of that medicine
     * @return the minimum stock requirement of that medicine
     */
    public int getMinStock() {return this.minStock;}

    /**
     * Returns the formatted ID string for the medicine, which includes a prefix and zero-padded ID.
     *
     * @return the formatted ID string
     */
    public String getIDString() {
        return getStrID(this.ID);
    }

    /**
     * Returns the status of the medicine's stock level. True if the amount is below the minimum stock, false otherwise.
     *
     * @return true if stock level is below minimum, false otherwise
     */
    public boolean getLevelStatus() {
        return this.amount < this.minStock;
    }

    /**
     * Generates a formatted ID string with a prefix and zero-padded ID based on the given integer ID.
     *
     * @param ID the unique ID of the medicine
     * @return the formatted ID string
     */
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
