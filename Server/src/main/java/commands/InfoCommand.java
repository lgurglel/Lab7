package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info","- выводит информацию о коллекции");
        this.collectionManager = collectionManager;

    }

    public InfoCommand() {
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try{
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                    lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            ResponseOutputer.append("Информация о коллекции:\n");
            ResponseOutputer.append(" Тип: " + collectionManager.collectionType() + "\n");
            ResponseOutputer.append(" Количество элементов: " + collectionManager.collectionSize() + "\n");
            ResponseOutputer.append(" Дата последней инициализации: " + lastInitTimeString + "\n");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "info";
    }
}
