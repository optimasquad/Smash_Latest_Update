package smash.app.com.smash;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView previousDay;
    private ImageView nextDay;
    private TextView currentDate;
    private Calendar cal = Calendar.getInstance();

    private RelativeLayout mLayout;
    private int eventIndex;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String state = getIntent().getStringExtra("STATE");
        database = AppDatabase.getDatabase(getApplicationContext());
        mLayout = (RelativeLayout) findViewById(R.id.left_event_column);
        eventIndex = mLayout.getChildCount();
        currentDate = (TextView) findViewById(R.id.display_current_date);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
        previousDay = (ImageView) findViewById(R.id.previous_day);
        nextDay = (ImageView) findViewById(R.id.next_day);
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousCalendarDate();
            }
        });
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCalendarDate();
            }
        });
    }

    private void previousCalendarDate() {
        mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }

    private void nextCalendarDate() {
        mLayout.removeViewAt(eventIndex - 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        currentDate.setText(displayDateInString(cal.getTime()));
        displayDailyEvents();
    }

    private String displayDateInString(Date mDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
        return formatter.format(mDate);
    }

    private void displayDailyEvents() {
        Date calendarDate = cal.getTime();
        List<EventObjects> dailyEvent = getAllFutureEvents(calendarDate);
        for (EventObjects eObject : dailyEvent) {
            Date eventDate = new Date();
            Date endDate = new Date();
            String eventMessage = eObject.getMessage();
            int eventBlockHeight = getEventTimeFrame(eventDate, endDate);
            Log.d(TAG, "Height " + eventBlockHeight);
            displayEventSection(eventDate, eventBlockHeight, eventMessage);
        }
    }

    private int getEventTimeFrame(Date start, Date end) {
        long timeDifference = end.getTime() - start.getTime();
        Calendar mCal = Calendar.getInstance();
        mCal.setTimeInMillis(timeDifference);
        int hours = mCal.get(Calendar.HOUR);
        int minutes = mCal.get(Calendar.MINUTE);
        return (hours * 60) + ((minutes * 60) / 100);
    }

    private void displayEventSection(Date eventDate, int height, String message) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        String displayValue = timeFormatter.format(eventDate);
        String[] hourMinutes = displayValue.split(":");
        int hours = Integer.parseInt(hourMinutes[0]);
        int minutes = Integer.parseInt(hourMinutes[1]);
        Log.d(TAG, "Hour value " + hours);
        Log.d(TAG, "Minutes value " + minutes);
        int topViewMargin = (hours * 60) + ((minutes * 60) / 100);
        Log.d(TAG, "Margin top " + topViewMargin);
        createEventView(topViewMargin, height, message);
    }

    private void createEventView(int topMargin, int height, String message) {
        TextView mEventView = new TextView(MainActivity.this);
        RelativeLayout.LayoutParams lParam = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lParam.topMargin = topMargin * 2;
        lParam.leftMargin = 24;
        mEventView.setLayoutParams(lParam);
        mEventView.setPadding(24, 0, 24, 0);
        mEventView.setHeight(height * 2);
        mEventView.setGravity(0x11);
        mEventView.setTextColor(Color.parseColor("#ffffff"));
        mEventView.setText(message);
        mEventView.setBackgroundColor(Color.parseColor("#3F51B5"));
        mLayout.addView(mEventView, eventIndex - 1);
    }


    public List<EventObjects> getAllFutureEvents(Date mDate) {
        Calendar calDate = Calendar.getInstance();
        Calendar dDate = Calendar.getInstance();
        calDate.setTime(mDate);
        List<EventObjects> eventList = new ArrayList<EventObjects>();
        int calDay = calDate.get(Calendar.DAY_OF_MONTH);
        int calMonth = calDate.get(Calendar.MONTH) + 1;
        int calYear = calDate.get(Calendar.YEAR);


        List<EventObjects> events = database.loginDao().getAllEvents();

        if (events.size() == 0) {
            String dateInString = "Friday, Jun 7, 2013 12:10:56 PM";
            String dateInString1 = "Friday, Jun 7, 2013 13:10:56 PM";

            database.loginDao().addEntity(new EventObjects(2, "abcd", dateInString, dateInString1));

        }
        return events;



/*
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                String startDate = cursor.getString(cursor.getColumnIndexOrThrow("reminder"));
                String endDate = cursor.getString(cursor.getColumnIndexOrThrow("end"));
                //convert start date to date object
                Date reminderDate = convertStringToDate(startDate);
                Date end = convertStringToDate(endDate);
                dDate.setTime(reminderDate);
                int dDay = dDate.get(Calendar.DAY_OF_MONTH);
                int dMonth = dDate.get(Calendar.MONTH) + 1;
                int dYear = dDate.get(Calendar.YEAR);

                if (calDay == dDay && calMonth == dMonth && calYear == dYear) {
                    events.add(new EventObjects(id, message, reminderDate, end));
                }
            } while (cursor.moveToNext());
        }*/
       /* cursor.close();
        return events;*/
    }

    private Date convertStringToDate(String dateInString) {
        DateFormat format = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}