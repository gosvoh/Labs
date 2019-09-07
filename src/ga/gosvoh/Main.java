/*
 * $Id$
 */
package ga.gosvoh;

import java.net.SocketException;

/**
 * Main класс
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Main {

    /**
     * Точка входа в программу
     *
     * @param args аргументы командной строки
     */
    @SuppressWarnings("UnusedAssignment")
    public static void main(String[] args) {
        UniverseCollection collection;
        if (System.getenv("MAINJSONFILE") == null)
            collection = new UniverseCollection(System.getProperty("user.home") + "/main.json");
        else collection = new UniverseCollection(System.getenv("MAINJSONFILE"));

        new RunServer();

        //collection.cli();
    }
}
