package ga.gosvoh;

/**
 * Вселенная
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class Universe {
    private String name, number;

    /**
     * Конструктор класса
     *
     * @param number номер вселенной
     * @param name   имя вселенной
     */
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

    /**
     * Получить имя вселенной
     *
     * @return имя вселенной
     */
    public String getName() {
        return name;
    }

    /**
     * Получить номер вселенной
     *
     * @return номер вселенной
     */
    public String getNumber() {
        return number;
    }
}
