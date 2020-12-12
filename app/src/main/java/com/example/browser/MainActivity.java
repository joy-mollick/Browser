package com.example.browser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    EditText editText;
    ProgressBar progressBar;
    ImageButton back, forward, stop, refresh, homeButton,historyButton;
    ImageButton gmail,instra,faceb,yutub,whtsapp;
    Button goButton;
    private LinearLayout parentLinearLayout,parentLinearLayout1,parentLinearLayout2,parentLinearLayout3;
    Data hist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            gmail = (ImageButton) findViewById(R.id.gmail);
            instra = (ImageButton) findViewById(R.id.instragam);
            faceb = (ImageButton) findViewById(R.id.facebook);
            yutub = (ImageButton) findViewById(R.id.youtube);
            whtsapp=(ImageButton) findViewById(R.id.whatsapp);


            final MyDBFunctions mf = new MyDBFunctions(getApplicationContext());

            editText = (EditText) findViewById(R.id.web_address_edit_text);
            back = (ImageButton) findViewById(R.id.back_arrow);
            forward = (ImageButton) findViewById(R.id.forward_arrow);
            stop = (ImageButton) findViewById(R.id.stop);
            goButton = (Button) findViewById(R.id.go_button);
            refresh = (ImageButton) findViewById(R.id.refresh);
            homeButton = (ImageButton) findViewById(R.id.home);
            historyButton = (ImageButton) findViewById(R.id.history);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            progressBar.setMax(100);
            progressBar.setVisibility(View.VISIBLE);
            webView = (WebView) findViewById(R.id.web_view);

            if (savedInstanceState != null) {
                webView.restoreState(savedInstanceState);
            } else {
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setSupportMultipleWindows(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.setBackgroundColor(Color.WHITE);

                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        progressBar.setProgress(newProgress);
                        if (newProgress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                            progressBar.setVisibility(ProgressBar.VISIBLE);
                        }
                        if (newProgress == 100) {
                            progressBar.setVisibility(ProgressBar.GONE);
                        } else {
                            progressBar.setVisibility(ProgressBar.VISIBLE);
                        }
                    }
                });
            }

            webView.setWebViewClient(new MyWebViewClient());
            gmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("https://mail.google.com");
                }
            });
            yutub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("https://www.youtube.com");
                }
            });
            faceb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("https://web.facebook.com");
                }
            });
            instra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("https://www.instagram.com");
                }
            });
            whtsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("https://www.whatsapp.com");
                }
            });

            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if (!NetworkState.connectionAvailable(MainActivity.this)) {
                            Toast.makeText(MainActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
                        } else {

                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                            webView.loadUrl("https://" + editText.getText().toString());
                            editText.setText("");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    }
                }
            });

            forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView.canGoForward()) {
                        webView.goForward();
                    }
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.stopLoading();
                }
            });

            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.reload();
                }
            });
            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.loadUrl("http://google.com");
                }
            });

            historyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, History.class);
                    startActivity(i);
                    finish();
                }
            });

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    DataTemp dt = new DataTemp(webView.getOriginalUrl(), currentDateandTime);
                    mf.addingDataToTable(dt);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    // TODO Auto-generated method stub
                    super.onLoadResource(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            webView.stopLoading();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
