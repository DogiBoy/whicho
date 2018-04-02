package com.example.dogi.whicho;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PostActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 2 ;

    private Uri uri1,uri2 = null;
    private ImageButton imageButton1, imageButton2;
    private EditText editName;
    private StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;



    private int imagenumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        editName = ( EditText) findViewById(R.id.editName);
        FirebaseApp.initializeApp(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Which One");

    }
    public void imageButtonClicked ( View view ){
        imagenumber = 1;
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT );
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLERY_REQUEST);


    }

    public void imageButtonClicked2 ( View view ){
        imagenumber = 2;
        Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT );
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,GALLERY_REQUEST);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {

            if(imagenumber == 1) {
                uri1 = data.getData();
                imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
                imageButton1.setImageURI(uri1);
            } else if(imagenumber == 2){
                uri2 = data.getData();
                imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
                imageButton2.setImageURI(uri2);
            }
        }
    }
    public void SubmitButtonClicked(View view){
        Intent mainIntent = new Intent(PostActivity.this,MainActivity.class);
        startActivity(mainIntent);
        final String titleValue = editName.getText().toString().trim();
        if (!TextUtils.isEmpty(titleValue)){

            StorageReference filePath = storageReference.child("PostImage").child(uri1.getLastPathSegment());
            filePath.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"Upload Complete", Toast.LENGTH_LONG).show();
                    Log.d("firebase", "complete");
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("title").setValue(titleValue);
                    newPost.child("image").setValue(downloadurl.toString());
                }
            });

            StorageReference filePath2 = storageReference.child("PostImage").child(uri2.getLastPathSegment());
            filePath2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(PostActivity.this,"Upload Complete 2", Toast.LENGTH_LONG).show();
                    Log.d("firebase", "complete");
                    DatabaseReference newPost = databaseReference.push();
                    newPost.child("title").setValue(titleValue);
                    newPost.child("image").setValue(downloadurl.toString());
                }
            });
        }

    }



}




