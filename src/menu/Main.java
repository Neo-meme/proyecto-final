package src.menu;

import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){
        JFrame window =new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("prueba de movimiento ");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.requestFocusInWindow(); // Asegura que el panel reciba el foco para detectar el teclado
        gamePanel.startGameThread(); // arranca el loop DESPUÉS de que la ventana sea visible
    }
}