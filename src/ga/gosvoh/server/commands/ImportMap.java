package ga.gosvoh.server.commands;

import ga.gosvoh.server.CommandManager;
import ga.gosvoh.server.server.ClientID;
import ga.gosvoh.server.Universe;
import ga.gosvoh.server.UniverseCollection;
import ga.gosvoh.server.utils.ReceivedData;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Загрузить словарь на сервер
 */
public class ImportMap implements Command {
    private boolean isError = false;
    private ClientID clientID;

    public ImportMap() {
    }

    public ImportMap(ClientID clientID) {
        this.clientID = clientID;
    }

    @Override
    public String execute(String[] cmd) {
        ConcurrentSkipListMap<Long, Universe> map = new ConcurrentSkipListMap<>();
        clientID.startLoadImport();

        CopyOnWriteArrayList<ReceivedData> receivedData = clientID.getReceivedData();

        if (receivedData == null)
            return "";

        receivedData.forEach(data -> {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Universe universe = (Universe) objectInputStream.readObject();
                System.out.println(universe);
                map.put(universe.getNumber(), universe);
            } catch (IOException e) {
                System.out.println("IO Ex");
                isError = true;
            } catch (ClassNotFoundException e) {
                System.out.println("Class Ex");
                isError = true;
            }
        });

        if (isError)
            return "Error while importing";

        UniverseCollection.setUniverseConcurrentSkipListMap(map);

        String ANS = CommandManager.ExecuteCommand("save");
        System.out.println(ANS);
        clientID.stopLoadImport();
        return ANS;
    }
}
