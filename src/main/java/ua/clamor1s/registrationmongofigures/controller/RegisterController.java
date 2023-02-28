package ua.clamor1s.registrationmongofigures.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.clamor1s.registrationmongofigures.service.RegisterService;

@RestController
@RequestMapping("/api/figures")
public class RegisterController {

    @Autowired
    private RegisterService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadZipFile(@RequestParam("file") MultipartFile file) {
        return service.uploadZipToDatabase(file);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByFullName(@RequestParam("full_name") String fullName) {
        return service.searchByFullName(fullName);
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getMostCommonNames() {
        return service.getPublicMostCommonNames();
    }

}
