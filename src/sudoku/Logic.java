package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudo extends JFrame implements ActionListener {
    public JTextField[][] celdas;
    public int[][] matriz;
    private static final int FILAS = 9;
    private static final int COLUMNAS = 9;
    JButton validarBoton;
    // Constructor
    public Sudo() {
        super("Sudoku");
        matriz = new int[FILAS][COLUMNAS];
        celdas = new JTextField[FILAS][COLUMNAS];
        initUI();
    }

    private void initUI()
    {
        int filas;
        int columnas;
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panel
        JPanel matrizPanel = new JPanel(new GridLayout(FILAS, COLUMNAS));

        // Celdas para los números y agregarlos al panel
        for (filas = 0; filas < FILAS; filas++) {
            for (columnas = 0; columnas < COLUMNAS; columnas++) {
                celdas[filas][columnas] = new JTextField();
                celdas[filas][columnas].setHorizontalAlignment(JTextField.CENTER);
                matrizPanel.add(celdas[filas][columnas]);
            }
        }

        validarBoton = new JButton("Validar");
        validarBoton.addActionListener(this);

        add(matrizPanel, BorderLayout.CENTER);
        add(validarBoton, BorderLayout.SOUTH);
    }
    // Método para validar el tablero
    private boolean validarMatriz()
    {
        int filas;
        int columnas;
        // Actualizar la matriz del tablero con los valores de los campos de texto
        for (filas = 0; filas < FILAS; filas++) {
            for (columnas = 0; columnas < COLUMNAS; columnas++) {
                String texto = celdas[filas][columnas].getText();
                if (!texto.isEmpty()) {
                    try {
                        matriz[filas][columnas] = Integer.parseInt(texto);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                } else {
                    // Celda vacía
                    matriz[filas][columnas] = 0;
                }
            }
        }

        /*
         * Verificar las reglas del sudoku
         * Método 1
         */
        return validarSudoku();
    }

    private boolean validarSudoku()
    {
        int i;
        int fila;
        int columna;
        // Verificar flas y columnas
        for (i = 0; i < FILAS; i++) {
            if (!filaValida(i) || !columnaValida(i)) {
                return false;
            }
        }

        // Verificar cada sub-cuadrante 3 x 3
        for (fila = 0; fila < FILAS; fila += 3) {
            for (columna = 0; columna < COLUMNAS; columna += 3) {
                if (!subCuadranteValido(fila, columna)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Verificar si una fila es válida
    private boolean filaValida(int fila){
        boolean[] repetido = new boolean[10];
        for (int columna = 0; columna < COLUMNAS; columna++) {
            int num = matriz[fila][columna];
            if (num != 0) {
                if (repetido[num]) {
                    return false; // Número repetido
                }
                repetido[num] = true;
            }
        }
        return true;
    }

    // Verificar si una columna es válida
    private boolean columnaValida(int col){
        boolean[] repetido = new boolean[10];
        for (int fila = 0; fila < FILAS; fila++) {
            int num = matriz[fila][col];
            if (num != 0) {
                if (repetido[num]) {
                    return false; // Número repetido
                }
                repetido[num] = true;
            }
        }
        return true;
    }

    // Verificar si un sub-cuadrante es valido
    private boolean subCuadranteValido(int inicioFila, int inicioCol){
        boolean[] repetido = new boolean[10];
        for (int fila = inicioFila; fila < inicioFila + 3; fila++) {
            for (int col = inicioCol; col < inicioCol + 3; col++) {
                int num = matriz[fila][col];
                if (num != 0) {
                    if (repetido[num]) {
                        return false;
                    }
                    repetido[num] = true;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == validarBoton) {
            if (validarMatriz()) {
                JOptionPane.showMessageDialog(null, "Sudoku valido!");
            } else {
                JOptionPane.showMessageDialog(null, "Sudoku invalido!");
            }
        }
    }
}
