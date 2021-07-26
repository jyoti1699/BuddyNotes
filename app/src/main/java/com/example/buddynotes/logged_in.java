package com.example.buddynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buddynotes.AdapterClasses.EAdapter;
import com.example.buddynotes.AdapterClasses.EHelperClass;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class logged_in extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseStorage storage;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    StorageReference ref;
    EditText edtxt;
    RecyclerView productRecycler1;
    RecyclerView.Adapter padapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        final ProgressBar pg = findViewById(R.id.pgbar1);
        pg.setVisibility((View.VISIBLE));

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);

        toolbar=findViewById(R.id.toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_myacc);




        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("uploadPDF");

        productRecycler1 = findViewById(R.id.product_recycler1);
        Query checkUsercred = databaseReference.orderByKey();
        checkUsercred.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Iterable<DataSnapshot> snapshotIterator = snapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    productRecycler1.setHasFixedSize(true); //contain card views..only visible that user loads
                    productRecycler1.setLayoutManager(new LinearLayoutManager(logged_in.this, LinearLayoutManager.VERTICAL, false));
                    ArrayList<EHelperClass> KLocations = new ArrayList<>();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        // listView.("Adrr = " +next.child("add1").getValue());
                        String name = (String) next.child("name").getValue();
                        String url = (String) next.child("url").getValue();
                        // String price = (String) next.child("price").getValue();
                        //String oldprice = (String) next.child("oldprice").getValue();
                        //String resID = (String) next.child("resID").getValue();
                        //int resID = Integer.valueOf(next.child("redID").getValue().toString());
                   /* arrayList.add("Name - "+docname+
                            "\nSpeciality - "+spec+
                            "\nAddress - "+addr+
                            "\nPhone - "+phone+
                            "\nRating - "+drbar);*/

                        KLocations.add(new EHelperClass(name,url));
                        padapter1 = new EAdapter(KLocations);
                        productRecycler1.setAdapter(padapter1);
                        //arrayAdapter = new ArrayAdapter<String>(doc_list.this, android.R.layout.simple_list_item_1, arrayList);
                        // listView.setAdapter(arrayAdapter);
                    }
                    pg.setVisibility((View.GONE));
                }
                else
                {
                    pg.setVisibility((View.GONE));
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pg.setVisibility((View.GONE));
            }
        });




    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_myacc: break;
            case R.id.nav_files:
                break;
            case R.id.nav_upload:
                Intent secondactivity1 = new Intent();
                secondactivity1.setClass(logged_in.this, Upload.class);
                startActivity(secondactivity1);

                break;
            case R.id.nav_aboutus:
                Intent secondactivity4 = new Intent();
                secondactivity4.setClass(logged_in.this, AboutUs.class);
                startActivity(secondactivity4);

                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }
}