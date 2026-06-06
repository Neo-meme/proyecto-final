package src.Games;

import src.MODELO.collision.CollisionChecker;
import src.MODELO.entity.Fantasma;
import src.MODELO.entity.Pacman;
import src.MODELO.entity.TipoPacman;
import src.CONTROLADOR.input.KeyHandler;
import src.MODELO.object.GestorClasificaciones;
import src.MODELO.object.Pellet;
import src.MODELO.tile.TileManager;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    // 1 = jugando, 2 = muerto (espera), 3 = juego terminado, 4 = pausa
    public int estadoJuego = 1; 
    public int vidas; 
    private long tiempoInicio = System.currentTimeMillis();
    private int tiempoJugado = 0;

    //--------------------- configuracion de pantalla ---------------------
    final int originalTileSize = 16;     
    final int scale = 2;                

    public final int tileSize = originalTileSize * scale; // 48*48 px por tile
    public final int maxScreenCol = 28; // 28 * 32 = 896px
    public final int maxScreenRow = 23; // 31 * 32 = 992px  (sin HUD)
    public final int hudHeight    = 32; // espacio para puntaje arriba
    public final int ScreenWidth = tileSize * maxScreenCol+ hudHeight;// 896px
    public  final int ScreenHeight = tileSize * maxScreenRow + hudHeight; // 992 + 48 = 1040px

    //----------------- poder de ralentizar o acelerar el tiempo
    public double speedMultiplier = 1.0; 
    public int cronoTimer = 0;           
    public int contadorMuertes = 0;
    
    // ── VARIABLES PARA COMER FANTASMAS ──
    public boolean fantasmasVulnerables = false; 
    public int timerVulnerabilidad = 0;

    // ── BOTONES DE LA INTERFAZ ──
    private JButton btnReanudar;
    private JButton btnReintentar;
    private JButton btnVolverMenu;
    private boolean pausaPresionadaPreviamente = false; 
 
    //------- funciones para escoger el tipo de pacman
    public TipoPacman tipoPacman;

    //----------- configuracion del loop -----------
    final int FPS = 60;
    Thread gameThread;
    public int nivelActual = 1; 
 
    // ------------- input de movimiento -------------
    public KeyHandler keyH      = new KeyHandler();
    public TileManager tileM    = new TileManager(this);
    public CollisionChecker cManager = new CollisionChecker(this); 
    public Pacman pacman;      

    // ------------- objetos del juego -------------
    public Pellet[] pellets;
    public int pelletCount = 0;
    public Fantasma[] fantasmas = new Fantasma[3];

    //---------------------- constructor ---------------------
    public GamePanel(TipoPacman tipoPacman){
        this.tipoPacman = tipoPacman;
       
        // ── CONFIGURAMOS LAS VIDAS SEGÚN EL PERSONAJE ELEGIDO ──
        if (tipoPacman != null && tipoPacman.toString().toUpperCase().contains("VELOZ")) {
            this.vidas = 1; 
        } else {
            this.vidas = 3; 
        }

        this.setPreferredSize (new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        // ── LAYOUT NULO PARA POSICIONAR BOTONES LIBREMENTE ──
        this.setLayout(null); 
        configurarBotones();
       
        pacman = new Pacman(this, keyH); 
        pacman.vidas = this.vidas;

        setupPellets();

        fantasmas[0] = new Fantasma(this, Color.RED,  tileSize * 12, tileSize * 10);  // Blinky
        fantasmas[1] = new Fantasma(this, Color.PINK, tileSize * 13, tileSize * 10);  // Pinky
        fantasmas[2] = new Fantasma(this, Color.CYAN, tileSize * 14, tileSize * 10);  // Inky
    }

    // ── CONFIGURACIÓN DE LOS BOTONES DE INTERFAZ ──
    private void configurarBotones() {
        // Botón de Reanudar (Solo para Pausa)
        btnReanudar = new JButton("Reanudar Partida");
        btnReanudar.setBounds(ScreenWidth / 2 - 120, ScreenHeight / 2, 240, 50);
        btnReanudar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReanudar.setFocusable(false); 
        btnReanudar.setVisible(false);
        btnReanudar.addActionListener(e -> {
            estadoJuego = 1;
            ocultarBotones();
            this.requestFocus(); 
        });
        this.add(btnReanudar);

        // Botón de Reintentar (Solo para Game Over)
        btnReintentar = new JButton("Reintentar Nivel");
        btnReintentar.setBounds(ScreenWidth / 2 - 120, ScreenHeight / 2, 240, 50);
        btnReintentar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReintentar.setFocusable(false); 
        btnReintentar.setVisible(false);
        btnReintentar.addActionListener(e -> reiniciarJuegoCompleto());
        this.add(btnReintentar);

        // Botón de Volver al Menú Principal (Salir)
        btnVolverMenu = new JButton("Salir al Menú Principal");
        btnVolverMenu.setBounds(ScreenWidth / 2 - 120, ScreenHeight / 2 + 70, 240, 50);
        btnVolverMenu.setFont(new Font("Arial", Font.BOLD, 16));
        btnVolverMenu.setFocusable(false);
        btnVolverMenu.setVisible(false);
        btnVolverMenu.addActionListener(e -> volverAlMenu());
        this.add(btnVolverMenu);
    }

    private void ocultarBotones() {
        btnReanudar.setVisible(false);
        btnReintentar.setVisible(false);
        btnVolverMenu.setVisible(false);
    }

    private void reiniciarJuegoCompleto() {
        // Restauramos vidas
        this.vidas = (tipoPacman != null && tipoPacman.toString().toUpperCase().contains("VELOZ")) ? 1 : 3;
        pacman.vidas = this.vidas;
        pacman.score = 0;
        tiempoInicio = System.currentTimeMillis();
        
        setupPellets();
        pacman.setDefaultValues();
        for(Fantasma f : fantasmas) {
            if(f != null) f.setDefaultValues();
        }
        
        estadoJuego = 1;
        ocultarBotones();
        this.requestFocus();
    }
   
    //---------------------- objetos del juego ---------------------
    private int[][] powerPelletPositions = {
        {1, 2},   {26, 2},  {1, 15},  {26, 15}  
    };

    private void setupPellets() {
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

    public void startGameThread(){
        gameThread = new Thread (this);
        gameThread.start();
    }
 
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; 
        double nextDrawTime = System.nanoTime() + drawInterval;
   
        while (gameThread != null){
            update();           
            repaint();          
       
            try {
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

        // ── LÓGICA DE PAUSA (Estado 4) ──
        if (keyH.pausePressed && !pausaPresionadaPreviamente) {
            if (estadoJuego == 1) {
                estadoJuego = 4; // Entrar en pausa
                btnReanudar.setVisible(true);
                btnVolverMenu.setVisible(true);
            } else if (estadoJuego == 4) {
                estadoJuego = 1; // Salir de pausa
                ocultarBotones();
            }
        }
        pausaPresionadaPreviamente = keyH.pausePressed;

        if(estadoJuego == 1){
            pacman.update();
           
            for(Fantasma f : fantasmas){
                if(f != null) f.update();
            }

            for (Fantasma f : fantasmas){
                if(f != null){
                    boolean choqueFantasma = cManager.checkEntityCollision(pacman, f);

                    if(choqueFantasma){
                        // ── LÓGICA: COMER FANTASMAS ──
                        if (fantasmasVulnerables) {
                            pacman.score += 200; // Come al fantasma
                            f.setDefaultValues(); // Lo manda a la caja
                        } else {
                            vidas--; 
                            pacman.vidas = vidas;
                               
                            if (vidas <= 0){
                                estadoJuego = 3; // Game Over
                                guardarClasificacion(); 
                                
                                // Mostrar botones de Game Over
                                btnReintentar.setVisible(true);
                                btnVolverMenu.setVisible(true);
                            } else {
                                estadoJuego = 2; // Pausa temporal por muerte
                                contadorMuertes = 60 ;   
                            }
                            break; 
                        }
                    }
                }
            }

            tiempoJugado =(int)((System.currentTimeMillis() - tiempoInicio)/1000);

            if (cronoTimer > 0) {
                cronoTimer--;
                if (cronoTimer == 0) speedMultiplier = 1.0; 
            }

            // ── VULNERABILIDAD TIMER ──
            if (timerVulnerabilidad > 0) {
                timerVulnerabilidad--;
                if (timerVulnerabilidad == 0) {
                    fantasmasVulnerables = false; // Vuelven a ser mortales
                }
            }

            checkPelletCollision();

        } else if (estadoJuego == 2) {
            contadorMuertes--;
            if (contadorMuertes <= 0) {
                pacman.setDefaultValues();
                for(Fantasma f : fantasmas){
                    if(f != null) f.setDefaultValues();
                }
                estadoJuego = 1; 
            }
        } 
    }

    private void checkPelletCollision() {
        // Creamos un rectángulo para Pac-Man basado en su posición actual y su hitbox
        Rectangle pacRect = new Rectangle(
            pacman.x + pacman.hitBox.x, 
            pacman.y + pacman.hitBox.y, 
            pacman.hitBox.width, 
            pacman.hitBox.height
        );

        for (Pellet pellet : pellets) {
            if (pellet.visible) {
                // Creamos un rectángulo para la bolita
                Rectangle pelletRect = new Rectangle(pellet.x, pellet.y, pellet.size, pellet.size);

                // Si los rectángulos se solapan, se come la bolita
                if (pacRect.intersects(pelletRect)) {
                    pellet.visible = false;

                    if (pellet.isPower) {
                        // ── ACTIVA EL PODER DE COMER FANTASMAS ──
                        fantasmasVulnerables = true;
                        timerVulnerabilidad = 60 * 8; 
                        
                        pacman.score += 50;
                        Random rand = new Random();
                        if (rand.nextBoolean()) speedMultiplier = 0.4;
                        else speedMultiplier = 2.0; 
                        cronoTimer = 60 * 8 ; 
                    } else {
                        pacman.score += 10; 
                    }
                }
            }
        }

        boolean todosComidos = true;
        for (Pellet pellet : pellets) {
            if (pellet.visible) {
                todosComidos = false;
                break;
            }
        }
        if (todosComidos) cargarSiguienteNivel();
    }

    private void guardarClasificacion(){
        GestorClasificaciones.guardarResultado(pacman.score, tiempoJugado);
    }

    // ── Renderizado ───────────────────────────────────────────────
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        drawHUD(g2);

        tileM.draw(g2);

        for (Pellet pellet : pellets) {
            pellet.draw(g2);
        }

        pacman.draw(g2);

        for (Fantasma f : fantasmas) {
            if (f != null) f.draw(g2);
        }

        if(estadoJuego == 3){ // GAME OVER
            g2.setColor(new Color(0,0,0,200));
            g2.fillRect(0,0,ScreenWidth,ScreenHeight);

            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            String text = "Game Over";

            int x = (ScreenWidth - g2.getFontMetrics().stringWidth(text))/2;
            int y = ScreenHeight / 2 - 50; 
            g2.drawString(text, x, y);
            
        } else if (estadoJuego == 4) { // PAUSA
            g2.setColor(new Color(0,0,0,200));
            g2.fillRect(0,0,ScreenWidth,ScreenHeight);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            String text = "PAUSA";

            int x = (ScreenWidth - g2.getFontMetrics().stringWidth(text))/2;
            int y = ScreenHeight / 2 - 50;
            g2.drawString(text, x, y);
        }
       
    }

    private void cargarSiguienteNivel() {
        if (nivelActual == 1) {
            nivelActual = 2;
            tileM.loadMap("nivel2.txt");
            setupPellets();
            pacman.setDefaultValues();
            for(Fantasma f : fantasmas) {
                if(f != null) f.setDefaultValues();
            }
        } else {
            System.out.println("¡Juego terminado!");
            guardarClasificacion();
            
            estadoJuego = 3; 
            btnReintentar.setVisible(true);
            btnVolverMenu.setVisible(true);
        }
    }

    private void volverAlMenu() {
        gameThread = null; 
        
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

    private void drawHUD(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ScreenWidth, hudHeight);

        g2.setColor(Color.YELLOW);
        g2.drawString("Puntos: " + pacman.score, 20, 20);
        g2.drawString("Tiempo: " + tiempoJugado + "s", 250, 20);
        g2.drawString("Vidas: ", 450, 20);

        for(int i = 0; i < vidas; i++){
            g2.setColor(pacman.colorPacman);
            g2.fillOval(510 + (i * 20), 8, 14, 14);
        }

        g2.drawString("Tipo: " + tipoPacman, 650, 20);
    }  
}