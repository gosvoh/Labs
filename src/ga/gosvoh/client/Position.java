package ga.gosvoh.client;

import java.io.Serializable;

/**
 * Позиция объекта
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
@SuppressWarnings("WeakerAccess")
public class Position implements Serializable {
    private long x, y, z;

    /**
     * Создать позицию, на такущей позиции
     *
     * @param position Позиция объекта
     */
    public Position(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }

    /**
     * Создать позицию в координатах
     *
     * @param x Коордната X
     * @param y Коордната Y
     * @param z Коордната Z
     */
    public Position(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Получить координату X
     *
     * @return Координата X
     */
    public long getX() {
        return x;
    }

    /**
     * Получить координату X
     *
     * @return Координата Y
     */
    public long getY() {
        return y;
    }

    /**
     * Получить координату X
     *
     * @return Координата Z
     */
    public long getZ() {
        return z;
    }

    /**
     * Задать позицию, на такущей позиции
     *
     * @param position Позиция объекта
     */
    public void set(Position position) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }

    /**
     * Задать позицию в координатах
     *
     * @param x Коордната X
     * @param y Коордната Y
     * @param z Коордната Z
     */
    public void set(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Получить позицию с нулевыми координатами
     *
     * @return Позиция с нулевыми координатами
     */
    public static Position zero() {
        return new Position(0, 0, 0);
    }

    /**
     * Рассчитать расстояние между нулевыми координатами и указанными
     *
     * @param p координаты для вычисления расстояния
     * @return расстояние между нулевыми координатами и указанными
     */
    public static double getDistance(Position p) {
        return Math.sqrt(Math.pow(p.getX(), 2) + Math.pow(p.getY(), 2) + Math.pow(p.getZ(), 2));
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
