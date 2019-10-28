package ga.gosvoh;

import ga.gosvoh.utils.*;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings({"InfiniteLoopStatement", "UnnecessaryContinue", "WeakerAccess"})
public class StartClient {
    private static DatagramChannel channel = null;
    private static InetSocketAddress inetSocketAddress;
    private static CopyOnWriteArrayList<ReceivedData> receivedData = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("Лабораторная работа по программированию. Версия " +
                StartClient.class.getPackage().getImplementationVersion());

        new UniverseCollection(Defines.DEFAULT_JSON_FILE_PATH);

        Scanner cmdScanner = new Scanner(System.in);
        int PORT;
        String ADDRESS;

        try {
            System.out.print("Введите порт сервера: ");
            PORT = Integer.parseInt(cmdScanner.nextLine());
            if (PORT < 0 || PORT > 65535)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            PORT = Defines.PORT;
            System.out.println("Неверно введён порт! Порт сервера: " + PORT);
        }
        try {
            System.out.print("Введите адрес сервера: ");
            ADDRESS = cmdScanner.nextLine();
            if (ADDRESS.equals(""))
                throw new UnknownHostException();
            inetSocketAddress = new InetSocketAddress(ADDRESS, PORT);
        } catch (UnknownHostException | IllegalArgumentException e) {
            ADDRESS = Defines.ADDRESS;
            System.out.println("Неверно введён адерс! Адрес сервера: " + ADDRESS);
            inetSocketAddress = new InetSocketAddress(ADDRESS, PORT);
        }

        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.print("Input command: ");
            String line = cmdScanner.nextLine();
            String[] splittedLine = line.split("[ \t]+");
            String command = splittedLine[0];
            String[] lineWithoutCommand = Arrays.copyOfRange(splittedLine, 1, splittedLine.length);
            StringBuilder sb = new StringBuilder();
            for (String s : lineWithoutCommand)
                sb.append(s).append(" ");

            try {
                if (CommandManager.isCommand(command))
                    CommandManager.ExecuteCommand(command);
                else
                    PacketUtils.sendData(sb.toString().getBytes(), CommandCodes.getCommandCode(command), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < Defines.RECEIVING_ATTEMPTS; i++) {
                try {
                    Thread.sleep(Defines.RECEIVING_TIMEOUT);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!receivePacket()) {
                    if (PacketUtils.isLoadImport())
                        i--;
                    continue;
                } else
                    break;
            }
        }
    }

    public static boolean receivePacket() {
        ReceivedData data = PacketUtils.receivePacket();
        if (data.haveData()) {
            if (data.getCommandCode() != CommandCodes.getCommandCode("load")) {
                System.out.println(new String(data.getData()).trim());
                return true;
            } else {
                if (!PacketUtils.isLoadImport())
                    receivedData.clear();
                PacketUtils.startLoadImport();
                if (receivedData.size() < data.getTotalCount()) {
                    receivedData.add(data);
                    if (receivedData.size() == data.getTotalCount()) {
                        CommandManager.ExecuteCommand("load");
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public static InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public static DatagramChannel getChannel() {
        return channel;
    }

    public static CopyOnWriteArrayList<ReceivedData> getReceivedData() {
        return receivedData;
    }
}
