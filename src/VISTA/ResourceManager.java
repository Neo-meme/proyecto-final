package VISTA;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * Gestor centralizado de recursos (imágenes) del juego.
 */
public class ResourceManager {
    
    private static final String RESOURCES_PATH = "resources";
    
    /**
     * Carga una imagen desde una ruta relativa a la carpeta resources/
     * @param relativePath Ruta relativa dentro de resources/ (ej: "cosas/titulo.png")
     * @return BufferedImage cargada, o null si hay error
     */
    public static BufferedImage loadImage(String relativePath) {
        BufferedImage image = null;
        
        try {
            // Intenta cargar desde el sistema de archivos (carpeta local del proyecto)
            File imageFile = new File(RESOURCES_PATH, relativePath);
            if (imageFile.exists()) {
                image = ImageIO.read(imageFile);
                return image;
            }
            
            // Si no existe localmente, intenta cargar desde el classpath (dentro del archivo compilado)
            String resourcePath = "/" + RESOURCES_PATH + "/" + relativePath;
            InputStream is = ResourceManager.class.getResourceAsStream(resourcePath);
            if (is != null) {
                image = ImageIO.read(is);
                return image;
            }
            
        } catch (Exception e) {
            System.out.println("✗ Error al cargar imagen: " + relativePath + " -> " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Obtiene la ruta completa de un recurso
     */
    public static String getResourcePath(String relativePath) {
        File resourceFile = new File(RESOURCES_PATH, relativePath);
        return resourceFile.getAbsolutePath();
    }
}