package persistence.bo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IpV4Marshaller {

    public String marshall(String ip, String name, String netId, String broadcast, String subnetmask, ArrayList<String> hosts) {
        IpV4 ipV4 = new IpV4();
        ipV4.setIp(ip);
        ipV4.setName(name);
        ipV4.setNetId(netId);
        ipV4.setBroadcast(broadcast);
        ipV4.setSubnetmask(subnetmask);
        ipV4.setHosts(hosts);

        // TODO Change file path
        String fullFilePath = String.format(
                "/Users/preichert/src/it5_netwerkadressenrechner/resources/out/%s_%s.xml",
                ipV4.getName(),
                ipV4.getIp()
        );
        try {
            File file = new File(fullFilePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(IpV4.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(ipV4, file);
            jaxbMarshaller.marshal(ipV4, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return fullFilePath;
    }
}