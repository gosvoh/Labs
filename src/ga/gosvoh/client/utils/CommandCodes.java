package ga.gosvoh.client.utils;

import java.util.concurrent.ConcurrentHashMap;

public class CommandCodes {
    private static ConcurrentHashMap<String, Byte> commandCodesMap = new ConcurrentHashMap<>();

    static {
        commandCodesMap.put("show", (byte) 0x01);
        commandCodesMap.put("add_if_max", (byte) 0x02);
        commandCodesMap.put("?", (byte) 0x03);
        commandCodesMap.put("help", (byte) 0x03);
        commandCodesMap.put("load", (byte) 0x04);
        commandCodesMap.put("import", (byte) 0x05);
        commandCodesMap.put("insert", (byte) 0x06);
        commandCodesMap.put("info", (byte) 0x07);
        commandCodesMap.put("remove", (byte) 0x08);
        commandCodesMap.put("remove_greater_key", (byte) 0x09);
        commandCodesMap.put("remove_lower", (byte) 0x0A);
        commandCodesMap.put("save", (byte) 0x0B);
        commandCodesMap.put("random", (byte) 0x0C);
        commandCodesMap.put("shutdown", (byte) 0x0D);
    }

    public static byte getCommandCode(String command) {
        if (commandCodesMap.containsKey(command))
            return commandCodesMap.get(command);
        else return 0x00;
    }
}
