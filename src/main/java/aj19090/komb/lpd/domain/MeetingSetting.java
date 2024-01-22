package aj19090.komb.lpd.domain;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId;
import ai.timefold.solver.core.api.domain.variable.*;

import java.util.List;

@PlanningEntity
public class MeetingSetting {
    @PlanningId
    private int id;
    private Meeting meeting;

    private TimeGrain startingTimeGrain;

    private Room room;

    private TimeGrain endTimeGrain;

    public MeetingSetting() {
    }

    public MeetingSetting(int id) {
        this.id = id;
    }

    public MeetingSetting(Integer id, Meeting meeting) {
        this.id = id;
        this.meeting = meeting;
    }

    public MeetingSetting(int id, Meeting meeting, TimeGrain startingTimeGrain, Room room) {
        this.id = id;
        this.meeting = meeting;
        this.startingTimeGrain = startingTimeGrain;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }
    @PlanningVariable
    public TimeGrain getStartingTimeGrain() {
        return startingTimeGrain;
    }

    public void setStartingTimeGrain(TimeGrain startingTimeGrain) {
        this.startingTimeGrain = startingTimeGrain;
    }
    @PlanningVariable
    public Room getRoom() {
        return room;
    }

    public TimeGrain getEndTimeGrain() {
        return this.endTimeGrain;
    }

    public void setEndTimeGrain(TimeGrain endTimeGrain) {
        this.endTimeGrain = endTimeGrain;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer calculateOverlap(MeetingSetting other) {
        if (startingTimeGrain == null || other.getStartingTimeGrain() == null) {
            return 0;
        }
        // start is inclusive, end is exclusive
        Integer start = startingTimeGrain.getId();
        Integer otherStart = other.startingTimeGrain.getId();
        Integer otherEnd = otherStart + other.meeting.getDurationInGrains();
        if (otherEnd < start) {
            return 0;
        }
        Integer end = start + meeting.getDurationInGrains();
        if (end < otherStart) {
            return 0;
        }
        return Math.min(end, otherEnd) - Math.max(start, otherStart);
    }

    public Integer getLastTimeGrainIndex() {
        if (startingTimeGrain == null) {
            return null;
        }
        return startingTimeGrain.getId() + meeting.getDurationInGrains() - 1;
    }

    /*public String getStartingDateTimeString() {
        if (startingTimeGrain == null) {
            return null;
        }
        return startingTimeGrain.getDateTimeString();
    }*/

    public int getRoomCapacity() {
        if (room == null) {
            return 0;
        }
        return room.getCapacity();
    }

    public List<Equipment> getRoomEquipment(){
        return room.getEquipment();
    }

    public int getRequiredCapacity() {
        return meeting.getRequiredAttendances().size() + meeting.getOptionalAttendances().size();
    }

    public List<Equipment> getRequiredEquipment(){
        return meeting.getRequiredEquipment();
    }

    public int getEquipmentMismatchCount(){
        if (meeting == null || room == null) {
            return 0;
        }

        List<Equipment> requiredEquipment = meeting.getRequiredEquipment();
        List<Equipment> availableEquipment = room.getEquipment();
        if (requiredEquipment == null) {
            return 0;
        }
        return (int) requiredEquipment.stream()
                .filter(equipment -> !availableEquipment.contains(equipment))
                .count();
    }

    @Override
    public String toString() {
        return "";
    }
}
