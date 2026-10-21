package items;

import helpers.Identifier;
import helpers.Translateable;
import items.materials.Material;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Item implements Translateable {

    public Item(Identifier id, Item.Settings settings) {
        this.id = id;
        this.settings = settings;
    }

    private Identifier id;
    private Settings settings;

    public String getName() {
        return translate("en");
    }

    @Override
    public String translate(String language) {
        String path;

        switch(language) {
            case "en": {
                path = "src/assets/data/lang/en.json";
                break;
            }
            case "de": {
                path = "src/assets/data/lang/de.json";
                break;
            }
            default: return "LANG_ERROR";
        }
        try {
            Object obj = new JSONParser().parse(new FileReader(path));
            JSONObject jo = (JSONObject) obj;

            return (String) jo.get(this.id.getId());

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Settings {
        private float cost = 0.00F;
        private HashMap<Material, Integer> requiredMaterials = new HashMap<>();
        private ItemManager.Items type = ItemManager.Items.NONE;

        public Item.Settings setCost(float cost) {
            this.cost = cost;
            return this;
        }
        public Item.Settings setType(ItemManager.Items type) {
            this.type = type;
            return this;
        }
        public ItemManager.Items type() {
            return this.type;
        }
        public Item.Settings addRequiredMaterial(Material material, int amount) {
            this.requiredMaterials.put(material, amount);
            return this;
        }
    }
}
