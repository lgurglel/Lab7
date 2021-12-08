package commands;


import exceptions.DatabaseManagerException;
import exceptions.UserAlreadyExistException;
import exceptions.WrongAmountOfParametersException;
import managers.DatabaseUserManager;
import managers.ResponseOutputer;
import utils.User;

public class SignUpCommand extends AbstractCommand{
    private DatabaseUserManager databaseUserManager;

    public SignUpCommand(DatabaseUserManager databaseUserManager) {
        super("sign_up", "- регистрация нового пользователя");
        this.databaseUserManager = databaseUserManager;
    }

    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            if (databaseUserManager.insertUser(user))
                ResponseOutputer.append("Регистрация прошла успешно!\n");
            else throw new UserAlreadyExistException();
            return true;
        } catch (WrongAmountOfParametersException e) {
            ResponseOutputer.append("У этой команды должен быть только один параметр: 'user'\n");
        } catch (DatabaseManagerException exception) {
            ResponseOutputer.append("Произошла ошибка при обращении к базе данных!\n");
        } catch (UserAlreadyExistException e) {
            ResponseOutputer.append("Этот пользователь уже существует!\n");
        } catch (ClassCastException e) {
            ResponseOutputer.append("Переданный клиентом объект неверен!\n");
        }
        return false;
    }
}
