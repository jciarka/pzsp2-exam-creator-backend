package com.PZSP2.PFIMJ.seed;

import com.PZSP2.PFIMJ.core.tests.ExerciseTypes;
import com.PZSP2.PFIMJ.db.entities.Answer;
import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.PZSP2.PFIMJ.db.entities.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SeedTests {
    public  static Test GetExampleTest(int versionsNum, int PlainTextExNum, int MarkdownExNum, int HtmlExNum, int ChoosePlainExNum, int ChooseMarkdownNum)
    {
        Test test = new Test();
        test.setTitle("Test 1");
        test.setDescription("Kol. 1");
        test.setExercises(new ArrayList<>());

        for (int i = 0; i < PlainTextExNum; i++) {
            test.getExercises().add(GetExamplePlainTextExercise("Przykładowe pytanie nr " + (i + 1) + " - typ PLAINTEXT",  versionsNum));
        }

        for (int i = 0; i < PlainTextExNum; i++) {
            test.getExercises().add(GetExampleMarkdownExercise("Przykładowe **pytanie** nr " + (i + 1) + " - $$(E=mc^2)$$，$$x_{1,2} = \\frac{-b \\pm \\sqrt{b^2-4ac}}{2b}.$$",  versionsNum));
        }

        for (int i = 0; i < PlainTextExNum; i++) {
            test.getExercises().add(GetExampleHtmlExercise("Przykładowe <strong>pytanie</strong> nr " + (i + 1) + " - typ HTML",  versionsNum));
        }

        for (int i = 0; i < PlainTextExNum; i++) {
            test.getExercises().add(GetExampleChoosePlainTextExercise(
                    "Przykładowe pytanie nr " + (i + 1) + " - typ CHOSE_PLAINTEXT",
                    "typ CHOSE_PLAINTEXT",  versionsNum, 5, 50
            ));
        }

        for (int i = 0; i < PlainTextExNum; i++) {
            test.getExercises().add(GetExampleChooseMarkdownExercise(
                "Przykładowe **pytanie** nr " + (i + 1) + " - $$typ = CHOOSE_MARKDOWN$$",
                "typ CHOSE_PLAINTEXT",  versionsNum, 5, 50
            ));
        }

        return test;
    }

    public static Exercise GetExamplePlainTextExercise(String text, int versionsNum) {
        return getExampleTextExercise(ExerciseTypes.PLAINTEXT, text, versionsNum);
    }

    public static Exercise GetExampleMarkdownExercise(String text, int versionsNum) {
        return getExampleTextExercise(ExerciseTypes.MARKDOWN, text, versionsNum);
    }

    public static Exercise GetExampleHtmlExercise(String text, int versionsNum) {
        return getExampleTextExercise(ExerciseTypes.HTML, text, versionsNum);
    }

    public static Exercise getExampleTextExercise(String type, String text, int versionsNum) {
        Exercise ex = new Exercise();
        ex.setTitle(type);
        ex.setType(type);

        ex.setVersions(new ArrayList<>());
        for (int i = 0; i < versionsNum; i++) {
            ExerciseVersion ver = new ExerciseVersion(
                    text + " - wersja " + (i + 1),
                    null
            );
            ex.getVersions().add(ver);
        }
        return ex;
    }

    public static Exercise GetExampleChoosePlainTextExercise(String text, String answerText, int versionsNum, int answerNum, int truePrc) {
        return  getExampleChooseExercise(ExerciseTypes.CHOOSE_PLAINTEXT, text, answerText, versionsNum, answerNum, truePrc);
    }

    public static Exercise GetExampleChooseMarkdownExercise(String text, String answerText, int versionsNum, int answerNum, int truePrc) {
        return  getExampleChooseExercise(ExerciseTypes.CHOOSE_MARKDOWN, text, answerText, versionsNum, answerNum, truePrc);
    }

    private static Exercise getExampleChooseExercise(String type, String text, String answerText, int versionsNum, int answerNum, int truePrc) {
        Exercise ex = new Exercise();
        ex.setTitle(text);
        ex.setType(type);

        ex.setVersions(new ArrayList<>());
        for (int i = 0; i < versionsNum; i++) {
            ExerciseVersion ver = new ExerciseVersion(
                    text + " - wersja " + (i + 1),
                    new ArrayList<>()
            );

            for (int j = 0; j < answerNum; j++) {
                Answer ans = new Answer("Odpowiedź " + (j + 1) + " - " + answerText, getTrueOrFalse(truePrc));
                ver.getAnswers().add(ans);
            }
            ex.getVersions().add(ver);
        }
        return ex;
    }

    private static boolean getTrueOrFalse(int probability){
        Random generator = new Random();
        return  generator.nextInt(100) < probability ;
    }
}
