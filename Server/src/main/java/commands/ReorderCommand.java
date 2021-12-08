package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;

public class ReorderCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ReorderCommand(CollectionManager collectionManager) {
        super("reorder","- отсортировать коллекцию в порядке, обратном нынешнему");
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String parameter, Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.reorder();
            ResponseOutputer.append("Коллекция отсортированна в обрятном порядке");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("У этой команды нет параметров!\n");
        }catch (Exception e){
            ResponseOutputer.append("Что-то не так");
        }
        return false;

    }

    @Override
    public String toString() {
        return "reorder";
    }
}
