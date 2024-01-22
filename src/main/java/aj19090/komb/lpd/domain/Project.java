package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

import java.util.List;

public class Project {
    @PlanningId
    private int id;
    private String name;
    private List<Employee> employees; // All employees involved in the project

    private List<Meeting> meetings;
    public Project() {
    }
    public Project(int id) {
        this.id = id;
    }

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
