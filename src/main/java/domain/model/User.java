package domain.model;

import static domain.util.CpfMask.formatCpf;

import com.opencsv.bean.CsvBindByName;;

public class User {

    @CsvBindByName(column = "id")
    private Integer id;

    @CsvBindByName(column = "cpf")
    private String cpf;

    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "phone")
    private String phone;

    public User() {
    }

    public User(Integer id, String cpf, String name, String phone) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
