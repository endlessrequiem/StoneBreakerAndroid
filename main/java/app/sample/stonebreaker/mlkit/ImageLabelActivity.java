/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mlkit;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.cloud.FirebaseVisionCloudDetectorOptions;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel;
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabelDetector;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;

import mlkit.helpers.MyHelper;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import stone.breaker.R;
import stone.breaker.mainmenu;

import static android.graphics.BitmapFactory.decodeFile;


public class ImageLabelActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTextView;

    FloatingActionButton gallery;
    Button back;
    private static final String TAG = ImageLabelActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud);

        mTextView = findViewById(R.id.text_view);
        findViewById(R.id.gallery).setOnClickListener(this);

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(ImageLabelActivity.this, mainmenu.class);
            startActivity(intent);
            finish();
        });

        gallery = findViewById(R.id.gallery);
        gallery.setOnClickListener(view -> {
            //Opens gallery picker
            EasyImage.openGallery(ImageLabelActivity.this, 100);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(ImageLabelActivity.this, "Image picker error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onImagePickerError: " + "Image picker error");
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    new BitmapFactory();
                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(decodeFile(imageFile.getAbsolutePath()));
                Toast.makeText(ImageLabelActivity.this, "Success, working our magic now!", Toast.LENGTH_LONG).show();


                    MyHelper.showDialog(ImageLabelActivity.this);
                    FirebaseVisionCloudDetectorOptions options = new FirebaseVisionCloudDetectorOptions.Builder()
                            .setModelType(FirebaseVisionCloudDetectorOptions.LATEST_MODEL)
                            .setMaxResults(5)
                            .build();


                    FirebaseVisionCloudLabelDetector detector = FirebaseVision.getInstance().getVisionCloudLabelDetector(options);
                    detector.detectInImage(image).addOnSuccessListener(labels -> {
                        MyHelper.dismissDialog();
                        for (FirebaseVisionCloudLabel label : labels) {
                            mTextView.append(label.getLabel() + ": " + label.getConfidence() + "/1 Machine Confidence \n\n");
                        }
                    }).addOnFailureListener(e -> {
                        MyHelper.dismissDialog();
                        mTextView.setText(e.getMessage());
                    });
                }
        });
    }
}
