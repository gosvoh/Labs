package ga.gosvoh.Commands;

import ga.gosvoh.Position;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Добавить новую рандомную вселенную в этот бренный мир
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RandomUniverse implements Command {
    private ConcurrentHashMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public RandomUniverse() {
        this.map = UniverseCollection.getUniverseConcurrentHashMap();
    }

    @Override
    public void execute() {
        Integer key;
        do {
            key = Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE));
        } while (map.containsKey(key));

        map.put(key, new Universe(Long.toHexString(Math.round(Math.random() * Long.MAX_VALUE)),
                "Universe " + key, new Position(
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)))));
        System.out.println("Вселенная добавлена!");
        new SaveMap().execute();
    }
}
