package com.posbilling.posbillingapplication.activity.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.utility.Utility;

import butterknife.BindView;

public class ActivityWebview extends BaseActivity {
    private String urltoparse;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_webview;
    }

    @Override
    protected void setPresenter() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utility.getInstance().showProgressDialogue(ActivityWebview.this);
        //ll_title.setBackgroundColor(Color.parseColor("#000000"));
        Intent intent = getIntent();
        if (intent.getStringExtra("from") != null) {
            if (intent.getStringExtra("from").equalsIgnoreCase("termsandcondition")) {
                toolbar_title.setText("Terms and Conditions");

                urltoparse = intent.getStringExtra("URLParse");
                loadPrivacyPDF();

            } else if (intent.getStringExtra("from").equalsIgnoreCase("privacypolicy")) {
                toolbar_title.setText("Privacy Policy");
                urltoparse = intent.getStringExtra("URLParse1");
                loadPrivacyPDF();
            }
            else if(intent.getStringExtra("from").equalsIgnoreCase("datausagepolicy")) {
                toolbar_title.setText("Data Usage Policy");
                urltoparse = intent.getStringExtra("URLParse2");
                loadPrivacyPDF();
            }
        }
        // textView_text.setText(Html.fromHtml(urltoparse));
    }


    private void loadPrivacyPDF() {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(urltoparse); // https://app.termly.io/document/terms-of-use-for-saas/16fb44b1-2268-4943-9f17-280a23a84e8d
        //webview.loadUrl("https://www.gositelink.com/terms-privacy");
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setLoadWithOverviewMode(true);
//        webview.getSettings().setUseWideViewPort(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //     Utility.getInstance().showProgressDialogue(ActivityWebView.this);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Utility.getInstance().dismissProgress();
            }
        });
    }
}
