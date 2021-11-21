package commands;

import exceptions.EmptyCollectionException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

public class FilterByPriceCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public FilterByPriceCommand(CollectionManager collectionManager) {
        super("filter_by_price", "- вывести элементы, значение поля price которых равно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Float price = Float.valueOf(parameter);
            String filteredInfo = collectionManager.filterByPrice(price);
            if (!filteredInfo.isEmpty()) ResponseOutputer.append(filteredInfo);
            else ResponseOutputer.append("В коллекции нет билета с таким названием!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.appendError("Использование: '" + getName() + " " + getDescription() + "'");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.appendError("Коллекция пуста!");
        } catch (Exception e) {
            ResponseOutputer.appendError("Неверно введен параметр!");
        }
        return false;
    }

    @Override
    public String toString() {
        return "filter_by_price";
    }
}
