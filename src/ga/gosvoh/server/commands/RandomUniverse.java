package ga.gosvoh.server.commands;

import ga.gosvoh.server.Position;
import ga.gosvoh.server.Universe;
import ga.gosvoh.server.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Добавить новую рандомную вселенную в этот бренный мир
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RandomUniverse implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    /**
     * Конструктор класса
     */
    public RandomUniverse() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        Long key;
        do {
            key = Math.round(Math.random() * Integer.MAX_VALUE);
        } while (map.containsKey(key));

        map.put(key, new Universe(key, "Universe " + key, new Position(
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)))));
        new SaveMap().execute(cmd);
        return ("Вселенная добавлена!");
    }
}
