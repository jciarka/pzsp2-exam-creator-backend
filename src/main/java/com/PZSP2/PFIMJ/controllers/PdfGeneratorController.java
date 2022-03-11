package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.core.pdf.ITextTestParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qkyrie.markdown2pdf.internal.exceptions.ConversionException;
import com.qkyrie.markdown2pdf.internal.exceptions.Markdown2PdfLogicException;

import java.io.*;

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
        byte[] data = in.readAllBytes();
        in.close();
        return data;
    }

    @GetMapping(
            value = "test2",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getEmptyDocument() throws IOException, ConversionException, Markdown2PdfLogicException {

        ITextTestParser parser = new ITextTestParser();
        parser.addHtmlTextParagraph("<h1>Paragraph</h1> <span>Paragraph żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  </span>");

        parser.addMarkdownParagraph("***Tą drę chce jeśli tędy twór***\n  żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  \n csdascdw ");

        parser.addLatexImage(
            "\\begin{align} y &=& x^4 + 4 &=& (x^2+2)^2-4x^2 &\\le& (x^2+2)^2 \\end{align}",
            60
        );
        
        parser.addPlainTextParagraph("Paragraph");


        parser.Close();

        ByteArrayOutputStream stream = parser.getStream();
        return stream.toByteArray();
    }
}
