package commands;

import exceptions.EmptyCollectionException;
import exceptions.NotFoundTicketException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import tasck.Ticket;


public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id","- удаляет элемент из колекции по введенному id");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            long id = Long.parseLong(parameter);
            Ticket ticketToRemove = collectionManager.getFromCollection(id);
            if (ticketToRemove == null) throw new NotFoundTicketException();
            collectionManager.removeFromCollection(ticketToRemove);
            ResponseOutputer.append("Билет успешно удален!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.appendError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            ResponseOutputer.appendError("ID должен быть представлен числом!");
        } catch (NotFoundTicketException exception) {
            ResponseOutputer.appendError("Билета с таким ID в коллекции нет!");
        }
        return false;
    }

    @Override
    public String toString() {
        return "remove_by_id";
    }
}
