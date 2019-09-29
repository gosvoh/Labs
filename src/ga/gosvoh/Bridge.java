package ga.gosvoh;

/**
 * Мост, находящийся на планете
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Bridge extends Planet {

    private String name;

    /**
     * Конструктор класса
     *
     * @param name имя моста
     */
    public Bridge(String name) {
        super(name);
        this.name = name;
    }
}
