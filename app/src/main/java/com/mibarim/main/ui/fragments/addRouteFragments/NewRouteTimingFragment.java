package com.mibarim.main.ui.fragments.addRouteFragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.mibarim.main.BootstrapApplication;
import com.mibarim.main.R;
import com.mibarim.main.activities.AddMapActivity;
import com.mibarim.main.models.enums.TimingOptions;
import com.mibarim.main.util.JalaliCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hamed on 3/5/2016.
 */
public class NewRouteTimingFragment extends Fragment implements View.OnTouchListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ScrollView timingScrolLayout;
    private int currentDialog;
    private TimePickerDialog timeDialog;
    private DatePickerDialog dialog;
    private static final String DATEPICKER = "DatePickerDialog";

    @Bind(R.id.today_layout)
    protected LinearLayout today_layout;
    @Bind(R.id.indate_layout)
    protected LinearLayout indate_layout;
    @Bind(R.id.weekly_layout)
    protected LinearLayout weekly_layout;

    @Bind(R.id.fa_today)
    protected AwesomeTextView fa_today;
    @Bind(R.id.fa_in_date)
    protected AwesomeTextView fa_in_date;
    @Bind(R.id.fa_weekly)
    protected AwesomeTextView fa_weekly;
    @Bind(R.id.fa_today_icon)
    protected AwesomeTextView fa_today_icon;
    @Bind(R.id.fa_in_date_icon)
    protected AwesomeTextView fa_in_date_icon;
    @Bind(R.id.fa_weekly_icon)
    protected AwesomeTextView fa_weekly_icon;


    @Bind(R.id.label_today)
    protected TextView label_today;
    @Bind(R.id.label_in_date)
    protected TextView label_in_date;
    @Bind(R.id.label_weekly)
    protected TextView label_weekly;


    @Bind(R.id.time_today)
    protected EditText time_today;
    @Bind(R.id.date_in_date)
    protected EditText date_in_date;
    @Bind(R.id.time_in_date)
    protected EditText time_in_date;
    @Bind(R.id.time_saturday)
    protected EditText time_saturday;
    @Bind(R.id.time_sunday)
    protected EditText time_sunday;
    @Bind(R.id.time_monday)
    protected EditText time_monday;
    @Bind(R.id.time_tuesday)
    protected EditText time_tuesday;
    @Bind(R.id.time_wednesday)
    protected EditText time_wednesday;
    @Bind(R.id.time_thursday)
    protected EditText time_thursday;
    @Bind(R.id.time_friday)
    protected EditText time_friday;

    public NewRouteTimingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        time_today.setOnTouchListener(this);
        time_in_date.setOnTouchListener(this);
        date_in_date.setOnTouchListener(this);
        time_saturday.setOnTouchListener(this);
        time_sunday.setOnTouchListener(this);
        time_monday.setOnTouchListener(this);
        time_tuesday.setOnTouchListener(this);
        time_wednesday.setOnTouchListener(this);
        time_thursday.setOnTouchListener(this);
        time_friday.setOnTouchListener(this);

        label_today.setOnTouchListener(this);
        label_in_date.setOnTouchListener(this);
        label_weekly.setOnTouchListener(this);
        fa_today_icon.setOnTouchListener(this);
        fa_in_date_icon.setOnTouchListener(this);
        fa_weekly_icon.setOnTouchListener(this);

        indate_layout.setVisibility(LinearLayout.GONE);
        today_layout.setVisibility(LinearLayout.GONE);
        weekly_layout.setVisibility(LinearLayout.GONE);


        UnSelectAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        timingScrolLayout = (ScrollView) inflater.inflate(R.layout.fragment_newroute_timing, container, false);
        return timingScrolLayout;
    }

    protected void UnSelectAll() {
        fa_today.setVisibility(View.GONE);
        fa_in_date.setVisibility(View.GONE);
        fa_weekly.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.label_today :
                case R.id.fa_today_icon:
                    UnSelectAll();
                    if (today_layout.isShown()) {
                        collapseLayers();
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = null;
                    } else {
                        collapseLayers();
                        today_layout.setVisibility(LinearLayout.VISIBLE);
                        fa_today.setVisibility(View.VISIBLE);
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = TimingOptions.Today;
                    }
                    break;
                case R.id.label_in_date:
                case R.id.fa_in_date_icon:
                    UnSelectAll();
                    if (indate_layout.isShown()) {
                        collapseLayers();
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = null;
                    } else {
                        collapseLayers();
                        indate_layout.setVisibility(LinearLayout.VISIBLE);
                        fa_in_date.setVisibility(View.VISIBLE);
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = TimingOptions.InDateAndTime;
                    }
                    break;
                case R.id.label_weekly:
                case R.id.fa_weekly_icon:
                    UnSelectAll();
                    if (weekly_layout.isShown()) {
                        collapseLayers();
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = null;
                    } else {
                        collapseLayers();
                        weekly_layout.setVisibility(LinearLayout.VISIBLE);
                        fa_weekly.setVisibility(View.VISIBLE);
                        ((AddMapActivity) getActivity()).routeRequest.TimingOption = TimingOptions.Weekly;
                    }
                    break;
                case R.id.time_today:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_today;
                    break;
                case R.id.date_in_date:
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.setThemeDark(true);
                    dpd.show(getActivity().getFragmentManager(), DATEPICKER);
                    currentDialog = R.id.date_in_date;
                    break;
                case R.id.time_in_date:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_in_date;
                    break;
                case R.id.time_saturday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_saturday;
                    break;
                case R.id.time_sunday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_sunday;
                    break;
                case R.id.time_monday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_monday;
                    break;
                case R.id.time_tuesday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_tuesday;
                    break;
                case R.id.time_wednesday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_wednesday;
                    break;
                case R.id.time_thursday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_thursday;
                    break;
                case R.id.time_friday:
                    timeDialog = new TimePickerDialog(getActivity(), this, hour, min, true);
                    timeDialog.show();
                    currentDialog = R.id.time_friday;
                    break;

            }
            return true;
        }
        return false;
    }

    private void collapseLayers() {
        today_layout.setVisibility(LinearLayout.GONE);
        indate_layout.setVisibility(LinearLayout.GONE);
        weekly_layout.setVisibility(LinearLayout.GONE);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        switch (currentDialog) {
            case R.id.date_in_date:
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                Date theDate = new JalaliCalendar().getGregorianDate(date);
                ((AddMapActivity) getActivity()).routeRequest.TheDate = theDate;
                date_in_date.setText(date);
                //Toaster.showLong(getActivity(), "date_in_date" + year + monthOfYear + dayOfMonth);
                break;
        }

    }

