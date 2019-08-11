/*
 * $Id$
 */
package ga.gosvoh;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UniverseCollection collection = new UniverseCollection(System.getProperty("user.home") + "/main.json");
        collection.cli();
    }
}
