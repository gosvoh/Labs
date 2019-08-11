package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

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
     */
    MapInfo() {
        this.map = UniverseCollection.getUniverseHashMap();
    }

    @Override
    public void execute() {
        System.out.println("Тип коллекции: " + map.getClass() +
                "\nДата инициализации: " + UniverseCollection.getInitDate() +
                "\nКоличество элементов: " + map.size());
    }
}
