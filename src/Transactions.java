import java.sql.Date;
import java.sql.Time;

public class Transactions {
    private int transactionCode;
    private String employeeName;
    private String currencyName;
    private Date transactionDate;
    private Time transactionTime;

    public Transactions(int transactionCode, String employeeName, String currencyName, Date transactionDate, Time transactionTime) {
        this.transactionCode = transactionCode;
        this.currencyName = currencyName;
        this.employeeName = employeeName;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
    }

    public int getTransactionCode() {
        return transactionCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Time getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionCode(int transactionCode) {
        this.transactionCode = transactionCode;
    }

    public void setEmployeeName(String currencyName) {
        this.employeeName = currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTransactionTime(Time transactionTime) {
        this.transactionTime = transactionTime;
    }
}