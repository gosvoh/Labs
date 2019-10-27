package ga.gosvoh.commands;

import ga.gosvoh.CommandManager;
import ga.gosvoh.StartClient;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.utils.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoadMap implements Command {
    private ConcurrentSkipListMap<Long, Universe> map = new ConcurrentSkipListMap<>();
    private CopyOnWriteArrayList<ReceivedData> data;
    private int totalCount = 0, receivedTotalCount;

    public LoadMap() {
    }

    public LoadMap(CopyOnWriteArrayList<ReceivedData> data) {
        this.data = data;
    }

    @Override
    public void execute() {
        map.clear();

        if (!PacketUtils.isLoadImport())
            PacketUtils.sendData("".getBytes(), (byte) 0x04, 1);
        else {
            ConcurrentSkipListMap<Long, Universe> map = new ConcurrentSkipListMap<>();

            data = StartClient.getReceivedData();

            if (data.size() == 0)
                return;

            data.forEach(data -> {
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getData());
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    Universe universe = (Universe) objectInputStream.readObject();
                    System.out.println(universe);
                    map.put(universe.getNumber(), universe);
                } catch (IOException e) {
                    System.out.println("IO Ex");
                } catch (ClassNotFoundException e) {
                    System.out.println("Class Ex");
                }
            });

            UniverseCollection.setUniverseConcurrentSkipListMap(map);
            new SaveMap().execute();
            System.out.println("Словарь успешно сохранён на диск!");
            PacketUtils.stopLoadImport();
        }
    }
}
