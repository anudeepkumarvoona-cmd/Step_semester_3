class BrokenStudent {
    static String name;
    static String regNo;
    static int attendance;

    BrokenStudent(String name, String regNo, int attendance) {
        BrokenStudent.name = name;
        BrokenStudent.regNo = regNo;
        BrokenStudent.attendance = attendance;
    }
}

class SrmStudent {
    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 0;

    SrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;

        admissionCount++;
        this.regNo = "RA2311003010"
                + (10 + admissionCount);
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println(
            "Students admitted so far: " + admissionCount);
    }
}

public class StaticInstanceDemo {
    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenStudent student1 =
                new BrokenStudent("Ravi", "RA101", 80);

        BrokenStudent student2 =
                new BrokenStudent("Meera", "RA102", 75);

        System.out.println(student1.name);
        System.out.println(student2.name);

        System.out.println();
        System.out.println("Fixed version:");

        SrmStudent s1 =
                new SrmStudent("Ravi", 82);

        SrmStudent s2 =
                new SrmStudent("Meera", 90);

        s1.printIdCard();
        s2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}