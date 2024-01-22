package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.lookup.PlanningId;

import java.util.List;

public class Employee {
    @PlanningId
    private int id;
    private String name;
    private String jobTitle;
    public Employee() {
    }
    public Employee(int id, String name, String jobTitle) {
        this.id = id;
        this.name = name;
        this.jobTitle = jobTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
