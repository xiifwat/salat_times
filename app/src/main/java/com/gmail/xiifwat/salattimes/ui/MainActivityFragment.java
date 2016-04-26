package com.gmail.xiifwat.salattimes.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.xiifwat.salattimes.R;
import com.gmail.xiifwat.salattimes.database.DataSource;
import com.gmail.xiifwat.salattimes.library.Model;
import com.gmail.xiifwat.salattimes.library.NextThreeDays;
import com.gmail.xiifwat.salattimes.library.TimeTableAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivityFragment extends Fragment {

    private List<Model> data = new ArrayList<>();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_timetable, container, false);

        TextView tvCity = (TextView) view.findViewById(R.id.tvCity);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);

        //-------------------------------------------------------

        DataSource dataSource = new DataSource(getActivity());
        dataSource.open();
        dataSource.insertToLocation();

        List<String> list = dataSource.getLocationName();

//        for( int k = 0; k< list.size(); k++) {
//            Log.d("tfx", "- " + list.get(k));
//        }
        dataSource.close();

        tvCity.setText(list.get(1).toUpperCase());
        tvCountry.setText(list.get(0));

        data.addAll(new NextThreeDays(getActivity(), new Date(), 0).getNextThreeDays());
        // ----------------------------------------------------------

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        TimeTableAdapter adapter = new TimeTableAdapter(getActivity(), data);
        mViewPager.setOnPageChangeListener(adapter);
        mViewPager.setAdapter(adapter);

        return view;
    }

//    public void setCountryNameTextView(String s) {
//        tvCountry.setText(list.get(0));
//    }
//
//    public void setCountryNameTextView(String s) {
//
//    }
}
