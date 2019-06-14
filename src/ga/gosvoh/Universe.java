package ga.gosvoh;

public class Universe {
    private String name;

    public Universe(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Universe{" +
                "name='" + name + '\'' +
                '}';
    }
}
