package ga.gosvoh;

/**
 * Позиция объекта
 *
 * @author Vokhmin Aleksey {@literal <}vohmina2011{@literal @}yandex.ru{@literal >}
 */
public class Position {
    private int x, y, z;

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
    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Получить координату X
     *
     * @return Координата X
     */
    private int getX() {
        return x;
    }

    /**
     * Получить координату X
     *
     * @return Координата Y
     */
    private int getY() {
        return y;
    }

    /**
     * Получить координату X
     *
     * @return Координата Z
     */
    private int getZ() {
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
    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Получить позицию с нулевыми координатами
     *
     * @return Позиция с нулевыми координатами
     */
    static Position zero() {
        return new Position(0, 0, 0);
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
