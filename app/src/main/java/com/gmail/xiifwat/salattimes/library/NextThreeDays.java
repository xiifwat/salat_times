package com.gmail.xiifwat.salattimes.library;

import android.content.Context;
import android.content.Intent;

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
        int dayOfWeek;

        DataSource dataSource = new DataSource(context);
        dataSource.open();
        dataSource.setDefaultLocation();

        List<Double> list = dataSource.getLocationCoordinates();
        dataSource.close();

        latitude = list.get(0);
        longitude = list.get(1);
        this.dateJ = date;

        Calendar c = Calendar.getInstance();
        c.setTime(dateJ);
        c.add(Calendar.DATE, add);
        dateJ = c.getTime();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, this.dateJ);
        ArrayList<String> prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ, dayOfWeek));



        c = Calendar.getInstance();
        c.setTime(dateJ);
        c.add(Calendar.DATE, 1);
        dateJ = c.getTime();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        prayTime.clear();
        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, dateJ);
        prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ, dayOfWeek));

        c.add(Calendar.DATE, 1);
        dateJ = c.getTime();
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        prayTime.clear();
        salatTimeCalculator = new SalatTimeCalculator(latitude, longitude, dateJ);
        prayTime = salatTimeCalculator.getFormattedTime();

        data.add(arrayListToModel(prayTime, dateJ, dayOfWeek));
    }

    public List<Model> getNextThreeDays() {
        return data;
    }

    private Model arrayListToModel(ArrayList<String> prayTime, Date date, int dayOfWeek) {

        SimpleDateFormat df2 = new SimpleDateFormat("MMMM dd, yyyy"); // November 1, 2014
        String dateText = df2.format(date);

        // dayOfWeek is a integer code of the day.
        String day;
        if(dayOfWeek==1) day="Sunday";
        else if(dayOfWeek==2) day="Monday";
        else if(dayOfWeek==3) day="Tuesday";
        else if(dayOfWeek==4) day="Wednesday";
        else if(dayOfWeek==5) day="Thursday";
        else if(dayOfWeek==6) day="Friday";
        else day="Saturday";

        Model model = new Model(prayTime.get(0), prayTime.get(1), prayTime.get(2),
                prayTime.get(3), prayTime.get(4), prayTime.get(5), dateText, day);

        return model;
    }
}
