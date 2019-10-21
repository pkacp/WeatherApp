package edu.kacprzak.weatherapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        new GetForecastByIdFromShrPrefAsyncTask(this).execute();

    }

    public void setListViewWithData(HashMap<String, String> data, ArrayList<HashMap> forecastArr){

        final ListView listView = findViewById(R.id.lvForecast);


    }

    public class CustomAdapter extends ArrayAdapter<String>
    {
        private Context context;
        private ArrayList<HashMap> strings;

        public CustomAdapter(Context context, ArrayList<HashMap> strings)
        {
            super(context, R.layout.forecast_row);
            this.context = context;
            this.strings = strings;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.forecast_row, parent, false);

            TextView tvForecastRowDate = rowView.findViewById(R.id.tvForecastRowDate);
            TextView tvForecastRowTemp = rowView.findViewById(R.id.tvForecastRowTemp);
            TextView tvForecastRowDesc = rowView.findViewById(R.id.tvForecastRowDesc);

            tvForecastRowDate.setText(strings.get(position).get("dt").toString());
            tvForecastRowTemp.setText(strings.get(position).get("temp").toString());
            tvForecastRowDesc.setText(strings.get(position).get("descryption").toString());

            return rowView;
        }
    }
}
