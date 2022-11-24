package com.varsitycollege.imbizoappwil02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class AddCategory extends AppCompatActivity {

    //Static variables
    public static final int CAMERA_REQUEST_CODE = 102; //Request code for camera
    public static final int CAMERA_PERM_CODE = 101; //Request code for camera permissions
    public static final int GALLERY_REQUEST_CODE = 105;

    Button btnCreate;
    ImageView imgGallery,imgCamera,imgAttached,imgBackFCreate,imgUploadVideo,imgUploadPodcast,imgAddVideoLink;
    EditText edtName,edtDescription;
    ProgressDialog progressDialog;

    //Firebase realtime database Reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    String currentPhotoPath,currentVideoPath,currentPodcastPath;
    User user;
    boolean checkVideo =false;
    boolean checkGalleryImage = false;
    boolean checkCameraImage = false;
    boolean checkPodcast = false;
    String name,description,id,imageUrl,videoUrl,podcastUrl,imageName,videoName,podcastName,edtVideoLink;
    String podcastfilepath,enteredUrl;

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
        setContentView(R.layout.activity_add_category);

        //Code to prevent dark mode on users phone
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnCreate = findViewById(R.id.btn_CreateCategory);
        imgGallery = findViewById(R.id.imgGalleryImage);
        imgCamera = findViewById(R.id.imgCameraImage);
        edtName = findViewById(R.id.edtCatName);
        edtDescription = findViewById(R.id.edtCatDescription);
        imgAttached = findViewById(R.id.img_AttachedImage);
        imgUploadVideo = findViewById(R.id.imgUploadVideo);
        imgUploadPodcast = findViewById(R.id.imgUploadPodcast);
        imgBackFCreate = findViewById(R.id.imgBackFCreate);
        imgAddVideoLink=findViewById(R.id.imgAddLink);

        imgAttached.setVisibility(View.INVISIBLE);

        imgBackFCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
                edtDescription.setText("");
                ListUtils.categoryImageList.clear();
                ListUtils.categoryVideoList.clear();
                ListUtils.categoryPodcastList.clear();
                ListUtils.collectionList.clear();
                Intent i = new Intent(AddCategory.this,adminHome.class);
                startActivity(i);
            }
        });

        imgUploadPodcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(AddCategory.this);
                checkPodcast=true;
                chooseAudio();
            }
        });


        imgUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(AddCategory.this);
                choosevideo();
                checkVideo=true;
            }
        });


        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                progressDialog = new ProgressDialog(AddCategory.this);
                checkGalleryImage=true;
            }
        });


        imgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions(); //Calling method that asks user for camera permission
                /*imgAttached.setVisibility(view.VISIBLE);*/
                progressDialog = new ProgressDialog(AddCategory.this);
                checkCameraImage=true;
            }
        });

       /* imgAddVideoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (ListUtils.categoryVideoList.get(1) == null) {
                    //---------------------------------------Code Attribution------------------------------------------------
                    //Author:Stackoverflow,Aaron
                    //Use:Display alert to the user to enter in the name of location
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);

                    builder.setTitle("Video Url");

                    // Set up the input
                    final EditText input = new EditText(AddCategory.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = input.getText().toString();
                            edtVideoLink = name;
                            if (!edtVideoLink.contains("youtube")){
                                Toast.makeText(AddCategory.this, "Please enter in a youtube URL!", Toast.LENGTH_SHORT).show();                            }
                            }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                    //Link:https://stackoverflow.com/questions/10903754/input-text-dialog-android
                    //-----------------------------------------------End------------------------------------------------------
                }
           // }
        });*/

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Validation val = new Validation();

                //try{
                    name = edtName.getText().toString();
                    description =edtDescription.getText().toString();
                    id = KeyGenerator.getRandomString(10);
                    imageUrl = ListUtils.categoryImageList.get(0);
                    videoUrl = ListUtils.categoryVideoList.get(0);
                    podcastUrl = ListUtils.categoryPodcastList.get(0);
                    imageName = ListUtils.categoryImageList.get(1);
                    videoName = ListUtils.categoryVideoList.get(1);
                    podcastName = ListUtils.categoryPodcastList.get(1);

                    //---------------------------------------Code Attribution------------------------------------------------
                    //Author:GeeksforGeeks
                    //Uses:validate if the input is empty and display field error, so the user know which component is an error

                    //name
                    if (edtName.length()==0){
                        edtName.setError("This field is required!");
                        // Link:https://www.geeksforgeeks.org/implement-form-validation-error-to-edittext-in-android/
                        // -----------------------------------------------End------------------------------------------------------
                    }else{
                        try {
                            if(val.isNullOrEmpty(edtName.getText().toString())==true || val.isAlphabet(edtName.getText().toString())== false){
                                Toast.makeText(AddCategory.this, "Enter in letter only!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                        }
                        //obtaining the user input entered to create a new collection
                        name = edtName.getText().toString();
                    }


                    //description
                    if (edtDescription.length()==0){
                        edtDescription.setError("This field is required!");
                        // Link:https://www.geeksforgeeks.org/implement-form-validation-error-to-edittext-in-android/
                        // -----------------------------------------------End------------------------------------------------------
                    }else{
                        try {
                            if(val.isNullOrEmpty(edtDescription.getText().toString())==true || val.isAlphabet(edtDescription.getText().toString())== false){
                                Toast.makeText(AddCategory.this, "Enter in letter only!", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                        }
                        //obtaining the user input entered to create a new collection
                        description =edtDescription.getText().toString();
                    }


                    //Image
                    if (ListUtils.categoryImageList.size()<=0) {
                        Toast.makeText(AddCategory.this, "Please attach image!", Toast.LENGTH_SHORT).show();
                    }

                    //Video
                    if (ListUtils.categoryVideoList.size()<=0) {
                        Toast.makeText(AddCategory.this, "Please attach video!", Toast.LENGTH_SHORT).show();
                    }

                   /* if (!edtVideoLink.equals("")){
                        videoName = "";
                        videoUrl = edtVideoLink;
                    }

*/


                /*  if (ListUtils.categoryVideoList.size()<=0 && edtVideoLink.equals("") ){
                    Toast.makeText(AddCategory.this, "Please upload a video or enter in a url!", Toast.LENGTH_SHORT).show();
                  }else{
                    if (videoUrl.equals("")){
                        videoUrl = edtVideoLink;
                        videoName ="";
                    }

                    if (edtVideoLink.equals("")){
                        videoUrl=ListUtils.categoryVideoList.get(0);
                        videoName =ListUtils.categoryVideoList.get(1);
                    }
                }*/

                    //Audio
                    if (ListUtils.categoryPodcastList.size()<=0) {
                        Toast.makeText(AddCategory.this, "Please attach podcast!", Toast.LENGTH_SHORT).show();
                    }

                 /*   //validated the inputs, if they are valid then collection is created and written to firebase realtime database
                    if (val.isNullOrEmpty(edtName.getText().toString())==true || val.isAlphabet(edtName.getText().toString())== false
                            || val.isNullOrEmpty(edtDescription.getText().toString())==true || val.isAlphabet(edtDescription.getText().toString())== false){
                        Toast.makeText(AddCategory.this, "Please check your inputs!", Toast.LENGTH_SHORT).show();
                    }else{*/
                        Collection collection = new Collection(id,name,description,imageUrl,videoUrl,podcastUrl,imageName,videoName,podcastName);

                        myRef = database.getReference().child("Categories");

                        myRef.child(collection.getCategoryId()).setValue(collection).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddCategory.this, collection.getCategoryName()+" added successfully!", Toast.LENGTH_SHORT).show();
                                edtName.setText("");
                                edtDescription.setText("");
                                ListUtils.categoryImageList.clear();
                                ListUtils.categoryVideoList.clear();
                                ListUtils.categoryPodcastList.clear();
                                Intent i = new Intent(AddCategory.this,adminHome.class);
                                startActivity(i);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddCategory.this,"Failed to Add!",Toast.LENGTH_SHORT).show();
                            }
                        });
                    //}

                   /* }catch(Exception e){
                    Toast.makeText(AddCategory.this, "Add Category Fail!", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

    }

    //-------Audio
    Uri audio_uri = null;

    private void chooseAudio(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 10);
    }

    private void uploadPodcast(String name, Uri contentUri) {

        //Firebase storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference audioRef = storageReference.child("Podcasts/" + name);
        //String podcastName = System.currentTimeMillis()+getAudiofiletype(uri);
        //StorageReference ref = FirebaseStorage.getInstance().getReference().child("Podcast/"+podcastName);

        audioRef.putFile(contentUri)
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
                                    ListUtils.categoryPodcastList.add(name);

                                    progressDialog.dismiss();
                                    audio_uri = null;
                                    Toast.makeText(AddCategory.this, "Podcast Uploaded!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCategory.this, "Upload Podcast Failed!", Toast.LENGTH_SHORT).show();
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
        // get the file type ,in this case its mp3
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(audiouri));
    }
    //link:https://stackoverflow.com/questions/58789591/how-to-upload-an-audio-file-to-firebase-storage
    //--------end audio


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

    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    Uri videouri;

    //Checking if the request is a camera or gallery request
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

                    Uri contentUri = data.getData();

                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String audioFileName = "MP3_" + timeStamp + "_" + getfiletype(contentUri);
                    /*File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//Getting the storage directory where the file is being stored
                    File audio = null;
                    try {
                        audio = File.createTempFile(
                                audioFileName,
                                ".mp3", //Extension used for the video
                                storageDir   //Directory to save the video
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Save a file: path for use with ACTION_VIEW intents
                    currentPodcastPath = audio.getAbsolutePath(); //Getting absolute path where video is saved*/

                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                   /* File f = new File(currentPodcastPath);
                    Uri podcastUri = Uri.fromFile(f);*/

                    uploadPodcast(audioFileName,contentUri);




                   // audio_uri =
                   /*
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());//Creating filename
                    String audioFileName = "MP3_" + timeStamp + "." + getAudiofiletype(audio_uri);// Specifying the file type
                    podcastfilepath=audio_uri.getEncodedPath();
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    uploadPodcast(audioFileName,podcastUri);
                    checkPodcast=false;*/
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

                    Uri contentUri = data.getData();//Creating content URI from the data
                    //Adding video upload progress bar
                    videouri = data.getData();
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String videoFileName = "MP4_" + timeStamp + "_" + getfiletype(contentUri);

                        try {
                            uploadvideo(videoFileName,contentUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    checkVideo=false;
                }
            }
        }
    }


  /*  //Method creates an video file name
    private File createAudioFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_" + getAudiofiletype();
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//Getting the storage directory where the file is being stored
        File video = File.createTempFile(
                imageFileName,
                ".mp4", //Extension used for the video
                storageDir   //Directory to save the video
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentVideoPath = video.getAbsolutePath(); //Getting absolute path where video is saved
        return video;
    }*/



    //---------------------------------------Code Attribution------------------------------------------------
    //Author:GeeksForGeeks
    //Uses:Upload video to firebase storage and Realtime-Database

    //Method creates an video file name
    private File createVideoFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);//Getting the storage directory where the file is being stored
        File video = File.createTempFile(
                imageFileName,
                ".mp4", //Extension used for the video
                storageDir   //Directory to save the video
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentVideoPath = video.getAbsolutePath(); //Getting absolute path where video is saved
        return video;
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo(String name, Uri contentUri) throws IOException {
        //Firebase storage reference
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference videoRef = storageReference.child("Videos/" + name);
       // File videoFile = createVideoFile();
        //String videoName = createVideoFile().getName();
                //System.currentTimeMillis() + "." + getfiletype(videouri);
        if (contentUri != null) {
            // save the selected video in Firebase storage
            //final StorageReference reference = FirebaseStorage.getInstance().getReference("Videos/");
            videoRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                            ListUtils.categoryVideoList.add(name);

                            progressDialog.dismiss();
                            Toast.makeText(AddCategory.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    /*Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("videolink", downloadUri);
                    reference1.child("" + System.currentTimeMillis()).setValue(map);*/
                    // Video uploaded successfully
                    // Dismiss dialog
                   /* progressDialog.dismiss();
                    Toast.makeText(AddCategory.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(AddCategory.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        ListUtils.categoryImageList.add(name);

                    }
                });
                progressDialog.dismiss();
                //When upload is successful the following message appears to the user
                Toast.makeText(AddCategory.this, "Image is uploaded", Toast.LENGTH_SHORT).show();
            }

            //Called if uploading image has failed
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddCategory.this, "Upload failed", Toast.LENGTH_SHORT).show();
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
    //Link:https://www.geeksforgeeks.org/how-to-upload-a-video-in-firebase-database-using-android-studio/
    //-----------------------------------------------End------------------------------------------------------
}