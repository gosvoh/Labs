package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Удалить все элементы из словаря, ключ которых превышает заданный
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class RemoveGreaterKey implements Command {
    private HashMap<Integer, Universe> map;
    private int key;

    public RemoveGreaterKey(HashMap<Integer, Universe> map, int key) {
        this.map = map;
        this.key = key;
    }

    @Override
    public void execute() {
        map.keySet().forEach(k -> {
            if (k > key)
                map.remove(k);
        });
    }
}
