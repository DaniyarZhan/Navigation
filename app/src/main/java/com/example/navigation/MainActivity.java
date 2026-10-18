package com.example.navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ImageView imageMap, imageApps, imageMail;
    private SeekBar seekMap;
    private RatingBar ratingApps;
    private LinearLayout mailControls;
    private CheckBox checkBoxCopy;
    private Button buttonSend;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageMap  = findViewById(R.id.image_map);
        imageApps = findViewById(R.id.image_apps);
        imageMail = findViewById(R.id.image_mail);

        seekMap = findViewById(R.id.seek_map);
        ratingApps = findViewById(R.id.rating_apps);
        mailControls = findViewById(R.id.mail_controls);
        checkBoxCopy = findViewById(R.id.checkbox_copy);
        buttonSend = findViewById(R.id.button_send);
        editTextEmail = findViewById(R.id.edittext_mail);

        seekMap.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                float scale = 0.5f + progress / 100f; // 0.5x … 1.5x
                imageMap.setScaleX(scale);
                imageMap.setScaleY(scale);
            }
            @Override public void onStartTrackingTouch(SeekBar bar) {}
            @Override public void onStopTrackingTouch(SeekBar bar) {}
        });

        ratingApps.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                Toast.makeText(this,
                        "Выбрано звёзд: " + (int) rating,
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonSend.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            boolean valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

            String msg;
            if (!valid) {
                msg = "Некорректный адрес";
            } else if (checkBoxCopy.isChecked()) {
                msg = "Отправлено (копия себе) на " + email;
            } else {
                msg = "Отправлено на " + email;
            }
            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show();
        });

        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            boolean map  = id == R.id.navigation_map;
            boolean apps = id == R.id.navigation_apps;
            boolean mail = id == R.id.navigation_mail;

            imageMap.setVisibility(map  ? View.VISIBLE : View.GONE);
            seekMap.setVisibility(map   ? View.VISIBLE : View.GONE);

            imageApps.setVisibility(apps ? View.VISIBLE : View.GONE);
            ratingApps.setVisibility(apps ? View.VISIBLE : View.GONE);

            imageMail.setVisibility(mail ? View.VISIBLE : View.GONE);
            mailControls.setVisibility(mail ? View.VISIBLE : View.GONE);

            return true;
        });

    }
}