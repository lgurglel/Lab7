import managers.RequestManager;
import managers.RequestProcessingRunning;
import utils.Request;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    private int port;
    private RequestManager requestManager;
    private DatagramSocket socket;
    private InetAddress address;
    private ExecutorService readRequest = Executors.newCachedThreadPool();
    private ExecutorService executeRequest = Executors.newFixedThreadPool(10);
    private Request request;
    private Scanner scanner;

    public Server(int port, RequestManager requestManager) {
        this.port = port;
        this.requestManager = requestManager;
    }

    public void run() {
        do_CTRL_C_Thread();
        try {
            System.out.println("Сервер запущен.");
            socket = new DatagramSocket(this.port);
            while (true) {
                try {
                    if (!readRequest.submit(() -> {
                        try {
                            request = getRequest();
                            System.out.println("Получена команда '" + request.getCommandName() + "'");
                            return true;
                        } catch (ClassNotFoundException | IOException e) {
                            System.out.println("Произошла ошибка при чтении запроса!");
                        }
                        return false;
                    }).get()) break;
                    executeRequest.execute(new RequestProcessingRunning(requestManager, request,address,port,socket));
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("При чтении запроса произошла ошибка многопоточности!");
                }
            }
        } catch (SocketException e) {
            System.out.println("Произошла ошибка при работе с сокетом!");
        }
    }

    private Request getRequest() throws IOException, ClassNotFoundException {
        byte[] getBuffer = new byte[socket.getReceiveBufferSize()];
        DatagramPacket getPacket = new DatagramPacket(getBuffer, getBuffer.length);
        socket.receive(getPacket);
        address = getPacket.getAddress();
        port = getPacket.getPort();
        return deserialize(getPacket, getBuffer);
    }

    private Request deserialize(DatagramPacket getPacket, byte[] buffer) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getPacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Request request = (Request) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    private void do_CTRL_C_Thread() {
        scanner = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Завершаю программу.");
        }));
    }
}
