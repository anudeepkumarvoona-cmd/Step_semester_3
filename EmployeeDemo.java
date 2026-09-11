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

public class EmployeeDemo {

    public static void main(String[] args) {

        Employee employee =
                new Employee("E101", "Ravi", 40000);

        ManagerEmployee manager =
                new ManagerEmployee("E102", "Divya",
                        70000, 8000);

        InternEmployee intern =
                new InternEmployee("E103", "Meera",
                        12000, 10000);

        Employee[] employees = {
            employee, manager, intern
        };

        for (Employee e : employees) {

            if (e instanceof ManagerEmployee) {

                ManagerEmployee m =
                        (ManagerEmployee) e;

                System.out.println(
                    "Manager effective pay: Rs "
                    + m.effectiveSalary());

            } else if (e instanceof InternEmployee) {

                InternEmployee i =
                        (InternEmployee) e;

                System.out.println(
                    "Intern effective pay: Rs "
                    + i.effectiveSalary());

            } else {

                System.out.println(
                    "Plain employee pay: Rs "
                    + e.getSalary());
            }
        }
    }
}