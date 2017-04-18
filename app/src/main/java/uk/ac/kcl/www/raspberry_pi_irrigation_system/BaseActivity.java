package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by User on 01/03/2017. This class is responsible for creating sliding animations
 * when activities are changed and for starting the login activity every time that the application
 * is resumed from background. To achieve this the finish(), onPause(), onResume() and onActivityResult() methods
 * are overwritten and two classes named onLeaveThisActivity and onStartNewActivity are created.
 */
public abstract class BaseActivity extends AppCompatActivity
{
    /**
     * The constant REQUEST_CODE.
     */
    public static final int REQUEST_CODE = 1;
    private boolean shouldCheckCredentials = false;

    @Override
    public void finish()
    {
        super.finish();
        onLeaveThisActivity();
    }

    /**
     * On leave this activity.
     * Overwrites original transition with slide back
     */
    protected void onLeaveThisActivity()
    {
        overridePendingTransition(R.anim.efl, R.anim.etr);
    }

    @Override
    public void startActivity(Intent intent, Bundle options)
    {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    /**
     * On start new activity.
     * Overwrites original transition with slide forward
     */
    protected void onStartNewActivity()
    {
        overridePendingTransition(R.anim.efr, R.anim.etl);
    }

    @Override
    protected void onPause() {
        shouldCheckCredentials = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(shouldCheckCredentials){
            Intent loginIntent = new Intent(this,LoginScreenActivity.class);
            startActivityForResult(loginIntent,REQUEST_CODE);
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BaseActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            shouldCheckCredentials = false;
        }
    }
}
