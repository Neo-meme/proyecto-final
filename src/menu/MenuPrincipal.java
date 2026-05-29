import javax.swing.*;

// Heredamos de JFrame para crear nuestra ventana principal del menú
public class MenuPrincipal extends JFrame {

    // construye la ventana cuando creamos el objeto
    public MenuPrincipal(ControladorMenu Controlador) {
        
         // Configuraciones de la ventana usando "this" (porque esta clase ya es la ventana) 
        this.setTitle("Strawus"); // Asignamos el título de la ventana superior
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Para que el programa cierre al darle a la X
        this.setResizable(false); // Para que el usuario no pueda cambiar el tamaño de la ventana
        this.setLocationRelativeTo(null); // Esto centra la ventana en la pantalla del usuario

        //Instanciamos el panel 
        PanelPrincipal PanelPrincipal = new PanelPrincipal(Controlador);
        this.CambiarPantalla(PanelPrincipal);

        this.setSize(1200, 800);
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

            //4. Se le da el foco al nuevo panel para que pueda detectar el teclado y mouse
            NuevaPantalla.requestFocus();
        
        
        }
}