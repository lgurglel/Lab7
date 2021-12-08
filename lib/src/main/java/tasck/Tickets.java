package tasck;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for get Marines value.
 */
public class Tickets implements Serializable {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Float price;
    private Long discount;
    private String comment;
    private TicketType type;
    private Venue venue;

    public Tickets(String name, Coordinates coordinates, Float price, Long discount, String comment, TicketType type, Venue venue) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.venue = venue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
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
                "\n     Venue Capacity: " + venue.getCapacity() +
                "\n     Address Street: " + venue.getAddress().getStreet() +
                "\n     Address ZipCode: " + venue.getAddress().getZipCode();
    }

}
