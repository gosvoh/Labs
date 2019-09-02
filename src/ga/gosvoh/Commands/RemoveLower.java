package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Удалить все элементы из словаря, ключ которых меньше чем заданный
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RemoveLower implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с ключом элемента
     */
    public RemoveLower(String[] cmd) {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        int key;
        try {
            key = Integer.parseInt(cmd[1]);
            map.keySet().forEach(k -> {
                if (k < key)
                    map.remove(k);
            });
            System.out.println("Элементы успешно удалены!");
            new SaveMap().execute();
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа, попробуйте ввести комнду заново.");
        }
    }
}
