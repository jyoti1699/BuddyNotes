package com.example.buddynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class Preview extends AppCompatActivity {

    private static PDFView pdfView;
    TextView textView;
    String url;
    Button dwnbtn,fullview;
    static ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        pg = findViewById(R.id.pgbar);
        pg.setVisibility((View.VISIBLE));

        String file = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        textView = findViewById(R.id.name);
        String fileExtension = ".pdf";
        textView.setText(file);
        pdfView = findViewById(R.id.pdfView);
        new RetrievePDFStream().execute(url);
        dwnbtn=findViewById(R.id.dwnbtn);
        fullview=findViewById(R.id.fullview);

        fullview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondactivity1 = new Intent(Preview.this, fullpreview.class);
                secondactivity1.putExtra("url",url);
                startActivity(secondactivity1);
            }
        });

        dwnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager downloadManager;
                Context context = Preview.this;
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalFilesDir(context, DIRECTORY_DOWNLOADS, file+fileExtension);
                downloadManager.enqueue(request);

            }
        });
    }
    static class RetrievePDFStream extends AsyncTask<String,Void,InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream=null;

            try{

                URL urlx=new URL(strings[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) urlx.openConnection();
                if(urlConnection.getResponseCode()==200){
                    inputStream=new BufferedInputStream(urlConnection.getInputStream());
                }
            }catch (IOException e){
                return null;
            }

            return inputStream;

        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream).load();
            pg.setVisibility((View.GONE));

        }

    } //viewing or streaming the pdf directly from firebase

}