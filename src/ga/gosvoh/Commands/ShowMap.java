package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.HashMap;

/**
 * Показать все элементы словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class ShowMap implements Command {
    private HashMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public ShowMap() {
        this.map = UniverseCollection.getUniverseHashMap();
    }

    @Override
    public void execute() {
        map.forEach((k, v) -> System.out.println("Ключ: " + k + "; Значение: " + v));
    }
}
