package Games;

import MODELO.collision.CollisionChecker;
import MODELO.entity.Fantasma;
import MODELO.entity.Pacman;
import MODELO.entity.TipoPacman;
import CONTROLADOR.input.KeyHandler;
import MODELO.object.GestorClasificaciones;
import MODELO.object.Pellet;
import MODELO.tile.TileManager;

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

    // 1 = jugando, 2 = muerto (espera), 3 = juego terminado, 4 = pausa, 5 = victoria
    public int estadoJuego = 1;
    public int vidas;
    private long tiempoInicio = System.currentTimeMillis();
    private int tiempoJugado = 0;

    //--------------------- configuracion de pantalla ---------------------
    final int originalTileSize = 16;
    final int scale = 2;

    public final int tileSize     = originalTileSize * scale;
    public final int maxScreenCol = 28;
    public final int maxScreenRow = 23;
    public final int hudHeight    = 32;
    public final int ScreenWidth  = tileSize * maxScreenCol + hudHeight;
    public final int ScreenHeight = tileSize * maxScreenRow + hudHeight;

    //----------------- cronokinesis
    public double speedMultiplier = 1.0;
    public int cronoTimer = 0;
    public int contadorMuertes = 0;

    // ── fantasmas vulnerables ──
    public boolean fantasmasVulnerables = false;
    public int timerVulnerabilidad = 0;

    // ── botones ──
    private JButton btnReanudar;
    private JButton btnReintentar;
    private JButton btnVolverMenu;
    private boolean pausaPresionadaPreviamente = false;

    public TipoPacman tipoPacman;

    //----------- loop -----------
    final int FPS = 60;
    Thread gameThread;
    public int nivelActual = 1;

    // ------------- sistemas -------------
    public KeyHandler keyH           = new KeyHandler();
    public TileManager tileM         = new TileManager(this);
    public CollisionChecker cManager = new CollisionChecker(this);
    public Pacman pacman;

    // ------------- objetos -------------
    public Pellet[] pellets;
    public int pelletCount = 0;
    public Fantasma[] fantasmas = new Fantasma[3];

    // ── cereza aleatoria ──
    public Pellet cereza = null;
    private int cerezaTimer   = 0;
    private int cerezaVisible = 0;
    private final int CEREZA_INTERVALO = 60 * 30; // aparece cada 30 seg
    private final int CEREZA_DURACION  = 60 * 10; // visible 10 seg

    //---------------------- constructor ---------------------
    public GamePanel(TipoPacman tipoPacman) {
        this.tipoPacman = tipoPacman;

        // ── vidas según tipo ──────────────────────────────────────
        switch (tipoPacman) {
            case CLASICO -> this.vidas = 3;
            case TANQUE  -> this.vidas = 5;
            case VELOZ   -> this.vidas = 1;
            default      -> this.vidas = 3;
        }

        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        this.setLayout(null);
        configurarBotones();

        tileM.cargarTexturaPared(); // cargar textura según tipoPacman

        pacman = new Pacman(this, keyH);
        pacman.vidas = this.vidas;

        setupPellets();
        cerezaTimer = CEREZA_INTERVALO;

        fantasmas[0] = new Fantasma(this, Color.RED,  tileSize * 12, tileSize * 10);
        fantasmas[1] = new Fantasma(this, Color.PINK, tileSize * 13, tileSize * 10);
        fantasmas[2] = new Fantasma(this, Color.CYAN, tileSize * 14, tileSize * 10);
    }

    // ── botones ───────────────────────────────────────────────────
    private void configurarBotones() {
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

        btnReintentar = new JButton("Reintentar Nivel");
        btnReintentar.setBounds(ScreenWidth / 2 - 120, ScreenHeight / 2, 240, 50);
        btnReintentar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReintentar.setFocusable(false);
        btnReintentar.setVisible(false);
        btnReintentar.addActionListener(e -> reiniciarJuegoCompleto());
        this.add(btnReintentar);

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
        // ── vidas según tipo ──────────────────────────────────────
        switch (tipoPacman) {
            case CLASICO -> this.vidas = 3;
            case TANQUE  -> this.vidas = 5;
            case VELOZ   -> this.vidas = 1;
            default      -> this.vidas = 3;
        }
        pacman.vidas = this.vidas;
        pacman.score = 0;
        tiempoInicio = System.currentTimeMillis();

        setupPellets();
        cerezaTimer   = CEREZA_INTERVALO;
        cereza        = null;

        pacman.setDefaultValues();
        for (Fantasma f : fantasmas) {
            if (f != null) f.setDefaultValues();
        }

        estadoJuego = 1;
        ocultarBotones();
        this.requestFocus();
    }

    // ── pellets ───────────────────────────────────────────────────
    private int[][] powerPelletPositions = {
        {1, 2}, {26, 2}, {1, 15}, {26, 15}
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
                    pellets[i] = new Pellet(col, row, tileSize, hudHeight, esPower);
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

    // ── cereza ────────────────────────────────────────────────────
    private void updateCereza() {
        if (cereza == null || !cereza.visible) {
            cerezaTimer--;
            if (cerezaTimer <= 0) spawnCereza();
        } else {
            cerezaVisible--;
            if (cerezaVisible <= 0) {
                cereza.visible = false;
                cerezaTimer = CEREZA_INTERVALO;
            }
        }
    }

    private void spawnCereza() {
        Random rand = new Random();
        int col, row, intentos = 0;
        do {
            col = rand.nextInt(maxScreenCol);
            row = rand.nextInt(maxScreenRow);
            intentos++;
        } while (tileM.mapTileNum[col][row] != 0 && intentos < 100);

        if (tileM.mapTileNum[col][row] == 0) {
            int px = col * tileSize + tileSize / 4;
            int py = row * tileSize + tileSize / 4;
            cereza = new Pellet(px, py, tileSize, hudHeight);
            cerezaVisible = CEREZA_DURACION;
        }
    }

    private void checkCerezaCollision() {
        if (cereza == null || !cereza.visible) return;

        Rectangle pacRect = new Rectangle(
            pacman.x + pacman.hitBox.x,
            pacman.y + pacman.hitBox.y,
            pacman.hitBox.width,
            pacman.hitBox.height
        );

        Rectangle cerezaRect = new Rectangle(
            cereza.x, cereza.y, cereza.size, cereza.size
        );

        if (pacRect.intersects(cerezaRect)) {
            cereza.visible = false;
            cerezaTimer = CEREZA_INTERVALO;
            pacman.score += 100;

            Random rand = new Random();
            if (rand.nextBoolean()) speedMultiplier = 0.4;
            else                    speedMultiplier = 2.0;
            cronoTimer = 60 * 8;
        }
    }

    // ── hilo ──────────────────────────────────────────────────────
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
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

    // ── lógica ────────────────────────────────────────────────────
    public void update() {

        // pausa
        if (keyH.pausePressed && !pausaPresionadaPreviamente) {
            if (estadoJuego == 1) {
                estadoJuego = 4;
                btnReanudar.setVisible(true);
                btnVolverMenu.setVisible(true);
            } else if (estadoJuego == 4) {
                estadoJuego = 1;
                ocultarBotones();
            }
        }
        pausaPresionadaPreviamente = keyH.pausePressed;

        if (estadoJuego == 1) {
            pacman.update();

            for (Fantasma f : fantasmas) {
                if (f != null) f.update();
            }

            for (Fantasma f : fantasmas) {
                if (f != null) {
                    boolean choque = cManager.checkEntityCollision(pacman, f);
                    if (choque) {
                        if (fantasmasVulnerables) {
                            pacman.score += 200;
                            f.setDefaultValues();
                        } else {
                            vidas--;
                            pacman.vidas = vidas;

                            if (vidas <= 0) {
                                estadoJuego = 3;
                                guardarClasificacion();
                                btnReintentar.setVisible(true);
                                btnVolverMenu.setVisible(true);
                            } else {
                                estadoJuego = 2;
                                contadorMuertes = 60;
                            }
                            break;
                        }
                    }
                }
            }

            tiempoJugado = (int)((System.currentTimeMillis() - tiempoInicio) / 1000);

            if (cronoTimer > 0) {
                cronoTimer--;
                if (cronoTimer == 0) speedMultiplier = 1.0;
            }

            if (timerVulnerabilidad > 0) {
                timerVulnerabilidad--;
                if (timerVulnerabilidad == 0) fantasmasVulnerables = false;
            }

            updateCereza();
            checkCerezaCollision();
            checkPelletCollision();

        } else if (estadoJuego == 2) {
            contadorMuertes--;
            if (contadorMuertes <= 0) {
                pacman.setDefaultValues();
                for (Fantasma f : fantasmas) {
                    if (f != null) f.setDefaultValues();
                }
                estadoJuego = 1;
            }
        }
    }

    // ── colisión pellets ──────────────────────────────────────────
    private void checkPelletCollision() {
        Rectangle pacRect = new Rectangle(
            pacman.x + pacman.hitBox.x,
            pacman.y + pacman.hitBox.y,
            pacman.hitBox.width,
            pacman.hitBox.height
        );

        for (Pellet pellet : pellets) {
            if (pellet.visible) {
                Rectangle pelletRect = new Rectangle(
                    pellet.x, pellet.y, pellet.size, pellet.size
                );

                if (pacRect.intersects(pelletRect)) {
                    pellet.visible = false;

                    if (pellet.isPower) {
                        // poder naranja: solo comer fantasmas
                        fantasmasVulnerables = true;
                        timerVulnerabilidad  = 60 * 8;
                        pacman.score += 50;
                    } else {
                        pacman.score += 10;
                    }
                }
            }
        }

        boolean todosComidos = true;
        for (Pellet pellet : pellets) {
            if (pellet.visible) { todosComidos = false; break; }
        }
        if (todosComidos) cargarSiguienteNivel();
    }

    private void guardarClasificacion() {
        GestorClasificaciones.guardarResultado(pacman.score, tiempoJugado);
    }

    // ── renderizado ───────────────────────────────────────────────
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        drawHUD(g2);
        tileM.draw(g2);

        for (Pellet pellet : pellets) {
            pellet.draw(g2);
        }

        if (cereza != null && cereza.visible) {
            cereza.draw(g2);
        }

        pacman.draw(g2);

        for (Fantasma f : fantasmas) {
            if (f != null) f.draw(g2);
        }

        if (estadoJuego == 3) {
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, ScreenWidth, ScreenHeight);
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            String text = "Game Over";
            int x = (ScreenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
            int y = ScreenHeight / 2 - 50;
            g2.drawString(text, x, y);

        } else if (estadoJuego == 4) {
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, ScreenWidth, ScreenHeight);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            String text = "PAUSA";
            int x = (ScreenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
            int y = ScreenHeight / 2 - 50;
            g2.drawString(text, x, y);
            
        // NUEVO: PANTALLA DE VICTORIA
        } else if (estadoJuego == 5) { 
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, ScreenWidth, ScreenHeight);
            
            // Texto principal de victoria
            g2.setColor(Color.YELLOW);
            g2.setFont(new Font("Arial", Font.BOLD, 80));
            String text = "¡Buen juego!";
            int x = (ScreenWidth - g2.getFontMetrics().stringWidth(text)) / 2;
            int y = ScreenHeight / 2 - 50;
            g2.drawString(text, x, y);
            
            // Mostrarle su puntuación final debajo
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 40));
            String scoreText = "Puntuación Final: " + pacman.score;
            int scoreX = (ScreenWidth - g2.getFontMetrics().stringWidth(scoreText)) / 2;
            g2.drawString(scoreText, scoreX, y + 60);
        }
    }

    // ── siguiente nivel ───────────────────────────────────────────
    private void cargarSiguienteNivel() {
        if (nivelActual == 1) {
            nivelActual = 2;
            tileM.loadMap("nivel2.txt");
            tileM.cargarTexturaPared(); // ← textura del nivel 2
            setupPellets();
            cerezaTimer = CEREZA_INTERVALO;
            cereza = null;
            pacman.setDefaultValues();
            for (Fantasma f : fantasmas) {
                if (f != null) f.setDefaultValues();
            }
        } else {
            // ¡GANÓ EL JUEGO! (Pasó el nivel 2)
            guardarClasificacion();
            estadoJuego = 5; // 5 = Estado de Victoria
            btnReintentar.setVisible(false); // Ocultamos el botón de reintentar
            btnVolverMenu.setVisible(true);  // Solo mostramos el de volver al menú
        }
    }

    // ── volver al menú ────────────────────────────────────────────
    private void volverAlMenu() {
        gameThread = null;

        javax.swing.SwingUtilities.invokeLater(() -> {
            java.awt.Window ventana =
                javax.swing.SwingUtilities.getWindowAncestor(this);

            if (ventana instanceof VISTA.MenuPrincipal menu) {
                CONTROLADOR.controller.ControladorMenu controlador =
                    new CONTROLADOR.controller.ControladorMenu();
                VISTA.PanelPrincipal panel =
                    new VISTA.PanelPrincipal(controlador);

                controlador.setVistaPrincipal(menu);
                menu.CambiarPantalla(panel);
            }
        });
    }

    // ── HUD ───────────────────────────────────────────────────────

    private void drawHUD(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, ScreenWidth, hudHeight);

        g2.setColor(Color.YELLOW);
        g2.drawString("Puntos: " + pacman.score, 20, 20);
        // tiempo MM:SS
        int min  = tiempoJugado / 60;
        int seg = tiempoJugado % 60;
        g2.drawString("Tiempo: " + min + ":" + seg, 250, 20);
        g2.drawString("Vidas: ", 450, 20);

        for(int i = 0; i < vidas; i++){
            g2.setColor(pacman.colorPacman);
            g2.fillOval(510 + (i * 20), 8, 14, 14);
        }

        g2.drawString("Tipo: " + tipoPacman, 650, 20);
    }  
}