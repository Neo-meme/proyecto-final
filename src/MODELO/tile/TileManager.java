package src.MODELO.tile;

import src.GAMES.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;

public class TileManager {

    private BufferedImage wallTexture;
    
    GamePanel gp;
    // ── Tipos de tiles disponibles ─────────────────────────────────
    public Tile[] tileSet;

    // ── Mapa: matriz con el número de tile en cada celda ──────────
    public int[][] mapTileNum;
  
    // ── Constructor ───────────────────────────────────────────────
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tileSet = new Tile[10]; // por ahora, solo 10 tipos de tile
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; // mapa del nivel
        getTileImage();
        loadMap("nivel 1.txt");
    }

    // ── Carga las imágenes de cada tipo de tile ────────────────────
    public void getTileImage() {
        // Tile 0 — suelo (sin colisión)
        tileSet[0] = new Tile();
        tileSet[0].collision = false;
        
        // Tile 1 — pared (con colisión)
        tileSet[1] = new Tile();
        tileSet[1].collision = true;

        // Tile 2 — túnel (sin colisión, Pac-Man puede pasar)
        tileSet[2] = new Tile();
        tileSet[2].collision = false;
        tileSet[2].tunnel    = true; // marca especial
        
    }

    public void cargarTexturaPared() {
        try {
            String nivel  = "nivel " + gp.nivelActual;
            String tipo  = gp.tipoPacman.toString().toUpperCase();// "clasico", "tanque", "veloz"
            String ruta   = "/resources/" + nivel + "/" + tipo + ".png";
            wallTexture = ImageIO.read(getClass().getResourceAsStream(ruta));
            tileSet[1].image = wallTexture;

        } catch (Exception e) {
            System.out.println("Error cargando textura: " + e.getMessage());
            wallTexture = null;
            tileSet[1].image = null;
        }
    }

    // ── Carga el mapa desde un archivo .txt ───────────────────────
   public void loadMap(String filePath) {
        int[][] tempMap;

        if (gp.nivelActual == 1) {
            tempMap = new int[][] {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,0,1/* */,0,0,0,0,1,1,1,0,1,1,0,1,1,1,1,1,1},
                {2,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,2},
                {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1},
                {1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,0,1},
                {1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1},
                {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
                {1,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,0,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,1,1,1,1,1,1,1,1,1,0,1},
                {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            };
        } else {
            tempMap = getNivel2();
        }

        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {
                mapTileNum[col][row] = tempMap[row][col];
            }
        }
    }

    private int[][] getNivel2() {
        return new int[][] {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,1,0,1,1,0,1,1,1,1,0,0,1,1,1,1,0,1,1,0,1,1,1,0,1},
            {1,0,1,1,1,0,1,1,0,1,1,1,1,0,0,1,1,1,1,0,1,1,0,1,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1},
            {1,1,1,1,0,1,1,1,1,0,1,1,0,0,0,0,1,1,0,1,1,1,1,0,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,0,1,1,0,0,0,0,1,1,0,0,0,0,0,0,1,1,1,1},
            {1,1,1,1,0,1,1,0,1,0,1,1,1,1,1,1,1,1,0,1,0,1,1,0,1,1,1,1},
            {2,0,0,0,0,1,1,0,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,0,0,0,0,2},
            {1,1,1,1,0,1,1,0,1,1,1,0,0,0,0,0,0,1,1,1,0,1,1,0,1,1,1,1},
            {1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1},
            {1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,0,1,1,0,1,1,0,0,1,1,1,0,1,1,0,1,1,0,1,1,1},
            {1,0,0,0,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,0,0,1},
            {1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1},
            {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
            {1,0,0,0,0,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,1,0,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    }

    // ── Dibuja el mapa en pantalla ─────────────────────────────────
    // ── Dibuja el mapa en pantalla ─────────────────────────────────
    public void draw(Graphics2D g2) {
        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {
                int tileNum = mapTileNum[col][row];
                int x = col * gp.tileSize;
                int y = row * gp.tileSize + gp.hudHeight;

                if (tileNum == 1) {
                    if (tileSet[1].image != null) {
                        g2.drawImage(tileSet[1].image, x, y,
                            gp.tileSize, gp.tileSize, null);
                    } else {
                        g2.setColor(java.awt.Color.BLUE);
                        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
                    }
                } else if (tileNum == 2) {
                    g2.setColor(java.awt.Color.BLACK);
                    g2.fillRect(x, y, gp.tileSize, gp.tileSize);
                } else {
                    g2.setColor(new java.awt.Color(10, 10, 10));
                    g2.fillRect(x, y, gp.tileSize, gp.tileSize);
                }
            }
        }
    }

}