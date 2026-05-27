package src.entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

    public int x, y; // coordenadas del personaje en el mapa
    public int speed; // velocidad de movimiento del personaje
    public String direction= "arriba"; // direccion en la que se esta moviendo el personaje

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // imagenes del personaje para cada direccion

    public int spriteCounter = 0; // contador para cambiar de imagen cada cierto tiempo
    public int spriteNum = 1; // numero de la imagen actual (1 o 2)

     // ── Hitbox para detección de colisiones ───────────────────────
    public Rectangle hitBox;// hitbox del personaje para detectar colisiones
    public int hitBoxDefaultX, hitBoxDefaultY; // para guardar la posicion original del hitbox
    public boolean collisionOn = false; // para saber si el personaje esta colisionando con algo

    // ── Métodos abstractos que cada entidad debe implementar ──────
    public abstract void setDefaultValues(); // para establecer los valores iniciales de la entidad
    public abstract void update(); // para actualizar la lógica de la entidad (movimiento, animación, etc)
    public abstract void draw(java.awt.Graphics2D g2); // para dibujar la entidad en pantalla


}

