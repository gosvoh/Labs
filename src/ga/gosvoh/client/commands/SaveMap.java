package ga.gosvoh.client.commands;

import ga.gosvoh.client.JsonWriter;
import ga.gosvoh.client.Universe;
import ga.gosvoh.client.UniverseCollection;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Сохранение объекта класса HashMap в Json файл по указанному пути
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings({"WeakerAccess", "TryWithIdenticalCatches"})
public class SaveMap implements Command {

    /**
     * Конструктор класса
     */
    public SaveMap() {
    }

    @Override
    public void execute() {
        ConcurrentSkipListMap<Long, Universe> map = UniverseCollection.getUniverseConcurrentSkipListMap();

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
