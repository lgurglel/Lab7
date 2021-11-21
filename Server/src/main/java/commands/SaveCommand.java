package commands;

import exceptions.EmptyCollectionException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class SaveCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    public SaveCommand(CollectionManager collectionManager) {
        super("save", "- сохраняет коллекцию в файл");
        this.collectionManager = collectionManager;
    }

    public SaveCommand() {

    }

    @Override
    public boolean execute(String parameter, Object objectArgument) {
        try {
            if (!parameter.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            collectionManager.saveCollection();
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
        return "save";
    }
}
