import java.util.*;

/**
 * A player inventory class which managers and allow to store a players items in a TreeMap. This class allows to search
 * through the inventory, add items to the inventory and remove items from the inventory.
 *
 * @author Andrew Booth
 * @version 28/05/2025
 */
public class PlayerInventory
{
    private final TreeMap<String,Integer> inventory = new TreeMap<>();
    private final static int INVENTORY_SIZE = 10;
    private int itemCount;
    private final ArrayList<String> itemNames = new ArrayList<>();
    private String selectedItemName;

    /**
     * constructor for Players Inventory which clear Inventory at creation and declare zero items in itemCount
     */
    public PlayerInventory()
    {
        clearInventory();
        itemCount = 0;
    }

    // getter methods
    //__________________________________________________________________________________________________________________

    /**
     * gets item count of how many items is in inventory
     * @return itemCount        -int value represent how many items in inventory.
     */
    public int getItemCount()
    {
        return itemCount;
    }

    /**
     * gets the Max size allowed for the Inventory.
     * @return INVENTORY_SIZE       -static final int value to represent the max size allowed for the inventory.
     */
    public int getInventorySize()
    {
        return INVENTORY_SIZE;
    }

    /**
     * gets an instance of the TreeMap inventory.
     * @return inventory        -a TreeMap to represent the name of item and amount of Potions in inventory.
     */
    public TreeMap<String,Integer> getInventory()
    {
        return inventory;
    }

    public ArrayList<String> getItemNames()
    {
        return itemNames;
    }

    public String getSelectedItemName()
    {
        return selectedItemName;
    }


    //Utilities methods
    //__________________________________________________________________________________________________________________


    /**
     * clears the inventory of all items
     */
    public void clearInventory()
    {
        inventory.clear();
    }

    /**
     * searches through each item in the Inventory for item passed through the parameter. If item is found in inventory
     * return true, otherwise return false.
     * @param item      - A string type parameter to represent the name of item searching for.
     * @return          - a boolean value to represent whether item the is found.
     */
    public boolean searchInventory(String item)
    {
        for (String key : inventory.keySet())
        {
            if (key.equals(item))
            {
                selectedItemName = key;
                return true;
            }
        }
        return false;
    }

    /**
     * adds an item passed through the item parameter to the Inventory by using the searchInventory() method. If item is
     * found then gets inventory item amount value, adds 1 to that value and then replaces the old value with the new
     * value.Otherwise, adds the item into the inventory and set the item amount value to 1.
     * @param item      -A string type parameter to represent the name of item to be added to inventory.
     */
    public void addToInventory(String item)
    {
        if(searchInventory(item))
        {
            int itemAmount =  getInventory().get(item) +1;
            getInventory().replace(item, getInventory().get(item),itemAmount);
        }
        else
        {
            getInventory().put(item,1);
            itemNames.add(item);
            itemCount++;
        }
    }

    /**
     * gets the amount of item stored in the inventory passed through the item parameter. Firstly check if item is in
     * the inventory using the searchInventory() method, if item is found then using the getInventory() method, then
     * get the value contained in the Inventory TreeMap using the item parameter as a key and return that value.
     * Otherwise return
     * @param item          -A string type parameter to represent the name of item that require the amount of.
     * @return itemAmount   -A int value to represent the amount of that item in inventory otherwise default 0.
     */
    public int getItemAmount(String item)
    {
        int itemAmount = 0;
        if(searchInventory(item))
        {
            itemAmount = getInventory().get(item);
        }
        return itemAmount;
    }

    /**
     * removes item, passed through the item parameter from the inventory. Firstly checks the quantity of item stored in
     * inventory, if quantity is greater than 1, get that amount, minus 1 and then replace the old value with the new
     * value leaving 1 or more quantity in inventory. Otherwise, remove item from inventory, as item quantity will
     * become 0 after minus the 1 indicating no item is left.
     * @param item      - A string type parameter to represent the name of item to remove.
     */
    public void removeItem(String item)
    {
        if (getItemAmount(item) >1)
        {
            int itemAmount = getInventory().get(item) - 1;
            getInventory().replace(item, getInventory().get(item),itemAmount);
        }
        else getInventory().remove(item);
    }
}
