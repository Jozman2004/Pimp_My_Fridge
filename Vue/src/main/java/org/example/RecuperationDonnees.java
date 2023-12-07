package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RecuperationDonnees extends JFrame {
    private JFrame frame;
    private JLabel labelTemperatureInterieure;
    private JLabel labelHumiditeInterieure;
    private JLabel labelTemperatureExterieure;
    private JLabel labelHumiditeExterieure;
    private JTextField fieldTemperatureConsigne;
    private JButton buttonEnvoyerConsigne;

    public RecuperationDonnees() {
        // Initialisation de la fenêtre principale
        frame = new JFrame("Mini-frigo USB - Groupe Projet N°10");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panneau pour afficher les températures
        JPanel panelTemperatures = new JPanel(new GridLayout(2, 2));
        labelTemperatureInterieure = new JLabel("Température Intérieure : ");
        labelTemperatureExterieure = new JLabel("Température Extérieure : ");
        panelTemperatures.add(labelTemperatureInterieure);
        panelTemperatures.add(new JLabel()); // espace vide
        panelTemperatures.add(labelTemperatureExterieure);
        panelTemperatures.add(new JLabel()); // espace vide

        //Panneau pour afficher les humidites
        JPanel panelHumidites = new JPanel(new GridLayout(2,2));
        labelHumiditeInterieure = new JLabel("Humidité Intérieure : ");
        labelHumiditeExterieure = new JLabel("Humidité Extérieure : ");
        panelHumidites.add(labelHumiditeInterieure);
        panelHumidites.add(new JLabel()); // espace vide
        panelHumidites.add(labelHumiditeExterieure);
        panelHumidites.add(new JLabel()); // espace vide

        // Panneau pour saisir la température de consigne
        JPanel panelConsigne = new JPanel(new FlowLayout());
        fieldTemperatureConsigne = new JTextField(10);
        buttonEnvoyerConsigne = new JButton("Valider");
        panelConsigne.add(new JLabel("Température de Consigne : "));
        panelConsigne.add(fieldTemperatureConsigne);
        panelConsigne.add(buttonEnvoyerConsigne);

        // Ajout des panneaux à la fenêtre
        frame.add(panelTemperatures, BorderLayout.NORTH); // Ajoute le panneau des températures en haut
        frame.add(panelHumidites, BorderLayout.CENTER); // Ajoute le panneau des humidités au centre
        frame.add(panelConsigne, BorderLayout.SOUTH); // Ajoute le panneau de consigne en bas

    }

    public void afficher() {
        frame.setVisible(true);
    }

    // Méthodes pour mettre à jour l'affichage des températures et des humidités
    public void mettreAJourTemperatureInterieure(double temperature) {
        labelTemperatureInterieure.setText("Température Intérieure : " + temperature + " °C");
    }

    public void mettreAJourTemperatureExterieure(double temperature) {
        labelTemperatureExterieure.setText("Température Extérieure : " + temperature + " °C");
    }
    public void mettreAJourHumiditeInterieure(double humidite) {
        labelHumiditeInterieure.setText("Humidité Intérieure : " + humidite + " %");
    }

    public void mettreAJourHumiditeExterieure(double humidite) {
        labelHumiditeExterieure.setText("Humidité Extérieure : " + humidite + " %");
    }


    // Méthodes pour récupérer la température saisie par l'utilisateur
    public String getTemperatureConsigne() {
        return fieldTemperatureConsigne.getText();
    }

    // Méthode pour réagir aux événements du bouton
    public void ajouterActionEnvoyerConsigne(ActionListener actionListener) {
        buttonEnvoyerConsigne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temperatureConsigne = getTemperatureConsigne();
                // Ici, tu peux envoyer la température de consigne au contrôleur ou à une autre partie du code.

                // Afficher le message dans la console
                System.out.println("Température de consigne envoyée : " + temperatureConsigne + "°C");

                // Afficher une boîte de dialogue de confirmation
                JOptionPane.showMessageDialog(frame,
                        "La température de consigne " + temperatureConsigne + "°C a bien été envoyée.",
                        "Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        RecuperationDonnees interfaceGraphique = new RecuperationDonnees();
        interfaceGraphique.afficher();

        // Simulation de mises à jour des températures
        // Ces valeurs pourraient être obtenues à partir d'un modèle réel
        interfaceGraphique.mettreAJourTemperatureInterieure(1);
        interfaceGraphique.mettreAJourTemperatureExterieure(2);
        interfaceGraphique.mettreAJourHumiditeInterieure(3);
        interfaceGraphique.mettreAJourHumiditeExterieure(4);

        // Gestion de la température de consigne
        interfaceGraphique.ajouterActionEnvoyerConsigne(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temperatureConsigne = interfaceGraphique.getTemperatureConsigne();
                // Ici tu peux manipuler la température de consigne,
                // par exemple, l'envoyer à ton contrôleur ou modèle pour traitement.
                System.out.println("Température de consigne saisie : " + temperatureConsigne + "°C");
            }
        });
    }
}