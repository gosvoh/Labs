package ga.gosvoh.client;

/**
 * Планета, находящаяся во вселенной
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
class Planet extends Universe {

    /**
     * Конструктор класса
     *
     * @param name имя планеты
     */
    Planet(String name) {
        super(Math.round(Math.random() * Integer.MAX_VALUE), name, Position.zero());
    }
}
