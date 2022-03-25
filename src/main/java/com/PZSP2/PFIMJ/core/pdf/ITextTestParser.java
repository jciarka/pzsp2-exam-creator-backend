package com.PZSP2.PFIMJ.core.pdf;

import com.github.rjeschke.txtmark.Processor;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;

import com.itextpdf.layout.property.TextAlignment;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.springframework.stereotype.Component;

import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;

@Component
public class ITextTestParser implements ITestParser {
    private final ByteArrayOutputStream stream;
    private final PdfWriter writer;
    private final PdfDocument pdfDoc;
    private final Document document;

    public ITextTestParser() {
        // Creating Writer
        stream = new ByteArrayOutputStream();
        writer = new PdfWriter(stream);

        // Creating a PdfDocument
        pdfDoc = new PdfDocument(writer);

        // Adding a new page
        pdfDoc.addNewPage();

        // Creating a Document
        document = new Document(pdfDoc);
    }

    @Override
    public void addTestHeader(String headerText) throws IOException {
        Text taskHeaderObject = new Text(headerText);
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        taskHeaderObject.setFont(font);

        Text nameAndIndex = new Text("\n\nImie i Nazwisko: ___________________________________________,    nr indeksu: _______");

        Paragraph para = new Paragraph(taskHeaderObject);
        para.setTextAlignment(TextAlignment.CENTER);
        para.add(nameAndIndex);

        document.add(para);
        addBlankLines(0);
    }

    @Override
    public void addTaskHeader(int taskNumber, int TaskPoints) throws IOException {
        String taskHeader = "zad " + taskNumber + ". (" + TaskPoints + "pkt)";
        // Creating text object
        Text taskHeaderObject = new Text(taskHeader);

        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        taskHeaderObject.setFont(font);
        Paragraph taskHeaderParagraph = new Paragraph(taskHeaderObject);
        taskHeaderParagraph.setMultipliedLeading(0);

        document.add(taskHeaderParagraph);
    }

    @Override
    public void addPlainTextParagraph(String text) {
        Text textObj = new Text(text);
        Paragraph para = new Paragraph(textObj);
        document.add(para);
    }

    @Override
    public void addBlankLines(int count) {
        for (int i = 0; i < count; i++) {
            Paragraph para = new Paragraph("\n");
            document.add(para);
        }
    }

    @Override
    public void addPageBreak(){
        AreaBreak aB = new AreaBreak();
        document.add(aB);
    }

    @Override
    public void addHtmlTextParagraph(String html) throws IOException {
        html = html.replaceFirst("<p>", "<p style=\"margin-top: 6px; margin-bottom: 6px;\">");
        html = "<span style=\"font-family: Helvetica; margin: 0px; padding: 0px;\">" + html + "</span>";
        System.out.println(html);
        InputStream in = new ByteArrayInputStream(html.getBytes());
        try {
            List<IElement> elements = HtmlConverter.convertToElements(in);
            for (IElement element : elements) {
                IBlockElement blElement = (IBlockElement) element;
                // blElement.setProperty(Property.LEADING, 0);
                document.add(blElement);
            } 
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie HTML jest nieprawodłowe!</strong>");
        }
        in.close();
    }

    @Override
    public void addMarkdownParagraph(String markdown) throws IOException  {
        try {
            String html = Processor.process(markdown);
            System.out.println(html);
            addHtmlTextParagraph(html);
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie Markdown jest nieprawodłowe! </br>" + markdown + "</strong>");
        }
    }

    @Override
    public void addLatexImage(String latex, int width) throws IOException  {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        
        try {
            processLatexToImage(stream, latex, "PNG", width * 5, Color.white, Color.black, true);  

            ImageData data = ImageDataFactory.createPng(stream.toByteArray());  // new ImageData(stream.toByteArray(), ImageType.BMP)
            Image image = new Image(data);    
            image.scaleToFit(width * 5, width * 5);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(image); 
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie LaTeX jest nieprawodłowe! </br>" + latex  + "</strong>");
        }

        stream.close();
    }

    @Override
    public void addChoosePlainTextParagraph(String text, List<String> answers) throws IOException {
        addChoosePlainTextParagraph(text, answers, new ArrayList<Integer>());
    }

