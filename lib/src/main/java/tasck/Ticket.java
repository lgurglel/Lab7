package tasck;

import utils.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ticket implements Comparable<Ticket>, Serializable {
    private long id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Float price;
    private Long discount;
    private String comment;
    private TicketType type;
    private Venue venue;
    private User owner;

    public Ticket(Long id, String name, Coordinates coordinates, LocalDateTime creationDate, Float price, Long discount, String comment, TicketType type, Venue venue, User owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.venue = venue;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
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
                "\n     Venue Capacity: " + venue.getCapacity()+
                "\n     Address Street: " + venue.getAddress().getStreet() +
                "\n     Address ZipCode: " + venue.getAddress().getZipCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return id == that.id && price == that.price && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(discount, that.discount) && Objects.equals(comment, that.comment) && type == that.type && Objects.equals(venue, that.venue) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, discount, comment, type, venue, owner);
    }

    public User getUser() {
        return owner;
    }
}