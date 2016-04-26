package com.gmail.xiifwat.salattimes.library;

import android.content.Context;
import android.util.Log;

import com.gmail.xiifwat.salattimes.database.DataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NextThreeDays {

    private Date dateJ;

    private List<Model> data = new ArrayList<>();


    public NextThreeDays(Context context, Date date, int add) {

        SalatTimeCalculator salatTimeCalculator;
        double latitude;
        double longitude;

        DataSource dataSource = new DataSource(context);
        dataSource.open();
        dataSource.insertToLocation();

        List<Double> list = dataSource.getLocationCoordinates();
        dataSource.close();

        latitude = list.get(0);
        longitude = list.get(1);
        this.dateJ = date;

        Calendar c = Calendar.getInstance();
        c.setTime(dateJ);
        c.add(Calendar.DATE, add);
        dateJ = c.getTime();

        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, this.dateJ);
        ArrayList<String> prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ));



        c = Calendar.getInstance();
        c.setTime(dateJ);
        c.add(Calendar.DATE, 1);
        dateJ = c.getTime();

        prayTime.clear();
        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, dateJ);
        prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ));

        c.add(Calendar.DATE, 1);
        dateJ = c.getTime();

        prayTime.clear();
        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, dateJ);
        prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ));
    }

    public List<Model> getNextThreeDays() {
        return data;
    }

    private Model arrayListToModel(ArrayList<String> prayTime, Date date) {

        SimpleDateFormat df2 = new SimpleDateFormat("MMMM dd, yyyy"); // November 1, 2014
        String dateText = df2.format(date);

        Model model = new Model(prayTime.get(0), prayTime.get(1), prayTime.get(2),
                prayTime.get(3), prayTime.get(4), prayTime.get(5), dateText, "arabic date");

        return model;
    }
}
