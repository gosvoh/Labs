package ga.gosvoh.commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class AddIfMax implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Контруктор класса
     */
    public AddIfMax() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
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
                    new SaveMap().execute(cmd);
                    return ("Элемент успешно добавлен!");
                }
            } else
                return ("Не удалось добавить элемент, его значение меньше необходимого.");
        } catch (JsonSyntaxException e) {
            return ("Неверный формат элемента, попробуйте ввести комнду заново.");
        }
        return "";
    }
}
