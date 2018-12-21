package stone.breaker;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.graphics.BitmapFactory.decodeFile;
import static com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions.*;


public class FaceDetectionActivity extends AppCompatActivity {

    private static final String TAG = FaceDetectionActivity.class.getSimpleName();

    TextView details;
    FloatingActionButton gallery;

    File jpgimage = null;
    String path = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facedet);

        details = findViewById(R.id.details);

        gallery = findViewById(R.id.gallery);


        gallery.setOnClickListener(view -> {
            //Opens gallery picker
            EasyImage.openGallery(FaceDetectionActivity.this, 100);
        });
        Button simpleButton5 = findViewById(R.id.back);
        simpleButton5.setOnClickListener(view -> {
            Intent intent = new Intent(FaceDetectionActivity.this, mainmenu.class);
            startActivity(intent);
            finish();
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(FaceDetectionActivity.this, "Image picker error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onImagePickerError: " + "Image picker error");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

                new BitmapFactory();
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(decodeFile(imageFile.getAbsolutePath()));
                Toast.makeText(FaceDetectionActivity.this, "Success, working our magic now!", Toast.LENGTH_LONG).show();

                FirebaseVisionFaceDetectorOptions options;
                options = new Builder()
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

                                        FirebaseVisionFaceLandmark leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                                        String leN;
                                        leN = String.valueOf(leftEar);
                                        String le = leN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");

                                        FirebaseVisionFaceLandmark rightEar = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                                        String reN;
                                        reN = String.valueOf(rightEar);
                                        String re = reN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                        String rcN;
                                        rcN = String.valueOf(rightCheek);
                                        String rc = rcN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                        String lcN;
                                        lcN = String.valueOf(leftCheek);
                                        String lc = lcN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark bottomMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
                                        String bmN;
                                        bmN = String.valueOf(bottomMouth);
                                        String bm = bmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark leftMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT);
                                        String lmN;
                                        lmN = String.valueOf(leftMouth);
                                        String lm = lmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark rightMouth = face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                                        String rmN;
                                        rmN = String.valueOf(rightMouth);
                                        String rm = rmN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        FirebaseVisionFaceLandmark noseBase = face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                                        String nbN;
                                        nbN = String.valueOf(noseBase);
                                        String nb = nbN.replaceAll("com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark", "");


                                        String facepositions;
                                        facepositions = (le + " Left Ear Position \n"
                                                + re + " Right Ear Position \n"
                                                + rc + " Right Cheek Position \n"
                                                + lc + " Left Cheek Position \n"
                                                + bm + " Bottom Mouth Position \n"
                                                + lm + " Left Mouth Position \n"
                                                + rm + " Right Mouth Position \n"
                                                + nb + " Nose Base Position \n");

                                        if (face.getSmilingProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                            float smileProb = face.getSmilingProbability();
                                            float finalProb = smileProb * 100;
                                            String smile = "";
                                            if (smileProb != 0) {
                                                smile = String.valueOf(finalProb) + "%" + " of Smile Detected \n";
                                            }
                                            if (face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                                float finalrighteyeopenProb = rightEyeOpenProb * 100;
                                                String righteyeopen = "";
                                                if (rightEyeOpenProb != 0) {
                                                    righteyeopen = String.valueOf(finalrighteyeopenProb) + "%" + " Right Eye Open \n";
                                                }
                                                if (face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                    float LeftEyeOpenProb = face.getLeftEyeOpenProbability();
                                                    float finallefteyeopenProb = LeftEyeOpenProb * 100;
                                                    String lefteyeopen = "";
                                                    if (LeftEyeOpenProb != 0) {
                                                        lefteyeopen = String.valueOf(finallefteyeopenProb) + "%" + " Left Eye Open \n";
                                                    }
                                                    if (face.getHeadEulerAngleZ() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                        float headanglez = face.getHeadEulerAngleZ();
                                                        float headanglezProb = headanglez * 100;
                                                        String headangz = "";
                                                        if (headanglezProb != 0) {
                                                            headangz = String.valueOf(headanglezProb) + " Degrees Head Tilted \n";
                                                        }
                                                        if (face.getHeadEulerAngleY() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                                                            float headangley = face.getHeadEulerAngleY();
                                                            float headangleyProb = headangley * 100;
                                                            String headangy = "";
                                                            if (headangley != 0) {
                                                                headangy = String.valueOf(headangleyProb) + " Degrees Head Rotated \n";
                                                            }

                                                            String end;
                                                            end = "\n\nEnd Results.";
                                                            details.setText(smile + righteyeopen
                                                                    + lefteyeopen + headangz + headangy
                                                                    + facepositions + end);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }
                                })
                        .addOnFailureListener(
                                e -> Toast.makeText(FaceDetectionActivity.this, "Error, please try again", Toast.LENGTH_LONG).show());

                FirebaseVisionFace faces;


                jpgimage = imageFile;
                path = imageFile.getAbsolutePath();
                Log.d(TAG, "Image picker success");
            }

        });
    }


}
