package com.posbilling.posbillingapplication.activity.contactus;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.lib.BaseActivity;
import com.posbilling.posbillingapplication.lib.BasePresenter;

import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivityContactUs extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @OnClick(R.id.contact_whatsapp)
    void contact_whatsapp(){
        sendwhatsappMessage();
    }

    private void sendwhatsappMessage() {
       /* PackageManager packageManager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone="+ "enter mobile number here" +"&text=" + URLEncoder.encode("message", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_contact_us;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("Contact us");
    }
}
