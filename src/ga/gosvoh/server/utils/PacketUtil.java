package ga.gosvoh.server.utils;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class PacketUtil {

    public static byte[] createMetadata(Byte commandCode, int countOfPackets, int totalCount) {
        ByteBuffer metadata = ByteBuffer.allocate(Defines.METADATA_LENGTH);
        return metadata.put(commandCode).put((byte) countOfPackets).putInt(totalCount).array();
    }

    public static ReceivedData receivePacket(DatagramSocket socket) {
        int currentPacketNumber = 1, receivedPackets = 0;

        ByteBuffer receivedBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH), dataBuffer;
        CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < Defines.RECEIVING_TIMEOUT) {
            receivedBuffer.clear();

            DatagramPacket packet = new DatagramPacket(receivedBuffer.array(), receivedBuffer.array().length);

            try {
                socket.receive(packet);
                if (packet.getData().length == 0)
                    continue;
                receivedPackets++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (receivedPackets == 0)
                continue;

            byte commandCode = receivedBuffer.get();
            int countOfPackets = receivedBuffer.get() & 0xff;
            int totalCount = receivedBuffer.getInt();

            if (countOfPackets > 0) {
                while (packetsParts.size() < countOfPackets)
                    packetsParts.add(null);
                if (packetsParts.size() == currentPacketNumber)
                    packetsParts.add(receivedBuffer);
                if (packetsParts.get(currentPacketNumber) == null)
                    packetsParts.set(currentPacketNumber, receivedBuffer);
                currentPacketNumber++;
            }

            if (countOfPackets == currentPacketNumber) {
                dataBuffer = ByteBuffer.allocate(countOfPackets * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH));
                for (ByteBuffer b : packetsParts)
                    dataBuffer.put(receivedBuffer.array(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
                return new ReceivedData(new InetSocketAddress(packet.getAddress(), packet.getPort()), commandCode, totalCount, dataBuffer.array());
            }
        }
        return new ReceivedData();
    }

    /**
     * Отправить только ответ отсервера
     *
     * @param cmd ответ от сервера
     */
    public static void sendData(DatagramSocket socket, InetSocketAddress address, byte[] cmd) throws PacketOverflowException {
        sendData(socket, address, cmd, (byte) 0x00, 1);
    }

    /**
     * Отправить ответ от сервера, в том числе и словарь
     *
     * @param cmd данные
     * @throws PacketOverflowException выбрасывает исключение, если пакетов больше чем 256
     */
    public static void sendData(DatagramSocket socket, InetSocketAddress address, byte[] cmd, byte commandCode, int totalCount) throws PacketOverflowException {
        int countOfPackets = (int) Math.ceil(cmd.length / (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH)) +
                (cmd.length % (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH) == 0 ? 0 : 1);
        if (countOfPackets > 256)
            throw new PacketOverflowException("Too many packets for this request!");
        ByteBuffer byteBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH);
        ByteBuffer data = ByteBuffer.wrap(cmd);
        for (int i = 0; i < countOfPackets; i++) {
            byteBuffer.clear();
            byteBuffer.put(createMetadata(commandCode, countOfPackets, totalCount));
            if (((data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH))) >= (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH))) {
                byteBuffer.put(data.array(), i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH), Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
            } else {
                byteBuffer.put(data.array(), i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH), (data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH))));
                byte[] spaces = new byte[(Defines.PACKET_LENGTH - Defines.METADATA_LENGTH) - (data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH)))];
                Arrays.fill(spaces, " ".getBytes()[0]);
                byteBuffer.put(spaces);
            }
            DatagramPacket packet = new DatagramPacket(byteBuffer.array(), byteBuffer.array().length, address);
            try {
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
