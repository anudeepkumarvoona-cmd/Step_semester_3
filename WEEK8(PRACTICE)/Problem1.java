import java.util.*;

abstract class Question {
    protected String questionText;
    protected String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean evaluate(String answer);
}

class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer);
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends Question {

    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, correctAnswer);
    }

    @Override
    public boolean evaluate(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
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

    public Attempt startExam(Examination examination) {
        return examination.startAttempt(this);
    }
}

class Examination {
    private String title;
    private List<Question> questions = new ArrayList<>();
    private boolean attemptSubmitted = false;

    public Examination(String title) {
        this.title = title;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Attempt startAttempt(Student student) {
        if (attemptSubmitted) {
            throw new IllegalStateException(
                    "An attempt has already been submitted."
            );
        }

        System.out.println(
                "Examination '" + title +
                "' started by " + student.getName() + "."
        );

        return new Attempt(student, this);
    }

    public void markSubmitted() {
        attemptSubmitted = true;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers = new HashMap<>();
    private boolean submitted = false;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
    }

    public void answerQuestion(int questionNumber, String answer) {

        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        if (questionNumber < 1 ||
                questionNumber > examination.getQuestions().size()) {
            System.out.println("Invalid question number.");
            return;
        }

        answers.put(questionNumber, answer);

        System.out.println(
                "Question " + questionNumber +
                " answered with '" + answer + "'."
        );
    }

    public void submit() {

        if (submitted) {
            System.out.println("Attempt already submitted.");
            return;
        }

        submitted = true;
        examination.markSubmitted();

        System.out.println(
                "Examination '" +
                examination.getTitle() +
                "' submitted successfully."
        );

        evaluate();
    }

    private void evaluate() {

        int correct = 0;

        List<Question> questions = examination.getQuestions();

        for (int i = 0; i < questions.size(); i++) {

            String answer = answers.get(i + 1);

            if (answer != null &&
                    questions.get(i).evaluate(answer)) {
                correct++;
            }
        }

        System.out.println(
                "Result for '" +
                examination.getTitle() +
                "' attempt: " +
                correct + "/" +
                questions.size() +
                " correct"
        );
    }
}

public class Problem1 {

    public static void main(String[] args) {

        Student student = new Student("Student");

        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(
                new MultipleChoiceQuestion(
                        "Question 1",
                        "A"
                )
        );

        exam.addQuestion(
                new MultipleChoiceQuestion(
                        "Question 2",
                        "B"
                )
        );

        Attempt attempt = student.startExam(exam);

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();

        // This will not change the submitted attempt.
        attempt.answerQuestion(1, "B");
    }
}