import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RefrigeratorInterface extends JFrame {
    private final JLabel targetTempLabel;
    private final JLabel interiorTempLabel;
    private final JLabel coolingModuleTempLabel;
    private final JLabel exteriorTempLabel;
    private final JLabel alertLabel;

    private double targetTemperature;
    private double interiorTemperature;
    private double coolingModuleTemperature;
    private double exteriorTemperature;

    private XYSeries interiorTempSeries;
    private XYSeries coolingModuleTempSeries;
    private XYSeries exteriorTempSeries;

    public RefrigeratorInterface() {
        setTitle("Mini Refrigerator");
        setSize(800, 600);

        targetTemperature = 5.0; // Default target temperature
        interiorTemperature = 10.0; // Initial interior temperature
        coolingModuleTemperature = 0.0; // Initial cooling module temperature
        exteriorTemperature = 25.0; // Initial exterior temperature

        targetTempLabel = new JLabel("Target Temperature: " + targetTemperature + "°C");
        interiorTempLabel = new JLabel("Interior Temperature: " + interiorTemperature + "°C");
        coolingModuleTempLabel = new JLabel("Cooling Module Temperature: " + coolingModuleTemperature + "°C");
        exteriorTempLabel = new JLabel("Exterior Temperature: " + exteriorTemperature + "°C");
        alertLabel = new JLabel();

        JButton setTempButton = getSetTempButton();

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel temperaturePanel = new JPanel();
        temperaturePanel.setLayout(new GridLayout(4, 1));
        temperaturePanel.add(targetTempLabel);
        temperaturePanel.add(interiorTempLabel);
        temperaturePanel.add(coolingModuleTempLabel);
        temperaturePanel.add(exteriorTempLabel);

        container.add(temperaturePanel, BorderLayout.NORTH);
        container.add(setTempButton, BorderLayout.SOUTH);
        container.add(alertLabel, BorderLayout.CENTER);

        interiorTempSeries = new XYSeries("Interior Temperature");
        coolingModuleTempSeries = new XYSeries("Cooling Module Temperature");
        exteriorTempSeries = new XYSeries("Exterior Temperature");

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(interiorTempSeries);
        dataset.addSeries(coolingModuleTempSeries);
        dataset.addSeries(exteriorTempSeries);

        JFreeChart chart = ChartFactory.createXYLineChart("Temperature", "Time", "Temperature (°C)", dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        container.add(chartPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton getSetTempButton() {
        JButton setTempButton = new JButton("Set Target Temperature");
        setTempButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Enter the target temperature (in °C):");
                try {
                    double newTargetTemp = Double.parseDouble(input);
                    setTargetTemperature(newTargetTemp);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid temperature input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return setTempButton;
    }

    public void setTargetTemperature(double temperature) {
        targetTemperature = temperature;
        targetTempLabel.setText("Target Temperature: " + targetTemperature + "°C");
        checkTemperatureConditions();
    }

    public void setInteriorTemperature(double temperature) {
        interiorTemperature = temperature;
        interiorTempLabel.setText("Interior Temperature: " + interiorTemperature + "°C");
        checkTemperatureConditions();
        interiorTempSeries.addOrUpdate(System.currentTimeMillis(), interiorTemperature);
    }

    public void setCoolingModuleTemperature(double temperature) {
        coolingModuleTemperature = temperature;
        coolingModuleTempLabel.setText("Cooling Module Temperature: " + coolingModuleTemperature + "°C");
        checkTemperatureConditions();
        coolingModuleTempSeries.addOrUpdate(System.currentTimeMillis(), coolingModuleTemperature);
    }

    public void setExteriorTemperature(double temperature) {
        exteriorTemperature = temperature;
        exteriorTempLabel.setText("Exterior Temperature: " + exteriorTemperature + "°C");
        checkTemperatureConditions();
        exteriorTempSeries.addOrUpdate(System.currentTimeMillis(), exteriorTemperature);
    }

    public void displayAlert(String message) {
        alertLabel.setText(message);
    }

    public void checkTemperatureConditions() {
        if (interiorTemperature <= exteriorTemperature) {
            displayAlert("Condensation Alert: Please dry the interior.");
        } else {
            displayAlert("");
        }

        if (interiorTemperature > targetTemperature) {
            displayAlert("Temperature Alert: Interior temperature is tooélevée.");
        } else {
            displayAlert("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RefrigeratorInterface refrigeratorInterface = new RefrigeratorInterface();
                refrigeratorInterface.setVisible(true);
            }
        });
    }
}