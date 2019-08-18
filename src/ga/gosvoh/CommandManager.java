package ga.gosvoh;

import ga.gosvoh.Commands.*;

import java.util.HashMap;

/**
 * Менеджер выборки команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class CommandManager {
    private static HashMap<String, Command> commandMap = new HashMap<>();

    /**
     * Конструктор класса
     *
     * @param line введённая с клавиатуры строка
     */
    CommandManager(String line) {
        String[] cmd = line.split("[ \t]+");
        InitializeCommands(cmd);
        try {
            commandMap.get(cmd[0].toLowerCase()).execute();
        } catch (NullPointerException e) {
            //e.printStackTrace();
            System.out.println("Неизвестная команда!");
        }
    }

    /**
     * Инициализация команд
     *
     * @param cmd команда с аргументами
     */
    private void InitializeCommands(String[] cmd) {
        commandMap.put("show", new ShowMap());
        commandMap.put("add_if_max", new AddIfMax(cmd));
        commandMap.put("?", new PrintHelp(cmd));
        commandMap.put("help", new PrintHelp(cmd));
        commandMap.put("exit", new Exit());
        commandMap.put("insert", new Insert(cmd));
        commandMap.put("info", new MapInfo());
        commandMap.put("remove", new Remove(cmd));
        commandMap.put("remove_greater_key", new RemoveGreaterKey(cmd));
        commandMap.put("remove_lower", new RemoveLower(cmd));
        commandMap.put("save", new SaveMap());
        commandMap.put("random", new RandomUniverse());
    }

    public static HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
