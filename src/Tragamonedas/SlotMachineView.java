package Tragamonedas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista de la máquina tragamonedas.
 * Proporciona la interfaz gráfica para interactuar con el usuario.
 */
public class SlotMachineView extends JFrame {
    private JLabel[] slotLabels = new JLabel[3]; // Etiquetas para mostrar las imágenes de los carretes
    private JButton spinButton = new JButton("Girar"); // Botón para iniciar el giro

    /**
     * Constructor de la vista.
     * Configura la ventana y los componentes de la interfaz gráfica.
     */
    public SlotMachineView() {
        setTitle("Máquina Tragamonedas"); // Título de la ventana
        setSize(600, 400); // Dimensiones de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al salir
        setLayout(new BorderLayout()); // Usa BorderLayout para organizar los elementos
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        
        // Deshabilitar el botón de maximizar
        setResizable(false);

        // Panel para los carretes
        JPanel slotsPanel = new JPanel();
        slotsPanel.setLayout(new GridLayout(1, 3)); // Distribuye los carretes en una fila

        // Inicializa los carretes con una imagen de pregunta
        for (int i = 0; i < 3; i++) {
            slotLabels[i] = new JLabel(new ImageIcon("resources/inicio.png"), SwingConstants.CENTER);
            slotsPanel.add(slotLabels[i]); // Agrega el carrete al panel
        }

        add(slotsPanel, BorderLayout.CENTER); // Agrega el panel de carretes al centro de la ventana
        add(spinButton, BorderLayout.SOUTH); // Agrega el botón de giro en la parte inferior
    }

    /**
     * Establece la imagen mostrada en un carrete específico.
     * @param index Índice del carrete (0, 1 o 2)
     * @param imagePath Ruta de la imagen a mostrar
     */
    public void setSlotSymbol(int index, String imagePath) {
        slotLabels[index].setIcon(new ImageIcon(imagePath));
    }

    /**
     * Asigna un listener al botón de giro.
     * @param listener Manejador de eventos para el botón de giro
     */
    public void setSpinButtonListener(ActionListener listener) {
        spinButton.addActionListener(listener);
    }

    /**
     * Muestra un mensaje emergente.
     * @param message Mensaje a mostrar en la ventana emergente
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
