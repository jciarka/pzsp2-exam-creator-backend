package com.PZSP2.PFIMJ.controllers;

import com.PZSP2.PFIMJ.core.pdf.ITestParser;
import com.PZSP2.PFIMJ.db.entities.Answer;
import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.models.tests.PrintableTest;
import com.PZSP2.PFIMJ.seed.SeedTests;
import com.PZSP2.PFIMJ.services.TestGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qkyrie.markdown2pdf.internal.exceptions.ConversionException;
import com.qkyrie.markdown2pdf.internal.exceptions.Markdown2PdfLogicException;

import javax.xml.crypto.Data;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

@RestController
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(path = "api/pdfGenerator")
public class PdfGeneratorController extends ControllerBase {

    @Autowired
    ITestParser parser;

    @Autowired
    TestGeneratorService genService;

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
                "Projekt zespo??owy 2",
                "kolokwium 1",
                new Date(System.currentTimeMillis()),
                SeedTests.GetExampleTest(5, 5, 5, 5, 5, 5),
                1,
                false,
                false,
                false,
                false
        );
        ByteArrayOutputStream stream = genService.GetStream();
        genService.Close();
        return stream.toByteArray();
    }

    private PrintableTest getExampleTest() {
        ExerciseVersion version1 = new ExerciseVersion(
            "Oto jest pytanie - wersia nr 1",
            Arrays.asList(new Answer("Odpowied?? 1", true),
                    new Answer("Odpowied?? 2", false),
                    new Answer("Odpowied?? 3", false),
                    new Answer("odpowied?? 4", true)));

        ExerciseVersion version2 = new ExerciseVersion(
                "Oto jest pytanie - wersia nr 2",
                Arrays.asList(new Answer("Odpowied?? 1", true),
                        new Answer("Odpowied?? 2", false),
                        new Answer("Odpowied?? 3", false),
                        new Answer("odpowied?? 4", true)));

        Exercise exercise = new Exercise();
        exercise.setVersions(Arrays.asList(
                version1, version2)
        );

        exercise.setTitle("Zadanie z geometrii");
        exercise.setType("CHOOSEONEPLAINTEXT");

        PrintableTest test = new PrintableTest();
        test.setTitle("Test 1");
        test.setDescription("Kol. 1");

        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(exercise);

        test.setExercises(exerciseList);

        return test;
    }

    @GetMapping(
            value = "test2",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public @ResponseBody byte[] getEmptyDocument() throws IOException, ConversionException, Markdown2PdfLogicException {
        parser.addTestHeader("Projekt zespo??owy 2 - kolokwium nr 1 - 11.03.2022");

        parser.addTaskHeader(1, 5);
        parser.addHtmlTextParagraph("<p> <strong> Paragraf jako tekst HTML </strong> </br> Paragraph ??????tor????owo??ci?? (??????tor????owo????) i r????owo??????to??ci?? (r????owo??????to????).  </p>");
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
                "***T?? dr?? chce je??li t??dy tw??r***\n  " +
                "??????tor????owo??ci?? (??????tor????owo????) i r????owo??????to??ci?? (r????owo??????to????).  \n csdascdw \n");


        parser.addTaskHeader(4, 5);
        String Text = "Wybierz prawdziwe odpowiedzi";
        List<String> answers = Arrays.asList(new String[]{"To jest odpowied?? nr 1", "To jest *odpowied??* nr 2", "To jest odpowied?? nr 3", "To jest odpowied?? nr 4", "To jest odpowied?? nr 5"});
        List<Integer> trueAnsers = Arrays.asList(new Integer[]{1, 3});
        parser.addChoosePlainTextParagraph(Text, answers, trueAnsers);

        parser.addTaskHeader(5, 5);
        parser.addChooseMarkdownParagraph(Text, answers, trueAnsers);

        parser.addTaskHeader(5, 5);
        parser.addHtmlTextParagraph("<p>Paragraph ??????tor????owo??ci?? (??????tor????owo????) i r????owo??????to??ci?? (r????owo??????to????).  </p>");
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
                "4. Fourth item  \n ***T?? dr?? chce je??li t??dy tw??r***\n  ??????tor????owo??ci?? (??????tor????owo????) i r????owo??????to??ci?? (r????owo??????to????).  \n csdascdw \n");

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
