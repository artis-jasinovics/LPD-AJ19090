package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
/*
Using best design principle: https://timefold.ai/docs/timefold-solver/latest/design-patterns/design-patterns#timeGrainPattern
 */
public class TimeGrain {
    @PlanningId
    private int id;
    private DayOfWeek dayOfWeek;
    private int startTime;
    private int endTime;
    public static final int GRAIN_LENGTH_IN_MINUTES = 15;
    public TimeGrain() {
    }

    public TimeGrain(int id) {
        this.id = id;
    }

    public TimeGrain(int id, DayOfWeek dayOfWeek, int startTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public TimeGrain(int id, DayOfWeek dayOfWeek, int startTime, int endTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeGrain{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                '}';
    }
}
