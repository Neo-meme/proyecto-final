package src.MODELO.object;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestorClasificaciones {

    public static void guardarResultado(
            int puntos,
            int tiempo){

        try(PrintWriter pw =
            new PrintWriter(
                new FileWriter(
                    "clasificaciones.txt",
                    true))){

            pw.println(
                puntos + ";" + tiempo
            );

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}