package ga.gosvoh;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JsonReader implements Closeable {
    private Scanner scanner;
    private HashMap<Integer, Universe> hashMap;
    private String json;

    public JsonReader(File file) throws FileNotFoundException {
        scanner = new Scanner(new FileReader(file));
    }

    public JsonReader(String json) {
        this.json = json;
    }

    public Universe readUniverse() throws JsonSyntaxException {
        return new Gson().fromJson(json, Universe.class);
    }

    public HashMap<Integer, Universe> readUniverseHasMap() {
        return new Gson().fromJson(scanner.toString(),
                new TypeToken<HashMap<Integer, Universe>>(){}.getType());
    }

    @Override
    public void close() {
        scanner.close();
    }
}
