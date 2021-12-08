package managers;

import exceptions.DatabaseManagerException;
import tasck.*;
import utils.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DatabaseCollectionManager {
    private final String SELECT_ALL_TICKET = "SELECT * FROM " + DatabaseManager.TICKET_TABLE;
    private final String SELECT_TICKET_BY_ID = SELECT_ALL_TICKET + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String SELECT_TICKET_BY_ID_AND_USER_ID = SELECT_TICKET_BY_ID + " AND " +
            DatabaseManager.TICKET_TABLE_USER_ID_COLUMN + " = ?";

    private final String INSERT_TICKET = "INSERT INTO " +
            DatabaseManager.TICKET_TABLE + " (" +
            DatabaseManager.TICKET_TABLE_NAME_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_X_COORD_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_Y_COORD_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_PRICE_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_DISCOUNT_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_COMMENT_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_TICKET_TYPE_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_VENUE_NAME_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_VENUE_CAPACITY_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_VENUE_ADDRESS_STREET_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_VENUE_ADDRESS_ZIPCODE_COLUMN + ", " +
            DatabaseManager.TICKET_TABLE_USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String DELETE_TICKET_BY_ID = "DELETE FROM " + DatabaseManager.TICKET_TABLE +
            " WHERE " + DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_TICKET_BY_USER_ID = "DELETE FROM " + DatabaseManager.TICKET_TABLE +
            " WHERE " + DatabaseManager.TICKET_TABLE_USER_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_NAME_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private static final String UPDATE_TICKET_COORDINATES_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_X_COORD_COLUMN + " = ?, " +
            DatabaseManager.TICKET_TABLE_Y_COORD_COLUMN + " = ? WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_PRICE_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_PRICE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_DISCOUNT_COUNT_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_DISCOUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_COMMENT_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_COMMENT_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_TICKET_TYPE_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_TICKET_TYPE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private final String UPDATE_TICKET_VENUE_BY_ID = "UPDATE " + DatabaseManager.TICKET_TABLE + " SET " +
            DatabaseManager.TICKET_TABLE_VENUE_NAME_COLUMN + " = ?, " +
            DatabaseManager.TICKET_TABLE_VENUE_CAPACITY_COLUMN + " = ?, " +
            DatabaseManager.TICKET_TABLE_VENUE_ADDRESS_STREET_COLUMN + " = ?, " +
            DatabaseManager.TICKET_TABLE_VENUE_ADDRESS_ZIPCODE_COLUMN + " = ? WHERE " +
            DatabaseManager.TICKET_TABLE_ID_COLUMN + " = ?";

    private DatabaseManager databaseManager;
    private DatabaseUserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseManager databaseManager, DatabaseUserManager databaseUserManager) {
        this.databaseManager = databaseManager;
        this.databaseUserManager = databaseUserManager;
    }

    private Ticket returnTicket(ResultSet resultSet, Long id) throws SQLException {
        String name = resultSet.getString("name");
        Long x = resultSet.getLong("x_coord");
        Double y = resultSet.getDouble("y_coord");
        Coordinates coordinates = new Coordinates(x, y);
        LocalDateTime creationDate = LocalDateTime.from(resultSet.getTimestamp("creationdate").toLocalDateTime());
        Float price = resultSet.getFloat("price");
        Long discount = resultSet.getLong("discount");
        String comment = resultSet.getString("comment");
        TicketType ticketType = TicketType.valueOf(resultSet.getString("tickettype"));
        int venueId = resultSet.getInt("venueid");
        String venueName = resultSet.getString("venuename");
        int capacity = resultSet.getInt("capacity");
        String street = resultSet.getString("street");
        String zipcode = resultSet.getString("zipcode");
        Address address = new Address(street, zipcode);
        Venue venue = new Venue(venueId, venueName, capacity, address);
        User owner = databaseUserManager.getUserById(resultSet.getInt("ownerid"));
        return new Ticket(id, name, coordinates, creationDate, price, discount, comment, ticketType, venue, owner);
    }

    public ArrayList<Ticket> getCollection() {
        ArrayList<Ticket> tickets = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ALL_TICKET, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                tickets.add(returnTicket(resultSet, id));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return tickets;
    }

    public Ticket insertTicket(Tickets tickets, User user) throws DatabaseManagerException {
        Ticket ticketToInsert;
        PreparedStatement insertTicket = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            LocalDateTime localDateTime = LocalDateTime.now();

            insertTicket = databaseManager.doPreparedStatement(INSERT_TICKET, true);
            insertTicket.setString(1, tickets.getName());
            insertTicket.setLong(2, tickets.getCoordinates().getX());
            insertTicket.setDouble(3, tickets.getCoordinates().getY());
            insertTicket.setTimestamp(4, Timestamp.valueOf(localDateTime));
            insertTicket.setDouble(5, tickets.getPrice());
            insertTicket.setFloat(6, tickets.getDiscount());
            insertTicket.setString(7, tickets.getComment());
            insertTicket.setString(8, tickets.getType().toString());
            insertTicket.setString(9, tickets.getVenue().getName());
            insertTicket.setInt(10, tickets.getVenue().getCapacity());
            insertTicket.setString(11, tickets.getVenue().getAddress().getStreet());
            insertTicket.setString(12, tickets.getVenue().getAddress().getZipCode());
            insertTicket.setInt(13, databaseUserManager.getUserIdByUsername(user));
            if (insertTicket.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetTicket = insertTicket.getGeneratedKeys();
            Long ticketID;
            if (resultSetTicket.next()) ticketID = resultSetTicket.getLong(1);
            else throw new SQLException();
            ticketToInsert = new Ticket(
                    ticketID,
                    tickets.getName(),
                    tickets.getCoordinates(),
                    localDateTime,
                    tickets.getPrice(),
                    tickets.getDiscount(),
                    tickets.getComment(),
                    tickets.getType(),
                    tickets.getVenue(),
                    user
            );
            databaseManager.commit();
            return ticketToInsert;
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при добавлении нового объекта в БД!");
            exception.printStackTrace();
            databaseManager.rollback();
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(insertTicket);
            databaseManager.setAutoCommit();
            databaseManager.closeConnection();
        }
    }

    public void updateTicketByID(Long ticketID, Tickets tickets) throws DatabaseManagerException {
        PreparedStatement updateTicketName = null;
        PreparedStatement updateTicketCoordinates = null;
        PreparedStatement updateTicketPrice = null;
        PreparedStatement updateTicketDiscount = null;
        PreparedStatement updateTicketComments = null;
        PreparedStatement updateTicketType = null;
        PreparedStatement updateTicketVenue = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            updateTicketName = databaseManager.doPreparedStatement(UPDATE_TICKET_NAME_BY_ID, false);
            updateTicketCoordinates = databaseManager.doPreparedStatement(UPDATE_TICKET_COORDINATES_BY_ID, false);
            updateTicketPrice = databaseManager.doPreparedStatement(UPDATE_TICKET_PRICE_BY_ID, false);
            updateTicketDiscount = databaseManager.doPreparedStatement(UPDATE_TICKET_DISCOUNT_COUNT_BY_ID, false);
            updateTicketComments = databaseManager.doPreparedStatement(UPDATE_TICKET_COMMENT_BY_ID, false);
            updateTicketType = databaseManager.doPreparedStatement(UPDATE_TICKET_TICKET_TYPE_BY_ID, false);
            updateTicketVenue = databaseManager.doPreparedStatement(UPDATE_TICKET_VENUE_BY_ID, false);

            if (tickets.getName() != null) {
                updateTicketName.setString(1, tickets.getName());
                updateTicketName.setLong(2, ticketID);
                if (updateTicketName.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getCoordinates() != null) {
                updateTicketCoordinates.setLong(1, tickets.getCoordinates().getX());
                updateTicketCoordinates.setDouble(2, tickets.getCoordinates().getY());
                updateTicketCoordinates.setLong(3, ticketID);
                System.out.println(UPDATE_TICKET_COORDINATES_BY_ID);
                if (updateTicketCoordinates.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getPrice() != -1) {
                updateTicketPrice.setFloat(1, tickets.getPrice());
                updateTicketPrice.setLong(2, ticketID);
                if (updateTicketPrice.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getDiscount() != -1) {
                updateTicketDiscount.setDouble(1, tickets.getDiscount());
                updateTicketDiscount.setLong(2, ticketID);
                if (updateTicketDiscount.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getComment() != null) {
                updateTicketComments.setString(1, tickets.getComment());
                updateTicketComments.setLong(2, ticketID);
                if (updateTicketComments.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getType() != null) {
                updateTicketType.setString(1, tickets.getType().toString());
                updateTicketType.setLong(2, ticketID);
                if (updateTicketType.executeUpdate() == 0) throw new SQLException();
            }
            if (tickets.getVenue() != null) {
                updateTicketVenue.setString(1, tickets.getVenue().getName());
                updateTicketVenue.setInt(2, tickets.getVenue().getCapacity());
                updateTicketVenue.setString(3, tickets.getVenue().getAddress().getStreet());
                updateTicketVenue.setString(4, tickets.getVenue().getAddress().getZipCode());
                updateTicketVenue.setLong(5, ticketID);
                if (updateTicketVenue.executeUpdate() == 0) throw new SQLException();
            }
            databaseManager.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Произошла ошибка при выполнении группы запросов на обновление объекта!");
            databaseManager.rollback();
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(updateTicketName);
            databaseManager.closePreparedStatement(updateTicketCoordinates);
            databaseManager.closePreparedStatement(updateTicketPrice);
            databaseManager.closePreparedStatement(updateTicketDiscount);
            databaseManager.closePreparedStatement(updateTicketComments);
            databaseManager.closePreparedStatement(updateTicketType);
            databaseManager.closePreparedStatement(updateTicketVenue);
            databaseManager.setAutoCommit();
            databaseManager.closeConnection();
        }
    }

    public boolean checkTicketByIdAndUserId(Long spaceMarineID, User user) throws DatabaseManagerException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_TICKET_BY_ID_AND_USER_ID, false);
            preparedStatement.setLong(1, spaceMarineID);
            preparedStatement.setInt(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса SELECT_TICKET_BY_ID_AND_USER_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
            databaseManager.closeConnection();
        }
    }

    public void deleteTicketById(Long ticketID) throws DatabaseManagerException {
        PreparedStatement deleteTicket = null;
        try {
            deleteTicket = databaseManager.doPreparedStatement(DELETE_TICKET_BY_ID, false);
            deleteTicket.setLong(1, ticketID);
            if (deleteTicket.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE_TICKET_BY_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(deleteTicket);
            databaseManager.closeConnection();
        }
    }

    public void deleteTicketByUserId(int userID) throws DatabaseManagerException {
        PreparedStatement deleteTicket = null;
        try {
            deleteTicket = databaseManager.doPreparedStatement(DELETE_TICKET_BY_USER_ID, false);
            deleteTicket.setInt(1, userID);
            if (deleteTicket.executeUpdate() == 0) throw new SQLException();
        } catch (SQLException exception) {
            System.out.println("Произошла ошибка при выполнении запроса DELETE_TICKET_BY_USER_ID!");
            throw new DatabaseManagerException();
        } finally {
            databaseManager.closePreparedStatement(deleteTicket);
            databaseManager.closeConnection();
        }
    }

    private int getUserId(User user) {
        try {
            PreparedStatement statement = databaseManager.doPreparedStatement(String.format("SELECT %s FROM %s WHERE %s = ?", DatabaseManager.USER_TABLE_ID_COLUMN, DatabaseManager.USER_TABLE, DatabaseManager.USER_TABLE_USERNAME_COLUMN), false);
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void clearCollection(User user) throws DatabaseManagerException {
        deleteTicketByUserId(getUserId(user));
    }
}
