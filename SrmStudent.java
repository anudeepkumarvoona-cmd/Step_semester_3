class SrmStudent{
    String name;
    String regNo;
    int attendance;

    SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    boolean isEligible() {
        return attendance >= 75;
    }

    // static because classAverage works on the whole array,
    // not on one particular student.
    static double classAverage(SrmStudent[] students) {
        int total = 0;

        for (SrmStudent student : students) {
            total = total + student.attendance;
        }

        return (double) total / students.length;
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA101", 82),
            new SrmStudent("Anitha", "RA102", 68),
            new SrmStudent("Karthik", "RA103", 91),
            new SrmStudent("Meera", "RA104", 74),
            new SrmStudent("Suresh", "RA105", 60)
        };

        for (SrmStudent student : students) {
            if (student.isEligible()) {
                System.out.println(student.name + " - " +
                        student.attendance + "% - Eligible");
            } else {
                System.out.println(student.name + " - " +
                        student.attendance + "% - Detained");
            }
        }

        System.out.println("Class average: " +
                classAverage(students) + "%");
    }
}