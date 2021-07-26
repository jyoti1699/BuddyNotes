package com.example.buddynotes;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    TextInputLayout enterphone,passwd;
    ProgressBar pg;
    TextView ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pg = findViewById(R.id.progressbar);
        pg.setVisibility((View.GONE));
        enterphone = findViewById(R.id.enterphone);
        passwd = findViewById(R.id.passwd);
       FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");





    }
  /*  private Boolean validatePhone() {
        String val = enterphone.getEditText().getText().toString();
        String phonepattern = "[0-9]{10}";
        if (val.isEmpty()) {
            enterphone.setError("Field cannot be empty");
            return false;
        } else   if(!val.matches(phonepattern)) {
            {
                enterphone.setError("Inavlid phone");
                return false;
            }

        } else {
            enterphone.setError(null);
            return true;
        }
    }
    private Boolean validatePass() {
        String val = passwd.getEditText().getText().toString();
        if (val.isEmpty()) {
            passwd.setError("Field cannot be empty");
            return false;
        } else {
            passwd.setError(null);
            passwd.setErrorEnabled(false);
            return true;
        }
    }
    public void sin(View view) {
        if(!validatePhone() || !validatePass()) {
            return;
        }
        pg.setVisibility((View.VISIBLE));
        isUser();
    }
    private void isUser() {
        final String userphone = enterphone.getEditText().getText().toString().trim();
        final String userpass = passwd.getEditText().getText().toString().trim();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("UserDetails");
        Query checkUsercred = reference.orderByChild("phone").equalTo(userphone);

        checkUsercred.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    enterphone.setError(null);
                    enterphone.setErrorEnabled(false);
                    String passwordfromDB = snapshot.child(userphone).child("pass").getValue(String.class);

                    if (passwordfromDB.equals(userpass)) {
                        enterphone.setError(null);
                        enterphone.setErrorEnabled(false);
                        //String mailfromDB = snapshot.child(userphone).child("mail").getValue(String.class);
                        //String phonefromDB = snapshot.child(userphone).child("phone").getValue(String.class);
                        Intent secondactivity1 = new Intent();
                        secondactivity1.setClass(MainActivity.this, logged_in.class);
                        startActivity(secondactivity1);

                    } else {
                        pg.setVisibility((View.GONE));
                        passwd.setError("Wrong password");
                        passwd.requestFocus();
                    }
                }
                else
                {
                    pg.setVisibility((View.GONE));
                    enterphone.setError("No such user exist");
                    enterphone.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/


}