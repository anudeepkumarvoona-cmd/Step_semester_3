import java.util.*;

interface CreditPolicy {
    int getCreditLimit();
    String getType();
}

class RegularCreditPolicy implements CreditPolicy {

    @Override
    public int getCreditLimit() {
        return 24;
    }

    @Override
    public String getType() {
        return "Regular";
    }
}

class HonorsCreditPolicy implements CreditPolicy {

    @Override
    public int getCreditLimit() {
        return 28;
    }

    @Override
    public String getType() {
        return "Honors";
    }
}

class ExchangeCreditPolicy implements CreditPolicy {

    @Override
    public int getCreditLimit() {
        return 20;
    }

    @Override
    public String getType() {
        return "Exchange";
    }
}

class Student {

    private String name;
    private CreditPolicy creditPolicy;
    private int currentCredits;

    public Student(
            String name,
            CreditPolicy creditPolicy,
            int currentCredits) {

        this.name = name;
        this.creditPolicy = creditPolicy;
        this.currentCredits = currentCredits;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return creditPolicy.getCreditLimit();
    }

    public String getType() {
        return creditPolicy.getType();
    }

    public boolean canAddCredits(int credits) {

        return currentCredits + credits
                <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }
}

class Elective {

    private String name;
    private int credits;
    private int capacity;

    private List<Student> enrolled =
            new ArrayList<>();

    private Queue<Student> waitlist =
            new LinkedList<>();

    public Elective(
            String name,
            int credits,
            int capacity) {

        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isEnrolled(Student student) {
        return enrolled.contains(student);
    }

    public boolean isWaiting(Student student) {
        return waitlist.contains(student);
    }

    public boolean isFull() {
        return enrolled.size() >= capacity;
    }

    public void enroll(Student student) {

        enrolled.add(student);
        student.addCredits(credits);

        System.out.println(
                student.getName() +
                " enrolled in " +
                name +
                " (credits: " +
                student.getCurrentCredits() +
                "/" +
                student.getCreditLimit() +
                ")."
        );
    }

    public void addToWaitlist(Student student) {

        waitlist.add(student);

        System.out.println(
                name +
                " is full. " +
                student.getName() +
                " added to waitlist (position " +
                waitlist.size() +
                ")."
        );
    }

    public Student removeFirstFromWaitlist() {
        return waitlist.poll();
    }

    public void drop(Student student) {

        if (enrolled.remove(student)) {

            student.removeCredits(credits);

            System.out.println(
                    student.getName() +
                    " dropped " +
                    name +
                    " (credits: " +
                    student.getCurrentCredits() +
                    "/" +
                    student.getCreditLimit() +
                    ")."
            );
        }
    }
}

class EnrollmentService {

    public void enroll(
            Student student,
            Elective elective) {

        // Credit limit must be checked first.
        if (!student.canAddCredits(
                elective.getCredits())) {

            System.out.println(
                    "Enrollment failed: " +
                    student.getName() +
                    " would exceed the " +
                    student.getType() +
                    " credit limit (" +
                    (student.getCurrentCredits()
                            + elective.getCredits()) +
                    "/" +
                    student.getCreditLimit() +
                    ")."
            );

            return;
        }

        if (elective.isEnrolled(student)
                || elective.isWaiting(student)) {

            System.out.println(
                    "Enrollment failed: " +
                    student.getName() +
                    " is already enrolled or waiting."
            );

            return;
        }

        if (elective.isFull()) {

            elective.addToWaitlist(student);

            return;
        }

        elective.enroll(student);
    }

    public void drop(
            Student student,
            Elective elective) {

        if (!elective.isEnrolled(student)) {
            return;
        }

        elective.drop(student);

        promoteNext(elective);
    }

    private void promoteNext(
            Elective elective) {

        if (elective.isFull()) {
            return;
        }

        while (!elective.isFull()) {

            Student next =
                    elective.removeFirstFromWaitlist();

            if (next == null) {
                return;
            }

            if (next.canAddCredits(
                    elective.getCredits())) {

                System.out.println(
                        next.getName() +
                        " promoted from waitlist and enrolled in " +
                        elective.getName() +
                        " (credits: " +
                        (next.getCurrentCredits()
                                + elective.getCredits()) +
                        "/" +
                        next.getCreditLimit() +
                        ")."
                );

                elective.enroll(next);

                return;

            } else {

                System.out.println(
                        next.getName() +
                        " could not be promoted because " +
                        "the credit limit would be exceeded."
                );
            }
        }
    }
}

public class Problem4 {

    public static void main(String[] args) {

        Elective cloud =
                new Elective(
                        "Cloud Computing",
                        4,
                        2
                );

        Student asha =
                new Student(
                        "Asha",
                        new RegularCreditPolicy(),
                        20
                );

        Student ravi =
                new Student(
                        "Ravi",
                        new HonorsCreditPolicy(),
                        22
                );

        Student neha =
                new Student(
                        "Neha",
                        new ExchangeCreditPolicy(),
                        12
                );

        Student kiran =
                new Student(
                        "Kiran",
                        new RegularCreditPolicy(),
                        22
                );

        EnrollmentService service =
                new EnrollmentService();

        service.enroll(asha, cloud);

        service.enroll(ravi, cloud);

        service.enroll(neha, cloud);

        service.enroll(kiran, cloud);

        service.drop(asha, cloud);
    }
}