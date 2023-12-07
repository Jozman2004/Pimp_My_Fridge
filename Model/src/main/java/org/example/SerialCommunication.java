package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SerialCommunication {

    private Map<String, SerialPort> portMap;

    public SerialCommunication() {
        this.portMap = new HashMap<>();
    }

    public Map<String, SerialPort> getPortMap() {
        return portMap;
    }

    public void testSerialPorts() {
        // Obtenez tous les ports série disponibles
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length == 0) {
            System.out.println("Aucun port série disponible.");
            return;
        }

        // Parcourez chaque port série et testez la connexion
        for (SerialPort port : ports) {
            // Enregistrez le nom du port dans une variable locale
            String portName = port.getSystemPortName();

            System.out.println("Tentative de connexion au port : " + portName);

            // Configurez les paramètres de port (vous pouvez ajuster ces paramètres selon vos besoins)
            port.setBaudRate(9600);
            port.setNumDataBits(8);
            port.setNumStopBits(1);
            port.setParity(SerialPort.NO_PARITY);

            // Ouvrez le port série
            if (port.openPort()) {
                System.out.println("Port ouvert avec succès.");
                System.out.println("Port : " + portName);

                // Attendez une réponse (ajustez le délai en fonction de la réponse attendue)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Lisez la réponse
                String response = port.toString();
                System.out.println("Réponse du port : " + response);

                // Ajoutez le port à la HashMap avec son nom comme clé
                portMap.put(portName, port);

                // NE fermez PAS le port série ici, pour le maintenir ouvert

                System.out.println("------------------------");
            } else {
                System.out.println("Échec de l'ouverture du port.");
            }
        }
    }

    public String readData(String portName) {
        SerialPort port = portMap.get(portName);
        if (portName != null && port.isOpen()) {
            try {
                port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 3000, 0); // Définir le délai à 1 secondes

                BufferedReader reader = new BufferedReader(new InputStreamReader(port.getInputStream()));
                StringBuilder data = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Donnée reçue : " + line);
                    data.append(line).append("\n");
                }
                return data.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            System.out.println("Impossible de lire les données. Le port n'est pas ouvert.");
            return "";
        }
    }




    public void sendData(String portName, String data) {
        SerialPort port = portMap.get(portName);
        if (port != null && port.isOpen()) {
            port.toString();
            System.out.println("Données envoyées au port " + portName + ": " + data);
        } else {
            System.out.println("Impossible d'envoyer des données. Le port n'est pas ouvert.");
        }
    }

    public void closePort(String portName) {
        SerialPort port = portMap.get(portName);
        if (port != null && port.isOpen()) {
            port.closePort();
            System.out.println("Port " + portName + " fermé avec succès.");
        }
    }

    public void openPort(String portName) {
        SerialPort port = portMap.get(portName);
        if (port != null && !port.isOpen()) {
            port.openPort();
            System.out.println("Port " + portName + " ouvert avec succès.");
        }
    }

    public static void main(String[] args) {
        SerialCommunication serialCommunication = new SerialCommunication();
        serialCommunication.testSerialPorts();
    }
}
