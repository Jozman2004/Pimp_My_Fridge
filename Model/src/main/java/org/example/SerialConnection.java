package org.example;

import com.fazecast.jSerialComm.*;

import java.util.HashMap;
import java.util.Map;
public class SerialConnection {
    static Map<String, SerialPort> portMap = new HashMap<>();

    public Map<String, SerialPort> getPortMap() {
        return portMap;
    }

    SerialPort[] ports = SerialPort.getCommPorts();

}
