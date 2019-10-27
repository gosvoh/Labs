package ga.gosvoh.utils;

import ga.gosvoh.StartClient;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class PacketUtils {
    private static boolean loadImport = false;

    public static byte[] createMetadata(Byte commandCode, int countOfPackets, int totalCount) {
        ByteBuffer metadata = ByteBuffer.allocate(Defines.METADATA_LENGTH);
        return metadata.put(commandCode).put((byte) countOfPackets).putInt(totalCount).array();
    }

    private static ArrayList<byte[]> wtf = new ArrayList<>();

    public static ReceivedData receivePacket() {
        int currentPacketNumber = 0, receivedPackets = 0;
        byte[] hell = new byte[Defines.PACKET_LENGTH];

        ByteBuffer receivedBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH), dataBuffer;
        //CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();
        ArrayList<byte[]> packetsParts = new ArrayList<>();


        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < Defines.RECEIVING_TIMEOUT) {
            receivedBuffer.clear();

            try {
                StartClient.getChannel().receive(receivedBuffer);
                if (receivedBuffer.position() == 0)
                    continue;
                receivedPackets++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (receivedPackets == 0)
                continue;

            receivedBuffer.flip();
            byte commandCode = receivedBuffer.get();
            int countOfPackets = receivedBuffer.get() & 0xff;
            int totalCount = receivedBuffer.getInt();

            if (countOfPackets > 0) {
                while (packetsParts.size() < countOfPackets)
                    packetsParts.add(null);
                if (packetsParts.size() == currentPacketNumber)
                    packetsParts.add(receivedBuffer.array().clone());
                if (packetsParts.get(currentPacketNumber) == null)
                    packetsParts.set(currentPacketNumber, receivedBuffer.array().clone());
                currentPacketNumber++;
            }

            if (countOfPackets == currentPacketNumber) {
                dataBuffer = ByteBuffer.allocate(countOfPackets * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH));
                for (byte[] b : packetsParts) {
                    dataBuffer.put(b, Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
                }
                return new ReceivedData(commandCode, totalCount, dataBuffer.array());
            }
        }
        return new ReceivedData();
    }

    /**
     * Отправить только ответ отсервера
     *
     * @param cmd ответ от сервера
     */
    public static void sendData(byte[] cmd) throws PacketOverflowException {
        sendData(cmd, (byte) 0x00, 1);
    }

    /**
     * Отправить ответ от сервера, в том числе и словарь
     *
     * @param cmd данные
     * @throws PacketOverflowException выбрасывает исключение, если пакетов больше чем 256
     */
    public static void sendData(byte[] cmd, byte commandCode, int totalCount) throws PacketOverflowException {
        int countOfPackets = (int) Math.ceil(cmd.length / (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH)) +
                (cmd.length % (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH) == 0 ? 0 : 1);
        countOfPackets = countOfPackets == 0 ? 1 : countOfPackets;
        if (countOfPackets > 256)
            throw new PacketOverflowException("Too many packets for this request!");
        ByteBuffer byteBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH);
        ByteBuffer data = ByteBuffer.wrap(cmd);
        for (int i = 0; i < countOfPackets; i++) {
            byteBuffer.clear();
            byteBuffer.put(createMetadata(commandCode, countOfPackets, totalCount));
            if (((data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH))) >= (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH)))
                byteBuffer.put(data.array(), i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH), Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
            else {
                byteBuffer.put(data.array(), i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH), (data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH))));
                byte[] spaces = new byte[(Defines.PACKET_LENGTH - Defines.METADATA_LENGTH) - (data.limit() - (i * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH)))];
                Arrays.fill(spaces, " ".getBytes()[0]);
                byteBuffer.put(spaces);
            }
            byteBuffer.flip();
            try {
                StartClient.getChannel().send(byteBuffer, StartClient.getInetSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startLoadImport() {
        loadImport = true;
    }

    public static void stopLoadImport() {
        loadImport = false;
    }

    public static boolean isLoadImport() {
        return loadImport;
    }
}
