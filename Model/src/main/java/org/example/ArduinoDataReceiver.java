package org.example;
public class ArduinoDataReceiver {
    public static void main(String[] args) {
        SerialCommunication serialCommunication = new SerialCommunication();

        // Test des ports série disponibles
        serialCommunication.testSerialPorts();

        // Remplacez "COMX" par le nom du port où est connecté Arduino
        String selectedPort = "COM14"; // Remplacez par le port réel utilisé par votre Arduino

        // Lisez et affichez les données du port série
        readAndDisplayTemperatureHumidity(serialCommunication, selectedPort);
    }

    private static void readAndDisplayTemperatureHumidity(SerialCommunication serialCommunication, String portName) {
        // Lisez et affichez les données du port série en continu
        while (true) {
            // Lisez une ligne de données du port série
            String data = serialCommunication.readData(portName);

            // Affichez les températures et humidités si des données sont présentes
            if (!data.isEmpty()) {
                // Divisez les données reçues en valeurs individuelles
                String[] values = data.split(";");

                // Vérifiez si les données contiennent les quatre valeurs attendues
                if (values.length == 4) {
                    // Récupérez les valeurs de température interne, externe, humidité interne et externe
                    String internalTemp = values[0];
                    String externalTemp = values[1];
                    String internalHumidity = values[2];
                    String externalHumidity = values[3];

                    // Affichez les valeurs dans la console
                    System.out.println("Température interne : " + internalTemp + "°C");
                    System.out.println("Température externe : " + externalTemp + "°C");
                    System.out.println("Humidité interne : " + internalHumidity + "%");
                    System.out.println("Humidité externe : " + externalHumidity + "%");
                } else {
                    System.out.println("Données invalides reçues depuis Arduino.");
                }
            }

            // Attendez un court instant (ajustez si nécessaire)
            try {
                Thread.sleep(500); // Attendre 0,5 seconde entre chaque lecture
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
