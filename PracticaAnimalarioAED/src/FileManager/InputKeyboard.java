package FileManager;

import LabMouses.*;

import java.io.File;
import java.io.FileInputStream; //lee bytes de un fichero.
import java.io.FileOutputStream; //escribe bytes en un fichero.
import java.io.IOException;

public class InputKeyboard {

    static int llamadas; // cuántas veces se llama a read()
    public static  void main(String[] args) throws IOException {
        // Fichero de prueba de 1 MiB
        try (FileOutputStream out = new FileOutputStream("grande.bin")) {//try with resuerces
            byte[] mega = new byte[1024 * 1024];
            for (int i = 0; i < mega.length; i++) mega[i] = (byte) i; //que no sean todos ceros
            out.write(mega);//escribe
        }
        llamadas = 0;
        copiarConBuffer("grande.bin", "copia2.bin");
        System.out.println("Con buffer : " + llamadas + " llamadas a read()");
        System.out.println("Tamaño: " + new File("copia1.bin").length());
    }

    /** Copia por bloques: read(byte[]) devuelve cuántos bytes ha leído. */
    static void copiarConBuffer(String origen, String destino) throws IOException {
        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[8192];
            //Es 8 KiB = 2¹³. Los discos y el sistema operativo trabajan en bloques y páginas de 4 KiB o múltiplos.
            // Un buffer que es potencia de 2 y múltiplo de eso encaja bien con cómo se leen los datos por debajo.
            int n;
            while ((n = in.read(buffer)) != -1) {
                llamadas++;
                out.write(buffer, 0, n); // ¡solo los n bytes leídos!
            }
        }
    }

}
