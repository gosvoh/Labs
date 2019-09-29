package ga.gosvoh.commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Удалить элеамент с заданным ключом из словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Remove implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public Remove() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        try {
            map.remove(Integer.parseInt(cmd[1]));
            new SaveMap().execute(cmd);
            return ("Элемент успешно удалён!");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return ("Неверный формат ключа, попробуйте ввести комнду заново.");
        }
    }
}
