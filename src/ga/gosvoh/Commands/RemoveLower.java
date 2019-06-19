package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class RemoveLower implements Command {
    private HashMap<Integer, Universe> map;
    private String element;

    public RemoveLower(HashMap<Integer, Universe> map, String element) {

    }

    @Override
    public void execute() {

    }
}
