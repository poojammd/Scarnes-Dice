package com.example.murali.scarnesdice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private int userOverallScore = 0;
    private int userTurnScore = 0;
    private int computerOverallScore = 0;
    private int computerTurnScore = 0;


    TextView score;
    ImageView facenumber;
    Button broll,bhold,breset;
    Random r;
    int n,m;
    final String LOG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG,"Entered OnCreate");
        score = findViewById(R.id.textView);
        facenumber = findViewById(R.id.imageView2);
        broll = findViewById(R.id.button);
        bhold = findViewById(R.id.button2);
        breset = findViewById(R.id.button3);
        score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);
        r = new Random();
        breset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userOverallScore = 0;
                userTurnScore = 0;
                computerOverallScore = 0;
                computerTurnScore = 0;
                score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                        "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);

            }
        });
        broll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int n=r.nextInt(6)+1;
                rollDieFace(n);
                if(n!=1)
                {
                    userTurnScore+=n;
                    score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                            "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);
                }
                else
                {
                    userTurnScore=0;
                    score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                            "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);
                    computerTurn();
                }

            }
        });
        bhold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userOverallScore+=userTurnScore;
                userTurnScore=0;
                score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                        "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);
                if(userOverallScore>=100) {

                    Toast.makeText(MainActivity.this, "The Player is the Winner\n", Toast.LENGTH_SHORT).show();
                   dialogbox();

                }
                else
                    computerTurn();
                            }
        });
    }
    private void computerTurn()
    {
        bhold.setEnabled(false);
        broll.setEnabled(false);
        computerTurnScore=0;

        n =r.nextInt(6)+1;
        rollDieFace(n);
        while(true)
        {

            if(n!=1)
            {
                computerTurnScore+=n;
                score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                        "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);


            }
            else
            {
                computerTurnScore=0;
                score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                        "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);
                break;
            }
            m =r.nextInt(2);
            if(m==1) {
                Toast.makeText(this, "Computer is Holding", Toast.LENGTH_SHORT).show();
                computerOverallScore += computerTurnScore;

                computerTurnScore = 0;
                score.setText("Your Score : " + userOverallScore + "  Computer Score : " + computerOverallScore +
                        "\nYour Current Score : " + userTurnScore + " Computer Current Score : " + computerTurnScore);
                if (computerOverallScore >= 100) {
                    Toast.makeText(this, "Player loses", Toast.LENGTH_SHORT).show();
                    dialogbox();
                }
                break;
            }

            else
            {
                Log.d(LOG_TAG,"Computer Didnt Hold");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                       // buttons[inew][jnew].setBackgroundColor(Color.BLACK);
                        n =r.nextInt(6)+1;
                        rollDieFace(n);
                    }
                }, 5000);
            }

        }


        bhold.setEnabled(true);
        broll.setEnabled(true);
        }

    private void rollDieFace(int n)
    {
        switch(n)
        {
            case 1 : facenumber.setImageResource(R.drawable.dice1); break;
            case 2 : facenumber.setImageResource(R.drawable.dice2); break;
            case 3 : facenumber.setImageResource(R.drawable.dice3); break;
            case 4 : facenumber.setImageResource(R.drawable.dice4); break;
            case 5 : facenumber.setImageResource(R.drawable.dice5); break;
            case 6 : facenumber.setImageResource(R.drawable.dice6); break;

        }
    }
    private void dialogbox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                new ContextThemeWrapper(MainActivity.this, R.style.AlertDialogCustom));
        builder.setCancelable(true);
        builder.setTitle("New Game");
        builder.setMessage("Would you like to start new game? ");
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userOverallScore = 0;
                        userTurnScore = 0;
                        computerOverallScore = 0;
                        computerTurnScore = 0;
                        score.setText("Your Score : "+userOverallScore+"  Computer Score : "+computerOverallScore+
                                "\nYour Current Score : "+userTurnScore+" Computer Current Score : "+computerTurnScore);




                    }
                });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Exiting App", Toast.LENGTH_SHORT).show();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
