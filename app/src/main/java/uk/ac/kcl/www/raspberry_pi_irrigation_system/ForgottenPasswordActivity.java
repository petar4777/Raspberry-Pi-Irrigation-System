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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static uk.ac.kcl.www.raspberry_pi_irrigation_system.SignUpActivity.KEY;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.SignUpActivity.KEY2;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.SignUpActivity.KEY3;
import static uk.ac.kcl.www.raspberry_pi_irrigation_system.SignUpActivity.KEY4;

/**
 * The type Forgotten password activity. This class is used to retrieve a forgotten pin. The user is asked to fill the information that he has provided
 * for his device. If it matches the records the password is shown in a text field. If there is no match, the textView`s text above this text field
 * is set to "WRONG INFORMATION"
 */
public class ForgottenPasswordActivity extends BaseActivity
{
    /**
     * The Ip edit.
     */
    EditText ipEdit;
    /**
     * The Ip text view.
     */
    TextView ipTextView;
    /**
     * The Username text view.
     */
    TextView usernameTextView;
    /**
     * The Password text view.
     */
    TextView passwordTextView;
    /**
     * The Hidden.
     */
    TextView hidden;
    /**
     * The Username edit.
     */
    EditText usernameEdit;
    /**
     * The Username pass edit.
     */
    EditText usernamePassEdit;
    /**
     * The Back.
     */
    Button back;
    /**
     * The Check retreive.
     */
    Button checkRetreive;
    /**
     * The Pin code.
     */
    TextView pinCode;
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
        setContentView(R.layout.activity_forgotten_password);

        back = (Button) findViewById(R.id.ForgotPassBack);
        checkRetreive = (Button) findViewById(R.id.RetrievePINButton);
        ipEdit = (EditText) findViewById(R.id.editTextIPFP);
        usernameEdit = (EditText) findViewById(R.id.editTextUsernameFP);
        usernamePassEdit = (EditText) findViewById(R.id.editTextUsernamePasswordFP);
        pinCode = (TextView) findViewById(R.id.textViewPassForgot);
        hidden = (TextView) findViewById(R.id.textViewHidded);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        ipTextView = (TextView) findViewById(R.id.textViewIPFP);
        usernameTextView = (TextView) findViewById(R.id.textViewUsernameFP);
        passwordTextView = (TextView) findViewById(R.id.textViewUsernamePassFP);

        checkRetreive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onC.start();
                String ip = ipEdit.getText().toString().trim();
                String username = usernameEdit.getText().toString().trim();
                String password = usernamePassEdit.getText().toString().trim();
                String realPass = getUserName(ForgottenPasswordActivity.this).toString().trim();
                String[] values = realPass.split("/");
                if(!ipEdit.getText().toString().trim().isEmpty() && !usernameEdit.getText().toString().trim().isEmpty() && !usernamePassEdit.getText().toString().trim().isEmpty())
                {
                    usernameTextView.setText("Enter the registered admin username:");
                    usernameTextView.setTextColor(Color.parseColor("#2c3e50"));
                    ipTextView.setText("Enter the IP of your registered device below:");
                    ipTextView.setTextColor(Color.parseColor("#2c3e50"));
                    passwordTextView.setText("Enter the registered username password:");
                    passwordTextView.setTextColor(Color.parseColor("#2c3e50"));

                    if(ip.equals(values[2]) && username.equals(values[3]) && password.equals(values[1]))
                    {
                        hidden.setTextColor(Color.parseColor("#2c3e50"));
                        pinCode.setTextSize(20);
                        pinCode.setGravity(Gravity.CENTER);
                        pinCode.setTextColor(Color.parseColor("#2c3e50"));
                        pinCode.setText(values[0]);
                        hidden.setText("Your PIN code is:");
                    }
                    else
                    {
                        hidden.setTextColor(Color.RED);
                        hidden.setText("WRONG INFORMATION.");
                        pinCode.setText("");
                    }
                }
                else
                {
                    hidden.setText("");
                    if(usernameEdit.getText().toString().trim().isEmpty())
                    {
                        usernameTextView.setText("Please enter the registered admin username.");
                        usernameTextView.setTextColor(Color.RED);
                    }
                    else
                    {
                        usernameTextView.setText("Enter the registered admin username:");
                        usernameTextView.setTextColor(Color.parseColor("#2c3e50"));
                    }

                    if(ipEdit.getText().toString().trim().isEmpty())
                    {
                        ipTextView.setText("Please enter the IP of your device.");
                        ipTextView.setTextColor(Color.RED);
                    }
                    else
                    {
                        ipTextView.setText("Enter the IP of your registered device below:");
                        ipTextView.setTextColor(Color.parseColor("#2c3e50"));
                    }

                    if(usernamePassEdit.getText().toString().trim().isEmpty())
                    {
                        passwordTextView.setText("Please enter the registered password.");
                        passwordTextView.setTextColor(Color.RED);
                    }
                    else
                    {
                        passwordTextView.setText("Enter the registered username password:");
                        passwordTextView.setTextColor(Color.parseColor("#2c3e50"));
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(ForgottenPasswordActivity.this, LoginScreenActivity.class));
                onLeaveThisActivity();
            }
        });
    }

    /**
     * Gets user name.
     *This method is used to retrieve the stored information in the SharedPreferences file.
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
