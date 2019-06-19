package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Показать информацию о словаре
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class MapInfo implements Command {
    private HashMap<Integer, Universe> map;

    /**
     * Конструктор класса
     *
     * @param map словарь, о котором нужно вывести информацию
     */
    public MapInfo(HashMap<Integer, Universe> map) {
        this.map = map;
    }

    @Override
    public void execute() {
        System.out.println("Тип коллекции: " + map.getClass() +
                "\nДата инициализации: " +
                "\nКоличество элементов: " + map.size());
    }
}
