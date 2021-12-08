package commands;

import exceptions.NonAuthorizedUserException;
import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;
import utils.User;


public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit", "завершить работу клиента");
    }

    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        }
        return false;
    }
}
