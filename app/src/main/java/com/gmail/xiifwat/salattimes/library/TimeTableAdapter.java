package com.gmail.xiifwat.salattimes.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gmail.xiifwat.salattimes.R;

import java.util.Date;
import java.util.List;

public class TimeTableAdapter extends PagerAdapter
        implements ViewPager.OnPageChangeListener {

    private Activity _activity;
    private List<Model> _data;
    private final String LOGTAG = "tfx_" + TimeTableAdapter.class.getSimpleName();

    public TimeTableAdapter(Activity activity, List<Model> data) {
        _activity = activity;
        _data = data;
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // ____ INFLATE NEW_DESIGN AND ADD TO VIEW PAGER

        LayoutInflater inflater = (LayoutInflater) _activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_date_today, container, false);

        TextView tvDateEnglish = (TextView) view.findViewById(R.id.tvDateEnglish);
        tvDateEnglish.setText(_data.get(position).getDateEnglish());

        TextView tvDayOfWeek = (TextView) view.findViewById(R.id.tvDayOfWeek);
        tvDayOfWeek.setText(_data.get(position).getDayOfWeek());


        LinearLayout llUpper = (LinearLayout) view.findViewById(R.id.ll_upper);

        llUpper.addView(generateView(inflater, "Fajr", _data.get(position).getFajrTime()));
        llUpper.addView(generateView(inflater, "Sunrise", _data.get(position).getSunriseTime()));
        llUpper.addView(generateView(inflater, "Duhr", _data.get(position).getDuhrTime()));

        llUpper.addView(generateView(inflater, "Asr", _data.get(position).getAsrTime()));
        llUpper.addView(generateView(inflater, "Maghrib", _data.get(position).getMaghribTime()));
        llUpper.addView(generateView(inflater, "Ishaa", _data.get(position).getIshaaTime()));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private View generateView(LayoutInflater inflater,
                              final String pName, final String pTime) {

        View singleView = inflater.inflate(R.layout.single_view, null, true);

        TextView tvPrayerName = (TextView) singleView.findViewById(R.id.sv_tv_prayerName);
        tvPrayerName.setText(pName);

        TextView tvPrayerTime = (TextView) singleView.findViewById(R.id.sv_tv_prayerTime);
        tvPrayerTime.setText(pTime);

        ImageView ivAlarmIcon = (ImageView) singleView.findViewById(R.id.sv_iv_alarmIcon);
        ivAlarmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show time picker dialog to set alarm

                String[] x = pTime.split(":");
                String[] y = x[1].split(" ");
                int hour = Integer.parseInt(x[0]);
                int minutes = Integer.parseInt(y[0]);

                if(y[1].endsWith("pm")) hour+=12;

                launchTimePickerDialog(hour, minutes, pName);
            }
        });

        if(pName.equalsIgnoreCase("Sunrise")) ivAlarmIcon.setVisibility(View.GONE);

        return singleView;
    }

    private void launchTimePickerDialogOld(int hour, int minutes, final String prayerName) {

        final int[] alarmTime = new int[2];

        TimePickerDialog.OnTimeSetListener onTimeSetListener
                = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(LOGTAG, "OnTimeSet Hour: " + hourOfDay+ " Minute: " + minute);
                alarmTime[0] = hourOfDay;
                alarmTime[1] = minute;
            }
        };

        TimePickerDialog tpd = new TimePickerDialog(_activity, onTimeSetListener,
                hour, minutes, true);
        tpd.setCancelable(false);
        tpd.setTitle("Set alarm for " + prayerName);

        tpd.setButton(DialogInterface.BUTTON_POSITIVE, "Set",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Launch alarm activity
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                                .putExtra(AlarmClock.EXTRA_MESSAGE, prayerName)
                                .putExtra(AlarmClock.EXTRA_HOUR, alarmTime[0])
                                .putExtra(AlarmClock.EXTRA_MINUTES, alarmTime[1])
                                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                        if (intent.resolveActivity(_activity.getPackageManager()) != null) {
                            _activity.startActivity(intent);
                        }
                    }
                });

        tpd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        tpd.show();
    }


    private void launchTimePickerDialog(int hour, int minutes, final String prayerName) {

        final TimePicker timePicker = new TimePicker(_activity);
        timePicker.setIs24HourView(false);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);

        new AlertDialog.Builder(_activity)
                .setTitle("Set alarm for " + prayerName)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(LOGTAG, timePicker.getCurrentHour() + ":"
                                + timePicker.getCurrentMinute());
                        // Set alarm using alarm intent
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                                .putExtra(AlarmClock.EXTRA_MESSAGE, prayerName)
                                .putExtra(AlarmClock.EXTRA_HOUR, timePicker.getCurrentHour())
                                .putExtra(AlarmClock.EXTRA_MINUTES, timePicker.getCurrentMinute())
                                .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                        if (intent.resolveActivity(_activity.getPackageManager()) != null) {
                            _activity.startActivity(intent);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
//                                Log.d(LOGTAG, "Cancelled!");
                            }
                        }).setView(timePicker).show();
    }

    // ---- METHODS FOR OnPageChangeListener

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    @Override
    public void onPageSelected(int i) {

//        Log.d("tfx", "onPageSelected " + i);

        if(_data.size()- i ==1) {

            Log.d(LOGTAG, "added ");
            _data.addAll(new NextThreeDays(_activity, new Date(), i+1).getNextThreeDays());
            notifyDataSetChanged();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {}
}
