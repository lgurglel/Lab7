package managers;

import tasck.Ticket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {

    private ArrayList<Ticket> tickets = new ArrayList<>();
    private FileManager fileManager;
    private LocalDateTime lastSaveTime;

    public CollectionManager(FileManager fileManager) {
        this.lastSaveTime = null;
        this.fileManager = fileManager;
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

    public void clearCollection() {
        tickets.clear();
    }

    public void saveCollection() {
        fileManager.writeCollection(tickets);
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

//    public boolean executeExecuteScriptCommand(FileManager fileManager, String parameter) {
//        String message = "";
//        try {
//            message = fileManager.readFile(parameter);
//        } catch (FileNotFoundException e) {
//            message = "false";
//        }
//    }
//
//    @Override
//    public boolean clearCommand() {
//        tickets = new ArrayList<>();
//        return new Response("Коллекция пуста!");
//    }
//
//    public boolean infoCommand() {
//        return new Response("Тип коллекции: " + tickets.getClass() +
//                "\nДата инициадизации: " + initDate +
//                "\nКолличество элементов коллекции: " + tickets.size());
//    }

//    public boolean removeByIdCommand(String parameter) {
//        long id = 0;
//        try {
//            id = Long.parseLong(parameter);
//        } catch (Exception e) {
//            return new Response("id должен быть числом!");
//        }
//        Iterator iterator = tickets.iterator();
//        while (iterator.hasNext()) {
//            Ticket ticket = (Ticket) iterator.next();
//            if (ticket.getId() == id) {
//                iterator.remove();
//                return new Response("Элемент успешно удалён");
//            }
//        }
//        return new Response("Элемента с таким id не существует!");
//
//    }
//
//    public boolean saveCommand() {
//        lastSaveTime = LocalDate.now();
//        return new Response(fileManager.writeCollection(tickets));
//    }

//    public boolean showCommand() {
//        String message = "";
//
//        for (Ticket ticket : tickets) {
//            message += ticket.show();
//        }
//
//        if (message.equals("")) {
//            message = "Коллекция пустая!";
//        }
//        return new Response(message);
//    }


//    public boolean updateCommand(String parameter, Object objectArgument) {
//        long id = 0;
//        try {
//            id = Long.parseLong(parameter);
//        } catch (Exception e) {
//            return new Response("id должен быть числом!");
//        }
//        try {
//            removeByIdCommand(parameter);
//            Ticket ticket = (Ticket) objectArgument;
//            addCommand(new Ticket(createNewId(),
//                    ticket.getName(),
//                    ticket.getCoordinates(),
//                    ticket.getCreationDate(),
//                    ticket.getPrice(),
//                    ticket.getDiscount(),
//                    ticket.getComment(),
//                    ticket.getType(),
//                    ticket.getVenue()));
//        } catch (Exception e) {
//            return new Response("Проблема с обновлением объекта");
//        }
//        return new Response("Объект успешно обновлён, или добавлен новый!");
//    }
//
//    public boolean sumOfDiscountCommand() {
//        long sumOfDiscount = 0;
//        for (Ticket ticket : tickets) {
//            sumOfDiscount += ticket.getDiscount() != null ? ticket.getDiscount().longValue():0;
//        }
//        return new Response("Сумма скидок всех эллементов: " + sumOfDiscount);
//    }
//
//    public boolean removeGreaterCommand(Ticket newTicket) {
//        ArrayList<Ticket> arrayList = new ArrayList<>(tickets.stream().sorted().collect(Collectors.toList()));
//        int num = 0;
//        for (Ticket ticket : arrayList) {
//            if (newTicket.compareTo(ticket) < 0) {
//                removeByIdCommand(String.valueOf(ticket.getId()));
//            }
//        }
//        return new Response("Если вы не увидите этой группы после команды 'show', то эта группа была слабым звеном!(");
//    }

//    public boolean addCommand(Ticket ticket) {
//            addCommand(ticket);
//        return new Response("Билет добавлен в коллекцию!");
//    }

//    public boolean removeAllByPriceCommand(String parameter) {
//        try {
//            Float price = Float.parseFloat(parameter);
//            Iterator iterator = tickets.iterator();
//            while (iterator.hasNext()) {
//                Ticket ticket = (Ticket) iterator.next();
//                if (ticket.getPrice().equals(price)) {
//                    iterator.remove();
//                    return new Response("Элементы удовлетворяющие усмловию удалены!");
//                }
//            }
//        } catch (Exception e) {
//            return new Response("Неверно введен параметр!");
//        }
//        return new Response("Не найдено цен равной заданной.");
//    }

//        public boolean reorder () {
//            if (!tickets.isEmpty()) {
//                tickets = tickets
//                        .stream()
//                        .sorted((x, y) -> x.compareTo(y) * -1)
//                        .collect(Collectors.toCollection(ArrayList::new));
//                return new Response("Коллекция отсортированна в обратном порядке!");
//            } else return new Response("Коллекция пуста!");
//        }
//
//        public boolean sort () {
//            if (!tickets.isEmpty()) {
//                tickets.sort(Ticket::compareTo);
//                return new Response("Коллекция отсортированна!");
//            } else return new Response("Коллекция пуста!");
//
//        }

}
