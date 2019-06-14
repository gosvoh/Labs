/*
 * $Id$
 */
package ga.gosvoh;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UniverseCollection collection = new UniverseCollection("~/main.json");
        collection.cli();
    }
}
