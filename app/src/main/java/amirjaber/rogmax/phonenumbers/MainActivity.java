package amirjaber.rogmax.phonenumbers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import amirjaber.rogmax.phonenumbers.adapters.CustomAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private final int RECEIVE_SMS_PERMISSION = 1;
    private final int SEND_SMS_PERMISSION = 2;

    private Spinner spinner, spinner2;
    private EditText phoneNumber;
    private Button submit;
    private List<String> ukraineCities = null;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission(Manifest.permission.SEND_SMS, SEND_SMS_PERMISSION);
        askPermission(Manifest.permission.RECEIVE_SMS, RECEIVE_SMS_PERMISSION);

        init();
        initCityArrays();

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries);
        for (String country : countries) {
            Log.d(TAG, "onCreate: " + country);
        }

        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(232);

        Log.d(TAG, "onCreate: Num of countries found" + countries.size());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection1 = String.valueOf(spinner.getSelectedItem());
                showShortToast(selection1 + " " + spinner.getSelectedItemPosition());

                switch (position) {
                    case 232:
                        int hideItem = 0;
                        spinner2.setEnabled(true);
                        CustomAdapter adapter = new CustomAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, ukraineCities, hideItem);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adapter);
                        spinner2.setSelection(1);

                        break;
                    default:
                        spinner2.setSelection(0);
                        spinner2.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner2.setSelection(position);
                String selection2 = String.valueOf(spinner2.getSelectedItem());
                showShortToast(selection2 + " " + spinner2.getSelectedItemPosition());
                ((TextView) view).setTextColor(Color.RED);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempNum = phoneNumber.getText().toString();

                if (tempNum.matches("")) {
                    num = "+380632945376";
                } else {
                    num = tempNum;
                }

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(num, null, "Message received: Country: " + spinner.getSelectedItem().toString() + ", City " + spinner2.getSelectedItem().toString(), null, null);
                    showShortToast("Message sent!");
                } catch (Exception e) {
                    showShortToast("SMS failed, please try again later!");
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case SEND_SMS_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Sms permission granted (Send)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sms permission denied (Send)", Toast.LENGTH_SHORT).show();
                }
            case RECEIVE_SMS_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Sms permission granted (Receive)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Sms permission denied (Receive)", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void init() {
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);

        phoneNumber = findViewById(R.id.phone_number);
        submit = findViewById(R.id.btn_submit);
    }

    public void initCityArrays() {
        ukraineCities = new ArrayList<>();
        ukraineCities.add(" ");
        ukraineCities.add("Vinnytsia");
        ukraineCities.add("Kiev");
        ukraineCities.add("Odessa");
        ukraineCities.add("Kharkov");
        ukraineCities.add("Lvov");
        ukraineCities.add("Dnepro");
    }

    private void askPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // go ahead and ask for permission
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(this, "Permission already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
