package ga.gosvoh.commands;

import ga.gosvoh.StartClient;
import ga.gosvoh.Universe;
import ga.gosvoh.UniverseCollection;
import ga.gosvoh.utils.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListMap;

import static ga.gosvoh.utils.Defines.*;

public class ImportMap implements Command {
    public ImportMap() {
    }

    @Override
    public void execute() {
        ConcurrentSkipListMap<Long, Universe> map = UniverseCollection.getSortedCollection();
        PacketUtils.startLoadImport();

        try {
            Thread.sleep(RECEIVING_TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Universe universe : map.values()) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(universe);

                PacketUtils.sendData(byteArrayOutputStream.toByteArray(), CommandCodes.getCommandCode("import"), map.size());

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ошибка во время загрузки словаря!");
            }
        }
        System.out.println("Словарь успешно отправлен!");

        PacketUtils.stopLoadImport();
    }
}
