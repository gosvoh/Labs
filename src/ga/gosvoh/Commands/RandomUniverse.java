package ga.gosvoh.Commands;

import ga.gosvoh.Position;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Добавить новую рандомную вселенную в этот бренный мир
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RandomUniverse implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public RandomUniverse() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        Integer key;
        do {
            key = Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE));
        } while (map.containsKey(key));

        map.put(key, new Universe(Long.toHexString(Math.round(Math.random() * Long.MAX_VALUE)),
                "Universe " + key, new Position(
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)))));
        new SaveMap().execute(cmd);
        return ("Вселенная добавлена!");
    }
}
