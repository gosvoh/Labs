package ga.gosvoh;

import ga.gosvoh.Commands.CommandManager;
import ga.gosvoh.Commands.MapInfo;
import ga.gosvoh.Commands.PrintHelp;
import ga.gosvoh.Commands.ShowMap;

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
    private Date initDate;
    private HashMap<Integer, Universe> universeHashMap = new HashMap<>();
    public static File mainFile = null,
            tempFile = new File(System.getProperty("user.home") + "/.temp.json");
    private JsonWriter mainWriter, tempWriter;
    private JsonReader reader;

    /**
     * Конструктор класса
     *
     * @param filePath путь до файла
     */
    public UniverseCollection(String filePath) {
        mainFile = new File(filePath);
        initDate = new Date(System.currentTimeMillis());
        try {
            for (int i = 0; i < 10; i++)
                universeHashMap.put(i, new Universe(Long.toHexString(Math.round(Math.random() * Integer.MAX_VALUE)),
                        "Universe " + (i + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, который производит выборку команд
     *
     * @param cmd   введённая строка
     * @param check строка образец
     * @return true, если введённая строка совпадает с образцом, иначе возвращает false
     */
    private boolean checkCmd(String[] cmd, String check) {
        return cmd[0].equalsIgnoreCase(check);
    }

    public void cli() {
        Scanner input = new Scanner(System.in);
        String line;

        System.out.println("Лабораторная работа по программированию. Версия " +
                Main.class.getPackage().getImplementationVersion() + "\n" +
                "Используйте ? или help для получения справки");

        while (true) {
            try {
                line = input.nextLine();
                new CommandManager(line, universeHashMap);
            } catch (Exception e) {
                break;
            }
        }

        /*while (true) {
            try {
                line = input.nextLine();
            } catch (Exception e) {
                break;
            }

            String[] cmd = line.split("[ \t]+");

            if ((cmd.length == 0) || cmd[0].equals(""))
                continue;

            if (cmd[0].charAt(0) == '#')
                continue;

            if (checkCmd(cmd, "?") || checkCmd(cmd, "help")) {
                new PrintHelp().execute();
                continue;
            }

            try {
                if (checkCmd(cmd, "info")) {
                    new MapInfo(universeHashMap).execute();
                    //info(universeHashMap);
                    continue;
                }

                if (checkCmd(cmd, "show")) {
                    new ShowMap(universeHashMap).execute();
                    //show(universeHashMap);
                    continue;
                }

                if (checkCmd(cmd, "exit") || checkCmd(cmd, "quit"))
                    System.exit(0);

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            System.out.println("Unknown command");
        }
        */

        System.exit(0);
    }

    private void show(HashMap hashMap) {
        hashMap.values().forEach(System.out::println);
    }

    /**
     * Показать информацию о коллекции
     */
    private void info(HashMap hashMap) {
        System.out.println("Тип: " + hashMap.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + hashMap.size());
    }
}
