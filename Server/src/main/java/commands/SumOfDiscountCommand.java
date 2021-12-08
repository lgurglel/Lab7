package commands;

import exceptions.EmptyCollectionException;
import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;

public class SumOfDiscountCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SumOfDiscountCommand(CollectionManager collectionManager) {
        super("sum_of_discount", "- выводит сумму цен всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            double sum_of_health = collectionManager.getSumOfPrice();
            if (sum_of_health == 0) throw new EmptyCollectionException();
            ResponseOutputer.append("Сумма цен всех билетов: " + sum_of_health + "\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        } catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "sum_of_discount";
    }
}
