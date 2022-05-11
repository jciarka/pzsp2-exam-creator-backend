package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.core.pdf.ITestParser;
import com.PZSP2.PFIMJ.db.entities.*;
import com.PZSP2.PFIMJ.models.tests.PrintableTest;
import com.PZSP2.PFIMJ.repositories.ISubjectsRepository;
import com.PZSP2.PFIMJ.repositories.ITestsRepository;
import com.PZSP2.PFIMJ.seed.SeedTests;
import com.PZSP2.PFIMJ.services.TestGeneratorService;
import com.PZSP2.PFIMJ.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.qkyrie.markdown2pdf.internal.exceptions.ConversionException;
import com.qkyrie.markdown2pdf.internal.exceptions.Markdown2PdfLogicException;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(path = "api/pdfGenerator")
public class PdfGeneratorController extends ControllerBase {

    @Autowired
    ITestParser parser;

    @Autowired
    TestService tService;

    @Autowired
    TestGeneratorService genService;

    @GetMapping(
        value = "{testId}",
        produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] generatePdfFromTest(
            @PathVariable Long testId,
            @RequestParam(name = "version", required = false, defaultValue = "1") final Long version,
            @RequestParam(name = "mixExercises", required = false, defaultValue = "false") final  Boolean mixExercises,
            @RequestParam(name = "mixChooseAnswers", required = false, defaultValue = "false") final  Boolean mixChooseAnswers,
            @RequestParam(name = "appendWithSolved", required = false, defaultValue = "false") final  Boolean appendWithSolved
        ) throws IOException, ConversionException, Markdown2PdfLogicException, ResponseStatusException {

        PrintableTest test = tService.getPrintableTest(testId);

        if (test == null)
            throw new ResponseStatusException(NOT_FOUND);

        genService.AddTest(
            test,
            new Date(System.currentTimeMillis()),
            version != null ? version.intValue() : null,
            version != null,
            mixExercises,
            mixChooseAnswers,
            false
        );

        if (appendWithSolved) {
            genService.AddTest(
                test,
                new Date(System.currentTimeMillis()),
                version.intValue(),
                version != null,
                mixExercises,
                mixChooseAnswers,
                true
            );
        }

        ByteArrayOutputStream stream = genService.GetStream();
        genService.Close();
        return stream.toByteArray();
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


        parser.addTaskHeader(4, 5);
        String Text = "Wybierz prawdziwe odpowiedzi";
        List<String> answers = Arrays.asList(new String[]{"To jest odpowiedź nr 1", "To jest *odpowiedź* nr 2", "To jest odpowiedź nr 3", "To jest odpowiedź nr 4", "To jest odpowiedź nr 5"});
        List<Integer> trueAnsers = Arrays.asList(new Integer[]{1, 3});
        parser.addChoosePlainTextParagraph(Text, answers, trueAnsers);

        parser.addTaskHeader(5, 5);
        parser.addChooseMarkdownParagraph(Text, answers, trueAnsers);

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
            value = "test3",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getTestDocument() throws IOException, ConversionException, Markdown2PdfLogicException {
        genService.AddTest(
                SeedTests.GetExampleTest(5, 5, 5, 5, 5, 5),
                new Date(System.currentTimeMillis()),
                1,
                false,
                false,
                false,
                false
        );

        genService.AddTest(
                SeedTests.GetExampleTest(5, 5, 5, 5, 5, 5),
                new Date(System.currentTimeMillis()),
                1,
                false,
                false,
                false,
                true
        );
        ByteArrayOutputStream stream = genService.GetStream();
        genService.Close();
        return stream.toByteArray();
    }
}
