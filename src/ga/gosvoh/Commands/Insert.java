package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Добавить элемент в словарь
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class Insert implements Command {
    private HashMap<Integer, Universe> map;
    private int key;
    private Universe element;

    /**
     * Конструктор класса
     *
     * @param map     словарь, куда требуется добавить элемент
     * @param key     ключ, по которому будет доступен элемент
     * @param element елемент в формате Json
     */
    public Insert(HashMap<Integer, Universe> map, int key, Universe element) {
        this.map = map;
        this.key = key;
        this.element = element;
    }

    @Override
    public void execute() {

    }
}
