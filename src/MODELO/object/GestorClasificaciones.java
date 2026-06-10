package MODELO.object;

import java.io.*;
import java.util.*;

public class GestorClasificaciones {

    private static final String ARCHIVO   = "clasificaciones.txt";
    private static final int    MAX_SCORES = 10;

    public static void guardarResultado(int puntos, int tiempo) {

        // 1. Leer registros existentes
        List<int[]> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 2) {
                    registros.add(new int[]{
                        Integer.parseInt(datos[0].trim()),
                        Integer.parseInt(datos[1].trim())
                    });
                }
            }
        } catch (Exception e) {
            // archivo no existe aún, se crea nuevo
        }

        // 2. Agregar el nuevo resultado
        registros.add(new int[]{puntos, tiempo});

        // 3. Ordenar de mayor a menor puntaje
        registros.sort((a, b) -> b[0] - a[0]);

        // 4. Conservar solo los 10 mejores
        if (registros.size() > MAX_SCORES) {
            registros = registros.subList(0, MAX_SCORES);
        }

        // 5. Reescribir el archivo completo
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, false))) {
            for (int[] r : registros) {
                pw.println(r[0] + ";" + r[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
