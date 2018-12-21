package stone.breaker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.graphics.BitmapFactory.decodeFile;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.*;


public class SmileFinder extends FaceDetectionActivity {

    private static final String TAG = SmileFinder.class.getSimpleName();

    TextView details;
    FloatingActionButton gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facedet);

        details = findViewById(R.id.details);

        gallery = findViewById(R.id.gallery);

        Button simpleButton5 = findViewById(R.id.back);
        simpleButton5.setOnClickListener(view -> {
            Intent intent = new Intent(SmileFinder.this, mainmenu.class);
            startActivity(intent);
            finish();
        });

        gallery.setOnClickListener(view -> {
            //Opens gallery picker
            EasyImage.openGallery(SmileFinder.this, 100);
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(SmileFinder.this, "Image picker error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onImagePickerError: " + "Image picker error");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                new BitmapFactory();
                Toast.makeText(SmileFinder.this, "Success, working our magic now!", Toast.LENGTH_LONG).show();
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(decodeFile(imageFile.getAbsolutePath()));

                FirebaseVisionFaceDetectorOptions options =
                        new Builder()
                                .setPerformanceMode(FaceDetector.ACCURATE_MODE)
                                .setLandmarkMode(ALL_LANDMARKS)
                                .setClassificationMode(ALL_CLASSIFICATIONS)
                                .setMinFaceSize(0.15f)
                                .build();


                FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                        .getVisionFaceDetector(options);


                detector.detectInImage(image)
                        .addOnSuccessListener(
                                faces -> {
                                    for (FirebaseVisionFace face : faces) {
                                        if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                            float smileProb = face.getSmilingProbability();
                                            float finalProb = smileProb * 100;
                                            if (finalProb >= 65.0) {
                                                String smilefound;
                                                smilefound = "Smile Found. \nHere is the percentage found " + finalProb + "%";
                                                details.setText(smilefound);
                                            } else {
                                                String nosmilefound;
                                                nosmilefound = "No Smile Found. \nHere is the percentage found " + finalProb + "%";
                                                details.setText(nosmilefound);

                                            }
                                        }
                                            }

                                })
                        .addOnFailureListener(
                                e -> Toast.makeText(SmileFinder.this, "Error, please try again", Toast.LENGTH_LONG).show());

            }

        });
    }


}


