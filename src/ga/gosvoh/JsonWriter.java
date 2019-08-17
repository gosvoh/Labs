package ga.gosvoh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;

import static java.nio.file.StandardOpenOption.*;

/**
 * Класс, отвечающий за преобразование объекта в файл формата Json
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see com.google.gson.Gson
 */
public class JsonWriter implements Closeable {
    private FileWriter fileWriter;
    private Path path;

    /**
     * Конструктор класса
     * <p>
     * /     * @param file файл, в который будет происходить запись
     *
     .  * @throws IOException кидает исключение если недостаточно прав для записи
     */
/*    public JsonWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }*/
    public JsonWriter(Path path) {
        this.path = path;
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
        //fileWriter.write(gson.toJson(object));

        try (SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, CREATE, WRITE)) {
            seekableByteChannel.write(ByteBuffer.wrap(gson.toJson(object).getBytes()));
        }


    }

    @Override
    public void close() {
        //fileWriter.close();
    }
}
