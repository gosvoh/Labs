package ga.gosvoh.Commands;

/**
 * Функциональный интерфейс, предназначенный для исполнения команды
 *
 * @author Vokhmin Aleksey <vohmina2011@yandex.ru>
 */
@FunctionalInterface
public interface Command {
    void execute();
}
