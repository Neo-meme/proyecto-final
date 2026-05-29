public class Main {
    public static void main(String[] args) {
        // 1. Instanciamos el Controlador (El que escucha)
        ControladorMenu controlador = new ControladorMenu();
        
        // 2. Instanciamos la Vista y le pasamos el controlador
        MenuPrincipal menu = new MenuPrincipal(controlador);
        
        // 3. Le pasamos la vista ya creada al controlador 
        controlador.setVistaPrincipal(menu);

        // 4. Mostramos la ventana
        menu.setVisible(true);
    }
}