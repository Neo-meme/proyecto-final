import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

public class PanelPrincipal  extends JPanel {
   public PanelPrincipal(ControladorMenu Controlador) {

        this.setPreferredSize(new Dimension(1200, 800)); 
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#05102E"));

        //Panel Principal(Usamos BorderLayout para organizar el título y los botones)
        this.setLayout(new BorderLayout()); 

        // Le asignamos un titulo y sus especificaciones
        JLabel Titulo = new JLabel(" Bienvenidos a Strawus " , JLabel.CENTER); // Le asignamos JLabel.CENTER para que el texto siempre este centrado
        
        // Aqui le damos el tipo de la letra y el tamaño al titulo 
        Titulo.setFont(new Font("SansSerif", Font.BOLD, 60));
        
        // Le asignamos el color
        Titulo.setForeground(Color.decode( "#9EA15C"));
        
        // Le asignamos separacion del borde superior 
        Titulo.setBorder(new EmptyBorder(100, 0, 0, 0));
        
        // Creamos el panel secundario para agrupar los botones
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS)); // Y_AXIS = Alinear verticalmente los botones
        PanelBotones.setBackground(Color.decode("#05102E"));       
        
        // Creamos los botones usando un arreglo
        String[] nombresBotones = {"Jugar", "Instrucciones", "Clasificaciones" , "Salir"};
        
        for (String Botones : nombresBotones) {
            JButton BotonesMenu = new JButton(Botones);
            BotonesMenu.setFont(new Font("SansSerif", Font.BOLD, 24));
            
            // Tamaño de todos los botones
            Dimension TamañoBotones = new Dimension(250, 60);
            BotonesMenu.setPreferredSize(TamañoBotones);
            BotonesMenu.setMaximumSize(TamañoBotones);
            BotonesMenu.setMinimumSize(TamañoBotones);
            
            // Centrar todo dentro de Boxlayout
            BotonesMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Agregamos el ActionListener a cada botón para que respondan a los clics
            BotonesMenu.addActionListener(Controlador); // Aquí es donde conectamos el botón con su acción (ControladorMenu)

            // Espacio entre botones 
            PanelBotones.add(Box.createRigidArea(new Dimension(0, 20))); // Agrega un espacio vertical de 20 píxeles entre los botones
            
            // Añadimos los botones al panel de botones
            PanelBotones.add(BotonesMenu);
        }

        // Asignamos el color al fondo del panel principal 
        this.setBackground(Color.decode("#05102E"));

        // Añadimos el panel de botones al panel principal 
        this.add(PanelBotones, BorderLayout.CENTER );
        
        // Añadimos el titulo al panel principal
        this.add(Titulo, BorderLayout.NORTH);

}

}