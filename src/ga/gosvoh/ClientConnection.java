package ga.gosvoh;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

@SuppressWarnings("WeakerAccess")
public class ClientConnection implements Runnable {
    private DatagramSocket socket;

    public ClientConnection(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            int PACKET_LENGTH = 2048;
            byte[] buffer = new byte[PACKET_LENGTH];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);
                System.out.println("Принят пакет!");
            } catch (SocketTimeoutException e) {
                ClientID.clearAll();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (packet.getAddress() == null)
                continue;
            ClientID clientID = ClientID.getClientID(packet.getAddress(), packet.getPort());

            System.out.println(clientID.toString());
            System.out.println(packet.getLength());
            System.out.println(ClientID.getIds().size());
        }
    }
}
