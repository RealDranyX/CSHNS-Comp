package items.materials;

import helpers.Identifier;
import helpers.Map;


public class MaterialManager {

    public enum Materials {
        NONE,

        WOOD,
        STONE,
        IRON,
        CRYSTAL,
        DIAMOND,
        GOLD
    }

    public static final Map<Materials, String> names = new Map<>();
    public static final Map<Materials, Identifier> ids = new Map<>();

    public static final Map<Materials, Material> materials = new Map<>();


    // Material Creation Here
    public static final Material WOOD = createMaterial("wood", new Material.Settings()
            .setMaterialType(Materials.WOOD)
            .setCost(0.00F)
            .setWeight(0.00F)
    );

    public static final Material STONE = createMaterial("stone", new Material.Settings()
            .setMaterialType(Materials.STONE)
            .setCost(0.00F)
            .setWeight(0.00F)
    );
    public static final Material IRON = createMaterial("iron", new Material.Settings()
            .setMaterialType(Materials.IRON)
            .setCost(0.00F)
            .setWeight(0.00F)
    );
    public static final Material CRYSTAL = createMaterial("crystal", new Material.Settings()
            .setMaterialType(Materials.CRYSTAL)
            .setCost(0.00F)
            .setWeight(0.00F)
    );
    public static final Material DIAMOND = createMaterial("diamond", new Material.Settings()
            .setMaterialType(Materials.DIAMOND)
            .setCost(0.00F)
            .setWeight(0.00F)
    );
    public static final Material GOLD = createMaterial("gold", new Material.Settings()
            .setMaterialType(Materials.GOLD)
            .setCost(0.00F)
            .setWeight(0.00F)
    );


    public static void init() {
        System.out.println("Initializing Materials");
    }

    private static Material createMaterial(String name, Material.Settings settings) {
        names.put(settings.type(), name);

        Identifier id = new Identifier(name, "Materials");
        ids.put(settings.type(), id);

        Material material = new Material(id, settings);
        materials.put(settings.type(), material);

        return material;
    }
    public Material getMaterial(Materials material) {
        return materials.getKey(material);
    }
    public Material getMaterial(Identifier id) {
        return materials.getKey(ids.getValue(id));
    }
    public Material getMaterial(String name) {
        return materials.getKey(names.getValue(name));
    }


    public Identifier getId(Materials materialType) {
        return ids.getKey(materialType);
    }
    public Identifier getId(String name) {
        return ids.getKey(names.getValue(name));
    }
    public Identifier getId(Material material) {
        return ids.getKey(materials.getValue(material));
    }


}
