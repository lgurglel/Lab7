package managers;

import commands.*;
import consoleWork.ConsoleWorker;
import consoleWork.Outputer;
import consoleWork.Scanner;
import consoleWork.ScriptScanner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private ConsoleWorker consoleWorker;
    private CollectionManager collectionManager;
    private FileManager fileManager;
    private List<String> scriptsFiles = new ArrayList();

    public CommandManager(ConsoleWorker consoleWorker, CollectionManager collectionManager, FileManager fileManager) {
        this.consoleWorker = consoleWorker;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
    }
    private List<AbstractCommand> commands = new ArrayList<>();
    private AbstractCommand helpCommand;
    private AbstractCommand infoCommand;
    private AbstractCommand showCommand;
    private AbstractCommand addCommand;
    private AbstractCommand updateCommand;
    private AbstractCommand removeByIdCommand;
    private AbstractCommand clearCommand;
    private AbstractCommand executeScriptCommand;
    private AbstractCommand exitCommand;
    private AbstractCommand removeGreaterCommand;
    private AbstractCommand reorderCommand;
    private AbstractCommand removeAllByPriceCommand;
    private AbstractCommand filterByPriceCommand;
    private AbstractCommand sortCommand;
    private AbstractCommand saveCommand;
    private AbstractCommand sumOfDiscountCommand;

    public CommandManager(AbstractCommand helpCommand,
                          AbstractCommand infoCommand,
                          AbstractCommand showCommand,
                          AbstractCommand addCommand,
                          AbstractCommand updateCommand,
                          AbstractCommand removeByIdCommand,
                          AbstractCommand clearCommand,
                          AbstractCommand executeScriptCommand,
                          AbstractCommand exitCommand,
                          AbstractCommand removeGreaterCommand,
                          AbstractCommand reorderCommand,
                          AbstractCommand removeAllByPriceCommand,
                          AbstractCommand filterByPriceCommand,
                          AbstractCommand saveCommand,
                          AbstractCommand sumOfDiscountCommand,
                          AbstractCommand sortCommand) {
        this.helpCommand = helpCommand;
        commands.add(helpCommand);
        this.infoCommand = infoCommand;
        commands.add(infoCommand);
        this.showCommand = showCommand;
        commands.add(showCommand);
        this.addCommand = addCommand;
        commands.add(addCommand);
        this.updateCommand = updateCommand;
        commands.add(updateCommand);
        this.removeByIdCommand = removeByIdCommand;
        commands.add(removeByIdCommand);
        this.clearCommand = clearCommand;
        commands.add(clearCommand);
        this.executeScriptCommand = executeScriptCommand;
        commands.add(executeScriptCommand);
        this.exitCommand = exitCommand;
        commands.add(exitCommand);
        this.removeGreaterCommand = removeGreaterCommand;
        commands.add(removeGreaterCommand);
        this.reorderCommand = reorderCommand;
        commands.add(reorderCommand);
        this.removeAllByPriceCommand = removeAllByPriceCommand;
        commands.add(removeAllByPriceCommand);
        this.filterByPriceCommand = filterByPriceCommand;
        commands.add(filterByPriceCommand);
        this.saveCommand = saveCommand;
        commands.add(saveCommand);
        this.sumOfDiscountCommand = sumOfDiscountCommand;
        commands.add(sumOfDiscountCommand);
        this.sortCommand = sortCommand;
        commands.add(sortCommand);
    }

    public boolean help(String argument, Object objectArgument) {
        if (helpCommand.execute(argument, objectArgument)) {
            for (AbstractCommand command : commands) {
                ResponseOutputer.appendTable(command.getName(), command.getDescription());
            }
            return true;
        } else return false;
    }

    public boolean info(String argument, Object objectArgument) {
        return infoCommand.execute(argument, objectArgument);
    }

    public boolean show(String argument, Object objectArgument) {
        return showCommand.execute(argument, objectArgument);
    }

    public boolean add(String argument, Object objectArgument) {
        return addCommand.execute(argument, objectArgument);
    }

    public boolean update(String argument, Object objectArgument) {
        return updateCommand.execute(argument, objectArgument);
    }

    public boolean removeById(String argument, Object objectArgument) {
        return removeByIdCommand.execute(argument, objectArgument);
    }

    public boolean save(String argument, Object objectArgument) {
        return saveCommand.execute(argument, objectArgument);
    }

    public boolean clear(String argument, Object objectArgument) {
        return clearCommand.execute(argument, objectArgument);
    }

    public boolean removeGreater(String argument, Object objectArgument) {
        return removeGreaterCommand.execute(argument, objectArgument);
    }

    public boolean removeAllByPrice(String argument, Object objectArgument) {
        return removeAllByPriceCommand.execute(argument, objectArgument);
    }

    public boolean reorder(String argument, Object objectArgument) {
        return reorderCommand.execute(argument, objectArgument);
    }

    public boolean sort(String argument, Object objectArgument) {
        return sortCommand.execute(argument, objectArgument);
    }

    public boolean filterByPrice(String argument, Object objectArgument) {
        return filterByPriceCommand.execute(argument, objectArgument);
    }

    public boolean sumOfDiscount(String argument, Object objectArgument) {
        return sumOfDiscountCommand.execute(argument, objectArgument);
    }

    public boolean exit(String argument, Object objectArgument) {
        return exitCommand.execute(argument, objectArgument);
    }
    public boolean executeScript(String argument, Object objectArgument) {
        return executeScriptCommand.execute(argument, objectArgument);
    }
