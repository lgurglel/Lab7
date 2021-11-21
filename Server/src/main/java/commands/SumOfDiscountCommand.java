package commands;

import exceptions.EmptyCollectionException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

public class SumOfDiscountCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SumOfDiscountCommand(CollectionManager collectionManager) {
        super("sum_of_discount", "- выводит сумму цен всех элементов в коллекции");
        this.collectionManager = collectionManager;
    }

    public SumOfDiscountCommand() {

    }

    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            float sum_of_price = collectionManager.getSumOfPrice();
            if (sum_of_price == 0) throw new EmptyCollectionException();
            ResponseOutputer.append("Сумма цен всех билетов: " + sum_of_price);
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.appendError("Коллекция пуста!");
        }
        return false;
    }

    @Override
    public String toString() {
        return "sum_of_discount";
    }
}
