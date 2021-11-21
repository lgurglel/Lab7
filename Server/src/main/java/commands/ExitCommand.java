package commands;

import exceptions.WrongAmountOfParametersException;
import managers.CollectionManager;
import managers.ResponseOutputer;


public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
        super("exit","- остановка программы");
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public boolean execute(String parameter,Object objectArgument) {
        try {
            if (objectArgument != null) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            ResponseOutputer.append("Использование: '" + getName() + " " + getDescription() + "'");
        }
        return false;
    }
}
