package managers;

import utils.Request;
import utils.Response;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class RequestProcessingRunning implements Runnable {
    private RequestManager requestManager;
    private Request request;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;
    private ExecutorService answerThread = Executors.newFixedThreadPool(10);

    public RequestProcessingRunning(RequestManager requestManager, Request request, InetAddress address, int port, DatagramSocket socket) {
        this.requestManager = requestManager;
        this.request = request;
        this.address = address;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void run() {
        Response response = requestManager.manage(request);
        answerThread.execute(new ResponseSenderThread(response, address, port, socket));
    }
}
