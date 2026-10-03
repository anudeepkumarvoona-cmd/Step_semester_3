import java.util.*;

interface ScoringRule {
    double calculateScore(int idea, int execution, int presentation);
}

class InnovationScoring implements ScoringRule {
    @Override
    public double calculateScore(int idea, int execution, int presentation) {
        return idea * 0.50 + execution * 0.30 + presentation * 0.20;
    }
}

class OpenScoring implements ScoringRule {
    @Override
    public double calculateScore(int idea, int execution, int presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Track {
    private String name;
    private ScoringRule scoringRule;

    public Track(String name, ScoringRule scoringRule) {
        this.name = name;
        this.scoringRule = scoringRule;
    }

    public String getName() {
        return name;
    }

    public double calculateScore(int idea, int execution, int presentation) {
        return scoringRule.calculateScore(
                idea, execution, presentation
        );
    }
}

class Project {
    private String name;
    private Team team;
    private Score score;

    public Project(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}

class Score {
    private int idea;
    private int execution;
    private int presentation;
    private double finalScore;

    public Score(
            int idea,
            int execution,
            int presentation,
            double finalScore) {

        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
        this.finalScore = finalScore;
    }

    public double getFinalScore() {
        return finalScore;
    }
}

class Judge {
    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public void scoreProject(
            Project project,
            Track track,
            int idea,
            int execution,
            int presentation,
            Hackathon hackathon) {

        if (hackathon.isPublished()) {
            System.out.println(
                    "Rescore rejected: Results have already been published."
            );
            return;
        }

        double finalScore =
                track.calculateScore(
                        idea,
                        execution,
                        presentation
                );

        Score score =
                new Score(
                        idea,
                        execution,
                        presentation,
                        finalScore
                );

        project.setScore(score);

        System.out.println(
                "Score recorded for '" +
                project.getName() +
                "'."
        );

        System.out.printf(
                "Final score: %.2f%n",
                finalScore
        );
    }
}

class Team {
    private String name;
    private List<Student> members;
    private Track track;
    private Project project;

    public Team(
            String name,
            List<Student> members,
            Track track) {

        this.name = name;
        this.members = members;
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public Track getTrack() {
        return track;
    }

    public void submitProject(String projectName) {

        if (project != null) {
            System.out.println(
                    "Submission failed: Team can submit only one project."
            );
            return;
        }

        project = new Project(
                projectName,
                this
        );

        System.out.println(
                "Project '" +
                projectName +
                "' submitted by " +
                name +
                "."
        );
    }

    public Project getProject() {
        return project;
    }
}

class Hackathon {

    enum State {
        OPEN,
        JUDGING,
        PUBLISHED
    }

    private String name;
    private State state;
    private List<Team> teams =
            new ArrayList<>();

    private Set<String> registeredStudents =
            new HashSet<>();

    public Hackathon(String name) {
        this.name = name;
        this.state = State.OPEN;
    }

    public boolean isPublished() {
        return state == State.PUBLISHED;
    }

    public void registerTeam(Team team) {

        if (state != State.OPEN) {
            System.out.println(
                    "Registration failed: Registration is closed."
            );
            return;
        }

        int size = team.getMembers().size();

        if (size < 2 || size > 4) {
            System.out.println(
                    "Registration failed: A team must have 2 to 4 members."
            );
            return;
        }

        for (Student student : team.getMembers()) {

            if (registeredStudents.contains(
                    student.getName())) {

                System.out.println(
                        "Registration failed: " +
                        student.getName() +
                        " already belongs to a team."
                );
                return;
            }
        }

        teams.add(team);

        for (Student student : team.getMembers()) {
            registeredStudents.add(
                    student.getName()
            );
        }

        System.out.println(
                "Team " +
                team.getName() +
                " registered (" +
                size +
                " members, " +
                team.getTrack().getName() +
                " track)."
        );
    }

    public void startJudging() {
        state = State.JUDGING;
    }

    public void publishResults() {
        state = State.PUBLISHED;

        System.out.println(
                "Results published."
        );
    }
}

public class Problem1 {

    public static void main(String[] args) {

        Hackathon hackathon =
                new Hackathon("Code Sprint");

        Track innovation =
                new Track(
                        "Innovation",
                        new InnovationScoring()
                );

        Track open =
                new Track(
                        "Open",
                        new OpenScoring()
                );

        Student asha =
                new Student("Asha");

        Student ravi =
                new Student("Ravi");

        Student neha =
                new Student("Neha");

        Student kiran =
                new Student("Kiran");

        List<Student> byteBusters =
                Arrays.asList(
                        asha,
                        ravi,
                        neha
                );

        Team team1 =
                new Team(
                        "ByteBusters",
                        byteBusters,
                        innovation
                );

        hackathon.registerTeam(team1);

        List<Student> soloCoder =
                Arrays.asList(kiran);

        Team team2 =
                new Team(
                        "SoloCoder",
                        soloCoder,
                        open
                );

        hackathon.registerTeam(team2);

        team1.submitProject("SmartAttend");

        hackathon.startJudging();

        Judge judge =
                new Judge("Judge 1");

        judge.scoreProject(
                team1.getProject(),
                innovation,
                8,
                7,
                9,
                hackathon
        );

        hackathon.publishResults();

        judge.scoreProject(
                team1.getProject(),
                innovation,
                10,
                7,
                9,
                hackathon
        );
    }
}