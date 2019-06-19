package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Удалить элеамент с заданным ключом из словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class Remove implements Command {
    private HashMap<Integer, Universe> map;
    private int key;

    /**
     * Конструктор класса
     *
     * @param map словарь, откуда требуется удалить элемент
     * @param key ключ элемента
     */
    public Remove(HashMap<Integer, Universe> map, int key) {
        this.map = map;
        this.key = key;
    }

    @Override
    public void execute() {
        map.remove(key);
    }
}
