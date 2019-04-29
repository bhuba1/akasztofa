package com.example.akasztofa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private ImageView img;
    private TextView debug;
    private TextView word;
    private int clickCount = 0;
    private String chosenWord;
    private String actWord;
    private String actWordSimple;
    private ArrayList<String> guessedChars;

    public static final String EXTRA_MESSAGE = "ember";
    public static final String EXTRA_MESSAGE2 = "actword";


    RandomStringGernerator rsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        input = findViewById(R.id.editText);
        img = findViewById(R.id.imageView);

        debug = findViewById(R.id.debugView);
        word = findViewById(R.id.wordView);


        guessedChars = new ArrayList<>();

        rsg = new RandomStringGernerator();
        this.chosenWord = rsg.getRandomString();
        debug.setText("Debug: " + chosenWord);

        setActWord();

        Drawable d0 = getResources().getDrawable(R.drawable.ember0);
        img.setImageDrawable(resize(d0));

        if(savedInstanceState != null && !savedInstanceState.isEmpty()) {
            this.clickCount = savedInstanceState.getInt("count");
            this.chosenWord = savedInstanceState.getString("word");
            this.debug.setText("Debug: " + this.chosenWord);
            this.guessedChars = savedInstanceState.getStringArrayList("guessed");
            this.actWord = savedInstanceState.getString("actword");
            this.actWordSimple = savedInstanceState.getString("actwordsimple");
            changeActWord();
            Drawable d;
            if(clickCount == 1) {
                d = getResources().getDrawable(R.drawable.ember1);
            } else if(clickCount == 2) {
                d = getResources().getDrawable(R.drawable.ember2);

            }else if(clickCount == 3){
                d = getResources().getDrawable(R.drawable.ember3);

            }else if(clickCount == 4) {
                d = getResources().getDrawable(R.drawable.ember4);
            }else if(clickCount == 5) {
                d = getResources().getDrawable(R.drawable.ember5);
            }
            else if(clickCount == 6 ){
                d = getResources().getDrawable(R.drawable.ember6);
            }else {

                d = getResources().getDrawable(R.drawable.ember0);
            }

            //img.setImageResource(R.drawable.elso);

        } else {
            this.clickCount = 0;
        }

        Intent intent = getIntent();
        if(intent.getStringExtra("chosen") != null) {
            int ember = intent.getIntExtra("ember",0);
            Drawable d;
            switch (ember) {
                case 0: d = getResources().getDrawable(R.drawable.ember0); break;
                case 1:d = getResources().getDrawable(R.drawable.ember1); break;
                case 2:d = getResources().getDrawable(R.drawable.ember2); break;
                case 3:d = getResources().getDrawable(R.drawable.ember3); break;
                case 4:d = getResources().getDrawable(R.drawable.ember4); break;
                case 5:d = getResources().getDrawable(R.drawable.ember5); break;
                case 6:d = getResources().getDrawable(R.drawable.ember6); break;
                default: d = getResources().getDrawable(R.drawable.ember0); break;
            }
            img.setImageDrawable(d);

            this.clickCount = intent.getIntExtra("count",0);
            this.chosenWord = intent.getStringExtra("chosen");
            this.actWordSimple = intent.getStringExtra("act");
            changeActWord();
            debug.setText(chosenWord);

        }


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", this.clickCount);
        outState.putString("word",this.chosenWord);
        outState.putStringArrayList("guessed",this.guessedChars);
        outState.putString("actword",this.actWord);
        outState.putString("actwordsimple",this.actWordSimple);
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
        if (id == R.id.changeActivity) {
            changeActivity();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        /*if (!shouldAllowBack()) {
            doSomething();
        } else {
            super.onBackPressed();
        }*/
    }
    public void setActWord(){
        String actWord = "";
        actWordSimple = "";
        debug.setText(chosenWord);
        for (int i = 0; i < chosenWord.length();i++) {


            actWordSimple += "_";

        }
        for(int i = 0; i < actWordSimple.length();i++){
            actWord+=" "+ actWordSimple.charAt(i);
        }
        word.setText("A szó eddig:" + actWord);

    }
    public void changeActWord() {
        String actWord = "";
        for(int i = 0; i < actWordSimple.length();i++){
            actWord+=" "+ actWordSimple.charAt(i);
        }
        word.setText("A szó eddig:" + actWord);
    }

    public void changeImg() {
        clickCount++;
        Drawable d;
        if(clickCount == 1) {
            d = getResources().getDrawable(R.drawable.ember1);
        } else if(clickCount == 2) {
            d = getResources().getDrawable(R.drawable.ember2);

        }else if(clickCount == 3){
            d = getResources().getDrawable(R.drawable.ember3);

        }else if(clickCount == 4) {
            d = getResources().getDrawable(R.drawable.ember4);
        }else if(clickCount == 5) {
            d = getResources().getDrawable(R.drawable.ember5);
        }
        else if(clickCount == 6 ){
            d = getResources().getDrawable(R.drawable.ember6);
        }else {
            Toast.makeText(this,"Vesztettél! A szó a(z) :" + chosenWord + " lett volna",Toast.LENGTH_LONG).show();
            this.chosenWord = rsg.getRandomString();
            setActWord();
            d = getResources().getDrawable(R.drawable.ember0);
            clickCount = 0;
        }

        //img.setImageResource(R.drawable.elso);

        img.setImageDrawable(resize(d));

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String stg = "Cool: " + input.getText();
                Toast.makeText(findViewById(R.id.imageView), stg, Toast.LENGTH_SHORT).show();
            }
        }, 10000);
        */
    }
    public void checkAnswer(View view) {
        String answer = input.getText().toString();
        //Toast.makeText(this,"Answer: "  + answer,Toast.LENGTH_SHORT).show();
        input.setText("");


        if(answer.toLowerCase().equals(chosenWord.toLowerCase())) {
            Toast.makeText(this,"Talált!",Toast.LENGTH_LONG).show();
            this.clickCount = 0;
            this.chosenWord = rsg.getRandomString();
            debug.setText("Debug:" + this.chosenWord);
            Drawable d = getResources().getDrawable(R.drawable.ember0);
            this.img.setImageDrawable(d);
            setActWord();

        }else if(answer.toLowerCase().length() == 1){
                char guessChar = answer.charAt(0);
                boolean guessed = false;
                List<Integer> indexes = new ArrayList<>();

                for (int i = 0; i < this.chosenWord.length();i++) {
                    if(chosenWord.toLowerCase().charAt(i) == guessChar) {
                        guessed = true;
                        indexes.add(i);

                    }
                }
                if(guessed) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.actWordSimple);
                    for (int index : indexes) {
                        /*if(index != 0 && index != indexes.size()-1) {
                            sb.setCharAt(index+1,guessChar);
                        }else {
                            sb.setCharAt(index,guessChar);
                        }*/
                        sb.setCharAt(index,guessChar);

                    }
                    this.actWordSimple = sb.toString();
                    changeActWord();
                    //Toast.makeText(this,"|" + actWordSimple +"|",Toast.LENGTH_LONG).show();
                    if(actWordSimple.toLowerCase().equals(chosenWord.toLowerCase())) {
                        Toast.makeText(this,"Talált!",Toast.LENGTH_LONG).show();
                        this.clickCount = 0;
                        this.chosenWord = rsg.getRandomString();
                        debug.setText("Debug:" + this.chosenWord);
                        Drawable d = getResources().getDrawable(R.drawable.ember0);
                        this.img.setImageDrawable(d);
                        setActWord();
                    }
                }else {
                    changeImg();
                }

        }else {
            changeImg();
        }

    }
    public void changeActivity() {
        Intent intent = new Intent(this, CoolerActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();

        switch (this.clickCount) {
            case 0:intent.putExtra("ember", 0); break;
            case 1:intent.putExtra("ember", 1); break;
            case 2:intent.putExtra("ember", 2); break;
            case 3:intent.putExtra("ember", 3); break;
            case 4:intent.putExtra("ember", 4); break;
            case 5:intent.putExtra("ember", 5); break;
            case 6:intent.putExtra("ember", 6); break;
        }


        intent.putExtra("act",actWordSimple);
        intent.putExtra("chosen",this.chosenWord);
        intent.putExtra("count",this.clickCount);
        startActivity(intent);
    }


    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 414, 615, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}
