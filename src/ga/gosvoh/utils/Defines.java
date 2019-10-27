package ga.gosvoh.utils;

@SuppressWarnings("WeakerAccess")
public class Defines {
    public static final int PACKET_LENGTH = 2048;
    public static final int PORT = 27965;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final String OK_ANSWER = "OK_ANSWER";
    public static final String FALSE_ANSWER = "FALSE_ANSWER";
    public static final int METADATA_LENGTH = 6;
    //public static final int COLLECTION_METADATA_LENGTH = 10;
    public static final String ADDRESS = "se.ifmo.ru";
    public static final int RECEIVING_TIMEOUT = 50;
    public static final int SHORT_RECEIVING_TIMEOUT = 50;
    public static final int RECEIVING_ATTEMPTS = 5;
    public static final String DEFAULT_JSON_FILE_PATH = System.getProperty("user.home") + "/main-local.json";
}
