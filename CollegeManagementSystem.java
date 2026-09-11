class FeeAccount {
    private double totalFee;
    private double amountPaid;

    FeeAccount(double totalFee) {
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid = amountPaid + amount;
        } else {
            System.out.println("Payment rejected");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(double totalFee) {
        super(totalFee);
    }

    void payInTwoInstallments(double amount) {
        pay(amount);
        pay(amount);
    }
}

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = 0;
    }

    boolean allot(String name) {
        if (occupied < beds) {
            occupied++;
            return true;
        }
        return false;
    }
}

class SrmStudent {
    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    SrmStudent(String name, String regNo,
               HostelFeeAccount feeAccount) {

        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;

        totalStudents++;
    }

    String fullStatus() {

        String roomNumber;

        if (room == null) {
            roomNumber = "unallotted";
        } else {
            roomNumber = room.roomNo;
        }

        return name + " | Due: Rs "
                + feeAccount.getDue()
                + " | Room: " + roomNumber;
    }
}

public class CollegeManagementSystem {

    public static void main(String[] args) {

        HostelFeeAccount fee1 =
                new HostelFeeAccount(150000);

        HostelFeeAccount fee2 =
                new HostelFeeAccount(200000);

        HostelFeeAccount fee3 =
                new HostelFeeAccount(200000);

        SrmStudent student1 =
                new SrmStudent("Ravi", "RA101", fee1);

        SrmStudent student2 =
                new SrmStudent("Anitha", "RA102", fee2);

        SrmStudent student3 =
                new SrmStudent("Karthik", "RA103", fee3);

        HostelRoom room1 =
                new HostelRoom("C-214", 1);

        HostelRoom room2 =
                new HostelRoom("C-507", 1);

        if (room1.allot(student1.name)) {
            student1.room = room1;
        }

        if (room2.allot(student2.name)) {
            student2.room = room2;
        }

        student3.room = null;

        student1.feeAccount.pay(10000);

        student2.feeAccount.pay(20000);

        student3.feeAccount.pay(-5000);

        System.out.println(student1.fullStatus());
        System.out.println(student2.fullStatus());
        System.out.println(student3.fullStatus());

        System.out.println(
            "Total students: " + SrmStudent.totalStudents);
    }
}