import java.time.LocalDate;

abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean canTakeLeave(
            LocalDate startDate,
            LocalDate endDate
    );
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(
            LocalDate startDate,
            LocalDate endDate) {

        return true;
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(
            LocalDate startDate,
            LocalDate endDate) {

        return true;
    }
}

class ContractEmployee extends Employee {

    public ContractEmployee(String name) {
        super(name);
    }

    @Override
    public boolean canTakeLeave(
            LocalDate startDate,
            LocalDate endDate) {

        return true;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {

    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void approve() {

        if (status == LeaveStatus.PENDING) {
            status = LeaveStatus.APPROVED;

            System.out.println(
                    "Leave request for " +
                    employee.getName() +
                    " approved. Status: Approved."
            );
        }
    }

    public void reject() {

        if (status == LeaveStatus.PENDING) {
            status = LeaveStatus.REJECTED;

            System.out.println(
                    "Leave request for " +
                    employee.getName() +
                    " rejected. Status: Rejected."
            );
        }
    }

    public void changeToPending() {

        if (status == LeaveStatus.APPROVED) {

            System.out.println(
                    "Cannot change status: Approved request " +
                    "cannot revert to Pending."
            );

        } else if (status == LeaveStatus.REJECTED) {

            System.out.println(
                    "Cannot change status: Rejected request " +
                    "cannot revert to Pending."
            );
        }
    }
}

class LeaveManager {

    public LeaveRequest submitRequest(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        if (!employee.canTakeLeave(
                startDate,
                endDate)) {

            System.out.println(
                    "Leave request cannot be submitted."
            );

            return null;
        }

        LeaveRequest request =
                new LeaveRequest(
                        employee,
                        startDate,
                        endDate
                );

        System.out.println(
                "Leave request submitted by " +
                employee.getName() +
                " for " +
                startDate +
                " to " +
                endDate +
                ". Status: Pending."
        );

        return request;
    }

    public void reviewAndApprove(
            LeaveRequest request) {

        request.approve();
    }

    public void reviewAndReject(
            LeaveRequest request) {

        request.reject();
    }
}

public class Problem4 {

    public static void main(String[] args) {

        LeaveManager manager =
                new LeaveManager();

        Employee john =
                new FullTimeEmployee("John Doe");

        LeaveRequest johnRequest =
                manager.submitRequest(
                        john,
                        LocalDate.of(2024, 10, 10),
                        LocalDate.of(2024, 10, 12)
                );

        manager.reviewAndApprove(johnRequest);

        Employee jane =
                new PartTimeEmployee("Jane Smith");

        LeaveRequest janeRequest =
                manager.submitRequest(
                        jane,
                        LocalDate.of(2024, 11, 1),
                        LocalDate.of(2024, 11, 5)
                );

        johnRequest.changeToPending();
    }
}