package ga.gosvoh.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class PacketUtils {
    /**
     * Добавить данные в ByteBuffer (костыль для уменьшения повторений кода)
     *
     * @param byteBuffer            буфер, куда нужно добавить данные
     * @param wrappedData           данные для добавления
     * @param countOfPackets        количество покетов
     * @param currentPacketIterator итератор текущего пакета данных для обработки
     * @param METADATA_LENGTH       длина метаданных, в зависимости от класса в котором используется
     */
    public static void putDataIntoByteBuffer(ByteBuffer byteBuffer, ByteBuffer wrappedData, int countOfPackets, int currentPacketIterator, int METADATA_LENGTH) {
        byteBuffer.put((byte) countOfPackets);
        byteBuffer.put((byte) currentPacketIterator);
        if (((wrappedData.limit() - (currentPacketIterator * (Defines.PACKET_LENGTH - METADATA_LENGTH))) >= (Defines.PACKET_LENGTH - METADATA_LENGTH)))
            byteBuffer.put(wrappedData.array(), currentPacketIterator * (Defines.PACKET_LENGTH - METADATA_LENGTH), Defines.PACKET_LENGTH - METADATA_LENGTH);
        else {
            byteBuffer.put(wrappedData.array(), currentPacketIterator * (Defines.PACKET_LENGTH - METADATA_LENGTH), (wrappedData.limit() - (currentPacketIterator * (Defines.PACKET_LENGTH - METADATA_LENGTH))));
            byte[] spaces = new byte[(Defines.PACKET_LENGTH - METADATA_LENGTH) - (wrappedData.limit() - (currentPacketIterator * (Defines.PACKET_LENGTH - METADATA_LENGTH)))];
            Arrays.fill(spaces, " ".getBytes()[0]);
            byteBuffer.put(spaces);
        }
    }

    public static ByteBuffer createMetadata(int countOfUniverses, int universeKey, int countOfPackets, int currentPacketNumber) {
        ByteBuffer metadata = ByteBuffer.allocate(Defines.METADATA_LENGTH);
        if (countOfUniverses == -1) {
            metadata.put(" ".getBytes(), 0, 8);
        } else metadata.putInt(countOfUniverses).putInt(universeKey);
        metadata.put((byte) countOfPackets).put((byte) currentPacketNumber);
        return metadata;
    }

    public static ReceivedData receivePacket() {
        int countOfUniverses, universeKey, receivedUniverse = 0;
        int countOfPackets = -1, currentPacketNumber, receivedPackets = 0;

        ByteBuffer receivedBuffer = ByteBuffer.allocate(Defines.PACKET_LENGTH), dataBuffer;
        CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();

        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < Defines.RECEIVING_TIMEOUT) {
            receivedBuffer.clear();

            try {
                DatagramChannel channel = DatagramChannel.open();
                channel.receive(receivedBuffer);
                if (receivedBuffer.position() == 0)
                    continue;
                receivedPackets++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (receivedPackets == 0)
                continue;

            countOfUniverses = receivedBuffer.getInt();
            universeKey = receivedBuffer.getInt();
            countOfPackets = receivedBuffer.get() & 0xff;
            currentPacketNumber = receivedBuffer.get() & 0xff;

            ReceivedData receivedData = new ReceivedData(
                    receivedBuffer.getInt(),
                    receivedBuffer.getInt(),
                    receivedBuffer.get() & 0xff,
                    receivedBuffer.get() & 0xff,
                    receivedBuffer.);

            /*if (countOfUniverses == 0) {
                if (countOfPackets > 0) {
                    while (packetsParts.size() < currentPacketNumber)
                        packetsParts.add(null);
                    if (packetsParts.size() == currentPacketNumber)
                        packetsParts.add(receivedBuffer);
                    if (packetsParts.get(currentPacketNumber) == null)
                        packetsParts.set(currentPacketNumber, receivedBuffer);

                    if (countOfPackets == currentPacketNumber){
                        dataBuffer = ByteBuffer.allocate(countOfPackets * (Defines.PACKET_LENGTH - Defines.METADATA_LENGTH));
                        for (ByteBuffer b : packetsParts) {
                            dataBuffer.put(receivedBuffer.array(), Defines.METADATA_LENGTH, Defines.PACKET_LENGTH - Defines.METADATA_LENGTH);
                            return dataBuffer;
                        }
                    }
                }
            }

            if (countOfUniverses > 1) {
                if (countOfPackets == 1) {

                }
            }*/
        }

        return null;
    }
}
