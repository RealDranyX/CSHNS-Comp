package items;

import helpers.Identifier;
import helpers.Map;

public class ItemManager {

    public enum Items {
        NONE,

        SWORD,
        SHIELD,
        BOW,
        PLATE_ARMOR,
        CHAINMAIL
    }

    public static void init() {
        System.out.println("Initializing Items");
    }

    public Map<Items, String> names = new Map<>();
    public Map<Items, Identifier> ids = new Map<>();
    public Map<Items, Item> items = new Map<>();

    private Item createItem(String name, Item.Settings settings) {
        names.put(settings.type(), name);
        Identifier id = new Identifier(name, "Item");
        ids.put(settings.type(), id);
        Item item = new Item(id, settings);
        items.put(settings.type(), item);
        return item;
    }
}
