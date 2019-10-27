package ga.gosvoh.commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Добавить новый элемент в словарь, если его значение превышает значение наибольшего элемента этого словаря
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class AddIfMax implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

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
            long universeNumber = universe.getNumber();
            AtomicLong maxNumber = new AtomicLong(Long.MIN_VALUE);
            map.forEach((k, v) -> {
                long valueNumber = v.getNumber();
                if (maxNumber.get() < valueNumber) {
                    maxNumber.set(valueNumber);
                }
            });
            if (universeNumber > maxNumber.get()) {
                map.put(universeNumber, universe);
                new SaveMap().execute(cmd);
                return ("Элемент успешно добавлен!");
            } else
                return ("Не удалось добавить элемент, его значение меньше необходимого.");
        } catch (JsonSyntaxException e) {
            return ("Неверный формат элемента, попробуйте ввести комнду заново.");
        }
    }
}
