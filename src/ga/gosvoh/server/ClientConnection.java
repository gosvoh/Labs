package ga.gosvoh.server;

import static ga.gosvoh.utils.Defines.*;

import ga.gosvoh.CommandManager;
import ga.gosvoh.utils.PacketOverflowException;
import ga.gosvoh.utils.PacketUtils;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class ClientConnection extends Thread implements Runnable {
    private CopyOnWriteArrayList<DatagramPacket> packetsParts;
    private DatagramSocket socket;
    private static InetSocketAddress inetSocketAddress;

    public ClientConnection(CopyOnWriteArrayList<DatagramPacket> packetsParts) {
        this.packetsParts = packetsParts;
    }

    @Override
    public void run() {
        inetSocketAddress = new InetSocketAddress(packetsParts.get(0).getAddress(), packetsParts.get(0).getPort());
        String cmd = unpackData(packetsParts).trim();
        System.out.println("Received data from " + inetSocketAddress + ": " + cmd);
        String answer = CommandManager.ExecuteCommand(cmd);
        try {
            Thread.sleep(RECEIVING_TIMEOUT * 3);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        sendData(answer);
        System.out.println(answer);
    }

    private String unpackData(CopyOnWriteArrayList<DatagramPacket> packetsParts) {
        StringBuilder ret = new StringBuilder();
        for (DatagramPacket packetsPart : packetsParts) {
            byte[] currentPacketData = packetsPart.getData();
            ret.append(new String(currentPacketData, METADATA_LENGTH, PACKET_LENGTH - METADATA_LENGTH));
        }
        return ret.toString();
    }

    private void sendData(String data) throws PacketOverflowException {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        int countOfPackets = (int) Math.ceil(data.getBytes().length / (PACKET_LENGTH - METADATA_LENGTH)) + (data.getBytes().length % (PACKET_LENGTH - METADATA_LENGTH) == 0 ? 0 : 1);
        if (countOfPackets > 256)
            throw new PacketOverflowException("Too many packets for this request!");
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH);
        ByteBuffer wrappedData = ByteBuffer.wrap(data.getBytes());
        for (int i = 0; i < countOfPackets; i++) {
            byteBuffer.clear();
            PacketUtils.putDataIntoByteBuffer(byteBuffer, wrappedData, countOfPackets, i, METADATA_LENGTH);
            byteBuffer.flip();
            try {
                DatagramPacket packet = new DatagramPacket(byteBuffer.array(), byteBuffer.array().length, inetSocketAddress);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ClientID.getClientID(packetsParts.get(0).getAddress(), packetsParts.get(0).getPort()).stopProcessing();
    }

    public static InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public static ClientID getCurrentClientID() {
        return ClientID.getClientID(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
    }
}
