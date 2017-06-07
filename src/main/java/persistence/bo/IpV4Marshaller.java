package persistence.bo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IpV4Marshaller {

    public String marshall(IpV4 ipV4) {

        // TODO Change file path
        String fullFilePath = String.format(
                "resources/out/%s_%s.xml",
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