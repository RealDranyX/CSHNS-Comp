package items.materials;

import helpers.Identifier;
import helpers.Translateable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Material implements Translateable {

    private Identifier id;
    private Settings settings;

    public Material(Identifier id, Settings materialSettings) {
        this.id = id;
        this.settings = materialSettings;
    }

    public String getName() {
        return this.translate("en");
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
        private float weight = 0.0F;
        private MaterialManager.Materials materialType = MaterialManager.Materials.NONE;
        public Settings setCost(float cost) {
            this.cost = cost;
            return this;
        }
        public Settings setWeight(float weight) {
            this.weight = weight;
            return this;
        }
        public Settings setMaterialType(MaterialManager.Materials materialType) {
            this.materialType = materialType;
            return this;
        }
        public MaterialManager.Materials type() {
            return this.materialType;
        }
    }
}
