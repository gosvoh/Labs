package ga.gosvoh.commands;

/**
 * Показать список доступных команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class PrintHelp implements Command {

    public PrintHelp() {
    }

    @Override
    public String execute(String[] cmd) {
        if (cmd.length > 1 && !cmd[0].equals("?"))
            switch (cmd[1]) {
                case "save":
                    return ("save                      - Сохраняет текущее состояние словаря в файл");
                case "remove_greater_key":
                    return ("remove_greater_key {Key}  - Удялет из словаря все элементы, ключ которых превышает заданный {Key}");
                case "info":
                    return ("info                      - Показать информацию о словаре");
                case "add_if_max":
                    return ("add_if_max {Json}         - Добавить новый элемент {Json} в словарь, если его значение превышает значение наибольшего элемента этого словаря" +
                            "\nСравниваются номера вселенных");
                case "remove_lower":
                    return ("remove_lower {Key}        - Удалить из словаря все элементы, ключ которых меньше, чем заданный {Key}");
                case "show":
                    return ("show                      - Показать все элементы словаря в строковом представлении");
                case "insert":
                    return ("insert {Key} {Json}       - Вставить новый элемент в словарь в заданным ключом {Key} и элементом {Json}");
                case "remove":
                    return ("remove {Key}              - Удалить элемент из словаря с заданным ключом {Key}");
                case "exit":
                case "quit":
                    return ("exit | quit               - Выйти из программы");
                case "help":
                case "?":
                    return ("? | help {команда}        - Показать список доступных команд или показать описание команды {команда}");
                case "random":
                    return ("random                    - Добавить новый элемент в словарь со случайным ключом и элементом");
                case "import":
                    return ("import                    - Импортировать словарь на сервер");
                case "load":
                    return ("load                      - Загрузить словарь на локальный компьютер");
                default:
                    return ("Такой команды не существует, введите help или ? для просмотра доступных команд");
            }
        else
            return ("Доступные команды: save, remove_greater_key {Key}, info, add_if_max {Json}, remove_lower {Key}, show, " +
                    "insert {Key} {Json}, remove {Key}, exit (quit), help (?), random, import, load"
            );
    }
}
