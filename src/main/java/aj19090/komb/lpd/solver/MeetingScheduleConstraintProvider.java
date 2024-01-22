package aj19090.komb.lpd.solver;

import ai.timefold.solver.core.api.domain.constraintweight.ConstraintConfiguration;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import aj19090.komb.lpd.domain.MeetingSetting;
import aj19090.komb.lpd.domain.RequiredAttendance;
import aj19090.komb.lpd.domain.OptionalAttendance;
import aj19090.komb.lpd.domain.TimeGrain;

import static ai.timefold.solver.core.api.score.stream.Joiners.equal;
import static ai.timefold.solver.core.api.score.stream.Joiners.overlapping;
public class MeetingScheduleConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                roomCapacityConstraint(constraintFactory),
                equipmentRequirementConstraint(constraintFactory),
                requiredAttendanceConstraint(constraintFactory),
                roomConflict(constraintFactory),
                requiredAndOptionalAttendanceConflict(constraintFactory),
                optionalAttendanceConstraint(constraintFactory),
                maximizeRoomUsageConstraint(constraintFactory),
                avoidOvertime(constraintFactory),
                oneBreakBetweenConsecutiveMeetings(constraintFactory)
        };
    }

    //Hard//
    /*
     Telpu piešķiršana sapulcēm: telpas sanāksmēm tiek piešķirtas tā, lai:
     Telpas ietilpības lielums[i] ir atbilstošs dalībnieku skaitam.
     Nepieciešamais aprīkojums (projektors p[i], videokonference v[i]) ir pieejams.
     Obligāto dalībnieku grafiki, kas nepārklājas: jāsaplāno sanāksmes, nodrošinot, ka obligātajiem dalībniekiem nav konfliktu un tie noteikti spēs apmeklēt sanāksmi.
     Sanāksmes ilguma ievērošana: jāievēro fiksēto plānoto sanāksmju ilgumu darba laikā.
     Unikālo telpu rezervēšana: nodrošiniet, lai katra telpa vienlaikus būtu rezervēta tikai vienai sapulcei.
     */
    protected Constraint roomCapacityConstraint(ConstraintFactory constraintFactory){
        return constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                .filter(meetingSetting -> meetingSetting.getRequiredCapacity() > meetingSetting.getRoomCapacity())
                .penalize(HardSoftScore.ONE_HARD,meetingSetting -> meetingSetting.getRequiredCapacity() - meetingSetting.getRoomCapacity())
                .asConstraint("roomCapacityConstraint");
    }
    public Constraint equipmentRequirementConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                .penalize(HardSoftScore.ONE_HARD,
                        MeetingSetting::getEquipmentMismatchCount).asConstraint("equipmentRequirementConstraint");
    }


    protected Constraint requiredAttendanceConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(RequiredAttendance.class,
                        equal(RequiredAttendance::getEmployee))
                .join(MeetingSetting.class,
                        equal((requiredAttendance1, requiredAttendance2) -> requiredAttendance1.getMeeting(),
                                MeetingSetting::getMeeting))
                .join(MeetingSetting.class,
                        equal((requiredAttendance1, requiredAttendance2, assignment1) -> requiredAttendance2
                                        .getMeeting(),
                                MeetingSetting::getMeeting),
                        overlapping((attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId(),
                                (attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains()))
                .penalize(HardSoftScore.ONE_HARD,
                        (requiredAttendance1, requiredAttendance2, assignment1, assignment2) -> assignment2
                                .calculateOverlap(assignment1))
                .asConstraint("requiredAttendanceConstraint");
    }
    protected Constraint roomConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(MeetingSetting.class,
                        equal(MeetingSetting::getRoom),
                        overlapping(meetingSetting -> meetingSetting.getStartingTimeGrain().getId(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains()))
                .penalize(HardSoftScore.ONE_HARD, (meetingSetting1, meetingSetting2) -> meetingSetting2.calculateOverlap(meetingSetting1))
                .asConstraint("roomConflict");
    }

    protected Constraint requiredAndOptionalAttendanceConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(RequiredAttendance.class)
                .join(OptionalAttendance.class,
                        equal(RequiredAttendance::getEmployee, OptionalAttendance::getEmployee))
                .join(constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                                .filter(meetingSetting -> meetingSetting.getStartingTimeGrain() != null),
                        equal((requiredAttendance, OptionalAttendance) -> requiredAttendance.getMeeting(),
                                MeetingSetting::getMeeting))
                .join(constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                                .filter(meetingSetting -> meetingSetting.getStartingTimeGrain() != null),
                        equal((requiredAttendance, OptionalAttendance, leftAssignment) -> OptionalAttendance.getMeeting(),
                                MeetingSetting::getMeeting),
                        overlapping((attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId(),
                                (attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains()))
                .penalize(HardSoftScore.ONE_HARD,
                        (requiredAttendance, OptionalAttendance, leftAssignment, rightAssignment) -> rightAssignment
                                .calculateOverlap(leftAssignment))
                .asConstraint("requiredAndOptionalAttendanceConflict");
    }

    protected Constraint avoidOvertime(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                .filter(MeetingSetting -> MeetingSetting.getStartingTimeGrain() != null)
                .ifNotExists(TimeGrain.class,
                        equal(MeetingSetting::getLastTimeGrainIndex, TimeGrain::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("avoidOvertime");
    }
    //Soft//
    /*
     Izvēlēto dalībnieku uzņemšana: plānojiet sanāksmes, ņemot vērā neobligāto dalībnieku pieejamību.
     Sapulces laika vēlmes: pēc iespējas ņemiet vērā darbinieku un projekta sanāksmes laika vēlmes (no rīta, pēcpusdienā).
     Vienmērīga telpas izmantošana: vienmērīgi sadaliet sapulces visās pieejamajās telpās.
     Bufera laiks starp sanāksmēm: Ideālā gadījumā jāieplāno bufera periodu (piemēram, 15 minūtes) starp secīgām sanāksmēm tajā pašā telpā, lai varētu tās izvēdināt.
     */

    protected Constraint optionalAttendanceConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(OptionalAttendance.class,
                        equal(OptionalAttendance::getEmployee))
                .join(constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                                .filter(meetingSetting -> meetingSetting.getStartingTimeGrain() != null),
                        equal((leftAttendance, rightAttendance) -> leftAttendance.getMeeting(),
                                MeetingSetting::getMeeting))
                .join(constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                                .filter(meetingSetting -> meetingSetting.getStartingTimeGrain() != null),
                        equal((leftAttendance, rightAttendance, leftAssignment) -> rightAttendance.getMeeting(),
                                MeetingSetting::getMeeting),
                        overlapping((attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId(),
                                (attendee1, attendee2, meetingSetting) -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId(),
                                meetingSetting -> meetingSetting.getStartingTimeGrain().getId() +
                                        meetingSetting.getMeeting().getDurationInGrains()))
                .penalize(HardSoftScore.ONE_SOFT,
                        (leftPreferredAttendance, rightPreferredAttendance, leftAssignment, rightAssignment) -> rightAssignment
                                .calculateOverlap(leftAssignment))
                .asConstraint("optionalAttendanceConstraint");
    }

    public Constraint maximizeRoomUsageConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(MeetingSetting.class)
                .filter(meetingSetting -> meetingSetting.getRoom() != null)
                .reward( HardSoftScore.ONE_SOFT)
                .asConstraint("maximizeRoomUsageConstraint"); // Reward each room meetingSetting
    }

    protected Constraint oneBreakBetweenConsecutiveMeetings(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                .filter(MeetingSetting -> MeetingSetting.getStartingTimeGrain() != null)
                .join(constraintFactory.forEachIncludingNullVars(MeetingSetting.class)
                                .filter(assignment -> assignment.getStartingTimeGrain() != null),
                        equal(MeetingSetting::getLastTimeGrainIndex,
                                (rightAssignment) -> rightAssignment.getStartingTimeGrain().getId() - 1))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("oneBreakBetweenConsecutiveMeetings");
    }
}
