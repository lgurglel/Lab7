package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;

public class ReorderCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public ReorderCommand(CollectionManager collectionManager) {
        super("reorder","- отсортировать коллекцию в порядке, обратном нынешнему");
        this.collectionManager = collectionManager;
    }

    public ReorderCommand() {

    }
    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
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
