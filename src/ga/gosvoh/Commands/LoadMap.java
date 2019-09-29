package ga.gosvoh.Commands;

import ga.gosvoh.Server.ClientConnection;
import ga.gosvoh.Server.ClientID;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.Utils.PacketOverflowException;
import ga.gosvoh.Utils.PacketUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

import static ga.gosvoh.Utils.Defines.*;

/**
 * Загрузить файл на локальный компьютер
 */
public class LoadMap implements Command {
    private ConcurrentSkipListMap<Integer, Universe> map;

    public LoadMap() {
        map = UniverseCollection.getSortedCollection();
    }

    @Override
    public String execute(String[] arguments) {
        DatagramSocket socket;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            return "Ошибка во время загрузки словаря!";
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(PACKET_LENGTH);

        Iterator iterator = map.keySet().iterator();
        for (Universe universe : map.values()) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(universe);

                int countOfPackets = (int) Math.ceil(byteArrayOutputStream.toByteArray().length / (PACKET_LENGTH - COLLECTION_METADATA_LENGTH)) +
                        (byteArrayOutputStream.toByteArray().length % (PACKET_LENGTH - COLLECTION_METADATA_LENGTH) == 0 ? 0 : 1);
                if (countOfPackets > 256)
                    throw new PacketOverflowException("Too many packets for this request!");

                ByteBuffer wrappedData = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                for (int i = 0; i < countOfPackets; i++) {
                    byteBuffer.clear();
                    byteBuffer.put(PacketUtils.intToBytes(map.size()));
                    byteBuffer.put(PacketUtils.intToBytes((int) iterator.next()));
                    PacketUtils.putDataIntoByteBuffer(byteBuffer, wrappedData, countOfPackets, i, COLLECTION_METADATA_LENGTH);
                    byteBuffer.flip();

                    try {
                        DatagramPacket packet = new DatagramPacket(byteBuffer.array(), byteBuffer.array().length, ClientConnection.getInetSocketAddress());
                        socket.send(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка во время загрузки словаря!";
            }
        }
        return "";
    }
}
