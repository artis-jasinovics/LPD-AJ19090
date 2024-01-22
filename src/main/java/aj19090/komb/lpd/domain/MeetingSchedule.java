package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import aj19090.komb.lpd.solver.MeetingScheduleConstraintProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
@PlanningSolution
public class MeetingSchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeetingSetting.class);
    private static final Integer HOUR = 3600;
    private static final Integer TIME8AM = 8 * HOUR;

    @ConstraintConfigurationProvider
    private MeetingScheduleConstraintConfiguration constraintConfiguration;
    @PlanningId
    private int id;
    @ProblemFactCollectionProperty
    private List<Meeting> meetingList;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeGrain> timeGrainList;
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Room> roomList;
    @ProblemFactCollectionProperty
    private List<Project> projectList;
    @PlanningEntityCollectionProperty
    private List<MeetingSetting> meetingSettingList;
    @ProblemFactCollectionProperty
    private List<Employee> employeeList;
    @ProblemFactCollectionProperty
    private List<Attendance> attendanceList;
    @PlanningScore
    private HardSoftScore score;
    public MeetingSchedule() {
        // Default constructor
    }

    public MeetingSchedule(int id) {
        this.id = id;
    }

// Getters and Setters

    public MeetingScheduleConstraintConfiguration getConstraintConfiguration() {
        return constraintConfiguration;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public void setConstraintConfiguration(MeetingScheduleConstraintConfiguration constraintConfiguration) {
        this.constraintConfiguration = constraintConfiguration;
    }
    @ValueRangeProvider(id = "roomRange")
    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }
    @ValueRangeProvider(id = "timeGrainRange")
    public List<TimeGrain> getTimeGrainList() {
        return timeGrainList;
    }

    public void setTimeGrainList(List<TimeGrain> timeGrainList) {
        this.timeGrainList = timeGrainList;
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<MeetingSetting> getMeetingSettingList() {
        return meetingSettingList;
    }

    public void setMeetingSettingList(List<MeetingSetting> meetingSettingList) {
        this.meetingSettingList = meetingSettingList;
    }
//GENERATOR

    @Override
    public String toString() {
        return "a";
    }
}
