package ga.gosvoh.Commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Добавить элемент в словарь
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Insert implements Command {
    private ConcurrentHashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с елементом в формате Json
     */
    public Insert(String[] cmd) {
        this.map = UniverseCollection.getUniverseConcurrentHashMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
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
            System.out.println("Элемент успешно добавлен.");
            new SaveMap().execute();
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат ключа, попробуйте ввести команду заново.");
        } catch (JsonSyntaxException e) {
            System.out.println("Неверный формат элемента, попробуйте ввести команду заново.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Неверный формат команды, попробуйте ввести команду заново.");
        }
    }
}
