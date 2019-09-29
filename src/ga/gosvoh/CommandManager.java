package ga.gosvoh;

import ga.gosvoh.commands.*;

import java.util.HashMap;

/**
 * Менеджер выборки команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings("WeakerAccess")
public class CommandManager {
    private static HashMap<String, Command> commandMap = new HashMap<>();

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
        String[] cmd = line.split("[ \t]+");
        return commandMap.containsKey(cmd[0].toLowerCase());
    }

    public static HashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
