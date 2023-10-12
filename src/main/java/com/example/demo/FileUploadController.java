package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

    @Autowired
    private FileAnalysisService fileAnalysisService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileAnalysisService.analyzeExcelFile(file);
            return "File uploaded and analyzed successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error analyzing the file.";
        }
    }
}

