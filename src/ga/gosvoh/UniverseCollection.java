package ga.gosvoh;

import java.io.File;
import java.util.*;

/**
 * Класс-оболочка для коллекции universeHashMap, реализованная с помощью HashMap
 *
 * @author Алексей Вохмин
 * @version 2.0
 * @see Universe
 */
public class UniverseCollection {
    private static String initDate;
    private static HashMap<Integer, Universe> universeHashMap = new HashMap<>();
    public static File mainFile;

    /**
     * Конструктор класса
     *
     * @param filePath путь до файла
     */
    public UniverseCollection(String filePath) {
        mainFile = new File(filePath);
        initDate = new Date().toString();

        try {
            for (int i = 0; i < 10; i++)
                universeHashMap.put(i, new Universe(Long.toHexString(Math.round(Math.random() * Integer.MAX_VALUE)),
                        "Universe " + (i + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, Universe> getUniverseHashMap() {
        return universeHashMap;
    }

    public static String getInitDate() {
        return initDate;
    }

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
