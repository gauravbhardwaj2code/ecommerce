package com.lms.exam.activities.pdfviewer;

import android.net.Uri;
import android.os.Bundle;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.os.FileUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lms.exam.R;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import es.dmoral.toasty.Toasty;

public class PdfViewer extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PRDownloader.initialize(this);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
            downloadPdf(getIntent().getStringExtra("url"),
                    getRootDirPath(),
                    "temp.pdf"
            );



    }


    private String getRootDirPath() {
         if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
             File file = ContextCompat.getExternalFilesDirs(
                    this.getApplicationContext(),
                    null
            )[0];
            return  file.getAbsolutePath();
        } else {
            return this.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    private void downloadPdf(String url,String dirPath,String fileName){
        PRDownloader.download(
                url,
                dirPath,
                fileName
        ).build()
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        File downloadedFile = new File(dirPath, fileName);
                        progressBar.setVisibility( View.GONE);
                        showPdfFromFile(downloadedFile);
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(
                                PdfViewer.this,
                        "Error in downloading file : $error",
                                Toast.LENGTH_LONG
                )
                    .show();
                    }
                });
    }

    private void showPdfFromFile(File downloadedFile) {

        PDFView pdfView =  findViewById(R.id.pdfView);
        pdfView.fromFile(downloadedFile)
                .password(null)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(
                                PdfViewer.this,
                                "Error at page", Toast.LENGTH_LONG
                        ).show();
                    }
                })
        .load();
    }

}
