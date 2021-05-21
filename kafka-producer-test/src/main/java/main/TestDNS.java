package main;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class TestDNS {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        java.security.Security.setProperty(Constants.JVM_TTL_SETTING , "3");
        String host = Constants.BOOTSTRAP_SERVER_HOST;
        while(true) {
            try {
                InetAddress[] address = InetAddress.getAllByName(host);
                System.out.println("Resolved " + host + "=>" + Arrays.asList(InetAddress.getAllByName(host)));
            } catch(Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            Thread.sleep(3000);
        }
    }
}
