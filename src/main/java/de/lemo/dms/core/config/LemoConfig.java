package de.lemo.dms.core.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "config")
class LemoConfig {

    @XmlElement(name = "dms")
    public DataManagementServer dataManagementServer;

}
