package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;

import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info","- выводит информацию о коллекции");
        this.collectionManager = collectionManager;

    }

    @Override
    public boolean execute(String parameter,Object objectArgument, User user) {
        try{
            if (user == null) throw new NonAuthorizedUserException();
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
        }catch (NonAuthorizedUserException e) {
            ResponseOutputer.append("Необходимо авторизоваться!\n");
        }
        return false;
    }

    @Override
    public String toString() {
        return "info";
    }
}
