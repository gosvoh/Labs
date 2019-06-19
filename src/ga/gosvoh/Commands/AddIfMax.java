package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class AddIfMax implements Command {
    private HashMap<Integer, Universe> map;
    private Universe element;

    /**
     * Контруктор класса
     *
     * @param map     словарь, куда требуется добавить элемент
     * @param element элемент в формате Json
     */
    public AddIfMax(HashMap<Integer, Universe> map, Universe element) {
        this.map = map;
        this.element = element;
    }

    @Override
    public void execute() {

    }
}
