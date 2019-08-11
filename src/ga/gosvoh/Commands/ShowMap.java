package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.HashMap;

/**
 * Показать все элементы словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class ShowMap implements Command {
    private HashMap<Integer, Universe> map;

    public ShowMap() {
        this.map = UniverseCollection.getUniverseHashMap();
    }

    @Override
    public void execute() {
        map.forEach((k, v) -> System.out.println("Ключ: " + k + "; Значение: " + v));
    }
}
