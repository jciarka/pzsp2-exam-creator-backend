package com.PZSP2.PFIMJ.services;

import com.PZSP2.PFIMJ.core.pdf.ITestParser;
import com.PZSP2.PFIMJ.core.tests.ExerciseTypes;
import com.PZSP2.PFIMJ.db.entities.Answer;
import com.PZSP2.PFIMJ.db.entities.Exercise;
import com.PZSP2.PFIMJ.db.entities.ExerciseVersion;
import com.PZSP2.PFIMJ.db.entities.Test;
import com.PZSP2.PFIMJ.models.tests.PrintableTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestGeneratorService {
    @Autowired
    private ITestParser parser;

    public void AddTest(
            PrintableTest test,
            Date testDate,
            Integer version,
            boolean mixVerions,
            boolean mixExercises,
            boolean mixChooseAnswers,
            boolean markCorrectAnswers
    ) throws IOException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        parser.addTestHeader(test.getSubjectName() + " - " + test.getTitle() + " - " + formatter.format(testDate));
        parser.addBlankLines(1);

        List<Exercise> exercises = test.getExercises().stream().collect(Collectors.toList());
        if (mixExercises) {
            Collections.shuffle(exercises);
        }

        for (int i = 0; i < exercises.size(); i++) {
            addExercise(i + 1, exercises.get(i), version, mixVerions, mixChooseAnswers, markCorrectAnswers);
        }

        parser.addPageBreak();
    }

    private void addExercise(int number, Exercise exercise, Integer version, boolean mixVerions, boolean mixChooseAnswers, boolean markCorrectAnswers) throws IOException {
        if (exercise.getPoints() != null) {
            parser.addTaskHeader(number, exercise.getPoints());
        } else {
            parser.addTaskHeader(number);
        }

        int exerciseVersion = getVersion(version, exercise.getVersions().size(), mixVerions);
        switch (exercise.getType()){
            case ExerciseTypes.PLAINTEXT:
                parser.addPlainTextParagraph(exercise.getVersions().get(exerciseVersion).getText());
                break;
            case ExerciseTypes.HTML:
                parser.addHtmlTextParagraph(exercise.getVersions().get(exerciseVersion).getText());
                break;
            case ExerciseTypes.MARKDOWN:
                parser.addMarkdownParagraph(exercise.getVersions().get(exerciseVersion).getText());
                break;
            case ExerciseTypes.CHOOSE_MARKDOWN:
            case ExerciseTypes.CHOOSE_PLAINTEXT:
                addChoose(exercise, exerciseVersion, mixChooseAnswers, markCorrectAnswers);
                break;
        }
    }

    private int getVersion(Integer versionNumber, Integer versionsCount, boolean mixVersions){
        Random generator = new Random();
        return !mixVersions ? versionNumber % versionsCount : generator.nextInt(versionsCount);
    }

    private void addChoose(Exercise exercise, Integer version, boolean mixChooseAnswers, boolean markCorrectAnswers) throws IOException {
        List<Answer> answers = exercise.getVersions().get(version).getAnswers().stream().collect(Collectors.toList());

        if (mixChooseAnswers)
        {
            Collections.shuffle(answers);
        }

        List<Integer> correct = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            if (answers.get(i).isPositive() && markCorrectAnswers) {
                correct.add(i);
            }
        }

        switch (exercise.getType())
        {
            case ExerciseTypes.CHOOSE_PLAINTEXT:
                parser.addChoosePlainTextParagraph(
                        exercise.getVersions().get(version).getText(),
                        answers.stream().map(x -> x.getText()).collect(Collectors.toList()),
                        correct);
                break;
            case ExerciseTypes.CHOOSE_MARKDOWN:
                parser.addChooseMarkdownParagraph(
                        exercise.getVersions().get(version).getText(),
                        answers.stream().map(x -> x.getText()).collect(Collectors.toList()),
                        correct
                );
                break;
        }
    }

    public ByteArrayOutputStream GetStream()
    {
        return parser.getStream();
    }

    public  void Close() {
        parser.Close();
    }


}
