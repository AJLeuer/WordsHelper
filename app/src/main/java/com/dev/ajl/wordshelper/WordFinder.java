package com.dev.ajl.wordshelper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.* ;
import java.lang.String ;
import java.lang.Thread ;
import java.util.ArrayList ;
import java.util.Arrays;
import java.util.Scanner ;
import java.io.FileInputStream ;

import com.dev.ajl.wordshelper.WordInput.* ;


/**
 * Created by Adam on 8/17/15.
 */
public class WordFinder implements WordInputHandler {


    /**
     * A string with space(s) instead of characters at one or more index
     */
    private String partialWord ;

    /**
     * * Stores all matches found after a call to search()
     */
    private ArrayList<String> solutions = new ArrayList<>() ;

    private ArrayList<String> wordList = new ArrayList<>() ;

    private long matchesFound = 0 ;

    private boolean searchCompleted = false ;

    private Context context ;

    private OutputDisplay outputDisplay ;


    public WordFinder(Context context, OutputDisplay outputDisplay) {

        this.context = context ;
        registerForOutput(outputDisplay);

        int id = context.getResources().getIdentifier("english_words", "raw", context.getPackageName()) ;
        final InputStream wordStream = context.getResources().openRawResource(id) ;

        final Thread arrayPopulatingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Util.convertToStringArray(wordList, wordStream) ;
            }
        }) ;

        arrayPopulatingThread.start() ;

        int i = 0 ; //debug code, remove
    }

    public void search() {

        clearPreviousResults() ;

        for (String solutionCandidate : wordList) {
            if (Util.compareIgnoringSpaces(partialWord, solutionCandidate)) {
                matchesFound++ ;
                solutions.add(solutionCandidate) ;
            }
        }

        searchCompleted = true ;
        outputDisplay.notifyToUpdateDisplay() ;
    }

    public void registerForOutput(OutputDisplay outputDisplay) {
        this.outputDisplay = outputDisplay ;
    }

    public ArrayList<String> getSolutionsList() {
        return this.solutions ;
    }

    @Override
    public void callBack(final String userInput) {
        this.partialWord = userInput ;
        search() ;
    }

    public void clearPreviousResults() {
        /* Don't change partialWord */
        solutions.clear() ;
        matchesFound = 0 ;
        searchCompleted = false ;
    }
}
