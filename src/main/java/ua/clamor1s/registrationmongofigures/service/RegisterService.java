package ua.clamor1s.registrationmongofigures.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterService {

    ResponseEntity<?> uploadZipToDatabase(MultipartFile file);

}
