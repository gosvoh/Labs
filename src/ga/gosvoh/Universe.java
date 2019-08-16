package ga.gosvoh;

/**
 * Вселенная
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
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
     * Получить имя объекта
     *
     * @return Имя объекта
     */
    public String getName() {
        return name;
    }

    /**
     * Получить номер объекта
     *
     * @return Номер объекта
     */
    public String getNumber() {
        return number;
    }
}