//    public void checkRequest(String request, Scanner scanner) {
//        if (request == null && request.equals("")) {
//            Outputer.println("Строка не должна быть пустой!");
//            return;
//        }
//        request = request.toLowerCase().trim();
//
//        String commandName = request.split(" ")[0];
//
//        // волидация проверка
//        AbstractCommand command = null;
//        if (commandName.equals(new AddCommand().toString())) {
//            command = new AddCommand(collectionManager);
//        } else if (commandName.equals(new ClearCommand().toString())) {
//            command = new ClearCommand(collectionManager);
//        } else if (commandName.equals(new ExecuteScriptCommand().toString())) {
//            command = new ExecuteScriptCommand(collectionManager, getParameter(request, scanner), fileManager);
//        } else if (commandName.equals(new ExitCommand().toString())) {
//            command = new ExitCommand(collectionManager);
//        } else if (commandName.equals(new HelpCommand().toString())) {
//            command = new HelpCommand(collectionManager);
//        } else if (commandName.equals(new InfoCommand().toString())) {
//            command = new InfoCommand(collectionManager);
//        } else if (commandName.equals(new RemoveByIdCommand().toString())) {
//            command = new RemoveByIdCommand(collectionManager, getParameter(request, scanner));
//        } else if (commandName.equals(new SaveCommand().toString())) {
//            command = new SaveCommand(collectionManager);
//        } else if (commandName.equals(new ShowCommand().toString())) {
//            command = new ShowCommand(collectionManager);
//        } else if (commandName.equals(new UpdateCommand().toString())) {
//            command = new UpdateCommand(collectionManager, getParameter(request, scanner));
//        } else if (commandName.equals(new SumOfDiscountCommand().toString())) {
//            command = new SumOfDiscountCommand(collectionManager);
//        } else if (commandName.equals(new RemoveGreaterCommand().toString())) {
//            command = new RemoveGreaterCommand(collectionManager);
//        } else if (commandName.equals(new FilterByPriceCommand().toString())) {
//            command = new FilterByPriceCommand(collectionManager, getParameter(request, scanner));
//        } else if (commandName.equals(new RemoveAllByPriceCommand().toString())) {
//            command = new RemoveAllByPriceCommand(collectionManager, getParameter(request, scanner));
//        } else if (commandName.equals(new ReorderCommand().toString())) {
//            command = new ReorderCommand(collectionManager);
//        } else if (commandName.equals(new SortCommand().toString())) {
//            command = new SortCommand(collectionManager);
//        }
//        if (command == null) {
//            Outputer.println("Неизвестная команда! Введите help, чтоб увидеть список команд.");
//            return;
//        } else {
//            Response response = command.execute(scanner);
//            if (command.toString().equals(new ExecuteScriptCommand().toString())) {
//                if (response.getMessage().equals("false")) {
//                    Outputer.println("Проблема с запуском скрипта");
//                } else {
//                    String filePath = new File(getParameter(request, scanner)).getAbsolutePath();
//                    if (scriptsFiles.contains(filePath)) {
//                        Outputer.println("\nРекурсия!");
//                        scriptsFiles.clear();
//                        return;
//                    } else {
//                        scriptsFiles.add(filePath);
//                        executeScript(response.getMessage());
//                        scriptsFiles.remove(filePath);
//                    }
//                }
//            } else {
//                Outputer.println(response.getMessage());
//            }
//        }
//    }

//    private void executeScript(String script) {
//        Scanner scanner = new ScriptScanner(script);
//        while (!scriptsFiles.isEmpty()) {
//            String command = scanner.read();
//            if (command == null) {
//                break;
//            }
//            checkRequest(command, scanner);
//        }
//    }
//
//
//    public String getParameter(String request, Scanner scanner) {
//        try {
//            return request.split(" ")[1];
//        } catch (Exception e) {
//            return "Неверно указан параметр";
//        }
//    }

}
