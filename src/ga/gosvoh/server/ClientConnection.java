package ga.gosvoh.server;

import ga.gosvoh.CommandManager;
import ga.gosvoh.commands.ImportMap;
import ga.gosvoh.commands.LoadMap;
import ga.gosvoh.utils.CommandCodes;
import ga.gosvoh.utils.PacketUtil;
import ga.gosvoh.utils.ReceivedData;

import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("WeakerAccess")
public class ClientConnection extends Thread implements Runnable {
    private CopyOnWriteArrayList<ReceivedData> receivedData;
    private DatagramSocket socket;
    private static InetSocketAddress inetSocketAddress;

    public ClientConnection(CopyOnWriteArrayList<ReceivedData> receivedData) {
        this.receivedData = receivedData;
        this.socket = RunServer.getSocket();
    }

    @Override
    public void run() {
        inetSocketAddress = receivedData.get(0).getAddress();
        String command = CommandCodes.getCommandCodesMap().get(receivedData.get(0).getCommandCode());
        String answer;
        getCurrentClientID().setReceivedData(receivedData);

        System.out.println("Received data from " + inetSocketAddress + ": " + command + " " + new String(receivedData.get(0).getData()));

        if (!command.equals("import") && !command.equals("load"))
            answer = CommandManager.ExecuteCommand(command + " " + new String(receivedData.get(0).getData()).trim());
        else if (command.equals("import"))
            answer = new ImportMap(getCurrentClientID()).execute(new String[0]);
        else answer = new LoadMap().execute(new String[0]);

        //System.out.println(answer);
        PacketUtil.sendData(socket, inetSocketAddress, answer.getBytes());
    }

    public static InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public static ClientID getCurrentClientID() {
        return ClientID.getClientID(inetSocketAddress.getAddress(), inetSocketAddress.getPort());
    }

    public CopyOnWriteArrayList<ReceivedData> getReceivedData() {
        return receivedData;
    }
}
