package ua.clamor1s.registrationmongofigures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.clamor1s.registrationmongofigures.repository.RegisterRepository;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterRepository repository;

    @Override
    public ResponseEntity<?> uploadZipToDatabase(MultipartFile file) {
        repository.clearDatabase();
        String fileName = file.getOriginalFilename();
        try {
            InputStream is = getJsonFileDataStreamFromZipStream(file.getInputStream());
            repository.fillDatabaseFromInputStream(is);
        }
        catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Incorrect zip file.");
        }

        return ResponseEntity.ok("oks... file: %s".formatted(fileName));
    }



    private InputStream getJsonFileDataStreamFromZipStream(InputStream is) throws IOException {
        ZipInputStream zis = new ZipInputStream(is);

        for (ZipEntry zipEntry; (zipEntry = zis.getNextEntry()) != null;) {
            if (zipEntry.isDirectory() || !zipEntry.getName().equals("pep.json")) {
                continue;
            }
            // here we will see data exactly from pep.json file (if there are more
            // than one files in zip archive)

            return zis;
        }

        throw new IOException("File pep.json not found.");
    }



}
