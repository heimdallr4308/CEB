import java.math.BigDecimal;

public class Currency {
    private String currencyName;
    private BigDecimal buyRate;
    private BigDecimal sellRate;
    private int yearOfIssue;
    private String account;

    public Currency(String currencyName, BigDecimal buyRate, BigDecimal sellRate, int yearOfIssue, String account) {
        this.currencyName = currencyName;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
        this.yearOfIssue = yearOfIssue;
        this.account = account;
    }
    public String getCurrencyName() {
        return currencyName;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public String getAccount() {
        return account;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}