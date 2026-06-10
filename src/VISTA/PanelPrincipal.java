package VISTA;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import CONTROLADOR.controller.ControladorMenu;

public class PanelPrincipal extends JPanel {
   
   private BufferedImage imagenFondo;
   
   public PanelPrincipal(ControladorMenu Controlador) {

        this.setPreferredSize(new Dimension(896, 768)); 
        this.setLayout(new BorderLayout());

        // Intentamos cargar la imagen de fondo completa
        imagenFondo = ResourceManager.loadImage("cosas/titulo.png");
        
        // Creamos el panel secundario para agrupar los botones
        JPanel PanelBotones = new JPanel();
        PanelBotones.setLayout(new BoxLayout(PanelBotones, BoxLayout.Y_AXIS)); // Y_AXIS = Alinear verticalmente los botones
        
        // Hacemos el panel transparente para que la imagen de fondo sea visible
        PanelBotones.setOpaque(false); 
        
        // Creamos los botones usando un arreglo
        String[] nombresBotones = {"Jugar", "Instrucciones", "Clasificaciones", "Salir"};
        
        // Empujamos los botones verticalmente hacia abajo para alinearlos con el diseño del fondo
        PanelBotones.add(Box.createRigidArea(new Dimension(0, 290)));

        for (String Botones : nombresBotones) {
            JButton BotonesMenu = new JButton(Botones);
            BotonesMenu.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 24));

            //Hacemos los botones invisibles para que coincidan con la imagen de fondo 
            BotonesMenu.setOpaque(false);
            BotonesMenu.setContentAreaFilled(false);
            BotonesMenu.setBorderPainted(false); // Quita el borde del botón
            BotonesMenu.setForeground(new Color(0, 0, 0, 0)); // Vuelve el texto de Java transparente

            // Tamaño de todos los botones
            Dimension TamañoBotones = new Dimension(280, 60);
            BotonesMenu.setPreferredSize(TamañoBotones);
            BotonesMenu.setMaximumSize(TamañoBotones);
            BotonesMenu.setMinimumSize(TamañoBotones);
            
            // Centrar todo dentro de BoxLayout
            BotonesMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            // Agregamos el ActionListener a cada botón para que respondan a los clics
            BotonesMenu.addActionListener(Controlador); 

            // Espacio entre botones 
            PanelBotones.add(Box.createRigidArea(new Dimension(0, 20))); 
            
            // Añadimos los botones al panel de botones
            PanelBotones.add(BotonesMenu);
        }

        // Añadimos el panel de botones al panel principal 
        this.add(PanelBotones, BorderLayout.CENTER);
   }

   @Override
   protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       if (imagenFondo != null) {
           // Dibuja la imagen adaptándola por completo a las dimensiones del panel
           g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), null);
       } else {
           // Color de respaldo en caso de que falle la lectura del archivo
           g.setColor(Color.decode("#05102E"));
           g.fillRect(0, 0, getWidth(), getHeight());
       }
   }
}