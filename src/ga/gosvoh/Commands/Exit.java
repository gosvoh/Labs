package ga.gosvoh.Commands;

import java.util.Scanner;

/**
 * Выход из программы
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Exit implements Command {
    @Override
    public void execute() {
        System.out.println("Действительно выйти из программы? (y | n)");
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("y")) {
            new SaveMap().execute();
            System.out.println("Выход из программы...");
            System.exit(0);
        }
    }
}
