package ga.gosvoh.Commands;

import java.util.Scanner;

/**
 * Выход из программы
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
public class Exit implements Command {
    @Override
    public void execute() {
        System.out.println("Действительно выйти из программы? (y | n)");
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("y")) {
            System.out.println("Выход из программы...");
            System.exit(0);
        }
    }
}
