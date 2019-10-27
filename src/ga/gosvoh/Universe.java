package ga.gosvoh;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Вселенная
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings("WeakerAccess")
public class Universe implements Comparable<Universe>, Serializable {
    private String name;
    private long number;
    private Date birthDate;
    private Position position;
    private static final long serialVersionUID = 1L;

    /**
     * Конструктор класса
     *
     * @param number номер вселенной
     * @param name   имя вселенной
     */
    public Universe(long number, String name, Position position) {
        this.name = name;
        this.number = number;
        this.birthDate = new Date();
        this.position = position;
    }

    /**
     * Получить имя объекта
     *
     * @return Имя объекта
     */
    public String getName() {
        return name;
    }

    /**
     * Получить номер объекта
     *
     * @return Номер объекта
     */
    public long getNumber() {
        return number;
    }

    /**
     * Получить дату рождения объекта
     *
     * @return Дата рождения объекта
     */
    @SuppressWarnings("WeakerAccess")
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Получить позицию объекта
     *
     * @return Позиция объекта
     */
    @SuppressWarnings("WeakerAccess")
    public Position getPosition() {
        return position;
    }

    /**
     * Установить дату рождения объекта
     *
     * @param birthDate Дата рождения объекта
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "ga.gosvoh.Universe{" +
                "name='" + name + '\'' +
                ", number=" + getNumber() +
                ", birthDate=" + birthDate +
                ", position=" + position +
                '}';
    }

    /**
     * Метод для естественной сортировки, сортировка по расстоянию от нулевых координат до вселенной
     */
    @Override
    public int compareTo(Universe o) {
        Position p = o.getPosition();
        return Double.compare(Position.getDistance(position), Position.getDistance(p));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Universe)) return false;
        Universe universe = (Universe) o;
        return number == universe.number &&
                name.equals(universe.name) &&
                birthDate.equals(universe.birthDate) &&
                position.equals(universe.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, number, birthDate, position);
    }
}
