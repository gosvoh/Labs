package ga.gosvoh;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Класс, отвечающий за преобразование строки в объект класса Universe или словарь HashMap<Integer, Universe>
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 * @see Universe
 * @see UniverseCollection
 */
public class JsonReader implements Closeable {
    private Scanner scanner;
    private HashMap<Integer, Universe> hashMap;
    private String json;

    /**
     * Конструктор класса
     *
     * @param file файл для чтения
     * @throws FileNotFoundException кидает исключение если файл не найден
     */
    public JsonReader(File file) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(file));
    }

    /**
     * Конструктор класса
     *
     * @param json строка в формате Json
     */
    public JsonReader(String json) {
        this.json = json;
    }

    /**
     * Преобразование строки в объект класса Universe
     *
     * @return объект класса Universe
     * @throws JsonSyntaxException кидает исключение если строка не в формате Json
     * @see Universe
     */
    public Universe readUniverse() throws JsonSyntaxException {
        return new Gson().fromJson(json, Universe.class);
    }

    /**
     * Преобразование файла в объект класса HashMap
     *
     * @return объект класса HashMap
     * @throws JsonSyntaxException кидает исключение если файл не в формате Json
     * @see UniverseCollection
     */
    public HashMap<Integer, Universe> readUniverseHasMap() throws JsonSyntaxException {
        return new Gson().fromJson(scanner.toString(),
                new TypeToken<HashMap<Integer, Universe>>(){}.getType());
    }

    @Override
    public void close() {
        scanner.close();
    }
}
