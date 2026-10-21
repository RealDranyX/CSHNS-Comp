package helpers;

public class Identifier {

    String name, idType, id;

    public Identifier(String name, String idType) {
        this.idType = idType;
        this.name = name;
        this.id = idType + ":" + name;
    }

    public boolean equals(Identifier id1) {
        return this.name.equals(id1.name) && this.idType.equals(id1.idType) && this.id.equals(id1.id);
    }

    public String toString() {
        return this.name;
    }
    public String getName() {
        return this.name;
    }
    public String getIdType() {
        return this.idType;
    }
    public String getId() {
        return this.id;
    }
}
