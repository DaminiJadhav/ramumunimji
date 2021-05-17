package com.posbilling.posbillingapplication.activity.dashboard.ui.help;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.activity.homeactivitytwo.HomeActivityTwo;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;

import java.net.URLEncoder;

import butterknife.OnClick;

/**
 * Created by Android PC (Ankur) on 28,February,2020
 */
public class FragmentHelp extends BaseFragment {
    private Context mContext;

    @OnClick(R.id.contact_whatsapp)
    void contact_whatsapp() {
        sendwhatsappMessage();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext != null) {
            mContext = ((HomeActivityTwo) getContext());
        }
       /* ((HomeActivityTwo) getContext()).setTitleOfScreen(getString(R.string.menu_help));
        ((HomeActivityTwo) getContext()).setVisibilityofRamumunimjiVisible(false, 1);*/

    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_help;
    }

    @Override
    public void onMakeServerCalls() {

    }

    private void sendwhatsappMessage() {
/*        PackageManager packageManager = mContext.getPackageManager();
        Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + "+enter mobile number here" + "&text=" + URLEncoder.encode("message", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
