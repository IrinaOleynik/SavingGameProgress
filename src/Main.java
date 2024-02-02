import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(10_000, 100, 1, 5.12);
        GameProgress progress2 = new GameProgress(9_000, 120, 2, 10.43);
        GameProgress progress3 = new GameProgress(100, 2, 90, 100.56);

        List<String> files = Arrays.asList("C://Users/User/Documents/Games/savegames/save1.dat",
                "C://Users/User/Documents/Games/savegames/save2.dat",
                "C://Users/User/Documents/Games/savegames/save3.dat");

        saveGame(progress1, files.get(0));
        saveGame(progress2, files.get(1));
        saveGame(progress3, files.get(2));

        zipFiles("C://Users/User/Documents/Games/savegames/zip.zip", files);
    }

    public static void saveGame(GameProgress progress, String file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String fullFilePath, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(fullFilePath))) {
            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                File saveFile = new File(file);
                ZipEntry entry = new ZipEntry(saveFile.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                fis.close();
                zout.closeEntry();
                saveFile.delete();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}