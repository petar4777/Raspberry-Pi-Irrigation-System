package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * The type Graph activity. This class is used to allow the user to choose between different graphs which are available.
 * The layout consists of buttons which lead to other activities.
 */
public class GraphActivity extends BaseActivity
{
    /**
     * The Graph activity back.
     */
    Button graphActivityBack;
    /**
     * The Temp time button.
     */
    Button tempTimeButton;
    /**
     * The Hum time button.
     */
    Button humTimeButton;
    /**
     * The Moist time button.
     */
    Button moistTimeButton;
    /**
     * The Water cons button.
     */
    Button waterConsButton;
    /**
     * The Custom graph button.
     */
    Button customGraphButton;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The constant whichActivity.
     */
    public static int whichActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        //BUTTONS
        graphActivityBack = (Button) findViewById(R.id.graphActivityBack);
        tempTimeButton = (Button) findViewById(R.id.tempTimeGraph);
        humTimeButton = (Button) findViewById(R.id.humTimeGraph);
        moistTimeButton = (Button) findViewById(R.id.moistTimeGraph);
        waterConsButton = (Button) findViewById(R.id.waterCons);
        customGraphButton = (Button) findViewById(R.id.customGraph);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        //END OF BUTTONS

        //ON CLICKS
        graphActivityBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(GraphActivity.this, MainActivity.class));
                onLeaveThisActivity();
            }
        });

        tempTimeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                whichActivity = 1;
                startActivity(new Intent(GraphActivity.this, CertainGraphActivity.class));
            }
        });

        humTimeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                whichActivity = 2;
                startActivity(new Intent(GraphActivity.this, CertainGraphActivity.class));
            }
        });

        moistTimeButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                whichActivity = 3;
                startActivity(new Intent(GraphActivity.this, CertainGraphActivity.class));
            }
        });

        waterConsButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                whichActivity = 4;
                startActivity(new Intent(GraphActivity.this, CertainGraphActivity.class));
            }
        });

        customGraphButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onC.start();
                startActivity(new Intent(GraphActivity.this, CustomGraphActivity.class));
            }
        });


        //END OF ON CLICKS
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        offC.start();
        startActivity(new Intent(GraphActivity.this, MainActivity.class));
        onLeaveThisActivity();
    }
}
