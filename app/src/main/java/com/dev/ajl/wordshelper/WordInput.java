package com.dev.ajl.wordshelper;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.util.AttributeSet ;
import android.view.inputmethod.InputMethodManager;
import android.widget.* ;

import java.lang.String ;


/**
 * Created by Adam on 8/16/15.
 */
public class WordInput extends EditText implements TextView.OnEditorActionListener {

    /**
     * Interface that provides a call back method by which a class can receive the text input
     * collected by WordInput
     */
    public interface WordInputHandler {
        void callBack(final String userInput) ;
    }

    protected WordInputHandler wordInputHandler ;

    public WordInput(Context context) {
        super(context);
        this.setSingleLine();
        this.setOnEditorActionListener(this);
    }

    public WordInput(Context context, AttributeSet attributeSet) {
        super(context, attributeSet) ;
        this.setSingleLine();
        this.setOnEditorActionListener(this);
    }

    @Override
    public Editable getText() {
        return super.getText();
    }

    /**
     * Called when an action is being performed.
     *
     * @param v        The view that was clicked.
     * @param actionId Identifier of the action.  This will be either the
     *                 identifier you supplied, or {@link EditorInfo#IME_NULL
     *                 EditorInfo.IME_NULL} if being called due to the enter key
     *                 being pressed.
     * @param event    If triggered by an enter key, this is the event;
     *                 otherwise, this is null.
     * @return Return true if you have consumed the action, else false.
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        //InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

        /* The user finished inputting text. Pack it into a string*/
        String userInput = this.getText().toString() ;
        wordInputHandler.callBack(userInput) ;
        return true;
    }

    /**
     * Allows for another class to register to receive the user input in
     * string form, by implementing the WordInputHandler interface and passing
     * itself as the argument
     *
     * @param wordInputHandler The object whose callBack method will be called upon
     *                         receipt of user text input
     */
    public void registerForInput(WordInputHandler wordInputHandler) {
        this.wordInputHandler = wordInputHandler ;
    }
}
