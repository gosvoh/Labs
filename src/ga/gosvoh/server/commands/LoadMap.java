package ga.gosvoh.server.commands;

import ga.gosvoh.server.server.ClientConnection;
import ga.gosvoh.server.Universe;
import ga.gosvoh.server.UniverseCollection;
import ga.gosvoh.server.server.ClientID;
import ga.gosvoh.server.server.RunServer;
import ga.gosvoh.server.utils.Defines;
import ga.gosvoh.server.utils.PacketUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Загрузить файл на локальный компьютер
 */
public class LoadMap implements Command {
    private ConcurrentSkipListMap<Long, Universe> map;

    public LoadMap() {
        map = UniverseCollection.getSortedCollection();
    }

    @Override
    public String execute(String[] arguments) {
        ClientID clientID = ClientConnection.getCurrentClientID();
        clientID.startLoadImport();

        try {
            Thread.sleep(Defines.RECEIVING_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Universe universe : map.values()) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(universe);

                PacketUtil.sendData(RunServer.getSocket(), clientID.getInetSocketAddress(), byteArrayOutputStream.toByteArray(), Defines.LOAD_COMMAND_BYTE_CODE, map.size());

            } catch (IOException e) {
                e.printStackTrace();
                clientID.stopLoadImport();
                return ("Ошибка во время загрузки словаря!");
            }
        }
        clientID.stopLoadImport();
        return ("Словарь успешно отправлен!");
    }
}
