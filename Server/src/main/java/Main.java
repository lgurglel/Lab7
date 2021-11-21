import commands.*;
import managers.FileManager;
import managers.CollectionManager;
import managers.CommandManager;
import managers.RequestManager;;

public class Main {
    public static final int PORT = 3274;
    public static final String ENV_VARIABLE = "envVariable";

    public static void main(String[] args) {
        FileManager fileManager = new FileManager(ENV_VARIABLE);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
                new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new AddCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new RemoveGreaterCommand(),
                new ReorderCommand(collectionManager),
                new RemoveAllByPriceCommand(collectionManager),
                new FilterByPriceCommand(collectionManager),
                new SaveCommand(collectionManager),
                new SumOfDiscountCommand(collectionManager),
                new SortCommand(collectionManager));
        RequestManager requestHandler = new RequestManager(commandManager);
        Server server = new Server(PORT, requestHandler);
        server.run();
    }
}
