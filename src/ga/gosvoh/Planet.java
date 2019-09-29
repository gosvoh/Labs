package ga.gosvoh;

/**
 * Планета, находящаяся во вселенной
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
class Planet extends Universe {

    private String name;

    /**
     * Конструктор класса
     *
     * @param name имя планеты
     */
    Planet(String name) {
        super(Double.toHexString(Math.round(Math.random() * Integer.MAX_VALUE)), name, Position.zero());
        this.name = name;
    }
}
