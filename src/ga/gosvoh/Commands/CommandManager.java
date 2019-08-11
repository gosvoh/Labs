package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class CommandManager {
    private HashMap<String, Command> commandMap = new HashMap<>();

    public CommandManager(String line) {
        //String[] cmd = line.split("[ \t]+");
        String[] cmd = line.split(" ");
        InitializeCommands(cmd);
        try {
            commandMap.get(cmd[0].toLowerCase()).execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Неизвестная команда!");
        }
    }

    private void InitializeCommands(String[] cmd) {
        commandMap.put("show", new ShowMap());
        commandMap.put("add_if_max", new AddIfMax(cmd));
        commandMap.put("?", new PrintHelp());
        commandMap.put("help", new PrintHelp());
        commandMap.put("exit", new Exit());
        commandMap.put("insert", new Insert(cmd));
        commandMap.put("info", new MapInfo());
        commandMap.put("remove", new Remove(cmd));
        commandMap.put("remove_greater_key", new RemoveGreaterKey(cmd));
        commandMap.put("remove_lower", new RemoveLower(cmd));
        commandMap.put("save", new SaveMap());
    }
}
