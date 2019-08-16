package ga.gosvoh.Commands;

import com.google.gson.JsonSyntaxException;
import ga.gosvoh.JsonReader;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;

import java.util.HashMap;

/**
 * Добавить элемент в словарь
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Insert implements Command {
    private HashMap<Integer, Universe> map;
    private String[] cmd;

    /**
     * Конструктор класса
     *
     * @param cmd команда с елементом в формате Json
     */
    public Insert(String[] cmd) {
        this.map = UniverseCollection.getUniverseHashMap();
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        try {
            int key = Integer.parseInt(cmd[1]);
            StringBuilder element = new StringBuilder();
            for (int i = 2; i < cmd.length; i++) {
                element.append(cmd[i]);
            }
            Universe universe = new JsonReader(element.toString()).readUniverse();
            if (universe == null)
                throw new JsonSyntaxException("");
            try {
                Integer.parseInt(universe.getNumber(), 16);
            } catch (NumberFormatException e) {
                System.out.println("Номер вселенной должен быть в шестнадцатеричном формате!");
                throw new JsonSyntaxException("");
            }

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
