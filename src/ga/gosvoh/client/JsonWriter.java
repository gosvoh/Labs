package ga.gosvoh.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;

/**
 * Класс, отвечающий за преобразование объекта в файл формата Json
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 * @see Gson
 */
public class JsonWriter {
    private Path path;

    /**
     * Конструктор класса
     *
     * @param path файл, в который будет происходить запись
     */
    public JsonWriter(Path path) {
        this.path = path;
    }

    /**
     * Запись объекта в файл формата Json
     *
     * @param object объект для записи
     * @throws IOException кидает исключение если недостаточно прав для записи
     */
    public void writeToFile(Object object) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        SeekableByteChannel seekableByteChannel = Files.newByteChannel(path, CREATE, WRITE, TRUNCATE_EXISTING);
        seekableByteChannel.write(ByteBuffer.wrap(gson.toJson(object).getBytes()));
    }
}
