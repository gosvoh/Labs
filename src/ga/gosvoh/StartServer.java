/*
 * $Id$
 */
package ga.gosvoh;

import ga.gosvoh.Server.RunServer;

import static ga.gosvoh.Utils.Defines.*;

/**
 * Main класс
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class StartServer {

    /**
     * Точка входа в программу
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        UniverseCollection collection;
        if (System.getenv("MAINJSONFILE") == null)
            collection = new UniverseCollection(DEFAULT_JSON_FILE_PATH);
        else collection = new UniverseCollection(System.getenv("MAINJSONFILE"));

        collection.RunServer();
    }
}
