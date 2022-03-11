package com.PZSP2.PFIMJ.core.pdf;

import com.github.rjeschke.txtmark.Configuration;
import com.github.rjeschke.txtmark.Processor;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.codec.Base64.OutputStream;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.image.ImageType;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;

import org.hibernate.result.Output;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;

import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class ITextTestParser {
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

    public void addPlainTextParagraph(String text) {
        Paragraph para = new Paragraph(text);
        document.add(para);
    }

    public void addHtmlTextParagraph(String html) throws IOException {
        try {
            html = "<span style=\"font-family: Helvetica; margin: 0px;\">" + html + "</span>";
            InputStream in = new ByteArrayInputStream(html.getBytes());
            List<IElement> elements = HtmlConverter.convertToElements(in);
            for (IElement element : elements) {
                document.add((IBlockElement)element);
            } 
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie LaTeX jest nieprawodłowe!</strong>");
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

    public void addMarkdownParagraph(String markdown) throws IOException  {
        try {
            String html = Processor.process(markdown);
            addHtmlTextParagraph(html);
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie LaTeX jest nieprawodłowe! </br>" + markdown + "</strong>");
        }
    }

    public void addLatexImage(String latex, int width) throws IOException  {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            processLatexToImage(stream, latex, "PNG", width * 5, Color.white, Color.black, true);  

            ImageData data = ImageDataFactory.createPng(stream.toByteArray());  // new ImageData(stream.toByteArray(), ImageType.BMP)
            Image image = new Image(data);    
            image.scaleToFit(width * 5, width * 5);
            image.setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(image); 
        } catch (Exception e) {
            addHtmlTextParagraph("<strong style=\"color: red;\"> UWAGA! Wyrażenie LaTeX jest nieprawodłowe! </br>" + latex  + "</strong>");
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

    public ByteArrayOutputStream getStream() {
        return stream;
    }

    public void Close() {
        document.close();
    }
}
