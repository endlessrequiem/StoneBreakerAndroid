package stone.breaker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class PasswordGenerator extends mainmenu {
    TextView mTextview;

    private String[] alphabetstring;
    private static final Random rgenerator = new Random();

    private String[] symbolstring;
    private static final Random rgenerator1 = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordgenerator);
        mTextview = findViewById(R.id.textView);

        ImageButton simpleButton4 = findViewById(R.id.imageButton3);
        simpleButton4.setOnClickListener(view -> {
            Intent intent = new Intent(PasswordGenerator.this, mainmenu.class);
            startActivity(intent);
            finish();
        });

        Random r = new Random();
        FloatingActionButton simpleButton1 = findViewById(R.id.button4);
        simpleButton1.setOnClickListener(view -> {
            Resources res = getResources();
            symbolstring = res.getStringArray(R.array.symbolstring);

            alphabetstring = res.getStringArray(R.array.alphabetstring);

                final int a = (int)(Math.random()*9+1);
                final int b = (int)(Math.random()*99+1);
                final int c = (int)(Math.random()*999+1);
                final int d = (int)(Math.random()*9999+1);

                final String v = symbolstring[rgenerator1.nextInt(symbolstring.length)];
                final String w = alphabetstring[rgenerator.nextInt(alphabetstring.length)];
                final String x = alphabetstring[rgenerator.nextInt(alphabetstring.length)];
                final String y = alphabetstring[rgenerator.nextInt(alphabetstring.length)];
                final String z = alphabetstring[rgenerator.nextInt(alphabetstring.length)];

                int result = (a + b + c + d);

                String stringresult = Integer.toString(result);

                String finalresult = (v + w + x + stringresult + y + z);

                TextView output = mTextview;
                output.setText(finalresult);
        });

    }

    }

