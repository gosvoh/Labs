package ga.gosvoh;

import ga.gosvoh.utils.*;

import static ga.gosvoh.utils.Defines.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings({"InfiniteLoopStatement", "UnnecessaryContinue"})
public class StartClient {
    private static DatagramChannel channel = null;
    private static InetSocketAddress inetSocketAddress;

    public static void main(String[] args) {
        new UniverseCollection(DEFAULT_JSON_FILE_PATH);

        Scanner cmdScanner = new Scanner(System.in);

        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        inetSocketAddress = new InetSocketAddress(ADDRESS, PORT);

        while (true) {
            System.out.print("Input command: ");
            String cmd = cmdScanner.nextLine();
            try {
                if (CommandManager.isCommand(cmd))
                    CommandManager.ExecuteCommand(cmd);
                else
                    PacketUtils.sendData(cmd.getBytes());
                    //sendPacket(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < RECEIVING_ATTEMPTS; i++) {
                try {
                    Thread.sleep(RECEIVING_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*if (!receivePacket()) {
                    continue;
                } else
                    break;*/
                if (!PacketUtils.receivePacket().isFullData())
                    continue;
                else
                    break;
            }
        }
    }

    public static void sendPacket(String data) throws PacketOverflowException {
        int countOfPackets = (int) Math.ceil(data.getBytes().length / (PACKET_LENGTH - METADATA_LENGTH)) + (data.getBytes().length % (PACKET_LENGTH - METADATA_LENGTH) == 0 ? 0 : 1);
        if (countOfPackets > 256)
            throw new PacketOverflowException("Too many packets for this request!");
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH);
        ByteBuffer d = ByteBuffer.wrap(data.getBytes());
        for (int i = 0; i < countOfPackets; i++) {
            byteBuffer.clear();
            byteBuffer.put(PacketUtils.createMetadata(0, 0, countOfPackets, i));
            if (((d.limit() - (i * (PACKET_LENGTH - METADATA_LENGTH))) >= (PACKET_LENGTH - METADATA_LENGTH)))
                byteBuffer.put(d.array(), i * (PACKET_LENGTH - METADATA_LENGTH), PACKET_LENGTH - METADATA_LENGTH);
            else {
                byteBuffer.put(d.array(), i * (PACKET_LENGTH - METADATA_LENGTH), (d.limit() - (i * (PACKET_LENGTH - METADATA_LENGTH))));
                byte[] spaces = new byte[(PACKET_LENGTH - METADATA_LENGTH) - (d.limit() - (i * (PACKET_LENGTH - METADATA_LENGTH)))];
                Arrays.fill(spaces, " ".getBytes()[0]);
                byteBuffer.put(spaces);
            }
            byteBuffer.flip();
            try {
                channel.send(byteBuffer, inetSocketAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean receivePacket() {
        CopyOnWriteArrayList<ByteBuffer> packetsParts = new CopyOnWriteArrayList<>();
        int countOfPackets = -1, receivedPackets = 0, currentPacketNumber = -1;
        ByteBuffer response;
        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < RECEIVING_TIMEOUT) {
            byteBuffer.clear();
            try {
                channel.receive(byteBuffer);
                if (byteBuffer.position() == 0)
                    continue;
                receivedPackets++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (receivedPackets > 0) {
                countOfPackets = byteBuffer.get(0) & 0xff;
                currentPacketNumber = byteBuffer.get(1) & 0xff;
            }
            if (countOfPackets > 0) {
                while (packetsParts.size() < currentPacketNumber)
                    packetsParts.add(null);
                if (packetsParts.size() == currentPacketNumber)
                    packetsParts.add(byteBuffer);
                if (packetsParts.get(currentPacketNumber) == null)
                    packetsParts.set(currentPacketNumber, byteBuffer);
            }
        }
        if (countOfPackets == receivedPackets) {
            response = ByteBuffer.allocate(countOfPackets * (PACKET_LENGTH - METADATA_LENGTH));
            for (ByteBuffer b : packetsParts) {
                b.flip();
                response.put(b.array(), METADATA_LENGTH, PACKET_LENGTH - METADATA_LENGTH);
            }
            System.out.println(new String(response.array()).trim());
            return true;
        } else return false;
    }

    public static InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public static DatagramChannel getChannel() {
        return channel;
    }
}
