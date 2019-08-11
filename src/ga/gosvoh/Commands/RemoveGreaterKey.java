package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.HashMap;

/**
 * Удалить все элементы из словаря, ключ которых превышает заданный
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class RemoveGreaterKey implements Command {
    private HashMap<Integer, Universe> map;
    private String[] cmd;

    public RemoveGreaterKey(String[] cmd) {
        this.map = UniverseCollection.getUniverseHashMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        int key;
        try {
            key = Integer.parseInt(cmd[1]);
            map.keySet().forEach(k -> {
                if (k > key)
                    map.remove(k);
            });
            System.out.println("Элементы успешно удалены!");
            new SaveMap().execute();
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа, попробуйте ввести комнду заново");
        }
    }
}
