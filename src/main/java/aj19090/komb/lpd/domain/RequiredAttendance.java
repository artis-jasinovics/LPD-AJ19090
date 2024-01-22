package aj19090.komb.lpd.domain;

public class RequiredAttendance extends Attendance {
    public RequiredAttendance() {
    }

    public RequiredAttendance(int id, Meeting meeting) {
        super(id, meeting);
    }

    @Override
    public String toString() {
        return "RequiredAttendance{} " ;
    }
}
