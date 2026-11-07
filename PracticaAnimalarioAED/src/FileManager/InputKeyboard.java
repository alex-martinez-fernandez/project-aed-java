package FileManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;   // lee bytes de un fichero
import java.io.FileOutputStream;  // escribe bytes en un fichero
import java.io.IOException;

public class InputKeyboard {

    static int llamadas; // cuántas veces se llama a read()

    public static void main(String[] args) throws IOException {
        // Fichero de prueba de 1 MiB
        try (FileOutputStream out = new FileOutputStream("grande.bin")) { // try-with-resources
            byte[] mega = new byte[1024 * 1024];
            for (int i = 0; i < mega.length; i++) mega[i] = (byte) i; // que no sean todos ceros
            out.write(mega); // escribe
        }

        // 1) Byte a byte, sin buffer
        llamadas = 0;
        long t0 = System.nanoTime();
        copiarByteAByte("grande.bin", "copia1.bin");
        long t1 = System.nanoTime();
        double seg1 = (t1 - t0) / 1_000_000_000.0;
        System.out.printf("Byte a byte    : %9d llamadas  %.4f s%n", llamadas, seg1);

        // 2) Buffer manual de 8192
        llamadas = 0;
        t0 = System.nanoTime();
        copiarConBuffer("grande.bin", "copia2.bin");
        t1 = System.nanoTime();
        double seg2 = (t1 - t0) / 1_000_000_000.0;
        System.out.printf("Buffer manual  : %9d llamadas  %.4f s%n", llamadas, seg2);

        // 3) BufferedInputStream / BufferedOutputStream
        llamadas = 0;
        t0 = System.nanoTime();
        copiarConBuffered("grande.bin", "copia3.bin");
        t1 = System.nanoTime();
        double seg3 = (t1 - t0) / 1_000_000_000.0;
        System.out.printf("Buffered...    : %9d llamadas  %.4f s%n", llamadas, seg3);

        // Comparación
        System.out.printf("Buffer manual es %.0f veces más rápido que byte a byte%n", seg1 / seg2);
        System.out.printf("Buffered es %.0f veces más rápido que byte a byte%n", seg1 / seg3);

        // Comprobar que las tres copias son del mismo tamaño
        System.out.println("Tamaños: " + new File("copia1.bin").length() + ", "
                + new File("copia2.bin").length() + ", "
                + new File("copia3.bin").length() + " bytes");
    }

    /** Copia byte a byte: una llamada al disco por cada byte (lento). */
    static void copiarByteAByte(String origen, String destino) throws IOException {
        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            int b; // int, no byte: -1 = fin del fichero
            while ((b = in.read()) != -1) {
                llamadas++;
                out.write(b);
            }
        }
    }

    /** Copia por bloques: read(byte[]) devuelve cuántos bytes ha leído. */
    static void copiarConBuffer(String origen, String destino) throws IOException {
        try (FileInputStream in = new FileInputStream(origen);
             FileOutputStream out = new FileOutputStream(destino)) {
            byte[] buffer = new byte[8192];
            // Es 8 KiB = 2¹³. Los discos y el sistema operativo trabajan en bloques y páginas de 4 KiB o múltiplos.
            // Un buffer que es potencia de 2 y múltiplo de eso encaja bien con cómo se leen los datos por debajo.
            int n;
            while ((n = in.read(buffer)) != -1) {
                llamadas++;
                out.write(buffer, 0, n); // ¡solo los n bytes leídos!  no out.write(buffer)
                // --> la última lectura nunca llena el array entero: meteríamos basura
            }
        }
    }

    /** Byte a byte, pero con un buffer interno de 8192 que gestiona Java. */
    static void copiarConBuffered(String origen, String destino) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(origen));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destino))) {
            int b;
            while ((b = in.read()) != -1) {
                llamadas++;
                out.write(b); // va al buffer; close() hace flush() al final
            }
        }
    }
}