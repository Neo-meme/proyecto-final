package src.MODELO.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pellet {

    // ── Posición en el mapa (en píxeles) ──────────────────────────
    public int x, y;
    private int hudOffset;
    // ── Estado ────────────────────────────────────────────────────
    public boolean visible = true; // false cuando Pac-Man lo consume

    // ── Tamaño visual ─────────────────────────────────────────────
    public int size;

    // ── poderes pa el pacman  ─────────────────────────────────────────────
    public boolean isPower; // true = power pellet (punto grande)

    // ── Constructor ───────────────────────────────────────────────
    public Pellet(int col, int row, int tileSize, int hudHeight, boolean isPower) {
        this.isPower  = isPower;
        this.size     = isPower ? tileSize / 2 : tileSize / 6; // grande o pequeño

        //posicionamos el pellet en el centro del tile
        this.x        = col * tileSize + (tileSize - size) / 2;
        this.y        = row * tileSize + (tileSize - size) / 2;
        this.hudOffset = hudHeight;
    }

    
    
    // ── Dibujo ────────────────────────────────────────────────────
    public void draw(Graphics2D g2) {
        if (!visible) return; // Si ya se la comió, no dibuja nada

        if (isPower) {
            // ── DIBUJAR FRUTICA (CEREZAS) ──
            int fruitSize = 14;
           
            // Usamos tu lógica: Posición real + el empujón del HUD para dibujarlo
            int drawX = x;
            int drawY = y + hudOffset;

            // 1. Dibujar los tallos (Verde)
            g2.setColor(new Color(34, 139, 34)); // Verde bosque
            g2.drawLine(drawX + fruitSize/2, drawY + fruitSize/2, drawX + fruitSize, drawY - 4); // Tallo 1
            g2.drawLine(drawX + fruitSize, drawY - 4, drawX + fruitSize + 8, drawY + fruitSize/2); // Tallo 2

            // 2. Dibujar las cerezas (Rojas)
            g2.setColor(Color.RED);
            g2.fillOval(drawX - 2, drawY + 2, fruitSize, fruitSize); // Cereza izquierda
            g2.fillOval(drawX + 8, drawY + 2, fruitSize, fruitSize); // Cereza derecha

            // 3. Detalle de brillo (Blanco)
            g2.setColor(Color.WHITE);
            g2.fillOval(drawX, drawY + 5, 4, 4);      // Brillo izquierda
            g2.fillOval(drawX + 10, drawY + 5, 4, 4); // Brillo derecha

        } else {
            // ── DIBUJAR PUNTO NORMAL ──
            // Usamos tu lógica exacta que funciona de maravilla
            g2.setColor(new Color(255, 255, 153)); // Amarillo pastel suave
            g2.fillOval(x, y + hudOffset, size, size);
        }
    }
}