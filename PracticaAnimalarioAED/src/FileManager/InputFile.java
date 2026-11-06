package FileManager;
import LabMouses.Population;
import java.io.File;
import java.io.IOException;

public class InputFile {
    public static void main(String[] args) {
        File carpeta = new File("carpeta");
        File newfile = new File(carpeta,"datos");

        System.out.println("fichero existe: " + newfile.exists());
        System.out.println("carpeta creada: " + carpeta.mkdirs());
        System.out.println("existe carpeta: " + carpeta.exists());
        try {
            System.out.println("crear file: " + newfile.createNewFile());
        } catch (IOException e) {
            System.out.println("Error creating the file: " + e.getMessage());
        }
        System.out.println("fichero creado: " + newfile.exists());
        System.out.println("nombre del fichero: " + newfile.getName());
        System.out.println("fichero creado: " + newfile.canRead());
        System.out.println("fichero path: " + newfile.getAbsolutePath());
        System.out.println("fichero : " + newfile.length());
        System.out.println("renombrado: " + newfile.renameTo(new File(carpeta, "ejemplo.html")));
        System.out.println("fichero renombrado: " + newfile.getAbsolutePath());
        System.out.println("fichero renombrado: " + newfile.getName());
    }


}
