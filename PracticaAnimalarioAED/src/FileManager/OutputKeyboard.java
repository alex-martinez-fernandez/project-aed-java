package FileManager;
import LabMouses.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class OutputKeyboard {
    static int llamadas; // cuántas veces llamamos nosotros a read()

    public static void main(String[] args) throws IOException {
        // Fichero de prueba de 1 MiB
        try (FileOutputStream out = new FileOutputStream("grande.bin")) {
            byte[] mega = new byte[1024 * 1024];
            for (int i = 0; i < mega.length; i++) mega[i] = (byte) i;
            out.write(mega);
        }

        llamadas = 0;
        long t0 = System.nanoTime();
        copiarConBuffered("grande.bin", "copia3.bin");
        long t1 = System.nanoTime();

        System.out.println("Llamadas a read(): " + llamadas);
        System.out.println("Tiempo: " + (t1 - t0) / 1_000_000 + " ms");
        System.out.println("Tamaño: " + new File("copia3.bin").length() + " bytes");
    }

    /** Copia byte a byte, pero a través de streams con buffer interno. */
    static void copiarConBuffered(String origen, String destino) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(origen));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destino))) {
            int b;
            while ((b = in.read()) != -1) {
                llamadas++;
                out.write(b);
            }
        }
    }
}
