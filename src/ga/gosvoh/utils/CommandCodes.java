package ga.gosvoh.utils;

import java.util.concurrent.ConcurrentHashMap;

public class CommandCodes {
    private static ConcurrentHashMap<Byte, String> commandCodesMap = new ConcurrentHashMap<>();

    static {
        commandCodesMap.put((byte) 0x00, "none");
        commandCodesMap.put((byte) 0x01, "show");
        commandCodesMap.put((byte) 0x02, "add_if_max");
        commandCodesMap.put((byte) 0x03, "help");
        commandCodesMap.put(Defines.LOAD_COMMAND_BYTE_CODE, "load");
        commandCodesMap.put((byte) 0x05, "import");
        commandCodesMap.put((byte) 0x06, "insert");
        commandCodesMap.put((byte) 0x07, "info");
        commandCodesMap.put((byte) 0x08, "remove");
        commandCodesMap.put((byte) 0x09, "remove_greater_key");
        commandCodesMap.put((byte) 0x0A, "remove_lower");
        commandCodesMap.put((byte) 0x0B, "save");
        commandCodesMap.put((byte) 0x0C, "random");
        commandCodesMap.put((byte) 0x0D, "shutdown");
    }

    public static ConcurrentHashMap<Byte, String> getCommandCodesMap() {
        return commandCodesMap;
    }
}
