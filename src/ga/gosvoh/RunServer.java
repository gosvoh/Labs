package ga.gosvoh;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class RunServer {
    private DatagramSocket socket;
    private static int PORT;
    private final int PACKET_LENGTH = 2048;
    private CopyOnWriteArrayList<ClientID> clientIDS = new CopyOnWriteArrayList<>();

    public RunServer() {
        Scanner input = new Scanner(System.in);
        String line;

        while (true) {
            try {
                System.out.print("Введите порт для запуска сервера: ");
                line = input.nextLine();
                PORT = Integer.parseInt(line);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }

        try {
            socket = new DatagramSocket(PORT);
            socket.setSoTimeout(10000);
        } catch (SocketException | IllegalArgumentException e) {
            System.out.println(e);
        }

        System.out.println("Сервер запущен!");

        new Thread(new ClientConnection(socket)).start();

        if (socket != null)
            socket.close();
    }
}
