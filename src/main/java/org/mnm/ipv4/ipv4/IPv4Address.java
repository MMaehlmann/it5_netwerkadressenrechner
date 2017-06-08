package org.mnm.ipv4.ipv4;

import org.mnm.ipv4.subnet.IPv4SubnetUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * &lt;pre&gt;
 * abstract class from which all other IPv4 addresses inherit their methods and variables
 *
 * Created by martin on 08/04/17.
 * &lt;/pre&gt;
 */
public abstract class IPv4Address{

    private int[] ipv4Address;

    public int[] getIpv4Address() {
        return ipv4Address;
    }

    public void setIpv4Address(int[] ipv4Address) {
        if (IPv4SubnetUtils.isValidIP(ipv4Address)) {
            this.ipv4Address = ipv4Address;
        }
    }

    /**
     * &lt;pre&gt;
     * &#64;Override
     *          toString
     *
     * &#64;return a dotted decimal notation ot the IPv4Address
     * &lt;/pre&gt;
     */
    @Override
    public String toString() {
        return Arrays.stream(ipv4Address)
                .mapToObj(i -> ((Integer) i).toString())
                .collect(Collectors.joining("."));
    }

    /**
     * <pre>
     * converts this ipv4Address to an ipv6 String
     *
     * @return an IPv6 String representing this IP
     * </pre>
     */
    public String toIPv6String(){
        String s = "";
        for(int i = 0; i < 4; i++) {
            s += Integer.toHexString(ipv4Address[i]);
            if(i % 2 != 0)
                s += ":";
        }

        return "2002:0000:0000:0000:0000:0000:"+ s.substring(0,s.length()-1);
    }

    /**
     * &lt;pre&gt;
     * &#64;Override
     *          toString
     *
     * &#64;return a dotted decimal notation ot the IPv4Address
     * &lt;/pre&gt;
     */
    public String toBinaryString() {
        return Arrays.stream(ipv4Address)
                .mapToObj(i -> ((Integer) i).toBinaryString(i))
                .collect(Collectors.joining("."));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPv4Address that = (IPv4Address) o;

        return Arrays.equals(ipv4Address, that.ipv4Address);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(ipv4Address);
    }
}
