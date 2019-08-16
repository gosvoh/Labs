package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Удалить все элементы из словаря, ключ которых превышает заданный
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RemoveGreaterKey implements Command {
    private ConcurrentHashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с ключом элемента
     */
    public RemoveGreaterKey(String[] cmd) {
        this.map = UniverseCollection.getUniverseConcurrentHashMap();
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
