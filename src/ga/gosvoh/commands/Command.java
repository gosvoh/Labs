package ga.gosvoh.commands;

/**
 * Функциональный интерфейс, предназначенный для исполнения команды
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@FunctionalInterface
public interface Command {

    /**
     * Выполнить команду
     */
    void execute();
}
