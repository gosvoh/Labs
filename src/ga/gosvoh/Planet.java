package ga.gosvoh;

/**
 * Планета, находящаяся во вселенной
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
class Planet extends Universe {

    /**
     * Конструктор класса
     *
     * @param name имя планеты
     */
    Planet(String name) {
        super(Double.toHexString(Math.round(Math.random() * Integer.MAX_VALUE)), name);
    }
}
