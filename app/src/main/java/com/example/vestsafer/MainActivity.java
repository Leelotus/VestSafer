package com.example.vestsafer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText IdText, PasswordText;
    TextView registerbutton;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IdText = (EditText) findViewById(R.id.IdText);
        PasswordText = (EditText) findViewById(R.id.PasswordText);
        button = (Button) findViewById(R.id.button);
        registerbutton = (TextView) findViewById(R.id.registerbutton);

        registerbutton.setOnClickListener(view -> {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
            MainActivity.this.startActivity(registerIntent);
        });

        button.setOnClickListener(view -> {
           final String userID = IdText.getText().toString();
           final String userPassword= PasswordText.getText().toString();

            Response.Listener<String> responseListener = response -> {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success)
                    {
                        String userID1 = jsonResponse.getString("userID");
                        String userPassword1 = jsonResponse.getString("userPassword");

                        if (!userID1.equals("admin"))
                        {
                            Toast.makeText(getApplicationContext(), "????????? ????????? ??????", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, WelcomActivity.class);
                            intent.putExtra("userID", userID1);
                            intent.putExtra("userPassword", userPassword1);
                            MainActivity.this.startActivity(intent);


                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "????????? ????????? ??????", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, adminActivitiy.class);
                            intent.putExtra("userID", userID1);
                            intent.putExtra("userPassword", userPassword1);
                            MainActivity.this.startActivity(intent);
                        }
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("???????????? ?????????????????????.")
                                .setNegativeButton("??????",null)
                                .create()
                                .show();
                        return;
                    }

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }
            };
            LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);
        });

    }
}