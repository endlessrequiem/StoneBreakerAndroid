package stone.breaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageButton;

import barcodereader.BarcodeActivity;
import mlkit.ImageLabelActivity;
import mlkit.LandmarkActivity;

public class mainmenu extends MainActivity implements
        View.OnClickListener {
    FloatingActionButton mLandmark;
    FloatingActionButton mTextActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        FloatingActionButton simpleButton1 = findViewById(R.id.pwgenerator);
        simpleButton1.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, PasswordGenerator.class);
            startActivity(intent);
            finish();
        });

        FloatingActionButton simpleButton3 = findViewById(R.id.reader);
        simpleButton3.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, BarcodeActivity.class);
            startActivity(intent);
            finish();
        });
        FloatingActionButton simpleButton5 = findViewById(R.id.faceread);
        simpleButton5.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, FaceDetectionActivity.class);
            startActivity(intent);
            finish();
        });
        FloatingActionButton simpleButton6 = findViewById(R.id.imageanalyzer);
        simpleButton6.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, ImageLabelActivity.class);
            startActivity(intent);
            finish();
        });

        ImageButton simpleButton4 = findViewById(R.id.back);
        simpleButton4.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        FloatingActionButton mLandmark = findViewById(R.id.mLandmark);
        mLandmark.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, LandmarkActivity.class);
            startActivity(intent);
            finish();
        });
        FloatingActionButton Finder = findViewById(R.id.SmileFinder);
        Finder.setOnClickListener(view -> {
            Intent intent = new Intent(mainmenu.this, SmileFinder.class);
            startActivity(intent);
            finish();
        });

    }


}