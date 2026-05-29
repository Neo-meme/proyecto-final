package src.tile;

import src.Games.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

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
        // tileSet[0].image = ImageIO.read(...) // imagen cuando tengas recursos

        // Tile 1 — pared (con colisión)
        tileSet[1] = new Tile();
        tileSet[1].collision = true;
        // tileSet[1].image = ImageIO.read(...) // imagen cuando tengas recursos
    }

    // ── Carga el mapa desde un archivo .txt ───────────────────────
    public void loadMap(String filePath) {

        // Mapa temporal hardcodeado 16x12
        // 1 = pared, 0 = suelo

        int[][] tempMap = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,0,1},
            {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
            {1,1,1,1,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,1,0,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,0,1,1,1,0,1,1,0,0,1,1,0,0,1,1,0,1,1,1,0,0,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
            {1,1,1,1,0,1,1,0,0,1,1,0,0,1,1,0,0,0,0,1,1,0,0,1,1,0,0,1,1,0,1,0,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,1,0,1,0,1,1,0,1,0,1},
            {1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1},
            {1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0,1,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
        };

        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {
                mapTileNum[col][row] = tempMap[row][col];
            }
        }
    }

    // ── Dibuja el mapa en pantalla ─────────────────────────────────
    public void draw(Graphics2D g2) {

        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {

                int tileNum = mapTileNum[col][row];
                int x = col * gp.tileSize;
                int y = row * gp.tileSize;

                // Temporal: dibujamos colores hasta tener imágenes PNG
                if (tileNum == 1) {
                    g2.setColor(java.awt.Color.BLUE);         // pared
                } else {
                    g2.setColor(java.awt.Color.BLACK);        // suelo
                }
                g2.fillRect(x, y, gp.tileSize, gp.tileSize);

                // Cuando ua esten las imágenes reemplazar lo anterior por: preguntal a alan cual serian mejor 
                // g2.drawImage(tileSet[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            }
        }
    }

}