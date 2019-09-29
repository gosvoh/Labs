package ga.gosvoh.commands;/*
package ga.gosvoh.Commands;


/**
 * Выход из программы
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */

public class Exit implements Command {
    @Override
    public void execute() {
        System.out.println("Выход из программы...");
        System.exit(0);
    }
}

