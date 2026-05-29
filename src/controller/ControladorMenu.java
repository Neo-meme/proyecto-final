import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Al implementar ActionListener, esta clase esta obligada a tener un metodo para responder a los clics
public class ControladorMenu implements ActionListener {

           //Creamos un atributo para conectar el controlador y las vistas del juego 
            private MenuPrincipal vistaPrincipal ;
            // Creamos el constructor
            public ControladorMenu(){
                // Aquí podríamos hacer algo con el controlador del juego si es necesario
            }
             // Este es el metodo que se activa cuando el usuario hace clic en un botón

            public void setVistaPrincipal(MenuPrincipal vistaPrincipal) {
                this.vistaPrincipal = vistaPrincipal;
            }



    @Override
    public void actionPerformed(ActionEvent e) {
        // e.getActionCommand() obtiene exactamente el texto del botón que fue presionado
        String botonPulsado = e.getActionCommand();

        // Usamos un switch para decidir qué hacer dependiendo del botón
        switch (botonPulsado) {
            case "Jugar":
                //Instanciamos la neuva vista (Juegoo)
                ControladorJuego GameController = new ControladorJuego(vistaPrincipal);
                GameController.iniciarPartida();
                break;
                
            case "Instrucciones":
                VistaInstrucciones vistaInstrucciones = new VistaInstrucciones(this);
                vistaPrincipal.CambiarPantalla(vistaInstrucciones);
                break;
                
            case "Clasificaciones":
                    VistaClasificaciones vistaClasificaciones = new VistaClasificaciones(this);
                    vistaPrincipal.CambiarPantalla(vistaClasificaciones);
                break;
                
             case "Volver":
                    PanelPrincipal panelPrincipal = new PanelPrincipal(this);
                    vistaPrincipal.CambiarPantalla(panelPrincipal);
                break;

            case "Salir":
                System.out.println("Cerrando el juego. ¡Adiós!");
                System.exit(0); // Comando nativo para cerrar la aplicación
                break;
        }
    }
}