class Employee {
    private String empId;
    private String empName;
    private double salary;

    Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {

    private double teamBonus;

    ManagerEmployee(String empId, String empName,
                    double salary, double teamBonus) {

        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {

    private double stipendCap;

    InternEmployee(String empId, String empName,
                   double salary, double stipendCap) {

        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {

        if (getSalary() < stipendCap) {
            return getSalary();
        }

        return stipendCap;
    }
}

class ParkingSlot {

    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(String slotNo, int capacity) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = 0;
    }

    boolean allot(String vehicleNo) {

        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }

        return false;
    }
}

class CompanyEmployeeRecord {

    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name,
                          String empId,
                          Employee employee) {

        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = null;

        totalRecords++;
    }

    String fullProfile() {

        double pay;

        if (employee instanceof ManagerEmployee) {

            ManagerEmployee manager =
                    (ManagerEmployee) employee;

            pay = manager.effectiveSalary();

        } else if (employee instanceof InternEmployee) {

            InternEmployee intern =
                    (InternEmployee) employee;

            pay = intern.effectiveSalary();

        } else {

            pay = employee.getSalary();
        }

        String slotNumber;

        if (slot == null) {
            slotNumber = "no parking assigned";
        } else {
            slotNumber = slot.slotNo;
        }

        return name + " | Pay: Rs "
                + pay + " | Slot: " + slotNumber;
    }
}

public class HRParkingSystem {

    public static void main(String[] args) {

        ManagerEmployee manager =
                new ManagerEmployee(
                    "E101", "Divya", 70000, 8000);

        Employee employee =
                new Employee(
                    "E102", "Karan", 40000);

        InternEmployee intern =
                new InternEmployee(
                    "E103", "Meera", 12000, 10000);

        CompanyEmployeeRecord record1 =
                new CompanyEmployeeRecord(
                    "Divya", "E101", manager);

        CompanyEmployeeRecord record2 =
                new CompanyEmployeeRecord(
                    "Karan", "E102", employee);

        CompanyEmployeeRecord record3 =
                new CompanyEmployeeRecord(
                    "Meera", "E103", intern);

        ParkingSlot slot1 =
                new ParkingSlot("A1", 1);

        ParkingSlot slot2 =
                new ParkingSlot("A2", 1);

        if (slot1.allot("TN01")) {
            record1.slot = slot1;
        }

        if (slot2.allot("TN02")) {
            record2.slot = slot2;
        }

        // record3 is intentionally left without parking

        System.out.println(record1.fullProfile());
        System.out.println(record2.fullProfile());
        System.out.println(record3.fullProfile());

        System.out.println(
            "Total records: "
            + CompanyEmployeeRecord.totalRecords);
    }
}