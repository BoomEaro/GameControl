package ru.boomearo.gamecontrol.objects.statistics;

/**
 * Представляет игрока, хранящий какое-то число, обычно относящиеся к статистике.
 */
public class StatsPlayer implements Comparable<StatsPlayer> {

    private final String name;
    private double value;

    public StatsPlayer(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(StatsPlayer o) {
        int d = Double.compare(o.getValue(), this.value);
        if (d == 0) {
            d = -1;
        }
        return d;
    }
}
