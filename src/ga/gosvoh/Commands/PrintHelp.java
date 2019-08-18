package ga.gosvoh.Commands;

/**
 * Показать список доступных команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class PrintHelp implements Command {
    private String[] cmd;

    public PrintHelp(String[] cmd) {
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        if (cmd.length > 1 && !cmd[0].equals("?"))
            switch (cmd[1]) {
                case "save":
                    System.out.println("save                      - Сохраняет текущее состояние словаря в файл");
                    break;
                case "remove_greater_key":
                    System.out.println("remove_greater_key {Key}  - Удялет из словаря все элементы, ключ которых превышает заданный {Key}");
                    break;
                case "info":
                    System.out.println("info                      - Показать информацию о словаре");
                    break;
                case "add_if_max":
                    System.out.println("add_if_max {Json}         - Добавить новый элемент {Json} в словарь, если его значение превышает значение наибольшего элемента этого словаря" +
                            "\nСравниваются номера вселенных");
                    break;
                case "remove_lower":
                    System.out.println("remove_lower {Key}        - Удалить из словаря все элементы, ключ которых меньше, чем заданный {Key}");
                    break;
                case "show":
                    System.out.println("show                      - Показать все элементы словаря в строковом представлении");
                    break;
                case "insert":
                    System.out.println("insert {Key} {Json}       - Вставить новый элемент в словарь в заданным ключом {Key} и элементом {Json}");
                    break;
                case "remove":
                    System.out.println("remove {Key}              - Удалить элемент из словаря с заданным ключом {Key}");
                    break;
                case "exit":
                case "quit":
                    System.out.println("exit | quit               - Выйти из программы");
                    break;
                case "help":
                case "?":
                    System.out.println("? | help {команда}        - Показать список доступных команд или показать описание команды {команда}");
                    break;
                case "random":
                    System.out.println("random                    - Добавить новый элемент в словарь со случайным ключом и элементом");
                    break;
                default:
                    System.out.println("Такой команды не существует, введите help или ? для просмотра доступных команд");
                    break;
            }
        else
            System.out.println("Доступные команды: save, remove_greater_key {Key}, info, add_if_max {Json}, remove_lower {Key}, show, " +
                    "insert {Key} {Json}, remove {Key}, exit (quit), help (?), random"
            );
    }
}
