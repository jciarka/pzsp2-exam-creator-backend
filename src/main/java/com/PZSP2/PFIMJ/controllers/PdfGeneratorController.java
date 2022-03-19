package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.core.pdf.ITestParser;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ITestParser parser;

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
        parser.addTestHeader("Projekt zespołowy 2 - kolokwium nr 1 - 11.03.2022");

        parser.addTaskHeader(1, 5);
        parser.addHtmlTextParagraph("<p> <strong> Paragraf jako tekst HTML </strong> </br> Paragraph żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  </p>");
        parser.addLatexImage(
                "\\begin{align} y &=& x^4 + 4 &=& (x^2+2)^2-4x^2 &\\le& (x^2+2)^2 \\end{align}",
                60
        );

        parser.addPageBreak();

        parser.addTaskHeader(2, 5);
        parser.addPlainTextParagraph("Paragraf jako tekst");

        parser.addTaskHeader(3, 5);
        parser.addMarkdownParagraph("To jest paragraf typu markdown \n" +
                "   - First item\n" +
                "   - Second item\n" +
                "   - Third item\n" +
                "   - Fourth item  \n " +
                "***Tą drę chce jeśli tędy twór***\n  " +
                "żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  \n csdascdw \n");


        parser.addTaskHeader(5, 5);
        parser.addHtmlTextParagraph("<p>Paragraph żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  </p>");
        parser.addLatexImage(
                "\\begin{align} y &=& x^4 + 4 &=& (x^2+2)^2-4x^2 &\\le& (x^2+2)^2 \\end{align}",
                60
        );

        parser.addBlankLines(15);

        parser.addTaskHeader(6, 5);
        parser.addPlainTextParagraph("Paragraph");
        parser.addLatexImage(
                "\\begin{align} y &=& x^4 + 4 &=& (x^2+2)^2-4x^2 &\\le& (x^2+2)^2 \\end{align}",
                60
        );

        parser.addBlankLines(10);

        parser.addTaskHeader(7, 5);
        parser.addMarkdownParagraph("casdcads ads\n1. First item\n" +
                "2. Second item\n" +
                "3. Third item\n" +
                "    1. Indented item\n" +
                "    2. Indented item\n" +
                "4. Fourth item  \n ***Tą drę chce jeśli tędy twór***\n  żółtoróżowością (żółtoróżowość) i różowożółtością (różowożółtość).  \n csdascdw \n");

        parser.addBlankLines(10);

        parser.addTaskHeader(8, 5);
        parser.addLatexImage(
                "\\begin{align} y &=& x^4 + 4 &=& (x^2+2)^2-4x^2 &\\le& (x^2+2)^2 \\end{align}",
                60
        );

        parser.addBlankLines(10);

        parser.Close();

        ByteArrayOutputStream stream = parser.getStream();
        return stream.toByteArray();
    }
}