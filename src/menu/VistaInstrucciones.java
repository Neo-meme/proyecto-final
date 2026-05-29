import javax.swing.*;
import java.awt.*;


public class VistaInstrucciones extends JPanel {

        public VistaInstrucciones(ControladorMenu Controlador){

            this.setBackground(Color.decode("#d20000"));
            this.setLayout(new BorderLayout()); // Usamos un layout para organizar las cosas

            JLabel Instrucciones = new JLabel("Instrucciones del juego", JLabel.CENTER);
            Instrucciones.setFont(new Font("Arial", Font.BOLD, 30)); 
            Instrucciones.setForeground(Color.WHITE);
        
        // 2. Creamos el botón
            JButton botonVolver = new JButton("Volver");
            botonVolver.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        // 3. Conectamos el botón al controlador que recibimos
            botonVolver.addActionListener(Controlador);
        
        // Añadimos las cosas al panel
            this.add(Instrucciones, BorderLayout.CENTER);
            this.add(botonVolver, BorderLayout.SOUTH); // Ponemos el botón en la parte de abajo
        
            }

    
}
