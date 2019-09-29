package ga.gosvoh;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Класс-оболочка для словаря universeHashMap, реализованного с помощью ConcurrentHashMap
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see Universe
 */
@SuppressWarnings("WeakerAccess")
public class UniverseCollection {
    private static Date initDate = new Date();
    private static ConcurrentSkipListMap<Integer, Universe> universeConcurrentSkipListMap;
    private static File mainFile;

    /**
     * Конструктор класса
     *
     * @param filePath путь до файла
     */
    public UniverseCollection(String filePath) {
        universeConcurrentSkipListMap = new ConcurrentSkipListMap<>();
        mainFile = new File(filePath);

        if (!Files.exists(mainFile.toPath())) {
            initCollection();
        } else {
            try {
                universeConcurrentSkipListMap = new JsonReader(mainFile).readUniverseConcurrentSkipListMap();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (universeConcurrentSkipListMap == null || universeConcurrentSkipListMap.isEmpty()) {
                System.out.println("Неверный фотмат файла, создаю новый словарь вселенных!");
                initCollection();
            }
        }

        universeConcurrentSkipListMap.values().forEach(universe -> initDate = initDate.compareTo(universe.getBirthDate()) >= 0 ? universe.getBirthDate() : initDate);
    }

    public UniverseCollection(ConcurrentSkipListMap<Integer, Universe> universeConcurrentSkipListMap) {
        UniverseCollection.universeConcurrentSkipListMap = universeConcurrentSkipListMap;
    }

    /**
     * Получить словарь объектов класса Universe
     *
     * @return Словарь объектов класса Universe
     * @see Universe
     */
    public static ConcurrentSkipListMap<Integer, Universe> getUniverseConcurrentSkipListMap() {
        return universeConcurrentSkipListMap;
    }

    public static void setUniverseConcurrentSkipListMap(ConcurrentSkipListMap<Integer, Universe> universeConcurrentSkipListMap) {
        UniverseCollection.universeConcurrentSkipListMap = universeConcurrentSkipListMap;
        CommandManager.ExecuteCommand("show");
    }

    /**
     * Получить дату и время инициализации
     *
     * @return Дата и время инициализации
     */
    public static Date getInitDate() {
        return initDate;
    }

    /**
     * Получить файл со словарём объектов класса Universe в формате Json
     *
     * @return Файл со словарём объектов класса Universe в формате Json
     */
    public static File getMainFile() {
        return mainFile;
    }

    /**
     * Создать коллекцию со случайными вселенными
     */
    private void initCollection() {
        for (int i = 0; i < 10; i++) {
            Universe universe = new Universe(Long.toHexString(Math.round(Math.random() * Long.MAX_VALUE)),
                    "Universe " + (i + 1), new Position(
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE))));
            universeConcurrentSkipListMap.put(i, universe);
        }
    }

    /**
     * Отсортировать словарь ConcurrentSkipListMap по значениям (костыль для сортировки)
     *
     * @param map коллекция для сортировки
     * @return отсортированная коллекция по значению
     */
    public static ConcurrentSkipListMap<Integer, Universe> sortByValues(ConcurrentSkipListMap<Integer, Universe> map) {
        Comparator<Integer> valueComparator = Comparator.comparing(map::get);
        ConcurrentSkipListMap<Integer, Universe> sortedByValues = new ConcurrentSkipListMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    public static ConcurrentSkipListMap<Integer, Universe> getSortedCollection() {
        return sortByValues(universeConcurrentSkipListMap);
    }

    /**
     * Добавить элемент в словарь
     */
    public void put(Integer key, Universe value) {
        if (universeConcurrentSkipListMap != null)
            universeConcurrentSkipListMap.put(key, value);
    }
}
