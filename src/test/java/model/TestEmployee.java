package model;

import io.qameta.allure.Step;

public class TestEmployee {

    public int id = 0;
    String firstName = "Ivan";
    String lastName = "Ivanov";
    String middleName = "Sergei";
    public int companyId;
    public String email = "IIS@Email.com";
    public String url = "fqf.com";
    String phone = "string";
    String birthdate = "2024-08-15T12:25:25.165Z";
    boolean isActive = true;


    public TestEmployee(int companyId){
        this.companyId = companyId;
    }

}