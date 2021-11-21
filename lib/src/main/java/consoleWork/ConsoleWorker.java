package consoleWork;

import java.util.Scanner;

public class ConsoleWorker implements consoleWork.Scanner {

    private static Scanner scanner = new Scanner(System.in);

    public static String read() {
        return scanner.nextLine();
    }

    public static void write(String str) {
        System.out.println(str);
    }

    public static boolean checkNextLine(){return scanner.hasNextLine();}

}
