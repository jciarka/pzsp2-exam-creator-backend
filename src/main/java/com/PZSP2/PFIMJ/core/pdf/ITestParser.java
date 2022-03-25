package com.PZSP2.PFIMJ.core.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ITestParser {
    void addTestHeader(String headerText) throws IOException;

    void addTaskHeader(int taskNumber, int TaskPoints) throws IOException;

    void addPlainTextParagraph(String text);

    void addBlankLines(int count);

    void addPageBreak();

    void addHtmlTextParagraph(String html) throws IOException;

    void addMarkdownParagraph(String markdown) throws IOException;

    void addLatexImage(String latex, int width) throws IOException;

    void  addChoosePlainTextParagraph(String text, List<String> answers) throws IOException;

    void  addChoosePlainTextParagraph(String text, List<String> answers, List<Integer> markedAnswersIndexes) throws IOException;

    void  addChooseMarkdownParagraph(String text, List<String> answers) throws IOException;

    void  addChooseMarkdownParagraph(String text, List<String> answers, List<Integer> markedAnswersIndexes) throws IOException;

    ByteArrayOutputStream getStream();

    void Close();
}
