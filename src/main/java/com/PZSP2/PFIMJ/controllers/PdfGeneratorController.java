package com.PZSP2.PFIMJ.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(path = "api/pdfGenerator")
public class PdfGeneratorController extends ControllerBase {

    @GetMapping(
            value = "test",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        File file = new ClassPathResource("static/test.pdf").getFile();
        InputStream in = new FileInputStream(file);
        return in.readAllBytes();
    }
}
