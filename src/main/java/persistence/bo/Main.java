package persistence.bo;

import java.util.ArrayList;

/**
 * Created by preichert on 30/05/2017.
 */
public class Main {
    public static void main(String args[]) {
        IpV4Marshaller ipV4Marshaller = new IpV4Marshaller();

        ArrayList<String> hosts = new ArrayList<>(2);
        hosts.add("192.168.1.100");
        hosts.add("192.168.1.120");

        ipV4Marshaller.marshall(
                "192.168.1.10",
                "Niklas",
                "192.168.1.1",
                "192.168.1.255",
                "255.255.255.0",
                hosts
        );
    }
}
