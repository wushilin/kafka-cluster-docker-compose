package main;

public class Constants {
    public static final String JVM_TTL_SETTING = "networkaddress.cache.ttl";
    public static final String BOOTSTRAP_SERVER_HOST = "kafka-bootstrap-1";
    public static final int BOOTSTRAP_SERVER_PORT = 9092;
    public static final String BOOTSTRAP_SERVER = String.format("%s:%d", BOOTSTRAP_SERVER_HOST, BOOTSTRAP_SERVER_PORT);
}
