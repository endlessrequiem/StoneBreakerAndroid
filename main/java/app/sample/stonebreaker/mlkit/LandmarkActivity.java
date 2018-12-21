package mlkit;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmark;
import com.google.firebase.ml.vision.cloud.landmark.FirebaseVisionCloudLandmarkDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionLatLng;

import java.io.File;

import mlkit.helpers.MyHelper;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import stone.breaker.R;
import stone.breaker.mainmenu;

import static android.graphics.BitmapFactory.decodeFile;

public class LandmarkActivity extends BaseActivity {
	private TextView mTextView;
	FloatingActionButton gallery;
	Button back;
	private static final String TAG = LandmarkActivity.class.getSimpleName();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cloud);
		mTextView = findViewById(R.id.text_view);

		back = findViewById(R.id.back);
		back.setOnClickListener(view -> {
            Intent intent = new Intent(LandmarkActivity.this, mainmenu.class);
            startActivity(intent);
            finish();
        });

		gallery = findViewById(R.id.gallery);
		gallery.setOnClickListener(view -> {
            //Opens gallery picker
            EasyImage.openGallery(LandmarkActivity.this, 100);
        });
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FirebaseApp.initializeApp(LandmarkActivity.this);
		EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
			@Override
			public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
				Toast.makeText(LandmarkActivity.this, "Image picker error", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "onImagePickerError: " + "Image picker error");
			}

			@Override
			public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {

				new BitmapFactory();
				new BitmapFactory();
				FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(decodeFile(imageFile.getAbsolutePath()));
				Toast.makeText(LandmarkActivity.this, "Success, working our magic now!", Toast.LENGTH_LONG).show();

				MyHelper.showDialog(LandmarkActivity.this);
				FirebaseVisionCloudDetectorOptions options = new FirebaseVisionCloudDetectorOptions.Builder()
						.setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
						.setMaxResults(5)
						.build();

				FirebaseVisionCloudLandmarkDetector detector = FirebaseVision.getInstance().getVisionCloudLandmarkDetector(options);

				detector.detectInImage(image).addOnSuccessListener(firebaseVisionCloudLandmarks -> {
                    MyHelper.dismissDialog();

                    StringBuilder result = new StringBuilder();
                    for (FirebaseVisionCloudLandmark landmark : firebaseVisionCloudLandmarks) {
                        String landmarkName = landmark.getLandmark();
                        float confidence = landmark.getConfidence();
                        result.append("Landmark: ").append(landmarkName).append("\n");
                        result.append("Confidence: ").append(confidence).append("\n");
                        for (FirebaseVisionLatLng loc : landmark.getLocations()) {
                            result.append("Location: ").append(loc.getLatitude()).append(",").append(loc.getLongitude()).append("\n");
                        }
                        result.append("\n");
                    }
                    if ("".equals(result.toString())) {
                        mTextView.setText(R.string.error_detect);
                    } else {
                        mTextView.setText(result.toString());
                    }
                }).addOnFailureListener(e -> {
                    MyHelper.dismissDialog();
                    mTextView.setText(e.getMessage());
                });
			}
		});
	}
}
