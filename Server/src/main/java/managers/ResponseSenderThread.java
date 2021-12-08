package managers;

import utils.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class ResponseSenderThread implements Runnable{
    private Response response;
    private InetAddress address;
    private int port;
    private DatagramSocket socket;

    public ResponseSenderThread(Response response, InetAddress address, int port, DatagramSocket socket) {
        this.response = response;
        this.address = address;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            sendResponse(response);
        } catch (IOException exception) {
            System.out.println("Произошла ошибка при отправке ответа!");
        }
    }

    private void sendResponse(Response response) throws IOException {
        byte[] sendBuffer = serialize(response);
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
        socket.send(sendPacket);
    }

    private byte[] serialize(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        objectOutputStream.flush();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return buffer;
    }
}
