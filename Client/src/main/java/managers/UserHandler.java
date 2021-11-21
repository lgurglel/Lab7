package managers;

import consoleWork.Outputer;
import exceptions.CommandUsageException;
import exceptions.IncorrectInputInScriptException;
import exceptions.ScriptRecursionException;
import tasck.Coordinates;
import tasck.Ticket;
import tasck.TicketType;
import tasck.Venue;
import utils.Request;
import utils.ResponseCode;
import utils.Tickets;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class UserHandler {
    private Scanner scanner;
    private Stack<File> scriptFileNames = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public Request interactiveMode(ResponseCode serverResponseCode) {
        String userInput;
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
                        Outputer.println("Возвращаюсь из скрипта '" + scriptFileNames.pop().getName() + "'!");
                    }
                    if (fileMode()) {
                        userInput = scanner.nextLine();
                        if (!userInput.isEmpty()) {
                            Outputer.println(userInput);
                        }
                    } else {
                        userInput = scanner.nextLine();
                    }
                    userCommand = (userInput.trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                    Outputer.println("Произошла ошибка при вводе команды!");
                    userCommand = new String[]{"", ""};
                }
                processCode = processCommand(userCommand[0], userCommand[1]);
            } while (processCode == ProcessingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                switch (processCode) {
                    case OBJECT:
                        Ticket ticketsAdder = generateTicketAdd();
                        return new Request(userCommand[0], userCommand[1], ticketsAdder);
                    case UPDATE_OBJECT:
                        Tickets ticketsUpdate = generateTicketsUpdate();
                        return new Request(userCommand[0], userCommand[1], ticketsUpdate);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptFileNames.isEmpty() && scriptFileNames.search(scriptFile) != -1) {
                            throw new ScriptRecursionException();
                        }
                        scannerStack.push(scanner);
                        scriptFileNames.push(scriptFile);
                        scanner = new Scanner(scriptFile);
                        Outputer.println("Выполняю скрипт '" + scriptFile.getName() + "'!");
                        break;
                }
            } catch (FileNotFoundException exception) {
                Outputer.printerror("Файл со скриптом не найден!");
            } catch (ScriptRecursionException exception) {
                Outputer.printerror("Скрипты не могут вызываться рекурсивно!");
                throw new IncorrectInputInScriptException();
            }
        } catch (IncorrectInputInScriptException exception) {
            Outputer.printerror("Выполнение скрипта прервано!");
            while (!scannerStack.isEmpty()) {
                scanner.close();
                scanner = scannerStack.pop();
            }
        }
        return new Request(userCommand[0], userCommand[1]);
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

    private Ticket generateTicketAdd() {
        TicketAdder ticketAdder = new TicketAdder(scanner);
        if (fileMode()) ticketAdder.setFileMode();
        return new Ticket(0l,
                ticketAdder.createNewName(),
                ticketAdder.createNewCoordinates(),
                null,
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

