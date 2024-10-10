import java.sql.Date;

public class Employees {
    private String employeeName;
    private Date dateOfBirth;
    private String positionName;

    public Employees(String employeeName, Date dateOfBirth, String positionName) {
        this.employeeName = employeeName;
        this.dateOfBirth = dateOfBirth;
        this.positionName = positionName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}