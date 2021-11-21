package tasck;

import java.io.Serializable;

public class Venue implements Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int capacity; //Значение поля должно быть больше 0
    private Address address; //Поле не может быть null

    public Venue(int id, String name, int capacity, Address address) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
    }

    public Venue(String newVenueName, int newCapacity, Address newAddress) {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Address getAddress() {
        return address;
    }
}