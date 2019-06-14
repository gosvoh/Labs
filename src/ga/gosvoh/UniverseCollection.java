package ga.gosvoh;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

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
     * @param filePath путь до файла
     */
    public UniverseCollection(String filePath) {
        mainFile = new File(filePath);
        initDate = new Date(System.currentTimeMillis());
    }

    /**
     * Метод, который производит выборку команд
     * @param cmd введённая строка
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

        for (; ; ) {
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
                printHelp();
                continue;
            }

            try {
                if (checkCmd(cmd, "info")) {

                }

                if (checkCmd(cmd, "exit") || checkCmd(cmd, "quit"))
                    System.exit(0);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }

            System.out.println("Unknown command");
        }

        System.exit(0);
    }


    /**
     * Показать список доступных команд
     * Пример команды: ?
     */
    private static void printHelp() {
        System.out.println(
                "Доступные команды: " +
                        "\nsave                     - сохранить коллекцию" +
                        "\nremove_greater_key {Key} - удалить из коллекции все элементы, ключ которых превышает заданный" +
                        "\ninfo                     - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" +
                        "\nadd_if_max {Json}        - добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                        "\nremove_lower {Key}       - удалить из коллекции все элементы, ключ которых меньше, чем заданный" +
                        "\nshow                     - вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                        "\ninsert {Key} {Json}      - добавить новый элемент с заданным ключом ( insert 10 {\"name\": \"Обливион\"} )" +
                        "\nremove {Key}             - удалить элемент из коллекции по его ключу" +
                        "\n{exit | quit}            - выйти из программы" +
                        "\n{help | ?}               - показать доступные команды"
        );
    }

}