//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        switch (currentDialog) {
            case R.id.time_today:
                ((AddMapActivity) getActivity()).routeRequest.TheTime = calendar;
                ((AddMapActivity) getActivity()).routeRequest.TheDate = calendar.getTime();
                time_today.setText(hourOfDay + ":" + minute);
                //Toaster.showLong(getActivity(), "time_today" + hourOfDay + minute);
                break;
            case R.id.time_in_date:
                ((AddMapActivity) getActivity()).routeRequest.TheTime = calendar;
                ((AddMapActivity) getActivity()).routeRequest.TheDate = calendar.getTime();
                time_in_date.setText(hourOfDay + ":" + minute);
                //Toaster.showLong(getActivity(), "time_in_date" + hourOfDay + minute);
                break;
            case R.id.time_saturday:
                ((AddMapActivity) getActivity()).routeRequest.SatDatetime = calendar;
                time_saturday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_sunday:
                ((AddMapActivity) getActivity()).routeRequest.SunDatetime = calendar;
                time_sunday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_monday:
                ((AddMapActivity) getActivity()).routeRequest.MonDatetime = calendar;
                time_monday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_tuesday:
                ((AddMapActivity) getActivity()).routeRequest.TueDatetime = calendar;
                time_tuesday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_wednesday:
                ((AddMapActivity) getActivity()).routeRequest.WedDatetime = calendar;
                time_wednesday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_thursday:
                ((AddMapActivity) getActivity()).routeRequest.ThuDatetime = calendar;
                time_thursday.setText(hourOfDay + ":" + minute);
                break;
            case R.id.time_friday:
                ((AddMapActivity) getActivity()).routeRequest.FriDatetime = calendar;
                time_friday.setText(hourOfDay + ":" + minute);
                break;
        }
    }

}
