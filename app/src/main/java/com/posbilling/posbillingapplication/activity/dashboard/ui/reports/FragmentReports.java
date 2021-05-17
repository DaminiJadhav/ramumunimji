package com.posbilling.posbillingapplication.activity.dashboard.ui.reports;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.posbilling.posbillingapplication.R;
import com.posbilling.posbillingapplication.activity.dashboard.ActivityDashboard;
import com.posbilling.posbillingapplication.lib.BaseFragment;
import com.posbilling.posbillingapplication.lib.BasePresenter;
import com.posbilling.posbillingapplication.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FragmentReports extends BaseFragment {
    private Context mContext;
    private String languageCode = "en";
    private String language = "en";


    @BindView(R.id.any_chart_view)
    AnyChartView any_chart_view;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_reports;
    }

    @Override
    public void onMakeServerCalls() {
        ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.menu_reports));
        ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(false,1);

    }

    @Override
    public void onStart() {
        super.onStart();
      //  setChart();
    }

    private void setChart() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Jan", 80540));
        data.add(new ValueDataEntry("Feb", 94190));
        data.add(new ValueDataEntry("March", 102610));
        data.add(new ValueDataEntry("April", 110430));
        data.add(new ValueDataEntry("May", 128000));
        data.add(new ValueDataEntry("June", 143760));
        data.add(new ValueDataEntry("July", 170670));
        data.add(new ValueDataEntry("Aug", 213210));
        data.add(new ValueDataEntry("Sept", 249980));
        data.add(new ValueDataEntry("Oct", 170670));
        data.add(new ValueDataEntry("Nov", 213210));
        data.add(new ValueDataEntry("Dec", 249980));

        Column column = cartesian.column(data);

        /*column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");*/

        cartesian.animation(false);
        cartesian.title("Past 12 months record");

        /*cartesian.yScale().minimum(0d);*/
/*
        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");*/

        /*cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);*/

        cartesian.xAxis(0).title("Month");
        cartesian.yAxis(0).title("Revenue");

        any_chart_view.setChart(cartesian);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext == null) {
            mContext = ((ActivityDashboard) getContext());
        }
        languageCode = Utility.getInstance().getLanguage(mContext);
        ((ActivityDashboard) getContext()).setTitleOfScreen(getString(R.string.menu_reports));
        ((ActivityDashboard) getContext()).setVisibilityofRamumunimjiVisible(false,1);

    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }
}