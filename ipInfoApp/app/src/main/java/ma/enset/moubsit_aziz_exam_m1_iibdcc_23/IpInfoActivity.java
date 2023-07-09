package ma.enset.moubsit_aziz_exam_m1_iibdcc_23;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class IpInfoActivity extends AppCompatActivity {
    private LinearLayout container;
    private final List<String> infoList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_info);
        EditText ipInput = findViewById(R.id.IpInput);
        progressBar = findViewById(R.id.progressBar);
        this.container = findViewById(R.id.container);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            infoList.clear();
            container.removeAllViews();
            String ipAddress = ipInput.getText().toString().trim();
            if (ipAddress.isEmpty()) {
                ipInput.setError("IP address is required");
                ipInput.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue requestQueue = Volley.newRequestQueue(IpInfoActivity.this);
            String url = "https://ipinfo.io/" + ipAddress + "/geo";
            @SuppressLint("SetTextI18n") StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String city = jsonObject.getString("city");
                    String region = jsonObject.getString("region");
                    String country = jsonObject.getString("country");

                    infoList.add("City : " + city);
                    infoList.add("Region : " + region);
                    infoList.add("Country : " + country);
                    String latLong = jsonObject.getString("loc");
                    infoList.forEach(text -> {
                        TextView toAdd = new TextView(IpInfoActivity.this);
                        toAdd.setText(text);
                        toAdd.setTextSize(16);
                        toAdd.setTypeface(null, Typeface.BOLD);
                        toAdd.setTextColor(Color.WHITE);
                        toAdd.setBackgroundColor(Color.DKGRAY);
                        int paddingY = 20;
                        toAdd.setPadding(0, paddingY, 0, paddingY);
                        toAdd.setGravity(Gravity.CENTER);
                        int marginBottom = 20;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(0, 0, 0, marginBottom);
                        toAdd.setLayoutParams(layoutParams);
                        container.addView(toAdd);
                    });
                    Button showMapButton = new Button(IpInfoActivity.this);
                    showMapButton.setText("Show Map");
                    int paddingY = 30;
                    showMapButton.setPadding(0, paddingY, 0, paddingY);
                    container.addView(showMapButton);

                    showMapButton.setOnClickListener(view1 -> {
                        Intent Map = new Intent(view.getContext(), MapActivity.class);
                        Map.putExtra("LatLong", latLong);
                        startActivity(Map);
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    progressBar.setVisibility(View.GONE);
                }
            }, error -> Log.e("API Error", error.toString()));
            requestQueue.add(request);
        });
    }
}
