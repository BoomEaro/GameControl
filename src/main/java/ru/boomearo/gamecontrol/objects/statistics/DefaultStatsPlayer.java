package ru.boomearo.gamecontrol.objects.statistics;

/**
 * Представляет игрока, хранящий какое-то число, обычно относящиеся к статистике.
 */
public class DefaultStatsPlayer implements IStatsPlayer {

    private final String name;
    private double value;

    private boolean changes = false;

    public DefaultStatsPlayer(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    //TODO Костыль в некотором роде, из-за значение double.
    @Override
    public String getFormattedValues() {
        return "" + (long) this.value;
    }

    @Override
    public boolean hasChanges() {
        return this.changes;
    }

    @Override
    public void setChanges(boolean changes) {
        this.changes = changes;
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
    public int compareTo(IStatsPlayer other) {
        if (other instanceof DefaultStatsPlayer dsp) {
            int d = Double.compare(dsp.getValue(), this.value);
            if (d == 0) {
                d = -1;
            }
            return d;
        }
        return 0;
    }
}
