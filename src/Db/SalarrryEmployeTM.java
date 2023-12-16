package Db;

public class SalarrryEmployeTM {
    private String empId;
    private  String firstName;
    private  String lastName;
    private String positionEmp;

    public SalarrryEmployeTM() {
    }

    private int salary;
    public SalarrryEmployeTM(String empId, String firstName, String lastName, String positionEmp, int salary) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionEmp = positionEmp;
        this.salary = salary;
    }



    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPositionEmp() {
        return positionEmp;
    }

    public void setPositionEmp(String positionEmp) {
        this.positionEmp = positionEmp;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
