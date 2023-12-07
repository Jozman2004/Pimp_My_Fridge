package org.example;

public class ArduinoCommunicationTest {
    public static void main(String[] args) {
        SerialCommunication serialCommunication = new SerialCommunication();

        // Test des ports série disponibles
        serialCommunication.testSerialPorts();

        // Remplacez "COMX" par le nom du port où est connecté Arduino
        String selectedPort = "COM14"; // Exemple, remplacez par le port réel utilisé par votre Arduino

        // Lisez et affichez les données du port série
        readAndDisplayArduinoData(serialCommunication, selectedPort);
    }

    private static void readAndDisplayArduinoData(SerialCommunication serialCommunication, String portName) {
        // Lisez et affichez les données du port série en continu
        while (true) {
            // Lisez une ligne de données du port série
            String data = serialCommunication.readData(portName);

            // Affichez les données si elles sont présentes
            if (!data.isEmpty()) {
                System.out.println("Données reçues depuis Arduino : " + data);
            }

            // Attendez un court instant (ajustez si nécessaire)
            try {
                Thread.sleep(1000); // Attendre 1 seconde entre chaque lecture
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