    @Override
    public void addChoosePlainTextParagraph(String text, List<String> answers, List<Integer> markedAnswersIndexes) throws IOException {
        StringBuilder builder = new StringBuilder("<p>\n");
        andAnswersAsHtmlList(text, answers, markedAnswersIndexes, builder);
        builder.append("</p>");
        addHtmlTextParagraph(builder.toString());
    }

    private void andAnswersAsHtmlList(String text, List<String> answers, List<Integer> markedAnswersIndexes, StringBuilder builder) {
        builder.append(text + "\n");
        builder.append("<ol type=\"a\">\n");
        for (int i = 0; i < answers.size(); i++) {
            String answer = answers.get(i);
            if (markedAnswersIndexes.contains(i)){
                answer = "<strong>"  + answer + "</strong>";
            }
            answer = "<li>"  + answer + "</li>";
            builder.append(answer);
        }
        builder.append("</ol>");
    }

    @Override
    public void addChooseMarkdownParagraph(String text, List<String> answers) throws IOException {
        addChoosePlainTextParagraph(text, answers, new ArrayList<Integer>());
    }

    @Override
    public void addChooseMarkdownParagraph(String text, List<String> answers, List<Integer> markedAnswersIndexes) throws IOException {
        StringBuilder builder = new StringBuilder();
        andAnswersMarkdownList(text, answers, markedAnswersIndexes, builder);
        addMarkdownParagraph(builder.toString());
    }

    private void andAnswersMarkdownList(String text, List<String> answers, List<Integer> markedAnswersIndexes, StringBuilder builder) {
        builder.append(text);
        for (int i = 0; i < answers.size(); i++) {
            char letter = (char) ('a' + i%26 );
            String answer = answers.get(i);
            if (markedAnswersIndexes.contains(i)) {
                builder.append("\n - **" + letter + ". " + answer + "**");
            } else  {
                builder.append("\n - " + letter + ". "+ answer);
            }
        }
    }

    private void processLatexToImage(ByteArrayOutputStream stream, String latex, String format, float size, Color bg, Color fg, boolean transparency) {
        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);
        icon.setInsets(new Insets(10, 10, 10, 10));
        int w = icon.getIconWidth(), h = icon.getIconHeight();
        
        BufferedImage image = new BufferedImage(w, h, transparency ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        if (bg != null && !transparency) {
            g2.setColor(bg);
            g2.fillRect(0, 0, w, h);
        }
        
        icon.setForeground(fg);
        icon.paintIcon(null, g2, 0, 0);
        try {
            boolean success = ImageIO.write(image, format, stream);
            System.out.println(success);
        } catch (IOException ex) {
            System.err.println("I/O error : Cannot generate : " + latex);
        }
        g2.dispose();
    }

    @Override
    public ByteArrayOutputStream getStream() {
        return stream;
    }

    @Override
    public void Close() {
        document.close();
    }
}

// public void addMarkdownParagraph(String markdown) throws IOException  {
//     ByteArrayOutputStream stream = new ByteArrayOutputStream();

//     try {
//         Markdown2PdfConverter
//         .newConverter()
//         .readFrom(() -> markdown)
//         .writeTo(out -> {
//             stream.write(out, 0, out.length);
//         })
//         .doIt();
//     } catch (ConversionException e) {
//         return;
//     } catch (Markdown2PdfLogicException e) {
//         return;
//     }
//     // } catch (NullPointerException e) {
//     //     return;
//     // } catch (IndexOutOfBoundsException  e) {
//     //     return;
//     // }

//     PdfReader reader = new PdfReader(new ByteArrayInputStream(stream.toByteArray()));
//     stream.close();

//     PdfDocument markDownPdfDoc = new PdfDocument(reader);
//     Document markdownDoc = new Document(markDownPdfDoc);

//     for (int i = 1; i <= markDownPdfDoc.getNumberOfPages(); i++) {
//         PdfPage page = markDownPdfDoc.getPage(i);
//         page.copyTo(pdfDoc);
//     }
// }


// // Creating a PdfDocument
//

// // Adding a new page
// pdfDoc.addNewPage();

// // Creating a Document
// document = new Document(pdfDoc);