package ga.gosvoh.server;

import ga.gosvoh.client.commands.*;
import ga.gosvoh.server.commands.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер выборки команд
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class CommandManager {
    private static ConcurrentHashMap<String, Command> commandMap = new ConcurrentHashMap<>();

    static {
        commandMap.put("show", new ShowMap());
        commandMap.put("add_if_max", new AddIfMax());
        commandMap.put("?", new PrintHelp());
        commandMap.put("help", new PrintHelp());
        commandMap.put("load", new LoadMap());
        commandMap.put("import", new ImportMap());
        commandMap.put("insert", new Insert());
        commandMap.put("info", new MapInfo());
        commandMap.put("remove", new Remove());
        commandMap.put("remove_greater_key", new RemoveGreaterKey());
        commandMap.put("remove_lower", new RemoveLower());
        commandMap.put("save", new SaveMap());
        commandMap.put("random", new RandomUniverse());
        commandMap.put("shutdown", new ShutdownServer());
    }

    public static String ExecuteCommand(String line) {
        String[] cmd = line.split("[ \t]+");
        try {
            return commandMap.get(cmd[0].toLowerCase()).execute(cmd);
        } catch (NullPointerException e) {
            return ("Неизвестная команда!");
        }
    }

    public static ConcurrentHashMap<String, Command> getCommandMap() {
        return commandMap;
    }
}
