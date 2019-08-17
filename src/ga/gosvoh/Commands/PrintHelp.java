package ga.gosvoh.Commands;

import ga.gosvoh.CommandManager;

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
        if (CommandManager.getCommandMap().containsKey(cmd[1])) {
            //TODO Запилить хэлп для каждой команды
        } else
            System.out.println("Доступные команды: " +
                    "\nsave                     - сохранить словарь" +
                    "\nremove_greater_key {Key} - удалить из словаря все элементы, ключ которых превышает заданный" +
                    "\ninfo                     - вывести в стандартный поток вывода информацию о словаре (тип, дата инициализации, количество элементов и т.д.)" +
                    "\nadd_if_max {Json}        - добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря" +
                    "\nremove_lower {Key}       - удалить из словаря все элементы, ключ которых меньше, чем заданный" +
                    "\nshow                     - вывести в стандартный поток вывода все элементы словаря в строковом представлении" +
                    "\ninsert {Key} {Json}      - добавить новый элемент с заданным ключом ( insert 10 {\"name\": \"Обливион\",\"number\":123, \"position\": { \"x\": 123, \"y\": 123, \"z\": 123}} )" +
                    "\nremove {Key}             - удалить элемент из словаря по его ключу" +
                    "\n{exit | quit}            - выйти из программы" +
                    "\n{help | ?}               - показать доступные команды" +
                    "\nrandom                   - добавить рандомную вселенную"
            );
    }
}
