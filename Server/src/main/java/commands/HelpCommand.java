package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;


public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help","- вывести справку по доступным командам");
    }

    @Override
    public boolean execute(String parameter,Object objectArgument, User user) {
        try {
            if (user == null) throw new NonAuthorizedUserException();
            if (objectArgument != null) throw new WrongAmountOfParametersException();
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
        return "help";
    }
}
