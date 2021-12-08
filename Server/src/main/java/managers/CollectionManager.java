package managers;

import tasck.Ticket;
import utils.User;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CollectionManager {

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private FileManager fileManager;
    private LocalDateTime lastSaveTime;
    private DatabaseCollectionManager databaseCollectionManager;
    private ReadWriteLock collectionLocker = new ReentrantReadWriteLock();


    public CollectionManager(DatabaseCollectionManager databaseCollectionManager) {
        this.databaseCollectionManager = databaseCollectionManager;

        loadCollection();
    }

    public int collectionSize() {
        return tickets.size();
    }

    public ArrayList<Ticket> getCollection() {
        return tickets;
    }

    public void addToCollection(Ticket ticket) {
        tickets.add(ticket);
    }

    public long generateId() {
        if (tickets.isEmpty()) return 1;
        long lastId = 0;
        for (Ticket ticket : tickets) {
            lastId = Math.max(lastId, ticket.getId());
        }
        return lastId + 1;
    }

    public String collectionType() {
        return tickets.getClass().getName();
    }

    public void clearCollection(User user) {
        tickets.removeIf(ticket -> ticket.getUser().equals(user));
    }

    public void removeGreater(Ticket ticketToCompare) {
        tickets.removeIf(ticket -> ticket.compareTo(ticketToCompare) > 0);
    }

    public void removeById(long idToCompare) {
        tickets.removeIf(entry -> entry.getId() == idToCompare);
    }

    public void removeAllByPrice(Float price) {
        tickets.removeIf(ticket -> ticket.getPrice().equals(price));
    }
    public List<Ticket> getAllByPrice(Float price) {
        return tickets.stream().filter(ticket -> ticket.getPrice().equals(price)).collect(Collectors.toList());
    }
    public List<Ticket> getGreater(Ticket ticketToCompare) {
        return tickets.stream().filter(ticket -> ticket.compareTo(ticketToCompare) > 0).collect(Collectors.toList());
    }

    public void removeFromCollection(Ticket ticket) {
        tickets.remove(ticket);
    }

    public Ticket getFromCollection(long id) {
        return tickets.stream().filter(ticket -> ticket.getId() == id).findFirst().orElse(null);
    }

    public float getSumOfPrice() {
        return tickets.stream().reduce(0f, (sum, p) -> sum += p.getPrice(), Float::sum);
    }

    public LocalDateTime getLastInitTime() {
        return lastSaveTime;
    }

    public String showCollection() {
        if (tickets.isEmpty()) return "Коллекция пуста!";
        return tickets.stream().reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }


    public void reorder() {
        tickets = tickets
                .stream()
                .sorted((x, y) -> x.compareTo(y) * -1)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void sort() {
        tickets.sort(Ticket::compareTo);
    }

    public String filterByPrice(Float parameter) {
        return tickets.stream().filter(ticket -> ticket.getPrice().equals(parameter))
                .reduce("", (sum, m) -> sum += m + "\n\n", (sum1, sum2) -> sum1 + sum2).trim();
    }

    public Ticket getByValue(Ticket ticketToFind) {
        return tickets.stream().filter(ticket -> ticket.equals(ticketToFind)).findFirst().orElse(null);
    }
    public void loadCollection() {
        tickets = databaseCollectionManager.getCollection();
        lastSaveTime = LocalDateTime.now();
        System.out.println("Коллекция загружена.");
    }

}
