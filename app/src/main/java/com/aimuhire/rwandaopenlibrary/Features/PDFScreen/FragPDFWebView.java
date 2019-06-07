package com.aimuhire.rwandaopenlibrary.Features.PDFScreen;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.aimuhire.rwandaopenlibrary.MainActivity;
import com.aimuhire.rwandaopenlibrary.R;
import com.aimuhire.rwandaopenlibrary.RootConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FragPDFWebView extends Fragment {
private  WebView pdfView;
private String mPdfPath;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            this.mPdfPath = getArguments().getString("pdf");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pdfwebview, container, false);
        pdfView = view.findViewById(R.id.wvPDFJSView);
        pdfView.setWebViewClient(new WebViewClient());
        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url="+ RootConstants.WEB_ROOT+"/"+this.mPdfPath);
return view;
    }


}
