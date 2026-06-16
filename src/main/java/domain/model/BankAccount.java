package domain.model;

import com.opencsv.bean.CsvBindByName;

public class BankAccount {

    @CsvBindByName(column = "id")
    private Integer id;

    @CsvBindByName(column = "email")
    private String email;

    @CsvBindByName(column = "number")
    private String number;

    @CsvBindByName(column = "status")
    private String status;

    @CsvBindByName(column = "agency")
    private String agency;

    @CsvBindByName(column = "balance")
    private Double balance;

    @CsvBindByName(column = "password")
    private String password;

    @CsvBindByName(column = "fk_user")
    private Integer user;

    @CsvBindByName(column = "fk_bank")
    private Integer Bank;

    public BankAccount() {
    }

    public BankAccount(Integer id, String email, String number, String status, String agency, Double balance,
            String password, Integer user, Integer Bank) {
        this.id = id;
        this.email = email;
        this.number = number;
        this.status = status;
        this.agency = agency;
        this.balance = balance;
        this.password = password;
        this.user = user;
        this.Bank = Bank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getBank() {
        return Bank;
    }

    public void setBank(Integer Bank) {
        this.Bank = Bank;
    }
}