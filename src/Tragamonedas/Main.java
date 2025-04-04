package Tragamonedas;

import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación de la máquina tragamonedas.
 */
public class Main {
    public static void main(String[] args) {
        // Asegura que la interfaz gráfica se ejecute en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            SlotMachine model = new SlotMachine(); // Crea el modelo de la máquina tragamonedas
            SlotMachineView view = new SlotMachineView(); // Crea la vista de la interfaz gráfica
            new SlotMachineController(model, view); // Crea el controlador que conecta modelo y vista
            view.setVisible(true); // Muestra la ventana principal
        });
    }
}
