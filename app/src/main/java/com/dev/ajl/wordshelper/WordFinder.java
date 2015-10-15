package com.dev.ajl.wordshelper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.io.* ;
import java.lang.String ;
import java.lang.Thread ;
import java.util.Iterator;
import java.util.List ;
import java.util.ArrayList ;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ListIterator;
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

    private List<String> wordList = Collections.synchronizedList(new ArrayList<String>()) ;

    private long matchesFound = 0 ;

    private boolean searchCompleted = false ;

    private Context context ;

    private OutputDisplay outputDisplay ;

    private class ThreadedWorker implements Runnable {

        public WordFinder wordFinder ;

        public ThreadedWorker(WordFinder wordFinder) {
            this.wordFinder = wordFinder ;
        }

        /**
         * Starts executing the active part of the class' code. This method is
         * called when a thread is started that has been created with a class which
         * implements {@code Runnable}.
         */
        @Override
        public void run() {
        }
    }


    public WordFinder(Context context, OutputDisplay outputDisplay) {

        this.context = context ;
        registerForOutput(outputDisplay);

        int englishWordsRawID = context.getResources().getIdentifier("english_words_raw", "raw", context.getPackageName()) ;
        final InputStream wordArrayStream = context.getResources().openRawResource(englishWordsRawID) ;
        Pointer<List<String>> wordListPtr = new Pointer<>(this.wordList) ;

        final Thread arrayPopulatingThread = new Thread(new WordFinder.ThreadedWorker(this) {
            @Override
            public void run() {
                //Util.convertToStringArray(wordList, wordStream) ;

                ObjectInputStream fileInput ;

                try {
                    fileInput = new ObjectInputStream(new BufferedInputStream(wordArrayStream)) ;
                    wordFinder.wordList = (List<String>) fileInput.readObject() ;
                    fileInput.close() ;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) ;

        arrayPopulatingThread.start() ;

        int i = 0 ; //debug code, remove
    }

    public void search() {

        clearPreviousResults() ;

        synchronized (wordList) {
            ListIterator<String> solutionIterator = wordList.listIterator() ;
            while (solutionIterator.hasNext()) {
                String solutionCandidate = solutionIterator.next() ;
                if (Util.compareIgnoringSpaces(partialWord, solutionCandidate)){
                    matchesFound++ ;
                    solutions.add(solutionCandidate);
                }
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
