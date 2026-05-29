import javax.swing.*;
import java.awt.*;


public class VistaClasificaciones extends JPanel {

        public VistaClasificaciones(ControladorMenu Controlador){
             
              //Configuramos el color del panel del juego 
                this.setBackground(Color.decode("#8f00fd"));
                //this.setFocusable(true) funciona para que el panel detecte el teclado,(Cliks, mouse)
                this.setFocusable(true); 

                // 2. Creamos el botón
                JButton botonVolver = new JButton("Volver");
                botonVolver.setFont(new Font("SansSerif", Font.BOLD, 20));
            
                // 3. Conectamos el botón al controlador que recibimos
                botonVolver.addActionListener(Controlador);
                this.add(botonVolver, BorderLayout.SOUTH); // Ponemos el botón en la parte de abajo

        }
}
