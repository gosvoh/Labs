package ga.gosvoh.commands;

import ga.gosvoh.JsonWriter;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Сохранение объекта класса HashMap в Json файл по указанному пути
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings({"WeakerAccess", "TryWithIdenticalCatches"})
public class SaveMap implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    /**
     * Конструктор класса
     */
    public SaveMap() {
    }

    @Override
    public void execute() {
        map = UniverseCollection.getUniverseConcurrentSkipListMap();

        try {
            JsonWriter jsonWriter = new JsonWriter(UniverseCollection.getMainFile().toPath());
            jsonWriter.writeToFile(map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
