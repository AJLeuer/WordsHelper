package com.dev.ajl.wordshelper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.* ;
import android.widget.* ;

//import static com.dev.ajl.wordshelper.R.id.resultsList;


public class MainActivity extends AppCompatActivity implements OutputDisplay {

    private RelativeLayout mainView ;
    private WordInput textInputBox ;
    private TextView resultsList ;

    private WordFinder wordFinder ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mainView = (RelativeLayout) findViewById(R.id.main_view);
        textInputBox = (WordInput) findViewById(R.id.text_input_box) ;
        resultsList = (TextView) findViewById(R.id.results_list) ;


        wordFinder = new WordFinder(this, this);
        wordFinder.registerForOutput(this) ;
        textInputBox.registerForInput(wordFinder);

    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateDisplay() {
        resultsList.refreshDrawableState();
    }

    @Override
    public void notifyToUpdateDisplay() {
        String results = Util.convertToString(wordFinder.getSolutionsList()) ;
        resultsList.setText(results) ;
        updateDisplay() ;
    }
}
