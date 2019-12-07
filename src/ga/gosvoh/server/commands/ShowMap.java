package ga.gosvoh.server.commands;

import ga.gosvoh.server.Universe;
import ga.gosvoh.server.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Показать все элементы словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class ShowMap implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    /**
     * Конструктор класса
     */
    public ShowMap() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    public ShowMap(ConcurrentSkipListMap<Long, Universe> map) {
        this.map = map;
    }

    @Override
    public String execute(String[] cmd) {
        StringBuilder ret = new StringBuilder();
        map.forEach((k, v) -> ret.append("Ключ: ").append(k).append("; Значение: ").append(v).append("\n"));
        return ret.toString();
    }
}
