package persistence.bo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Created by preichert on 30/05/2017.
 */
@XmlRootElement(name = "ipv4")
public class IpV4 {

    private String name;
    private String ip;
    private ArrayList<String> hosts = new ArrayList<>();
    private String netId;
    private String subnetmask;
    private String broadcast;
    private ArrayList<IpV4> subSubNet = new ArrayList<>();

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    @XmlElement
    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<String> getHosts() {
        return hosts;
    }

    @XmlElement
    public void setHosts(ArrayList<String> hosts) {
        this.hosts = hosts;
    }

    public String getNetId() {
        return netId;
    }

    @XmlElement
    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getSubnetmask() {
        return subnetmask;
    }

    @XmlElement
    public void setSubnetmask(String subnetmask) {
        this.subnetmask = subnetmask;
    }

    @XmlElement
    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public ArrayList<IpV4> getSubSubNet() {
        return subSubNet;
    }

    @XmlElement(name = "ipv4")
    public void setSubSubNet(ArrayList<IpV4> subSubNet) {
        this.subSubNet = subSubNet;
    }
}
