package ua.clamor1s.registrationmongofigures.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class RegisterServiceImpl implements RegisterService {


    @Override
    public ResponseEntity<?> uploadZipToDatabase(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            getJsonFileFromZipStream(file.getInputStream());
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Incorrect zip file.");
        }

        return ResponseEntity.ok("oks... file: %s".formatted(fileName));
    }

    private void getJsonFileFromZipStream(InputStream is) throws IOException {
        ZipInputStream zis = new ZipInputStream(is);

        for (ZipEntry zipEntry; (zipEntry = zis.getNextEntry()) != null;) {
            if (zipEntry.isDirectory() || !zipEntry.getName().equals("pep.json")) {
                continue;
            }
            // here we will see data exactly from pep.json file (if there are more
            // than one file in zip archive)
            Scanner sc = new Scanner(zis);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            return;
        }

        throw new IOException("File pep.json not found.");
    }



}
