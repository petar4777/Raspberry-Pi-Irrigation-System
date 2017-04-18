package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The type Sign up activity.
 * This activity allows the user to register a new device. This is done by providing its IP address, admin username, password for t
 */
public class SignUpActivity extends BaseActivity
{
    /**
     * The constant KEY.
     */
    public static String KEY = "PASSWORD";
    /**
     * The constant KEY2.
     */
    public static String KEY2 = "PIN";
    /**
     * The constant KEY3.
     */
    public static String KEY3 = "IP";
    /**
     * The constant KEY4.
     */
    public static String KEY4 = "USERNAME";

    /**
     * The Back.
     */
    Button back;
    /**
     * The Ip.
     */
    EditText ip;
    /**
     * The Username.
     */
    EditText username;
    /**
     * The Username pass.
     */
    EditText usernamePass;
    /**
     * The First pin.
     */
    EditText firstPIN;
    /**
     * The Second pin.
     */
    EditText secondPIN;
    /**
     * The Stop button.
     */
    Button stopButton;
    /**
     * The Username text.
     */
    TextView usernameText;
    /**
     * The Username password text.
     */
    TextView usernamePasswordText;
    /**
     * The Ip text.
     */
    TextView ipText;
    /**
     * The First pin text.
     */
    TextView firstPINText;
    /**
     * The Second pin text.
     */
    TextView secondPINText;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ip = (EditText) findViewById(R.id.editTextIP);
        username = (EditText) findViewById(R.id.editTextUsername);
        usernamePass = (EditText) findViewById(R.id.editTextUsernamePasswordA);
        firstPIN = (EditText) findViewById(R.id.editTextFirstPIN);
        secondPIN = (EditText) findViewById(R.id.editTextSecondPIN);
        stopButton = (Button) findViewById(R.id.SignUpStop);
        usernameText = (TextView) findViewById(R.id.textViewUsername);
        usernamePasswordText = (TextView) findViewById(R.id.textViewUsernamePassword);
        ipText = (TextView) findViewById(R.id.textViewIP);
        firstPINText = (TextView) findViewById(R.id.textViewFirstPin);
        secondPINText = (TextView) findViewById(R.id.textViewSecondPin);
        back = (Button) findViewById(R.id.SignUpBack);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onC.start();
                if(!ip.getText().toString().trim().isEmpty() && !username.getText().toString().trim().isEmpty() && !usernamePass.getText().toString().trim().isEmpty() && !firstPIN.getText().toString().trim().isEmpty() && !secondPIN.getText().toString().trim().isEmpty())
                {
                    saveUserName(firstPIN.getText().toString().trim(), usernamePass.getText().toString().trim(), ip.getText().toString().trim(), username.getText().toString().trim(), SignUpActivity.this);
                    String pincode = getUserName(SignUpActivity.this);
                    Log.e("PINS", pincode);
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                }
                else
                {
                   if(ip.getText().toString().trim().isEmpty())
                   {
                       ipText.setText("Please fill the IP.");
                       ipText.setTextColor(Color.RED);
                   }
                   else
                   {
                       ipText.setText("Enter the IP of your device below:");
                       ipText.setTextColor(Color.parseColor("#2c3e50"));
                   }

                    if(username.getText().toString().trim().isEmpty())
                    {
                        usernameText.setText("Please fill the username.");
                        usernameText.setTextColor(Color.RED);
                    }
                    else
                    {
                        usernameText.setText("Admin username:");
                        usernameText.setTextColor(Color.parseColor("#2c3e50"));
                    }

                    if(usernamePass.getText().toString().trim().isEmpty())
                    {
                        usernamePasswordText.setText("Please fill the username password.");
                        usernamePasswordText.setTextColor(Color.RED);
                    }
                    else
                    {
                        usernamePasswordText.setText("Password:");
                        usernamePasswordText.setTextColor(Color.parseColor("#2c3e50"));
                    }

                    if(firstPIN.getText().toString().trim().isEmpty())
                    {
                        firstPINText.setText("Please enter a 4-digit PIN.");
                        firstPINText.setTextColor(Color.RED);
                    }
                    else
                    {
                        firstPINText.setText("Enter a 4-digit PIN:");
                        firstPINText.setTextColor(Color.parseColor("#2c3e50"));
                    }

                    if(secondPIN.getText().toString().trim().isEmpty())
                    {
                        secondPINText.setText("Please confirm your PIN.");
                        secondPINText.setTextColor(Color.RED);
                    }
                    else
                    {
                        secondPINText.setText("Confirm PIN:");
                        secondPINText.setTextColor(Color.parseColor("#2c3e50"));
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(SignUpActivity.this, ChooseSignOrLogActivity.class));
                onLeaveThisActivity();
            }
        });
    }

    /**
     * Save user name.
     *
     * @param pin      the pin
     * @param password the password
     * @param ip       the ip
     * @param username the username
     * @param context  the context
     */
    public static void saveUserName(String pin, String password, String ip, String username, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit();
        SharedPreferences.Editor editor2 = context.getSharedPreferences(KEY2, Activity.MODE_PRIVATE).edit();
        SharedPreferences.Editor editor3 = context.getSharedPreferences(KEY3, Activity.MODE_PRIVATE).edit();
        SharedPreferences.Editor editor4 = context.getSharedPreferences(KEY4, Activity.MODE_PRIVATE).edit();
        editor.putString("password", password);
        editor2.putString("pin", pin);
        editor3.putString("IP", ip);
        editor4.putString("username", username);
        editor.commit();
        editor2.commit();
        editor3.commit();
        editor4.commit();
    }

    /**
     * Gets user name.
     *
     * @param context the context
     * @return the user name
     */
    public static String getUserName(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY,Activity.MODE_PRIVATE);
        SharedPreferences savedSession1 = context.getSharedPreferences(KEY2,Activity.MODE_PRIVATE);
        SharedPreferences savedSession2 = context.getSharedPreferences(KEY3,Activity.MODE_PRIVATE);
        SharedPreferences savedSession3 = context.getSharedPreferences(KEY4,Activity.MODE_PRIVATE);

//        SharedPreferences.Editor edit =  savedSession.edit();
//        edit.clear();
//        edit.commit();
        String all = savedSession1.getString("pin","")+"/"+savedSession.getString("password","")+"/"+savedSession2.getString("IP","")+"/"+ savedSession3.getString("username","");
        return all;

    }
}
