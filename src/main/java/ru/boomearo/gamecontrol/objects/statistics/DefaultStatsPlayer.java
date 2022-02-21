package ru.boomearo.gamecontrol.objects.statistics;

/**
 * Представляет игрока, хранящий какое-то число, обычно относящиеся к статистике.
 */
public class DefaultStatsPlayer implements IStatsPlayer, Comparable<DefaultStatsPlayer> {

    private final String name;
    private double value;

    public DefaultStatsPlayer(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return Значение статистики
     */
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(DefaultStatsPlayer o) {
        int d = Double.compare(o.getValue(), this.value);
        if (d == 0) {
            d = -1;
        }
        return d;
    }
}
