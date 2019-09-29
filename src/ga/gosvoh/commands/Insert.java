package ga.gosvoh.commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.Date;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Добавить элемент в словарь
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Insert implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    /**
     * Конструктор класса
     */
    public Insert() {
        this.map = UniverseCollection.getUniverseConcurrentSkipListMap();
    }

    @Override
    public String execute(String[] cmd) {
        try {
            int key = Integer.parseInt(cmd[1]);
            StringBuilder element = new StringBuilder();
            for (int i = 2; i < cmd.length; i++) {
                ////Фикс для пробела в имени Вселенной////
                boolean space = false;
                int quote = 0;
                for (char c : cmd[i].toCharArray()) {
                    if (c == '"')
                        quote++;
                }
                if (quote == 1)
                    element.append(cmd[i]).append(" ");
                    //////////////////////////////////////////
                else
                    element.append(cmd[i]);
            }
            Universe universe = new JsonReader(element.toString()).readUniverse();
            if (universe == null)
                throw new JsonSyntaxException("");

            universe.setBirthDate(new Date());
            map.put(key, universe);
            new SaveMap().execute(cmd);
        } catch (NumberFormatException e) {
            return ("Неверный формат ключа, попробуйте ввести команду заново.");
        } catch (JsonSyntaxException e) {
            return ("Неверный формат элемента, попробуйте ввести команду заново.");
        } catch (ArrayIndexOutOfBoundsException e) {
            return ("Неверный формат команды, попробуйте ввести команду заново.");
        }
        return ("Элемент успешно добавлен.");
    }
}
