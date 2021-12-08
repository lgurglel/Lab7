import exceptions.NotDeclaredValueException;
import exceptions.WrongAmountOfParametersException;
import managers.AuthManager;
import managers.UserHandler;

import java.util.Scanner;

public class Main {
    private static String host = "localhost";
    private static int port = 3274;

    public static void main(String[] args) {
//        if (!checkArgs(args)) return;
        System.out.println("Клиент запущен");
        Scanner scanner = new Scanner(System.in);
        AuthManager authManager = new AuthManager(scanner);
        UserHandler userHandler = new UserHandler(scanner, authManager);
        Client client = new Client(host, port, userHandler, authManager);
        client.run();
        scanner.close();
    }

//    private static boolean checkArgs(String[] args) {
//        try {
//            if (args.length != 2) throw new WrongAmountOfParametersException();
//            host = args[0];
//            port = Integer.parseInt(args[1]);
//            if (port < 0) throw new NotDeclaredValueException();
//            return true;
//        } catch (WrongAmountOfParametersException e) {
//            System.out.println("Нужно передавать 2 параметра <String host, int port>!");
//        } catch (NotDeclaredValueException e) {
//            System.out.println("Порт не может быть отрицательным числом!");
//        } catch (NumberFormatException e) {
//            System.out.println("Порт должен быть представлен числом!");
//        }
//        return false;
//    }
}

