package com.example.akasztofa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CoolerActivity extends AppCompatActivity {
    private ImageView img;
    private TextView actView;
    private TextView debug;
    private int clickCount;
    private String chosenWord;
    private String actWordSimple;
    private Button aButton;

    RandomStringGernerator rsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img = findViewById(R.id.imageView2);
        actView = findViewById(R.id.actView);
        debug = findViewById(R.id.debugView);
        aButton = findViewById(R.id.aButton);

        rsg = rsg = new RandomStringGernerator();

        Intent intent = getIntent();



        clickCount = intent.getIntExtra("count",0);
        actWordSimple = intent.getStringExtra("act");
        actView.setText(this.actWordSimple);
        chosenWord = intent.getStringExtra("chosen");
        debug.setText("Debug: "+intent.getStringExtra("chosen"));

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
    }
    @Override
    public void onBackPressed() {
        /*if (!shouldAllowBack()) {
            doSomething();
        } else {
            super.onBackPressed();
        }*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cooler, menu);
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
    public void changeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        switch (this.clickCount) {
            case 0:intent.putExtra("ember", 0); break;
            case 1:intent.putExtra("ember", 1); break;
            case 2:intent.putExtra("ember", 2); break;
            case 3:intent.putExtra("ember", 3); break;
            case 4:intent.putExtra("ember", 4); break;
            case 5:intent.putExtra("ember", 5); break;
            case 6:intent.putExtra("ember", 6); break;
        }
        intent.putExtra("count",this.clickCount);
        intent.putExtra("chosen",this.chosenWord);
        intent.putExtra("act",this.actWordSimple);
        startActivity(intent);
    }

    public void checkAnswer(View view) {
        String answer = "";
        Button b = (Button)view;
        answer = b.getText().toString();

        if(answer.toLowerCase().equals(chosenWord.toLowerCase())) {
            Toast.makeText(this,"Talált!",Toast.LENGTH_LONG).show();
            this.clickCount = 0;
            this.chosenWord = rsg.getRandomString();
            debug.setText("Debug:" + this.chosenWord);
            Drawable d = getResources().getDrawable(R.drawable.ember0);
            this.img.setImageDrawable(d);

        }else if(answer.toLowerCase().length() == 1){

            char guessChar = answer.toLowerCase().charAt(0);
            //Toast.makeText(this,"Cool String: |" + guessChar+"|",Toast.LENGTH_SHORT).show();
            boolean guessed = false;
            List<Integer> indexes = new ArrayList<>();

            for (int i = 0; i < this.chosenWord.length();i++) {
                char actChar = chosenWord.toLowerCase().charAt(i);

                /*switch (actChar) {
                    case 'á': actChar = 'a';break;

                }*/

                if(actChar == guessChar) {
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
                this.actView.setText(actWordSimple);
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
    public void setActWord(){

        actWordSimple = "";
        debug.setText(chosenWord);
        for (int i = 0; i < chosenWord.length();i++) {


            actWordSimple += "_";

        }
        actView.setText("A szó eddig:" + actWordSimple);

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



        img.setImageDrawable(resize(d));


    }
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 414, 615, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}
