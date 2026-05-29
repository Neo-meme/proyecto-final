
import javax.swing.*;
import src.Games.GamePanel;
import java.awt.*;


public class VistaJuego extends JPanel {

        private GamePanel gamePanel; 

        public VistaJuego(ControladorJuego Controlador){

                this.setLayout(new BorderLayout());
                //Configuramos el color del panel del juego 
                this.setBackground(Color.decode("#201259"));
                //this.setFocusable(true) funciona para que el panel detecte el teclado
                this.setFocusable(true); 
                this.setPreferredSize(new Dimension(1200, 800));

                gamePanel = new GamePanel();
                this.add(gamePanel, BorderLayout.CENTER);
        } 
        
        // Método para arrancar el juego desde ControladorJuego
        public void iniciarJuego() {
                gamePanel.requestFocusInWindow();
                gamePanel.startGameThread();
        }

        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
        }
}
