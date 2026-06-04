package src.GAMES;

import src.MODELO.collision.CollisionChecker;
import src.MODELO.entity.Fantasma;
import src.MODELO.entity.Pacman;
import src.CONTROLADOR.input.KeyHandler;
import src.MODELO.object.GestorClasificaciones;
import src.MODELO.object.Pellet;
import src.MODELO.tile.TileManager;

import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class GamePanel extends JPanel implements Runnable {

    // HUD para vida y puntaje 
    public int vidas = 3;
    private long tiempoInicio = System.currentTimeMillis();
    private int tiempoJugado = 0;

    //--------------------- configuracion de pantalla ---------------------

    final int originalTileSize =16;     // 16*16 tamaño base del tile en pixeles 
    final int scale =2;                // factor de escala

    public final int tileSize = originalTileSize * scale; // 48*48 px por tile
    public final int maxScreenCol = 28; // 28 * 32 = 896px
    public final int maxScreenRow = 23; // 31 * 32 = 992px  (sin HUD)
    public final int hudHeight    = 32; // espacio para puntaje arriba
    public final int ScreenWidth = tileSize * maxScreenCol+ hudHeight;// 896px
    public  final int ScreenHeight = tileSize * maxScreenRow + hudHeight; // 992 + 48 = 1040px

    //----------------- poder de ralentizar o acelerar el tiempo
    public double speedMultiplier = 1.0; // 1.0 = normal, 0.5 = lento, 2.0 = rápido
    public int cronoTimer = 0;           // cuántos frames dura el efecto

  

    //----------- configuracion del loop -----------
    final int FPS = 60;
    Thread gameThread;
    public int nivelActual = 1; // para cargar el mapa correcto desde TileManager
  

    // ------------- input de movimiento -------------
    public KeyHandler keyH      = new KeyHandler();
    public TileManager tileM    = new TileManager(this);       // 1. primero el mapa
    public CollisionChecker cManager = new CollisionChecker(this); // 2. luego colisiones
    public Pacman pacman        = new Pacman(this, keyH);      // 3. luego Pac-Man

  

    // ------------- objetos del juego -------------
    public Pellet[] pellets;
    public int pelletCount = 0;

    // Arreglo (Array) para almacenar nuestros 3 fantasmas
    public Fantasma[] fantasmas = new Fantasma[3]; 


    //---------------------- constructor ---------------------
    public GamePanel(){
        this.setPreferredSize (new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        setupPellets(); 

        // Inicializamos los fantasmas asignando colores y posiciones únicas
        fantasmas[0] = new Fantasma(this, Color.RED,  tileSize * 12, tileSize * 10);  // Blinky
        fantasmas[1] = new Fantasma(this, Color.PINK, tileSize * 13, tileSize * 10);  // Pinky
        fantasmas[2] = new Fantasma(this, Color.CYAN, tileSize * 14, tileSize * 10);  // Inky
    }
    
    //---------------------- objetos del juego ---------------------

    private int[][] powerPelletPositions = {
        {1, 2},   // esquina superior izquierda
        {26, 2},  // esquina superior derecha
        {1, 15},  // esquina inferior izquierda
        {26, 15}  // esquina inferior derecha
    };

    private void setupPellets() {
        // Contar cuántas celdas de suelo hay para crear el arreglo
        int count = 0;
        for (int col = 0; col < maxScreenCol; col++) {
            for (int row = 0; row < maxScreenRow; row++) {
                if (tileM.mapTileNum[col][row] == 0) count++;
            }
        }

        pellets = new Pellet[count];
        int i = 0;
        for (int col = 0; col < maxScreenCol; col++) {
            for (int row = 0; row < maxScreenRow; row++) {
                if (tileM.mapTileNum[col][row] == 0) {
                    boolean esPower = esPowerPellet(col, row);
                    pellets[i] = new Pellet(col, row, tileSize,hudHeight,esPower);
                    i++;
                }
            }
        }
        pelletCount = count;
    }

    private boolean esPowerPellet(int col, int row) {
        for (int[] pos : powerPelletPositions) {
            if (pos[0] == col && pos[1] == row) return true;
        }
        return false;
    }


    //-------------- arranque del hilo del juego --------------
    public void startGameThread(){
        gameThread = new Thread (this);
        gameThread.start();
    }
  

    //--------------  Game loop (se ejecuta en el hilo) --------------
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.0166666667 segundos por frame
        double nextDrawTime = System.nanoTime() + drawInterval;
    
        while (gameThread != null){
            update();           // 1. actualizar lógica
            repaint();          // 2. redibujar pantalla
        
            try {
                // ── Cronokinesis: modificar el intervalo según speedMultiplier ──
                double intervaloEfectivo = drawInterval / speedMultiplier;
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1_000_000;
                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += intervaloEfectivo;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }
   

    // ── Lógica del juego ─────────────────────
    public void update() {

        // ── Actualizar el tiempo jugado  ─────────────────────────────────────
        tiempoJugado =(int)((System.currentTimeMillis() - tiempoInicio)/1000);

        // ── Cronokinesis timer ─────────────────────────────────────
        if (cronoTimer > 0) {
            cronoTimer--;
            if (cronoTimer == 0) {
                speedMultiplier = 1.0; // restaurar velocidad normal
            }
        }
        pacman.update(); // actualizar la lógica

        // Actualizamos cada fantasma en el arreglo
        for (Fantasma f : fantasmas) {
            f.update(); 
        }
        checkPelletCollision();
    }

    private void checkPelletCollision() {
        for (Pellet pellet : pellets) {
            if (pellet.visible) {
                // centro de Pac-Man
                int pacCenterX = pacman.x + tileSize / 2;
                int pacCenterY = pacman.y + tileSize / 2;

                // distancia entre centros
                int pelletCenterX = pellet.x + pellet.size / 2; // corrección: agregar getter o hacer size public
                int pelletCenterY = pellet.y + pellet.size / 2;

                int dist = (int) Math.sqrt(
                    Math.pow(pacCenterX - pelletCenterX, 2) +
                    Math.pow(pacCenterY - pelletCenterY, 2)
                );

                if (dist < tileSize / 2) {
                    pellet.visible = false;
                    pacman.score += 10; // sumar puntos
                }
            }
        }

        // verificar si todos los pellets fueron consumidos
        boolean todosComidos = true;
        for (Pellet pellet : pellets) {
            if (pellet.visible) {
                todosComidos = false;
                break;
            }
        }
        if (todosComidos) {
            cargarSiguienteNivel();
        }
    }

    // guardado de clasificaciones
    private void guardarClasificacion(){
        GestorClasificaciones.guardarResultado(
            pacman.score,
            tiempoJugado
        );
    }

    // ── Renderizado ───────────────────────────────────────────────
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawHUD(g2); // dibujar el HUD antes del mapa

        tileM.draw(g2); // dibujar el mapa

        // dibujar pellets
        for (Pellet pellet : pellets) {
            pellet.draw(g2);
        }

        pacman.draw(g2); // dibujar a Pacman

        // dibujar cada fantasma en el arreglo
        for (Fantasma f : fantasmas) {
            f.draw(g2); // 4. Enemigos
        }

        g2.dispose(); // libera recursos del objeto gráfico
    }

    // IMPORTANTE ESTO SOLO ES DE PRUEBA . ACA SE CARGA EL NIVEL Y SE TERMINA PARA PROBRAR LAS CALIFICACIONES 
    private void cargarSiguienteNivel() {

        if (nivelActual == 1) {

            nivelActual = 2;
            tileM.loadMap("nivel2.txt");
            setupPellets();
            pacman.setDefaultValues();

        } else {

            System.out.println("¡Juego terminado!");

            guardarClasificacion();

            gameThread = null;

            volverAlMenu();
        }
    }

    private void volverAlMenu() {

        javax.swing.SwingUtilities.invokeLater(() -> {

            java.awt.Window ventana =
                javax.swing.SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof src.VISTA.MenuPrincipal menu) {

                src.CONTROLADOR.controller.ControladorMenu controlador =
                        new src.CONTROLADOR.controller.ControladorMenu();

                src.VISTA.PanelPrincipal panel =
                        new src.VISTA.PanelPrincipal(controlador);

                controlador.setVistaPrincipal(menu);

                menu.CambiarPantalla(panel);
            }
        });
    }

    // DESPUES DE DEBE ELIMINAR ALAN PARA CONFIGUAR BIEN CUANDOS SE MUERE EL PACMAN 
    // IMPORTANTE ESTO SOLO ES DE PRUEBA . ACA SE CARGA EL NIVEL Y SE TERMINA PARA PROBRAR LAS CALIFICACIONES 


    private void drawHUD(Graphics2D g2){

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ScreenWidth, hudHeight);

        g2.setColor(Color.YELLOW);

        g2.drawString(
            "Puntos: " + pacman.score,
            20,
            20
        );

        g2.drawString(
            "Tiempo: " + tiempoJugado + "s",
            250,
            20
        );

        g2.drawString(
            "Vidas: " + vidas,
            450,
            20
        );
    }
        
}

