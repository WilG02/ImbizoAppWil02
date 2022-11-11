package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UpdateCategory extends AppCompatActivity {

    //Static variables
    public static final int CAMERA_REQUEST_CODE = 102; //Request code for camera
    public static final int CAMERA_PERM_CODE = 101; //Request code for camera permissions
    public static final int GALLERY_REQUEST_CODE = 105;

    Button btnUpdate;
    ImageView imgGallery,imgCamera,imgAttached,
            imgBackFUpdate,imgUploadVideo,imgUploadPodcast;
    EditText edtName,edtDescription;
    ProgressDialog progressDialog;

    //Firebase realtime database Reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String currentPhotoPath;
    User user;
    boolean checkVideo =false;
    boolean checkGalleryImage = false;
    boolean checkCameraImage = false;
    boolean checkPodcast = false;

    String podcastfilepath;

    // what is saved
    String name,description,id,imageUrl,videoUrl,podcastUrl;

    //changes
    String nameUpdate,descriptionUpdate,imageUrlUpdate,
            videoUrlUpdate,podcastUrlUpdate,imageNameUpdate,videoNameUpdate,podcastNameUpdate;

    Collection c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---------------------------------------Code Attribution------------------------------------------------
        //Author:geeksforgeeks
        //Uses:Hides the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); //Hide the action bar
        //Link:https://www.geeksforgeeks.org/different-ways-to-hide-action-bar-in-android-with-examples/#:~:text=If%20you%20want%20to%20hide,AppCompat
        //-----------------------------------------------End------------------------------------------------------
        setContentView(R.layout.activity_update_category);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Intent i = getIntent();
        c = i.getParcelableExtra("Collection");

        btnUpdate = findViewById(R.id.btnUpdateCategory);
        imgGallery = findViewById(R.id.imgGalleryImageUpdate);
        imgCamera = findViewById(R.id.imgCameraImageUpdate);
        edtName = findViewById(R.id.edtCategoryUpdateName);
        edtDescription = findViewById(R.id.edtCategoryUpdateDescription);
        imgAttached = findViewById(R.id.imgAttachedImageDisplay);
        imgUploadVideo = findViewById(R.id.imgUploadVideoUpdate);
        imgUploadPodcast = findViewById(R.id.imgUploadPodcastUpdate);
        imgBackFUpdate = findViewById(R.id.imgBackFUpdate);

        imgAttached.setVisibility(View.INVISIBLE);
        edtName.setText(c.getCategoryName());
        edtDescription.setText(c.getCategoryInformation());

        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions(); //Calling method that asks user for camera permission
                progressDialog = new ProgressDialog(UpdateCategory.this);
                checkCameraImage=true;
            }
        });

        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                progressDialog = new ProgressDialog(UpdateCategory.this);
                checkGalleryImage=true;
            }
        });

        imgUploadPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(UpdateCategory.this);
                checkPodcast=true;
                chooseAudio();
            }
        });

        imgUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(UpdateCategory.this);
                choosevideo();
                checkVideo=true;
            }
        });

        imgBackFUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                edtDescription.setText("");
                ListUtils.categoryImageList.clear();
                ListUtils.categoryVideoList.clear();
                ListUtils.categoryPodcastList.clear();
                ListUtils.collectionList.clear();
                Intent i = new Intent(UpdateCategory.this,adminHome.class);
                i.putExtra("Collection",c);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.getText().toString() == null){
                    nameUpdate = c.getCategoryName();
                }else{
                    nameUpdate = edtName.getText().toString();
                }

                if (edtDescription.getText().toString() == null){
                    descriptionUpdate = c.getCategoryInformation();
                }else{
                    descriptionUpdate = edtDescription.getText().toString();
                }

                if (ListUtils.categoryImageList.size() == 0){
                    imageUrlUpdate = c.getCategoryImageUrl();
                    imageNameUpdate = c.getCategoryImageName();
                }else{
                    imageUrlUpdate = ListUtils.categoryImageList.get(0);
                    imageNameUpdate = ListUtils.categoryImageList.get(1);
                }

                if (ListUtils.categoryVideoList.size() == 0){
                    videoUrlUpdate = c.getCategoryVideoUrl();
                    videoNameUpdate = c.getCategoryVideoName();
                }else{
                    videoUrlUpdate = ListUtils.categoryVideoList.get(0);
                    videoNameUpdate =ListUtils.categoryVideoList.get(1);
                }

                if (ListUtils.categoryPodcastList.size() == 0){
                    podcastUrlUpdate = c.getCategoryPodcastUrl();
                    podcastNameUpdate = c.getCategoryPodcastName();
                }else{
                    podcastUrlUpdate = ListUtils.categoryPodcastList.get(0);
                    podcastNameUpdate = ListUtils.categoryPodcastList.get(1);
                }

                //---------------------------------------Code Attribution------------------------------------------------
                //Author:Foxandroid
                //Uses:Update data in Firebase realtime database
                HashMap categoryUpdate = new HashMap();
                categoryUpdate.put("categoryId",c.getCategoryId());
                categoryUpdate.put("categoryImageUrl",imageUrlUpdate);
                categoryUpdate.put("categoryInformation",descriptionUpdate);
                categoryUpdate.put("categoryName",nameUpdate);
                categoryUpdate.put("categoryPodcastUrl",podcastUrlUpdate);
                categoryUpdate.put("categoryVideoUrl",videoUrlUpdate);

                Collection collection = new Collection(c.getCategoryId(),nameUpdate,descriptionUpdate,imageUrlUpdate,videoUrlUpdate,podcastUrlUpdate);

                myRef.child("Categories").child(c.getCategoryId()).updateChildren(categoryUpdate).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(UpdateCategory.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                                   /* edtName.setText("");
                                    edtDescription.setText("");
                                    ListUtils.categoryImageList.clear();
                                    ListUtils.categoryVideoList.clear();
                                    ListUtils.categoryPodcastList.clear();
                                    ListUtils.collectionList.clear();*/





                                    Intent i = new Intent(UpdateCategory.this,adminHome.class);
                                    //i.putExtra("Collection",collection);
                                    startActivity(i);
                                }else{
                                    edtName.setText("");
                                    edtDescription.setText("");
                                    ListUtils.categoryImageList.clear();
                                    ListUtils.categoryVideoList.clear();
                                    ListUtils.categoryPodcastList.clear();
                                }
                            }});
                //Link:https://www.youtube.com/watch?v=oNNHR-LeTnI
                //-----------------------------------------------End------------------------------------------------------
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If camera request
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                File f = new File(currentPhotoPath);
                Log.d("tag", "Absolute URI of image is " + Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                if(checkCameraImage==true){
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    //Method call to upload the data to firebase storage
                    UploadImageToFirebase(f.getName(), contentUri);
                    checkCameraImage=false;
                }
            }
        }
        if (checkPodcast==true){
            if (requestCode == 10) {
                audio_uri = data.getData();
                podcastfilepath=audio_uri.getEncodedPath();
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                uploadPodcast(podcastfilepath,audio_uri);
                checkPodcast=false;
            }
            checkPodcast=false;
        }


        //if gallery request
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                if (checkGalleryImage==true) {
                    Uri contentUri = data.getData();//Creating content URI from the data
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());//Creating filename
                    String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);// Specifying the file type
                    Log.d("tag", "OnActivityResult: Gallery Image Uri:     " + imageFileName);//Displaying absolute Uri through ImageView

                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    //Method call to upload image to Firebase storage
                    UploadImageToFirebase(imageFileName, contentUri);
                    checkGalleryImage=false;
                }

                if(checkVideo==true){
                    //Adding video upload progress bar
                    videouri = data.getData();
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    uploadvideo();
                    checkVideo=false;
                }
            }
        }
    }


    //--------- audio -------------

    Uri audio_uri = null;

    private void chooseAudio(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 10);
    }

    private void uploadPodcast(final String file, final Uri uri) {

        StorageReference ref = FirebaseStorage.getInstance().getReference().child("Podcast/"+System.currentTimeMillis()+getAudiofiletype(uri));
        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                        downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                ListUtils.categoryPodcastList.clear();

                                // get the url for image in firebase storage and add it to an Arraylist
                                String genFilePath = downloadUri.getResult().toString();
                                ListUtils.categoryPodcastList.add(genFilePath);

                                progressDialog.dismiss();

                                audio_uri = null;
                                Toast.makeText(UpdateCategory.this, "Podcast Uploaded!!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateCategory.this, "Upload Podcast Failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        // show the progress bar
                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    }
                });
    }

    private String getAudiofiletype(Uri audiouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(audiouri));
    }

    //link:https://stackoverflow.com/questions/58789591/how-to-upload-an-audio-file-to-firebase-storage
    //----------audio end------------


    //---------video-------------

    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
    Uri videouri;

    //---------------------------------------Code Attribution------------------------------------------------
    //Author:GeeksForGeeks
    //Uses:Upload video to firebase storage and Realtime-Database

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {
        if (videouri != null) {
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Videos/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                    downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            ListUtils.categoryVideoList.clear();

                            // get the url for image in firebase storage and add it to an Arraylist
                            String genFilePath = downloadUri.getResult().toString();
                            ListUtils.categoryVideoList.add(genFilePath);

                            progressDialog.dismiss();
                            Toast.makeText(UpdateCategory.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                  }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(UpdateCategory.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }
    }
    //Link:https://www.geeksforgeeks.org/how-to-upload-a-video-in-firebase-database-using-android-studio/
    //-----------------------------------------------End------------------------------------------------------
    //----------video end------------

    //-----------camera & gallery-----------

    //---------------------------------------Code Attribution------------------------------------------------
    //Author:SmallAcademy
    //Uses:Select an image from Camera(ask permissions) or Gallery,upload the image to firebase storage and display image in imageView

    // Method ask the camera permissions
    //If camera permission is not granted, requesting the permission on runtime.
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    //Method checks if permission is granted for camera
    //If this condition is true - the user has given permission to use the camera
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Opening Camera
    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
        finish();
    }

    //---------------------------------------Code Attribution------------------------------------------------
    //Author:SmallAcademy
    //Uses:Upload image to firebase storage

    //Method to push image to Firebase storage
    private void UploadImageToFirebase(String name, Uri contentUri) {

        //Firebase storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference image = storageReference.child("Images/" + name);

        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //Called when image upload is successful
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
                downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        imgAttached.setVisibility(View.VISIBLE);

                        Picasso.get().load(uri).into(imgAttached); //Getting the Uri from firebase and using the picasso class to display the image
                        ListUtils.categoryImageList.clear();

                        // get the url for image in firebase storage and add it to an Arraylist
                        String genFilePath = downloadUri.getResult().toString();
                        ListUtils.categoryImageList.add(genFilePath);
                    }
                });
                progressDialog.dismiss();
                //When upload is successful the following message appears to the user
                Toast.makeText(UpdateCategory.this, "Image is uploaded", Toast.LENGTH_SHORT).show();
            }

            //Called if uploading image has failed
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateCategory.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                // show the progress bar
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Uploaded " + (int) progress + "%");
            }
        });
    }
    //Link:https://www.youtube.com/watch?v=dKX2V992pWI&list=PLlGT4GXi8_8eopz0Gjkh40GG6O5KhL1V1&index=5
    //-----------------------------------------------End------------------------------------------------------


    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver(); //Get extension of image that user has selected
        MimeTypeMap mime = MimeTypeMap.getSingleton(); //Lists out all the supported types of images
        return mime.getExtensionFromMimeType(c.getType(contentUri)); //Gets the extension of the mimetype from the Url, selected from gallery
    }

    //Method creates an image file name
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //Simple date format created to get timestamp
        String imageFileName = "JPEG_" + timeStamp + "_"; //Creating the image file , stored with JPEG name and the timestamp created above
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//Getting the storage directory where the file is being stored
        File image = File.createTempFile( //Creating an image file and passing parameters
                imageFileName,
                ".jpg", //Extension used for the image
                storageDir   //Directory to save the image
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath(); //Getting absolute path where image is saved
        return image;
    }

    //Method handles picture with camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Creating a new camera intent

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) { //Checks if the camera is present in the device or not
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(); //Returns image with the file name
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, //Creating a photo URI to get the URI for the file
                        "com.varsitycollege.imbizoappwil02.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                //finish();
            }
        }
    }
    //Link:https://www.youtube.com/watch?v=s1aOlr3vbbk&list=PLlGT4GXi8_8eopz0Gjkh40GG6O5KhL1V1&index=2
    //Link:https://www.youtube.com/watch?v=KaDwSvOpU5E&list=PLlGT4GXi8_8eopz0Gjkh40GG6O5KhL1V1&index=3
    //Link:https://www.youtube.com/watch?v=q5pqnT1n-4s&list=PLlGT4GXi8_8eopz0Gjkh40GG6O5KhL1V1&index=4
    //-----------------------------------------------End------------------------------------------------------




    //------------camera & gallery end----------

}