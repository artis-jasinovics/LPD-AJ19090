package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

public abstract class Attendance {
    @PlanningId
    private int id;
    private Employee employee;
    private Meeting meeting;

    public Attendance() {
    }

    public Attendance(int id, Meeting meeting) {
        this.id = id;
        this.meeting = meeting;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "";
    }
}
