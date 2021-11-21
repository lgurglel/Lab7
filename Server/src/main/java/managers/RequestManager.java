package managers;

import utils.Request;
import utils.Response;
import utils.ResponseCode;

public class RequestManager {
    private CommandManager commandManager;

    public RequestManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response manage(Request request) {
        ResponseCode responseCode = executeCommand(request.getCommandName(), request.getArgument(), request.getObjectArgument());
        return new Response(responseCode,ResponseOutputer.getAndClear());
    }

    private ResponseCode executeCommand(String command, String argument, Object objectArgument) {
        switch (command) {
            case "":
                break;
            case "help":
                if (!commandManager.help(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "info":
                if (!commandManager.info(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "show":
                if (!commandManager.show(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "add":
                if (!commandManager.add(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "update":
                if (!commandManager.update(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "remove_by_id":
                if (!commandManager.removeById(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "clear":
                if (!commandManager.clear(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "execute_script":
                if (!commandManager.executeScript(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "exit":
                if (!commandManager.exit(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "remove_greater":
                if (!commandManager.removeGreater(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "remove_all_by_price":
                if (!commandManager.removeAllByPrice(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "reorder":
                if (!commandManager.reorder(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "sum_of_discount":
                if (!commandManager.sumOfDiscount(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "sort":
                if (!commandManager.sort(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "save":
                if (!commandManager.save(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            case "filter_by_price":
                if (!commandManager.filterByPrice(argument, objectArgument)) return ResponseCode.ERROR;
                break;
            default:
                ResponseOutputer.append("Команда '" + command + "' не найдена. Наберите 'help' для справки.\n");
                return ResponseCode.ERROR;
        }
        return ResponseCode.OK;
    }
}
