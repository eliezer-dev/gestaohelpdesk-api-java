package dev.eliezer.superticket.providers;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.Base64;

import static dev.eliezer.superticket.config.Upload.*;

public class DiskStorage {

    public static String saveFiles(MultipartFile file) throws IOException {

            byte[] bytes = file.getBytes();
            String filename = generateRandomHex(10, file.getOriginalFilename());

            Path pathTMP = Paths.get(TMP_FOLDER + filename);
            Path pathFinal = Paths.get(UPLOAD_FOLDER + filename);
            Files.write(pathTMP, bytes);
            Files.move(pathTMP, pathFinal);
            return filename;
    }

    public static String getFiles (String filename) throws IOException {
        Path path = Paths.get(UPLOAD_FOLDER + filename);
        File fileInBytes = new File(path.toString());
        return Base64.getEncoder().encodeToString(Files.readAllBytes(fileInBytes.toPath()));
    }



}
