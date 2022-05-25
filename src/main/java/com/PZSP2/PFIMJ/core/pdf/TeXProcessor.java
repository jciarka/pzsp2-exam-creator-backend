package com.PZSP2.PFIMJ.core.pdf;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

import static org.scilab.forge.jlatexmath.TeXConstants.ALIGN_CENTER;


public class TeXProcessor {
    private static final String REGEX = "(^|[^\\\\]{1})\\$\\$([^\\s][^$]*[^\\s])?\\$\\$";
    private static final Pattern sPattern = Pattern.compile( REGEX );
    private static final Component sComponent = new Component() {};

    private static final String escapesREGEX = "\\\\\\$\\$";
    private static final Pattern escapesPattern = Pattern.compile( escapesREGEX );

    private String toSvg( final String tex ) {
        final var len = tex.length();
        assert len > 5;

        final var formula = new TeXFormula( tex.substring( 2, len - 2 ) );
        final var icon = formula.createTeXIcon( ALIGN_CENTER, 20f );
        icon.setInsets( new Insets( 10, 0, 0, 4 ) );
        final var width = icon.getIconWidth();
        final var height = icon.getIconHeight();
        final var graphics = new SVGGraphics2D( width, height );
        icon.paintIcon( sComponent, graphics, 0, 0 );
        return graphics.getSVGElement();
    }

    private String toImg(String latex) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TeXFormula formula = new TeXFormula(latex);
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 100f);
        icon.setInsets(new Insets(10, 10, 10, 10));
        int w = icon.getIconWidth(), h = icon.getIconHeight();

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();

        icon.setForeground(Color.black);
        icon.paintIcon(null, g2, 0, 0);

        String output = new String();
        try {
            boolean success = ImageIO.write(image, "PNG", stream);
            String encodedImage = Base64.getEncoder().encodeToString(stream.toByteArray());
            output = "<img style='display:inline;max-height:30px;position:relative;top:10px;' id='base64image'\n" +
                    "src='data:image/png;base64,"+ encodedImage +"' />";
            System.out.println(success);
        } catch (IOException ex) {
            System.err.println("I/O error : Cannot generate : " + latex);
        }
        g2.dispose();
        return output;
    }

    public String apply( String markdown ) {
        try {
            String withLatex = sPattern.matcher( markdown ).replaceAll(
                    ( result ) -> toImg( result.group() ) );
            return replaceAllNoRegex(withLatex, "\\$$", "$$");
        } catch( final Exception ex ) {
            return markdown;
        }
    }

    private String replaceAllNoRegex(final String text, final String phrase, final String newPhrase) {
        StringBuilder builder = new StringBuilder();

        int oldIndex = 0;
        int index = text.indexOf(phrase, oldIndex);

        while (index >= 0) {
            builder.append(text.substring(oldIndex, index) + newPhrase);

            index += phrase.length();
            oldIndex = index;
            index = text.indexOf(phrase, oldIndex);
        }

        builder.append(text.substring(oldIndex, text.length()));
        return builder.toString();
    }
}
