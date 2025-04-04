package Tragamonedas;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Modelo de la máquina tragamonedas.
 * Maneja la lógica del juego, incluyendo los giros y la detección de jackpot.
 */
public class SlotMachine {
    private final String[] symbols = {"A", "B", "C", "D", "E", "1", "2", "3", "7"}; // Símbolos posibles
    private final Map<String, String> symbolToImageMap; // Mapeo de símbolos a rutas de imágenes
    private String[] result = new String[3]; // Resultado actual de los carretes
    private Random random = new Random(); // Generador de números aleatorios

    /**
     * Constructor de la máquina tragamonedas.
     * Inicializa los carretes con una imagen de pregunta y define las rutas de las imágenes.
     */
    public SlotMachine() {
        symbolToImageMap = new HashMap<>();
        symbolToImageMap.put("A", "resources/1.jpg");
        symbolToImageMap.put("B", "resources/2.jpg");
        symbolToImageMap.put("C", "resources/3.jpg");
        symbolToImageMap.put("D", "resources/4.jpg");
        symbolToImageMap.put("E", "resources/5.jpg");
        symbolToImageMap.put("1", "resources/6.jpg");
        symbolToImageMap.put("2", "resources/7.jpg");
        symbolToImageMap.put("3", "resources/8.jpg");
        symbolToImageMap.put("7", "resources/9.jpg");

        for (int i = 0; i < 3; i++) {
            result[i] = "resources/inicio.png"; // Imagen inicial en los carretes
        }
    }

    /**
     * Realiza un giro en el carrete especificado.
     * @param index Índice del carrete a girar (0, 1 o 2)
     */
    public void spin(int index) {
        String symbol = symbols[random.nextInt(symbols.length)];
        result[index] = symbolToImageMap.get(symbol); // Asigna la ruta de la imagen en lugar del símbolo
    }

    /**
     * Devuelve el resultado actual de los carretes con las rutas de las imágenes.
     * @return Copia del arreglo de resultados con rutas de imágenes.
     */
    public String[] getResult() {
        return result.clone();
    }

    /**
     * Verifica si los tres carretes muestran el mismo símbolo (Jackpot).
     * @return true si hay un Jackpot, false en caso contrario
     */
    public boolean isJackpot() {
        return result[0].equals(result[1]) && result[1].equals(result[2]);
    }
}
