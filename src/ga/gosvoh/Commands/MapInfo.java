package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Показать информацию о словаре
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class MapInfo implements Command {
    private ConcurrentHashMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public MapInfo() {
        this.map = UniverseCollection.getUniverseConcurrentHashMap();
    }

    @Override
    public void execute() {
        System.out.println("Тип коллекции: " + map.getClass() +
                "\nДата инициализации: " + UniverseCollection.getInitDate() +
                "\nКоличество элементов: " + map.size());
    }
}
