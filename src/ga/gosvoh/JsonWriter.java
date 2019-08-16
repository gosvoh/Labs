package ga.gosvoh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс, отвечающий за преобразование объекта в файл формата Json
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see com.google.gson.Gson
 */
public class JsonWriter implements Closeable {
    private FileWriter fileWriter;

    /**
     * Конструктор класса
     *
     * @param file файл, в который будет происходить запись
     * @throws IOException кидает исключение если недостаточно прав для записи
     */
    public JsonWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    /**
     * Конструктор класса
     *
     * @param fileName путь файла, в который будет происходить запись
     * @throws IOException кидает исключение если недостаточно прав для записи
     */
    public JsonWriter(String fileName) throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            fileWriter = new FileWriter(fileName);
        }
    }

    /**
     * Запись объекта в файл формата Json
     *
     * @param object объект для записи
     * @throws IOException кидает исключение если недостаточно прав для записи
     */
    public void writeToFile(Object object) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        fileWriter.write(gson.toJson(object));
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
