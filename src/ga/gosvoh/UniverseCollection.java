package ga.gosvoh;

import ga.gosvoh.Commands.ShowMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Класс-оболочка для словаря universeHashMap, реализованного с помощью ConcurrentHashMap
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see Universe
 */
public class UniverseCollection {
    private static Date initDate = new Date();
    private static ConcurrentSkipListMap<Integer, Universe> universeConcurrentSkipListMap = new ConcurrentSkipListMap<>();
    private static File mainFile;

    /**
     * Конструктор класса
     *
     * @param filePath путь до файла
     */
    public UniverseCollection(String filePath) {
        mainFile = new File(filePath);

        if (!Files.exists(mainFile.toPath())) {
            initCollection();
        } else {
            try {
                universeConcurrentSkipListMap = new JsonReader(mainFile).readUniverseConcurrentSkipListMap();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (universeConcurrentSkipListMap == null)
                initCollection();
        }

        universeConcurrentSkipListMap.values().forEach(universe -> initDate = initDate.compareTo(universe.getBirthDate()) >= 0 ? universe.getBirthDate() : initDate);
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
    private ConcurrentSkipListMap<Integer, Universe> sortByValues(ConcurrentSkipListMap<Integer, Universe> map) {
        Comparator<Integer> valueComparator = Comparator.comparing(map::get);
        ConcurrentSkipListMap<Integer, Universe> sortedByValues = new ConcurrentSkipListMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

    /**
     * Запуск 5 лабораотрной
     */
    void cli() {
        Scanner input = new Scanner(System.in);
        String line;

        System.out.println("Лабораторная работа по программированию. Версия " +
                Main.class.getPackage().getImplementationVersion() + "\n" +
                "Используйте ? или help для получения справки");

        while (true) {
            try {
                line = input.nextLine();
                new CommandManager(line);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        System.exit(0);
    }
}
