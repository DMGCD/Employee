package TM;

import java.sql.Date;

public class addEmployeeTM {

    public addEmployeeTM(String empID, String firstName, String lastName, String gender, String phoneNum, String position, Date date) {
        this.setEmpID(empID);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setGender(gender);
        this.setPhoneNum(phoneNum);
        this.setPosition(position);
        this.setDate(date);
    }

  private  String empID;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNum;
    private String position;
    private Date date;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
