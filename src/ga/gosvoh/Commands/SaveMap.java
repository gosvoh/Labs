package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;

/**
 * Сохранение объекта класса Map в Json файл по указанному пути
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class SaveMap implements Command {
    private HashMap<Integer, Universe> map;

    public SaveMap(HashMap<Integer, Universe> map) {
        this.map = map;
    }

    @Override
    public void execute() {

    }
}
