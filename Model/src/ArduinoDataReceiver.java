package src;

public class ArduinoDataReceiver {
    public static void main(String[] args) {
        SerialCommunication serialCommunication = new SerialCommunication();

        // Test des ports série disponibles
        serialCommunication.testSerialPorts();

        // Sélectionnez le port que vous souhaitez utiliser (remplacez "COMX" par le nom du port)
        String selectedPort = "COM13";

        // Lisez et affichez les données du port série
        readAndDisplayArduinoData(serialCommunication, selectedPort);
    }

    private static void readAndDisplayArduinoData(SerialCommunication serialCommunication, String portName) {
        // Lisez et affichez les données du port série en continu
        while (true) {
            // Lisez une ligne de données du port série
            String data = serialCommunication.readData(portName);

            // Affichez les données dans la console IntelliJ si des données sont présentes
            if (!data.isEmpty()) {
                System.out.println("Données reçues : " + data);
            }

            // Attendez un court instant (ajustez si nécessaire)
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

