package aj19090.komb.lpd.domain;

import java.util.Date;
import java.util.List;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
public class Meeting {
    @PlanningId
    private int id;
    private String name;
    private String description;
    private Project project;
    private Integer durationInGrains; // Duration of the meeting in terms of number of Time Grains
    private List<Equipment> requiredEquipment;
    private List<RequiredAttendance> requiredAttendances;
    private List<OptionalAttendance> optionalAttendances;

    // Default constructor
    public Meeting() {
    }

    public Meeting(int id, String name, String description, Integer durationInGrains) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.durationInGrains = durationInGrains;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getDurationInGrains() {
        return durationInGrains;
    }

    public void setDurationInGrains(Integer durationInGrains) {
        this.durationInGrains = durationInGrains;
    }

    public List<Equipment> getRequiredEquipment() {
        return requiredEquipment;
    }

    public void setRequiredEquipment(List<Equipment> requiredEquipment) {
        this.requiredEquipment = requiredEquipment;
    }

    public List<RequiredAttendance> getRequiredAttendances() {
        return requiredAttendances;
    }

    public void setRequiredAttendances(List<RequiredAttendance> requiredAttendances) {
        this.requiredAttendances = requiredAttendances;
    }

    public List<OptionalAttendance> getOptionalAttendances() {
        return optionalAttendances;
    }

    public void setOptionalAttendances(List<OptionalAttendance> optionalAttendances) {
        this.optionalAttendances = optionalAttendances;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
