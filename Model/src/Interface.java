package src;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Interface {

    public static void main(String[] args) {
        SerialCommunication portManager = new SerialCommunication();
        portManager.testSerialPorts(); // Appel de la méthode pour tester les ports série

        Map<String, SerialPort> portMap = portManager.getPortMap(); // Récupération de la HashMap des ports série

        JPanel containers1 = new JPanel();
        JPanel containers2 = new JPanel();
        JPanel containers3 = new JPanel();
        JPanel containers4 = new JPanel();

        JComboBox<String> comboBox;

        // Utilisation de la Map obtenue pour construire le JComboBox
        comboBox = new JComboBox<>(portMap.keySet().toArray(new String[0]));

        JFrame frame = new JFrame("Arduino PMF");
        JLabel label1 = new JLabel("Select a port :", JLabel.LEFT);
        JLabel label = new JLabel("Hello World !", JLabel.CENTER);

        JTextField textfield1 = new JTextField(10);
        JTextField textfield2 = new JTextField(10);

        JButton sendButton = new JButton("SEND");
        JButton openButton = new JButton("Open Port");
        JButton closeButton = new JButton("Close Port");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKey = (String) comboBox.getSelectedItem();
                portManager.openPort(selectedKey);
            }
        });


        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKey = (String) comboBox.getSelectedItem();
                portManager.closePort(selectedKey);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKey = (String) comboBox.getSelectedItem();
                SerialPort selectedPort = portMap.get(selectedKey);
                String v1 = textfield1.getText();
                String v2 = textfield2.getText();
                if (v1.isEmpty() && v2.isEmpty()) {
                    System.out.println("Entrez des valeurs");
                } else if (v1.isEmpty()) {
                    System.out.println("Entrer la valeur maquante");
                } else if (v2.isEmpty()) {
                    System.out.println("Entrer la valeur manquante");
                } else {
                    System.out.println("Selected Port :" + " " + selectedPort.getSystemPortName());
                    System.out.println("Value Left :" + " " + v1);
                    System.out.println("Value Right :" + " " + v2);
                    JOptionPane.showMessageDialog(null, "Selected Port :" + " " + selectedPort.getSystemPortName()
                            + "\n Value Left :" + " " + v1 + "\n Value Right :" + " " + v2);
                    portManager.sendData(selectedKey, "V1:" + v1 + ";V2:" + v2);
                }
            }
        });

        containers1.add(textfield1);
        containers1.add(textfield2);
        containers2.add(sendButton);
        containers3.add(comboBox);
        containers4.add(openButton);
        containers4.add(closeButton);

        frame.setLayout(new GridLayout(6, 1));
        frame.add(label);
        frame.add(label1);
        frame.add(containers3);
        frame.add(containers1);
        frame.add(containers4);
        frame.add(containers2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setVisible(true);


    }
}

