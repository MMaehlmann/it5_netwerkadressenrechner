package persistence.bo;

import javax.swing.text.html.Option;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

/**
 * Created by preichert on 02/06/2017.
 */
public class IpV4Unmarshaller {

    public Optional<IpV4> unmarshal(String fileName) {
        try {
            String fullFilePath = String.format("it5_netwerkadressenrechner/resources/out/%s", fileName);
            File file = new File(fullFilePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(IpV4.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            IpV4 ipV4 = (IpV4) jaxbUnmarshaller.unmarshal(file);
            System.out.println(ipV4);

            return Optional.of(ipV4);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
