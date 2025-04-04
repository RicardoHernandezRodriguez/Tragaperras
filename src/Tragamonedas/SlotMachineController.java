package Tragamonedas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Controlador de la máquina tragamonedas.
 * Maneja la interacción entre el modelo (lógica de la máquina) y la vista (interfaz gráfica).
 */
public class SlotMachineController {
    private SlotMachine model; // Modelo de la máquina tragamonedas
    private SlotMachineView view; // Vista de la máquina tragamonedas
    private Clip jackpotClip; // Clip de audio para el Jackpot
    private Clip backgroundMusicClip; // Clip de audio para la música de fondo

    /**
     * Constructor de la clase controlador.
     * @param model Instancia del modelo SlotMachine
     * @param view Instancia de la vista SlotMachineView
     */
    public SlotMachineController(SlotMachine model, SlotMachineView view) {
        this.model = model;
        this.view = view;

        // Cargar la música de fondo
        try {
            File bgMusicFile = new File("resources/LuigiCasino.wav"); // Ajusta la ruta del archivo de música
            AudioInputStream bgMusicStream = AudioSystem.getAudioInputStream(bgMusicFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(bgMusicStream);
            playBackgroundMusic(); // Iniciar reproducción de la música de fondo
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        // Cargar el sonido del Jackpot
        try {
            File audioFile = new File("resources/jackpot.wav"); // Ajusta la ruta del archivo de sonido
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            jackpotClip = AudioSystem.getClip();
            jackpotClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        // Configura el botón de giro para iniciar la animación de los carretes
        view.setSpinButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSpin();
            }
        });
    }

    /**
     * Inicia el giro de los carretes de la máquina tragamonedas en hilos separados.
     */
    private void startSpin() {
        Thread reel1 = new Thread(() -> spinReel(0)); // Hilo para el primer carrete
        Thread reel2 = new Thread(() -> spinReel(1)); // Hilo para el segundo carrete
        Thread reel3 = new Thread(() -> spinReel(2)); // Hilo para el tercer carrete

        reel1.start();
        reel2.start();
        reel3.start();

        // Hilo que espera a que terminen los giros y verifica si hay un premio mayor
        new Thread(() -> {
            try {
                reel1.join(); // Espera a que termine el primer carrete
                reel2.join(); // Espera a que termine el segundo carrete
                reel3.join(); // Espera a que termine el tercer carrete

                SwingUtilities.invokeLater(() -> {
                    if (model.isJackpot()) { // Verifica si se obtuvo un jackpot
                        stopBackgroundMusic(); // Detener la música de fondo
                        playJackpotSound(); // Reproduce sonido de Jackpot
                        view.showMessage("¡JACKPOT! 🎉"); // Muestra el mensaje del Jackpot
                        stopJackpotSound(); // Detiene el sonido cuando se cierra el mensaje
                        playBackgroundMusic(); // **Reinicia la música de fondo**
                    }
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Simula el giro de un carrete en la posición especificada.
     * @param index Índice del carrete a girar (0, 1 o 2)
     */
    private void spinReel(int index) {
        try {
            for (int i = 0; i < 10; i++) { // Simula 10 giros antes de detenerse
                model.spin(index); // Actualiza el estado del carrete en el modelo
                String symbol = model.getResult()[index]; // Obtiene el símbolo actual

                // Actualiza la interfaz gráfica en el hilo de eventos de Swing
                SwingUtilities.invokeLater(() -> view.setSlotSymbol(index, symbol));

                Thread.sleep(100 + (index * 50)); // Simula un retraso entre giros
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce el sonido del Jackpot.
     */
    private void playJackpotSound() {
        if (jackpotClip != null) {
            jackpotClip.setFramePosition(0); // Reinicia el sonido
            jackpotClip.start();
        }
    }

    /**
     * Detiene el sonido del Jackpot.
     */
    private void stopJackpotSound() {
        if (jackpotClip != null) {
            jackpotClip.stop();
        }
    }

    /**
     * Reproduce la música de fondo en bucle.
     */
    private void playBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.setFramePosition(0); // Reinicia desde el inicio
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir en bucle
            backgroundMusicClip.start();
        }
    }

    /**
     * Detiene la música de fondo.
     */
    private void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.stop();
        }
    }
}
