package src.VISTA;

import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.*;

import src.CONTROLADOR.controller.ControladorMenu;

// Heredamos de JFrame para crear nuestra ventana principal del menú
public class MenuPrincipal extends JFrame {

    // construye la ventana cuando creamos el objeto
    public MenuPrincipal(ControladorMenu Controlador) {
        
         // Configuraciones de la ventana usando "this" (porque esta clase ya es la ventana) 
        this.setTitle("Strawus"); // Asignamos el título de la ventana superior
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Para que el programa cierre al darle a la X
        this.setLocationRelativeTo(null); // Esto centra la ventana en la pantalla del usuarioç

        // ── Icono de la ventana ───────────────────────────────────
        try {
            Image icono = ImageIO.read(
                getClass().getResourceAsStream("/resources/cosas/icono.png")
            );
            this.setIconImage(icono);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono");
        }
        // ─────────────────────────────────────────────────────────

        //Instanciamos el panel 
        PanelPrincipal PanelPrincipal = new PanelPrincipal(Controlador);
        this.CambiarPantalla(PanelPrincipal);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


        public void CambiarPantalla(JPanel NuevaPantalla){
            //1.bORRAR EL MENU PARA DEJARLO EN BLANCO 
            this.getContentPane().removeAll();
            
            //2. Ponemos el panel nuevo
            this.setContentPane(NuevaPantalla);
        
            //3. Utilizamos el repaint para actualizar la ventana y mostrar el nuevo panel
            this.revalidate();
            this.repaint();
            this.pack();                          // ventana se ajusta al nuevo panel
            this.setLocationRelativeTo(null);
            //4. Se le da el foco al nuevo panel para que pueda detectar el teclado y mouse
            NuevaPantalla.requestFocus();

        }

}