/*
 * $Id$
 */
package ga.gosvoh;

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
    public static void main(String[] args) {
        UniverseCollection collection;
        if (System.getenv("MAINJSONFILE") == null)
            collection = new UniverseCollection(System.getProperty("user.home") + "/main.json");
        else collection = new UniverseCollection(System.getenv("MAINJSONFILE"));

        collection.cli();
    }
}
