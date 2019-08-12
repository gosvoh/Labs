package ga.gosvoh.Commands;

import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.HashMap;

/**
 * Удалить элеамент с заданным ключом из словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class Remove implements Command {
    private HashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с ключом элемента
     */
    public Remove(String[] cmd) {
        this.map = UniverseCollection.getUniverseHashMap();
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
