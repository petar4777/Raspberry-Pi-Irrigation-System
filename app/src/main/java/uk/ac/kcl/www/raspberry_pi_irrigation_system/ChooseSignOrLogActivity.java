package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Choose sign or log activity.
 * This activity pops up first when the application is loaded. It has a very basic
 * functionality, it allows the user to choose between two options- to login with a
 * registered device or to link a new device.
 */
public class ChooseSignOrLogActivity extends BaseActivity
{
    /**
     * The Button log in.
     */
    Button buttonLogIn;
    /**
     * The Button sign in.
     */
    Button buttonSignIn;
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
        setContentView(R.layout.activity_choose_sign_or_log);
        buttonLogIn = (Button) findViewById(R.id.logInButton);
        buttonSignIn = (Button) findViewById(R.id.signUpButton);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);

        buttonSignIn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                startActivity(new Intent(ChooseSignOrLogActivity.this, SignUpActivity.class));
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                startActivity(new Intent(ChooseSignOrLogActivity.this, LoginScreenActivity.class));
            }
        });
    }
}
