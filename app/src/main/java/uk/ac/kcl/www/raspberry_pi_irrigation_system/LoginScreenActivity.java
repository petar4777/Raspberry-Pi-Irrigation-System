package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The type Login screen activity. This class is used to allow the user to login into the application.
 * To login, the user should provide his PIN code which is set when a new device has been linked. The passed
 * input by the user is compared with the stored information in the SharedPreferences file. If a match is found
 * the
 */
public class LoginScreenActivity extends BaseActivity
{
    /**
     * The Back.
     */
    Button back;
    /**
     * The constant KEY2.
     */
    public static String KEY2 = "PIN";
    /**
     * The constant KEY.
     */
    public static String KEY = "PASSWORD";
    /**
     * The constant KEY3.
     */
    public static String KEY3 = "IP";
    /**
     * The constant KEY4.
     */
    public static String KEY4 = "USERNAME";
    /**
     * The Login.
     */
    Button login;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The Forgotten pass.
     */
    Button forgottenPass;
    /**
     * The Text.
     */
    TextView text;
    /**
     * The Login edit.
     */
    EditText loginEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        login = (Button) findViewById(R.id.LogInWithAcc);
        forgottenPass = (Button) findViewById(R.id.ForgottenPass);
        text = (TextView) findViewById(R.id.textView2222);
        loginEdit = (EditText) findViewById(R.id.LogInEditText);
        back = (Button) findViewById(R.id.LoginBack);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                String storedData = getUserName(LoginScreenActivity.this);
                String[] splitValues = storedData.split("/");
                String pincode = splitValues[0];
                if(loginEdit.getText().toString().trim().equals(pincode))
                {
                    startActivity(new Intent(LoginScreenActivity.this, MainActivity.class));
                }
                else
                {
                    text.setText("Wrong PIN code.");
                    text.setTextColor(Color.RED);
                }

              //  startActivity(new Intent(ChooseSignOrLogActivity.this, LoginScreenActivity.class));

            }
        });

        forgottenPass.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                startActivity(new Intent(LoginScreenActivity.this, ForgottenPasswordActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(LoginScreenActivity.this, ChooseSignOrLogActivity.class));
                onLeaveThisActivity();
            }
        });
    }

    /**
     * Gets user name.
     *
     * @param context the context
     * @return the user name
     */
    public String getUserName(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences savedSession1 = context.getSharedPreferences(KEY2,Activity.MODE_PRIVATE);
        SharedPreferences savedSession2 = context.getSharedPreferences(KEY3,Activity.MODE_PRIVATE);
        SharedPreferences savedSession3 = context.getSharedPreferences(KEY4,Activity.MODE_PRIVATE);

        String all = savedSession1.getString("pin","")+"/"+savedSession.getString("password","")+"/"+savedSession2.getString("IP","")+"/"+ savedSession3.getString("username","");
        return all;

    }
}
