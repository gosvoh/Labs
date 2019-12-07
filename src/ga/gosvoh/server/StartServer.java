/*
 * $Id$
 */
package ga.gosvoh.server;

import ga.gosvoh.server.utils.Defines;

import java.util.Scanner;

import static ga.gosvoh.server.utils.Defines.*;

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
        System.out.println("Лабораторная работа по программированию. Версия " +
                StartServer.class.getPackage().getImplementationVersion());

        UniverseCollection collection;
        if (System.getenv("MAINJSONFILE") == null)
            collection = new UniverseCollection(DEFAULT_JSON_FILE_PATH);
        else collection = new UniverseCollection(System.getenv("MAINJSONFILE"));

        System.out.print("Введите порт сервера или оставьте пустым (стандартный порт 27965): ");

        int PORT;
        try {
            PORT = Integer.parseInt(new Scanner(System.in).nextLine());
        } catch (NumberFormatException e) {
            PORT = Defines.PORT;
        }

        collection.RunServer(PORT);
    }
}
