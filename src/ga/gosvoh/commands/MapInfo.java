package ga.gosvoh.commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Показать информацию о словаре
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class MapInfo implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    /**
     * Конструктор класса
     */
    public MapInfo() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        return ("Тип коллекции: " + map.getClass() +
                "\nДата инициализации: " + UniverseCollection.getInitDate() +
                "\nКоличество элементов: " + map.size());
    }
}
