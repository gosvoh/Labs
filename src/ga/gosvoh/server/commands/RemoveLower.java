package ga.gosvoh.server.commands;

import ga.gosvoh.server.Universe;
import ga.gosvoh.server.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Удалить все элементы из словаря, ключ которых меньше чем заданный
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class RemoveLower implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    /**
     * Конструктор класса
     */
    public RemoveLower() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        long key;
        try {
            key = Long.parseLong(cmd[1]);
            map.keySet().forEach(k -> {
                if (k < key)
                    map.remove(k);
            });
            new SaveMap().execute(cmd);
            return ("Элементы успешно удалены!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return ("Неверный формат ключа, попробуйте ввести комнду заново.");
        }
    }
}
