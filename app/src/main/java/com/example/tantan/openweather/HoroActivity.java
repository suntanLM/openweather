package com.example.tantan.openweather;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class HoroActivity extends AppCompatActivity {
    private static final String URL = "http://lichvansu.wap.vn/tu-vi-hang-ngay-12-cung-hoang-dao-";
    private Button btnDate;
    private TextView tvDate;
    private Calendar cal;
    private Date date;
    private static ImageView horoIcon;
    private static final String SHARED_PREFERENCE_NAME = "BIRTHDAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horo);
        btnDate = (Button) findViewById(R.id.btn_changeDate);
        btnDate.setOnClickListener(showDatepicker);
        tvDate = (TextView) findViewById(R.id.tv_bd);
        horoIcon = (ImageView) findViewById(R.id.iv_horoicon);
        setNoti();
        //start up date
        cal = Calendar.getInstance();
        date = cal.getTime();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        int day = sharedPreferences.getInt("DAY",date.getDate());
        int month = sharedPreferences.getInt("MONTH",date.getMonth());
        int year = sharedPreferences.getInt("YEAR",date.getYear()+1900);
        tvDate.setText(day+"/"+(month+1)+"/"+year);
        date.setDate(day);
        date.setMonth(month);
        date.setYear(year);
        load(date);

    }

    private void load(Date d) {
        int day = d.getDate();
        int month = d.getMonth() + 1;
        if (month == 1) {
            if (day > 19) {
                new DownloadTask(this).execute(URL + "bao-binh.html");
            } else {
                new DownloadTask(this).execute(URL + "ma-ket.html");
            }
        } else if (month == 2) {
            if (day > 18) {
                new DownloadTask(this).execute(URL + "song-ngu.html");
            } else {
                new DownloadTask(this).execute(URL + "bao-binh.html");
            }
        } else if (month == 3) {
            if (day > 20) {
                new DownloadTask(this).execute(URL + "bach-duong.html");
            } else {
                new DownloadTask(this).execute(URL + "song-ngu.html");
            }
        } else if (month == 4) {
            if (day > 19) {
                new DownloadTask(this).execute(URL + "kim-nguu.html");
            } else {
                new DownloadTask(this).execute(URL + "bach-duong.html");
            }
        } else if (month == 5) {
            if (day > 20) {
                new DownloadTask(this).execute(URL + "song-tu.html");
            } else {
                new DownloadTask(this).execute(URL + "kim-nguu.html");
            }
        } else if (month == 6) {
            if (day > 21) {
                new DownloadTask(this).execute(URL + "cu-giai.html");
            } else {
                new DownloadTask(this).execute(URL + "song-tu.html");
            }
        } else if (month == 7) {
            if (day > 22) {
                new DownloadTask(this).execute(URL + "su-tu.html");
            } else {
                new DownloadTask(this).execute(URL + "cu-giai.html");
            }
        } else if (month == 8) {
            if (day > 22) {
                new DownloadTask(this).execute(URL + "xu-nu.html");
            } else {
                new DownloadTask(this).execute(URL + "su-tu.html");
            }
        } else if (month == 9) {
            if (day > 22) {
                new DownloadTask(this).execute(URL + "thien-binh.html");
            } else {
                new DownloadTask(this).execute(URL + "xu-nu.html");
            }
        } else if (month == 10) {
            if (day > 23) {
                new DownloadTask(this).execute(URL + "ho-cap.html");
            } else {
                new DownloadTask(this).execute(URL + "thien-binh.html");
            }
        } else if (month == 11) {
            if (day > 21) {
                new DownloadTask(this).execute(URL + "nhan-ma.html");
            } else {
                new DownloadTask(this).execute(URL + "ho-cap.html");
            }
        } else if (month == 12) {
            if (day > 21) {
                new DownloadTask(this).execute(URL + "ma-ket.html");
            } else {
                new DownloadTask(this).execute(URL + "nhan-ma.html");
            }
        }
    }

    View.OnClickListener showDatepicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tvDate.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    cal.set(year, month, dayOfMonth);
                    date = cal.getTime();
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("DAY",dayOfMonth);
                    editor.putInt("MONTH",month);
                    editor.putInt("YEAR",year);
                    editor.apply();
                    load(date);
                }
            };

            String s = tvDate.getText() + "";
            String strArrtmp[] = s.split("/");
            int ngay = Integer.parseInt(strArrtmp[0]);
            int thang = Integer.parseInt(strArrtmp[1]) - 1;
            int nam = Integer.parseInt(strArrtmp[2]);

            DatePickerDialog pic = new DatePickerDialog(
                    HoroActivity.this,
                    callback, nam, thang, ngay);
            pic.setTitle("Chọn ngày sinh:");
            pic.show();

        }
    };

    static class DownloadTask extends AsyncTask<String, Void, Void> {
        private Activity mActivity;
        private String content = "";
        private String horoName;
        private String iconSRC;

        public DownloadTask(Activity activity) {
            mActivity = activity;
        }

        @Override
        protected Void doInBackground(String... params) {
            Document document = null;
            try {
                document = (Document) Jsoup.connect(params[0]).get();
                if (document != null) {
                    Elements elements = document.select("div.inputThongtin").select("p");
                    if (elements != null) {
                        for (int i = 1; i < elements.size() - 2; i++) {
                            String temp = elements.get(i).text();
                            content = content + "\n\t\t" + temp;
                        }

                    }

                    Element title = document.select("div.inputThongtin").select("strong").first();
                    if (title != null) {
                        String t = title.text();
                        horoName = t;
                    }

                    Element icon = document.select("div.inputThongtin").select("img").first();
                    if (icon != null) {
                        String ic = icon.attr("src");
                        iconSRC = ic;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TextView tv = (TextView) mActivity.findViewById(R.id.tv_horo);
            tv.setText(content);

            TextView tvtt = (TextView) mActivity.findViewById(R.id.tv_title);
            tvtt.setText(horoName);

            Log.e("icon", iconSRC);
            Ion.with(horoIcon)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .load("http://lichvansu.wap.vn/" + iconSRC);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            content = "";
        }
    }

    private void setNoti()
    {
        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);

        Calendar calendar = null;
        calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE,38);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),86400000,pendingIntent);
    }
}
