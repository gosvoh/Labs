package ga.gosvoh.Commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class AddIfMax implements Command {
    private HashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Контруктор класса
     *
     * @param cmd команда с элементом в формате Json
     */
    public AddIfMax(String[] cmd) {
        this.map = UniverseCollection.getUniverseHashMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < cmd.length; i++) {
            stringBuilder.append(cmd[i]);
        }

        try {
            Universe universe = new JsonReader(stringBuilder.toString()).readUniverse();
            if (universe == null)
                throw new JsonSyntaxException("");
            int universeNumber = Integer.parseInt(universe.getNumber(), 16);
            AtomicInteger maxNumber = new AtomicInteger(Integer.MIN_VALUE);
            map.forEach((k, v) -> {
                int valueNumber = Integer.parseInt(v.getNumber(), 16);
                if (maxNumber.get() < valueNumber) {
                    maxNumber.set(valueNumber);
                }
            });
            if (universeNumber > maxNumber.get()) {
                int newKey = Collections.max(map.keySet()) + 1;
                if (map.containsKey(newKey))
                    //noinspection UnusedAssignment
                    newKey = newKey + 1;
                else {
                    map.put(newKey, universe);
                    System.out.println("Элемент успешно добавлен!");
                    new SaveMap().execute();
                }
            } else
                System.out.println("Не удалось добавить элемент, его значение меньше необходимого.");
        } catch (JsonSyntaxException e) {
            System.out.println("Неверный формат элемента, попробуйте ввести комнду заново.");
        }
    }
}
