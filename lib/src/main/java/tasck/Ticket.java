package tasck;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket implements Comparable<Ticket>, Serializable {
    private long id;
    private String name;
    private Coordinates coordinates;
    private ZonedDateTime creationDate;
    private Float price;
    private Long discount;
    private String comment;
    private TicketType type;
    private Venue venue;

    public Ticket(Long id, String name, Coordinates coordinates, ZonedDateTime creationDate, Float price, Long discount, String comment, TicketType type, Venue venue) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.venue = venue;
    }

    public long getId() {
        return id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Float getPrice() {
        return price;
    }

    public Long getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Venue getVenue() {
        return venue;
    }

    @Override
    public int compareTo(Ticket o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String toString() {
        return  "\nTicket: " +
                "\n     Creation Date: " + creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                "\n     Id: " + id +
                "\n     Name: " + name +
                "\n     Coordinates: X:" + coordinates.getX() + " Y:" + coordinates.getY() +
                "\n     Price: " + price +
                "\n     Discount: " + discount +
                "\n     Comment: " + comment +
                "\n     Type: " + type +
                "\n     Venue Id: " + venue.getId() +
                "\n     Venue Name: " + venue.getName() +
                "\n     Venue Capacity: " + venue.getCapacity();
//                "\n     Address Street: " + venue.getAddress().getStreet() +
//                "\n     Address ZipCode: " + venue.getAddress().getZipCode();
    }
}