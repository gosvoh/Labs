package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Показать все элементы словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class ShowMap implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public ShowMap() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    public ShowMap(ConcurrentSkipListMap<Integer, Universe> map) {
        this.map = map;
    }

    @Override
    public void execute() {
        map.forEach((k, v) -> System.out.println("Ключ: " + k + "; Значение: " + v));
    }
}
