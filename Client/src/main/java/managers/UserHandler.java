package managers;

import consoleWork.Outputer;
import exceptions.CommandUsageException;
import exceptions.IncorrectInputInScriptException;
import exceptions.ScriptRecursionException;
import tasck.Coordinates;
import tasck.TicketType;
import tasck.Venue;
import utils.Request;
import utils.ResponseCode;
import tasck.Tickets;
import utils.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class UserHandler {
    private Scanner scanner;
    private Stack<File> scriptFileNames = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();
    private AuthManager authManager;

    public UserHandler(Scanner scanner, AuthManager authManager) {
        this.scanner = scanner;
        this.authManager = authManager;
    }

    public Request interactiveMode(ResponseCode serverResponseCode, User user) {
        String userInput = "";
        String[] userCommand = {"", ""};
        ProcessingCode processCode = null;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == ResponseCode.SERVER_EXIT || serverResponseCode == ResponseCode.ERROR)) {
                        throw new IncorrectInputInScriptException();
                    }
                    while (fileMode() && !scanner.hasNextLine()) {
                        scanner.close();
                        scanner = scannerStack.pop();
                        System.out.println("Возвращаюсь из скрипта '" + scriptFileNames.pop().getName() + "'!");
                    }
                    if (fileMode()) {
                        userInput = scanner.nextLine();
                        if (!userInput.isEmpty()) {
                            System.out.println(userInput);
                        }
                    } else {
                        if (scanner.hasNext()) {
                            userInput = scanner.nextLine();
                        } else {
                            System.out.println("Клиент завершен!");
                            System.exit(0);
                        }
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    System.out.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                }
                processCode = processCommand(userCommand[0], userCommand[1]);
            } while (processCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                switch (processCode) {
                    case OBJECT:
                        Tickets ticketsToInsert = generateTicketAdd();
                        return new Request(userCommand[0], userCommand[1], ticketsToInsert, user);
                    case UPDATE_OBJECT:
                        Tickets ticketsToUpdate = generateTicketsUpdate();
                        return new Request(userCommand[0], userCommand[1], ticketsToUpdate, user);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptFileNames.isEmpty() && scriptFileNames.search(scriptFile) != -1) {
                            throw new ScriptRecursionException();
                        }
                        scannerStack.push(scanner);
                        scriptFileNames.push(scriptFile);
                        scanner = new Scanner(scriptFile);
                        System.out.println("Выполняю скрипт '" + scriptFile.getName() + "'!");
                        break;
                    case LOG_IN:
                        return authManager.handle();
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                System.out.println("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            System.out.println("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                scanner.close();
                scanner = scannerStack.pop();
            }
        }
        return new Request(userCommand[0], userCommand[1], null, user);
    }
    /**
     * Launches the command.
     *
     * @param command Command to launch.
     * @return Exit code.
     */
    private ProcessingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return ProcessingCode.ERROR;
                case "log_out":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "log_in":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.LOG_IN;
                case "help":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "show":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "clear":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "exit":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "sum_of_price":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "sort":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "reorder":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "save":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "add":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.OBJECT;
                case "update":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.UPDATE_OBJECT;
                case "remove_by_id":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.SCRIPT;
                case "remove_all_by_price":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                case "remove_greater":
                    if (!commandArgument.isEmpty()) throw new CommandUsageException();
                    return ProcessingCode.OBJECT;
                case "filter_by_price":
                    if (commandArgument.isEmpty()) throw new CommandUsageException();
                    break;
                default:
                    Outputer.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessingCode.ERROR;
            }
        } catch (CommandUsageException exception) {
            System.out.println("Проверьте правильность ввода аргументов!");
            return ProcessingCode.ERROR;
        }
        return ProcessingCode.OK;
    }

    private Tickets generateTicketAdd() {
        TicketAdder ticketAdder = new TicketAdder(scanner);
        if (fileMode()) ticketAdder.setFileMode();
        return new Tickets(ticketAdder.createNewName(),
                ticketAdder.createNewCoordinates(),
                ticketAdder.createNewPrice(),
                ticketAdder.createNewDiscount(),
                ticketAdder.createNewComment(),
                ticketAdder.createNewType(),
                ticketAdder.createNewVenue());
    }

    private Tickets generateTicketsUpdate() {
        TicketAdder ticketAdder = new TicketAdder(scanner);
        if (fileMode()) {
            ticketAdder.setFileMode();
        } else {
            ticketAdder.setUserMode();
        }
        String name = ticketAdder.askAboutChangingField("Хотите изменить название билета?") ?
                ticketAdder.createNewName() : null;
        Coordinates coordinates = ticketAdder.askAboutChangingField("Хотите изменить координаты?") ?
                ticketAdder.createNewCoordinates() : null;
        float price = ticketAdder.askAboutChangingField("Хотите изменить цену?") ?
                ticketAdder.createNewPrice() : -1;
        Long discount = ticketAdder.askAboutChangingField("Хотите изменить скидку на билет?") ?
                ticketAdder.createNewDiscount() : -1;
        String comment = ticketAdder.askAboutChangingField("Хотите изменить коментарий к билету?") ?
                ticketAdder.createNewComment() : null;
        TicketType type = ticketAdder.askAboutChangingField("Хотите изменить тип битела?") ?
                ticketAdder.createNewType() : null;
        Venue venue = ticketAdder.askAboutChangingField("Хотите изменить место проведения мероприятния на бителе?") ?
                ticketAdder.createNewVenue() : null;
        return new Tickets(
                name,
                coordinates,
                price,
                discount,
                comment,
                type,
                venue
        );
    }

    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
}

