package aj19090.komb.lpd.generate;

import aj19090.komb.lpd.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MeetingScheduleDataGenerator {

    /*MeetingScheduleDataGenerator generator = new MeetingScheduleDataGenerator();
    generator.writeMeetingSchedule(50, 5);
    generator.writeMeetingSchedule(100, 5);
    generator.writeMeetingSchedule(200, 5);
    generator.writeMeetingSchedule(400, 5);
    generator.writeMeetingSchedule(800, 5);
    */

    private final int[] durationInGrainsOptions = {
            1, // 15 mins
            2, // 30 mins
            3, // 45 mins
            4, // 1 hour
            6, // 90 mins
            8, // 2 hours
            16, // 4 hours
    };

    private final int[] personsPerMeetingOptions = {
            2,
            3,
            4,
            5,
            6,
            8,
            10,
            12,
            14,
            16,
            20,
            30,
    };

    private final int[] startingMinuteOfDayOptions = {
            8 * 60, // 08:00
            8 * 60 + 15, // 08:15
            8 * 60 + 30, // 08:30
            8 * 60 + 45, // 08:45
            9 * 60, // 09:00
            9 * 60 + 15, // 09:15
            9 * 60 + 30, // 09:30
            9 * 60 + 45, // 09:45
            10 * 60, // 10:00
            10 * 60 + 15, // 10:15
            10 * 60 + 30, // 10:30
            10 * 60 + 45, // 10:45
            11 * 60, // 11:00
            11 * 60 + 15, // 11:15
            11 * 60 + 30, // 11:30
            11 * 60 + 45, // 11:45
            13 * 60, // 13:00
            13 * 60 + 15, // 13:15
            13 * 60 + 30, // 13:30
            13 * 60 + 45, // 13:45
            14 * 60, // 14:00
            14 * 60 + 15, // 14:15
            14 * 60 + 30, // 14:30
            14 * 60 + 45, // 14:45
            15 * 60, // 15:00
            15 * 60 + 15, // 15:15
            15 * 60 + 30, // 15:30
            15 * 60 + 45, // 15:45
            16 * 60, // 16:00
            16 * 60 + 15, // 16:15
            16 * 60 + 30, // 16:30
            16 * 60 + 45, // 16:45
            17 * 60, // 17:00
            17 * 60 + 15, // 17:15
            17 * 60 + 30, // 17:30
            17 * 60 + 45, // 17:45
    };

    //private final StringDataGenerator fullNameGenerator = StringDataGenerator.buildFullNames();

    protected Random random;

    public MeetingScheduleDataGenerator() {

    }
    public MeetingSchedule writeMeetingSchedule(int meetingListSize, int roomListSize) {
        int timeGrainListSize = meetingListSize * durationInGrainsOptions[durationInGrainsOptions.length - 1] / roomListSize;
        return createMeetingSchedule(meetingListSize, timeGrainListSize, roomListSize);
    }
    public MeetingSchedule createMeetingSchedule(int meetingListSize, int timeGrainListSize,
                                                 int roomListSize) {
        System.out.println("createMeetingSchedule!");
        random = new Random(37);
        MeetingSchedule meetingSchedule = new MeetingSchedule(0);
        MeetingScheduleConstraintConfiguration constraintConfiguration = new MeetingScheduleConstraintConfiguration();
        meetingSchedule.setConstraintConfiguration(constraintConfiguration);
        createMeetingListAndAttendanceList(meetingSchedule, meetingListSize);
        createTimeGrainList(meetingSchedule, timeGrainListSize);
        createRoomList(meetingSchedule, roomListSize);
        createPersonList(meetingSchedule);
        createProjectList(meetingSchedule, 3);
        linkAttendanceListToPersons(meetingSchedule);
        createMeetingAssignmentList(meetingSchedule);

        return meetingSchedule;
    }
    private void createProjectList(MeetingSchedule meetingSchedule, int projectListSize) {
        System.out.println("createProjectList!");
        List<Project> projectList = new ArrayList<>();
        List<Employee> employees = meetingSchedule.getEmployeeList();
        List<Meeting> meetings = meetingSchedule.getMeetingList(); // Get the list of meetings
        List<Employee> newEmployees;

        for (int i = 0; i < projectListSize; i++) {
            newEmployees = new ArrayList<>();
            String projectName = "Project " + i;
            Project project = new Project(i, projectName);
            // Randomly assign some employees to this project
            int numberOfEmployees = random.nextInt(employees.size());
            for (int j = 0; j < numberOfEmployees; j++) {
                Employee employee = employees.get(random.nextInt(employees.size()));
                newEmployees.add(employee);
            }
            project.setEmployees(newEmployees);
            projectList.add(project);
        }

        // Randomly assign projects to meetings
        for (Meeting meeting : meetings) {
            Project project = projectList.get(random.nextInt(projectList.size()));
            meeting.setProject(project); // Assuming Meeting class has a setProject method
        }

        meetingSchedule.setProjectList(projectList);
    }
    private void createMeetingListAndAttendanceList(MeetingSchedule meetingSchedule, int meetingListSize) {
        System.out.println("createMeetingListAndAttendanceList!");
        List<Meeting> meetingList = new ArrayList<>(meetingListSize);
        List<Attendance> globalAttendanceList = new ArrayList<>();
        int attendanceId = 0;
        for (int i = 0; i < meetingListSize; i++) {
            String topic = "Test";
            int durationInGrains = durationInGrainsOptions[random.nextInt(durationInGrainsOptions.length)];
            int attendanceListSize = personsPerMeetingOptions[random.nextInt(personsPerMeetingOptions.length)];
            int requiredAttendanceListSize = Math.max(2, random.nextInt(attendanceListSize + 1));
            Meeting meeting = new Meeting(i,topic, topic, durationInGrains);

            List<RequiredAttendance> requiredAttendanceList = new ArrayList<>();

            for (int j = 0; j < requiredAttendanceListSize; j++) {
                RequiredAttendance attendance = new RequiredAttendance(attendanceId++, meeting);
                // person is filled in later
                requiredAttendanceList.add(attendance);
                globalAttendanceList.add(attendance);
            }
            meeting.setRequiredAttendances(requiredAttendanceList);
            int preferredAttendanceListSize = attendanceListSize - requiredAttendanceListSize;
            List<OptionalAttendance> preferredAttendanceList = new ArrayList<>();
            for (int j = 0; j < preferredAttendanceListSize; j++) {
                OptionalAttendance attendance = new OptionalAttendance(attendanceId++, meeting);
                // person is filled in later
                preferredAttendanceList.add(attendance);
                globalAttendanceList.add(attendance);
            }
            meeting.setOptionalAttendances(preferredAttendanceList);
            meetingList.add(meeting);
        }
        meetingSchedule.setMeetingList(meetingList);
        meetingSchedule.setAttendanceList(globalAttendanceList);
    }

    private void createTimeGrainList(MeetingSchedule meetingSchedule, int timeGrainListSize) {
        System.out.println("createTimeGrainList!");
        List<TimeGrain> timeGrainList = new ArrayList<>();
        for (int i = 0; i < timeGrainListSize; i++) {
            int dayOfYear = (i / startingMinuteOfDayOptions.length) + 1;
            int startingMinuteOfDay = startingMinuteOfDayOptions[i % startingMinuteOfDayOptions.length];
            TimeGrain timeGrain = new TimeGrain(i, DayOfWeek.of(1), startingMinuteOfDay, 0);
            timeGrainList.add(timeGrain);
        }
        meetingSchedule.setTimeGrainList(timeGrainList);
    }

    private void createRoomList(MeetingSchedule meetingSchedule, int roomListSize) {
        System.out.println("createRoomList!");
        final int roomsPerFloor = 20;
        List<Room> roomList = new ArrayList<>();
        for (int i = 0; i < roomListSize; i++) {
            String name = "R";
            int capacityOptionsSubsetSize = personsPerMeetingOptions.length * 3 / 4;
            int capacity = personsPerMeetingOptions[personsPerMeetingOptions.length - (i % capacityOptionsSubsetSize) - 1];
            Equipment[] possibleEquipment = Equipment.values();
            List<Equipment> equipmentList = new ArrayList<>();
            equipmentList.add(possibleEquipment[random.nextInt(possibleEquipment.length)]);
            Room room = new Room(i, name, capacity, equipmentList);
            roomList.add(room);
        }
        meetingSchedule.setRoomList(roomList);
    }

    private void createPersonList(MeetingSchedule meetingSchedule) {
        System.out.println("createPersonList!");
        int attendanceListSize = 0;
        for (Meeting meeting : meetingSchedule.getMeetingList()) {
            attendanceListSize += meeting.getRequiredAttendances().size()
                    + meeting.getOptionalAttendances().size();
        }
        int personListSize = attendanceListSize * meetingSchedule.getRoomList().size() * 3
                / (4 * meetingSchedule.getMeetingList().size());
        List<Employee> personList = new ArrayList<>();
        List<Project> projects = meetingSchedule.getProjectList();
        for (int i = 0; i < personListSize; i++) {
            String fullName = "John Doe";
            String jobTitle = "Professional";
            Employee person = new Employee(i, fullName, jobTitle);
            personList.add(person);
        }
        meetingSchedule.setEmployeeList(personList);
    }

    private void linkAttendanceListToPersons(MeetingSchedule meetingSchedule) {
        System.out.println("linkAttendanceListToPersons!");
        for (Meeting meeting : meetingSchedule.getMeetingList()) {
            List<Employee> availablePersonList = new ArrayList<>(meetingSchedule.getEmployeeList());
            int attendanceListSize = meeting.getRequiredAttendances().size() + meeting.getOptionalAttendances().size();
            if (availablePersonList.size() < attendanceListSize) {
                throw new IllegalStateException("The availablePersonList size (" + availablePersonList.size()
                        + ") is less than the attendanceListSize (" + attendanceListSize + ").");
            }
            for (RequiredAttendance requiredAttendance : meeting.getRequiredAttendances()) {
                requiredAttendance.setEmployee(availablePersonList.remove(random.nextInt(availablePersonList.size())));
            }
            for (OptionalAttendance preferredAttendance : meeting.getOptionalAttendances()) {
                preferredAttendance.setEmployee(availablePersonList.remove(random.nextInt(availablePersonList.size())));
            }
        }
    }

    private void createMeetingAssignmentList(MeetingSchedule meetingSchedule) {
        System.out.println("createMeetingAssignmentList!");
        List<Meeting> meetingList = meetingSchedule.getMeetingList();
        List<MeetingSetting> meetingAssignmentList = new ArrayList<>(meetingList.size());
        for (Meeting meeting : meetingList) {
            MeetingSetting meetingAssignment = new MeetingSetting(meeting.getId(), meeting);
            meetingAssignmentList.add(meetingAssignment);
        }
        meetingSchedule.setMeetingSettingList(meetingAssignmentList);
    }

    @Override
    public String toString() {
        return "";
    }
}
