package commands;

import exceptions.EmptyCollectionException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

public class RemoveAllByPriceCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public RemoveAllByPriceCommand(CollectionManager collectionManager) {
        super("remove_all_by_price", "- удаляет из коллекции все элементы, значение поля price которого эквивалентно заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            Float price = Float.valueOf(parameter);
            collectionManager.removeAllByPrice(price);
            ResponseOutputer.append("Все билеты с ценой = " + parameter + " успешно удалены!\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки\n");
        } catch (EmptyCollectionException exception) {
            ResponseOutputer.append("Коллекция пуста!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "remove_all_by_price";
    }
}
