package ga.gosvoh.Commands;

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
public class SaveMap implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public SaveMap() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public void execute() {
        try {
            JsonWriter jsonWriter = new JsonWriter(UniverseCollection.getMainFile().toPath());
            jsonWriter.writeToFile(map);
            System.out.println("Файл сохранён!");
        } catch (IOException e) {
            System.out.println("У вас недостаточно прав для сохранения файла!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
