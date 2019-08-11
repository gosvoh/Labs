package ga.gosvoh;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonWriter implements Closeable {
    private FileWriter fileWriter;

    public JsonWriter(File file) throws IOException {
        fileWriter = new FileWriter(file);
    }

    public JsonWriter(String fileName) throws IOException {
        if (!Files.exists(Path.of(fileName))) {
            fileWriter = new FileWriter(fileName);
        }
    }

    public void writeToFile(Object object) throws IOException {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        fileWriter.write(gson.toJson(object));
    }

    @Override
    public void close() throws IOException {
        fileWriter.close();
    }
}
