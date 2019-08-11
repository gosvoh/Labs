package ga.gosvoh;

public class Universe {
    private String name, number;

    public Universe(String number, String name) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Universe{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}
