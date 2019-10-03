package ga.gosvoh.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static ga.gosvoh.utils.Defines.PACKET_LENGTH;

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
        if (((wrappedData.limit() - (currentPacketIterator * (PACKET_LENGTH - METADATA_LENGTH))) >= (PACKET_LENGTH - METADATA_LENGTH)))
            byteBuffer.put(wrappedData.array(), currentPacketIterator * (PACKET_LENGTH - METADATA_LENGTH), PACKET_LENGTH - METADATA_LENGTH);
        else {
            byteBuffer.put(wrappedData.array(), currentPacketIterator * (PACKET_LENGTH - METADATA_LENGTH), (wrappedData.limit() - (currentPacketIterator * (PACKET_LENGTH - METADATA_LENGTH))));
            byte[] spaces = new byte[(PACKET_LENGTH - METADATA_LENGTH) - (wrappedData.limit() - (currentPacketIterator * (PACKET_LENGTH - METADATA_LENGTH)))];
            Arrays.fill(spaces, " ".getBytes()[0]);
            byteBuffer.put(spaces);
        }
    }

    public static byte[] intToBytes(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

    public static int bytesToInt(byte... bytes) {
        int ret = 0;
        for (int i = 0; i < 4 && i < bytes.length; i++) {
            ret <<= 8;
            ret |= bytes[i] & 0xff;
        }
        return ret;
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


        return new ReceivedData();
    }

    public static void sendData(byte[] data) {

    }
}
