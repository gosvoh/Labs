package ga.gosvoh.Commands;

import ga.gosvoh.JsonWriter;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.io.IOException;
import java.util.HashMap;

/**
 * Сохранение объекта класса Map в Json файл по указанному пути
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class SaveMap implements Command {
    private HashMap<Integer, Universe> map;

    SaveMap() {
        this.map = UniverseCollection.getUniverseHashMap();
    }

    @Override
    public void execute() {
        try (JsonWriter jsonWriter = new JsonWriter(UniverseCollection.mainFile)) {
            jsonWriter.writeToFile(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл сохранён!");
    }
}
