public class Suppliers {
    private String account;
    private String phoneNumber;
    private String contactPerson;
    private String email;

    public Suppliers(String account, String phoneNumber, String contactPerson, String email) {
        this.account = account;
        this.phoneNumber = phoneNumber;
        this.contactPerson = contactPerson;
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}