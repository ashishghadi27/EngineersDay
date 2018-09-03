package com.rootdevs.ashish.engineersday;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1200;

    private Speaker speaker;
    EditText qts;
    String question = "", answer = "";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qts = (EditText)findViewById(R.id.qts);
        button = (Button)findViewById(R.id.answer);
        checkTTS();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lets_speak();
            }
        });


    }

    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                speaker = new Speaker(this);
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    public void lets_speak(){
        speaker.allow(true);
        //speaker.speak(getString(R.string.start_speaking));
        question = qts.getText().toString();
        if (question.equals("What is the name of your college")){
            answer = "Shah and Anchor Kutchhi Engineering College";
        }
        else{
            answer = "None of your Business";
        }
        //speaker = new Speaker(this);
        speaker.speak(answer);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speaker.destroy();
    }
}
