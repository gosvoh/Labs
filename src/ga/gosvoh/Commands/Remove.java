package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Удалить элеамент с заданным ключом из словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Remove implements Command {
    private ConcurrentHashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с ключом элемента
     */
    public Remove(String[] cmd) {
        this.map = UniverseCollection.getUniverseConcurrentHashMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        try {
            map.remove(Integer.parseInt(cmd[1]));
            System.out.println("Элемент успешно удалён!");
            new SaveMap().execute();
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа, попробуйте ввести комнду заново.");
        }
    }
}
