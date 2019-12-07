package ga.gosvoh.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Класс, отвечающий за преобразование строки в объект класса Universe или словарь HashMap {@literal <} Integer, Universe {@literal >}
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see Universe
 * @see UniverseCollection
 * @see Gson
 */
public class JsonReader implements Closeable {
    private Scanner scanner;
    private String json;

    /**
     * Конструктор класса
     *
     * @param file файл для чтения
     * @throws FileNotFoundException кидает исключение если файл не найден
     */
    JsonReader(File file) throws FileNotFoundException {
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
     * Преобразование файла в объект класса ConcurrentHashMap
     *
     * @return объект класса HashMap
     * @throws JsonSyntaxException кидает исключение если файл не в формате Json
     * @see UniverseCollection
     */
    ConcurrentSkipListMap<Long, Universe> readUniverseConcurrentSkipListMap() throws JsonSyntaxException {
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine())
            stringBuilder.append(scanner.nextLine());
        return new Gson().fromJson(stringBuilder.toString(),
                new TypeToken<ConcurrentSkipListMap<Long, Universe>>() {
                }.getType());
    }

    @Override
    public void close() {
        scanner.close();
    }
}
