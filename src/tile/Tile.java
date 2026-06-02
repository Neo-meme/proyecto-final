package src.tile;

import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    public boolean tunnel    = false; // true si es el túnel lateral
}

//Simple pero importante. Cada tile tiene una imagen y un booleano que dice si es una pared o no.