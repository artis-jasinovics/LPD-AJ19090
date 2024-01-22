package aj19090.komb.lpd;

import ai.timefold.solver.core.api.score.ScoreExplanation;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.solver.SolutionManager;
import ai.timefold.solver.core.api.solver.Solver;
import ai.timefold.solver.core.api.solver.SolverFactory;
import aj19090.komb.lpd.domain.MeetingSchedule;
import aj19090.komb.lpd.generate.MeetingScheduleDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MeetingScheduleDataGenerator generator = new MeetingScheduleDataGenerator();
        MeetingSchedule problem = generator.writeMeetingSchedule(3,5);

        SolverFactory<MeetingSchedule> solverFactoryFromXML = SolverFactory
                .createFromXmlResource("SolverConfig.xml");

        Solver<MeetingSchedule> solver = solverFactoryFromXML.buildSolver();
        MeetingSchedule solution = solver.solve(problem);


        SolutionManager<MeetingSchedule, HardSoftScore> solutionManager = SolutionManager.create(solverFactoryFromXML);
        ScoreExplanation<MeetingSchedule, HardSoftScore> scoreExplanation = solutionManager.explain(solution);
        LOGGER.info(scoreExplanation.getSummary());

    }
}