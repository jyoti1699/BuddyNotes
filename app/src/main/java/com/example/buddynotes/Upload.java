package com.example.buddynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buddynotes.AdapterClasses.putPDF;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Upload extends AppCompatActivity {

    Button uploadbtn;
    EditText edtxt;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        uploadbtn=findViewById(R.id.upload);

        edtxt=findViewById(R.id.edtext);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadPDF");

        uploadbtn.setEnabled(false);
       edtxt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectPDF();
           }
       });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            uploadbtn.setEnabled(true);
            edtxt.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/") + 1 ));
            uploadbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uploadFILE(data.getData());
                }
            });
        }
    }

    private void uploadFILE(Uri data) {
        final ProgressDialog progressDialog  = new ProgressDialog(Upload.this);
        progressDialog.setTitle("FILE IS UPLOADING...");
        progressDialog.show();

        StorageReference reference = storageReference.child("uploadPDF" + System.currentTimeMillis() + ".pdf");

        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        putPDF putPDF = new putPDF(edtxt.getText().toString(),uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(putPDF);
                        Toast.makeText(Upload.this, "FILE UPLOADED", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent secondactivity1 = new Intent(Upload.this, logged_in.class);
                        secondactivity1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(secondactivity1);

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0* snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage((int) progress + "%"+" Uploaded");
            }
        });
    }

   private  void selectPDF()
    {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        //noinspection deprecation
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECTED"),12);

    }
}