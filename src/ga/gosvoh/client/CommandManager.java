package ga.gosvoh.client;

import ga.gosvoh.client.commands.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер выборки команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings("WeakerAccess")
public class CommandManager {
    private static ConcurrentHashMap<String, Command> commandMap = new ConcurrentHashMap<>();

    static {
        commandMap.put("import", new ImportMap());
        commandMap.put("exit", new Exit());
        commandMap.put("load", new LoadMap());
    }

    public static boolean ExecuteCommand(String line) {
        String[] cmd = line.split("[ \t]+");
        try {
            commandMap.get(cmd[0].toLowerCase()).execute();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isCommand(String line) {
        return commandMap.containsKey(line.toLowerCase());
    }
}
