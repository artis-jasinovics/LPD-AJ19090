package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfiguration;
import ai.timefold.solver.core.api.domain.constraintweight.ConstraintWeight;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

@ConstraintConfiguration(constraintPackage = "aj19090.komb.lpd.solver")
public class MeetingScheduleConstraintConfiguration {
    public static final String ROOM_CAPACITY = "Room capacity";
    public static final String EQUIPMENT_REQUIREMENT = "Required equipment";
    public static final String REQUIRED_ATTENDANCE = "Required attendance conflict";
    public static final String ROOM_CONFLICT = "Room conflict";
    public static final String REQUIRED_AND_OPTIONAL_ATTENDANCE = "Required and optional attendance conflict";

    public static final String OPTIONAL_ATENDANCE = "Optional attendance conflict";
    public static final String MAXIMIZE_ROOM_USAGE = "Maximize all rooms";
    public static final String AVOID_OVER_TIME =  "Meeting in overtime";
    public static final String BREAK_BETWEEN_MEETINGS = "Break between meetings";
    @ConstraintWeight(ROOM_CAPACITY)
    private HardSoftScore roomCapacity = HardSoftScore.ofHard(1);
    @ConstraintWeight(EQUIPMENT_REQUIREMENT)
    private HardSoftScore equipmentRequirement = HardSoftScore.ofHard(1);
    @ConstraintWeight(REQUIRED_ATTENDANCE)
    private HardSoftScore requiredAttendance = HardSoftScore.ofHard(1);
    @ConstraintWeight(REQUIRED_AND_OPTIONAL_ATTENDANCE)
    private HardSoftScore requiredAndOptionalAttendance = HardSoftScore.ofHard(1);
    @ConstraintWeight(ROOM_CONFLICT)
    private HardSoftScore roomConflict = HardSoftScore.ofHard(1);
    @ConstraintWeight(OPTIONAL_ATENDANCE)
    private HardSoftScore optionalAttendance = HardSoftScore.ofSoft(1);

    @ConstraintWeight(MAXIMIZE_ROOM_USAGE)
    private HardSoftScore maxRoomUsage = HardSoftScore.ofSoft(1);
    @ConstraintWeight(AVOID_OVER_TIME)
    private HardSoftScore avoidOverTime = HardSoftScore.ofHard(1);

    @ConstraintWeight(BREAK_BETWEEN_MEETINGS)
    private HardSoftScore breakBetweenMeetings = HardSoftScore.ofSoft(1);


    public MeetingScheduleConstraintConfiguration() {
    }

    //Getters, setters

    public HardSoftScore getRequiredAndOptionalAttendance() {
        return requiredAndOptionalAttendance;
    }

    public void setRequiredAndOptionalAttendance(HardSoftScore requiredAndOptionalAttendance) {
        this.requiredAndOptionalAttendance = requiredAndOptionalAttendance;
    }

    public HardSoftScore getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(HardSoftScore roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public HardSoftScore getEquipmentRequirement() {
        return equipmentRequirement;
    }

    public void setEquipmentRequirement(HardSoftScore equipmentRequirement) {
        this.equipmentRequirement = equipmentRequirement;
    }

    public HardSoftScore getRequiredAttendance() {
        return requiredAttendance;
    }

    public void setRequiredAttendance(HardSoftScore requiredAttendance) {
        this.requiredAttendance = requiredAttendance;
    }

    public HardSoftScore getRoomConflict() {
        return roomConflict;
    }

    public void setRoomConflict(HardSoftScore roomConflict) {
        this.roomConflict = roomConflict;
    }

    public HardSoftScore getOptionalAttendance() {
        return optionalAttendance;
    }

    public void setOptionalAttendance(HardSoftScore optionalAttendance) {
        this.optionalAttendance = optionalAttendance;
    }

    public HardSoftScore getMaxRoomUsage() {
        return maxRoomUsage;
    }

    public void setMaxRoomUsage(HardSoftScore maxRoomUsage) {
        this.maxRoomUsage = maxRoomUsage;
    }

    public HardSoftScore getAvoidOverTime() {
        return avoidOverTime;
    }

    public void setAvoidOverTime(HardSoftScore avoidOverTime) {
        this.avoidOverTime = avoidOverTime;
    }

    public HardSoftScore getBreakBetweenMeetings() {
        return breakBetweenMeetings;
    }

    public void setBreakBetweenMeetings(HardSoftScore breakBetweenMeetings) {
        this.breakBetweenMeetings = breakBetweenMeetings;
    }
}
