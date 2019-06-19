package ga.gosvoh.Commands;

import ga.gosvoh.Universe;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class CommandManager {
    private HashMap<String, Command> commandMap = new HashMap<>();
    private HashMap<Integer, Universe> map;
    private String element;
    private int key;
    private Universe elem;

    public CommandManager(String line, HashMap<Integer, Universe> map) {
        this.map = map;
        String[] cmd = line.split("[ \t]+");

        InitializeCommands();
        commandMap.get("show").execute();
    }

    private void InitializeCommands() {
        commandMap.put("show", new ShowMap(map));

    }
}
