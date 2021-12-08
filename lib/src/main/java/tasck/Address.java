package tasck;

import java.io.Serializable;

public class Address implements Serializable {
    private String street; //Поле может быть null
    private String zipCode; //Длина строки должна быть не меньше 10, Поле может быть null

    public Address(String street, String zipCode) {
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }
}