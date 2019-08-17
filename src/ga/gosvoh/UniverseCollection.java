package ga.gosvoh;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс-оболочка для словаря universeHashMap, реализованного с помощью ConcurrentHashMap
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see Universe
 */
public class UniverseCollection {
    private static Date initDate = new Date();
    private static ConcurrentHashMap<Integer, Universe> universeConcurrentHashMap = new ConcurrentHashMap<>();
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
                universeConcurrentHashMap = new JsonReader(mainFile).readUniverseConcurrentHashMap();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (universeConcurrentHashMap == null)
                initCollection();
        }

        universeConcurrentHashMap.values().forEach(universe -> initDate = initDate.compareTo(universe.getBirthDate()) >= 0 ? universe.getBirthDate() : initDate);
    }

    /**
     * Получить словарь объектов класса Universe
     *
     * @return Словарь объектов класса Universe
     * @see Universe
     */
    public static ConcurrentHashMap<Integer, Universe> getUniverseConcurrentHashMap() {
        return universeConcurrentHashMap;
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

    private void initCollection() {
        universeConcurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 3; i++)
            universeConcurrentHashMap.put(i, new Universe(Long.toHexString(Math.round(Math.random() * Long.MAX_VALUE)),
                    "Universe " + (i + 1), new Position(
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)),
                    Math.toIntExact(Math.round(Math.random() * Integer.MAX_VALUE)))));
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
