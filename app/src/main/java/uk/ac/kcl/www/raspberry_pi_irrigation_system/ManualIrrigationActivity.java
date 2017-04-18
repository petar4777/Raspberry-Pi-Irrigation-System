package uk.ac.kcl.www.raspberry_pi_irrigation_system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The type Manual irrigation activity.
 * This class is used to create manual irrigation plans. The user should load his schedule inside of a listView.
 * Once he has prepared his schedule he can upload it via clicking on the "Upload" button at the bottom left part of the
 * screen. For convenience, a three buttons are created which allow the user to edit the listView by deleting some of its entries,
 * resetting it completely or reloading a past irrigation script from the Raspberry Pi.
 */
public class ManualIrrigationActivity extends BaseActivity {
    /**
     * The P.
     */
    Point p;
    /**
     * The Number.
     */
    ArrayList<String> number = new ArrayList<>();
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
     * The Info.
     */
    String[] info;
    /**
     * The Time.
     */
    ArrayList<String> time = new ArrayList<>();
    /**
     * The Duration.
     */
    ArrayList<String> duration = new ArrayList<>();
    /**
     * The Command.
     */
    String[] command = new String[6];
    /**
     * The List view items.
     */
    ArrayList<String> listViewItems=new ArrayList<String>();
    /**
     * The Adapter.
     */
    ArrayAdapter<String> adapter;
    /**
     * The On c.
     */
    MediaPlayer onC;
    /**
     * The Off c.
     */
    MediaPlayer offC;
    /**
     * The Fancy c.
     */
    MediaPlayer fancyC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_irrigation);

        //BUTTONS
        final Spinner spinChooseTime = (Spinner) findViewById(R.id.spinnerChooseTime);
        final Spinner spinChooseHowLong = (Spinner) findViewById(R.id.spinnerChooseHowLong);
        Button addToListViewButton = (Button) findViewById(R.id.buttonAddToListView);
        final ListView manualIrrigationListView = (ListView) findViewById(R.id.manualIrrigationListView);
        Button uploadToScript = (Button) findViewById(R.id.buttonUploadPlan);
        Button removeItem = (Button) findViewById(R.id.buttonResetListView);
        Button backButton = (Button) findViewById(R.id.manualIrrigationActivityBack);
        Button getPreviousButton = (Button) findViewById(R.id.buttonGetPrevious);
        Button deleteSelection = (Button) findViewById(R.id.buttonDeleteSelection);
        onC = MediaPlayer.create(this, R.raw.on);
        offC = MediaPlayer.create(this, R.raw.off);
        fancyC = MediaPlayer.create(this, R.raw.fancy);
        //END OF BUTTONS

        //SPINNERS
        //SPIN1
        String[] timeValues = new String[24];
        timeValues[0] = "00:00";
        timeValues[1] = "01:00";
        timeValues[2] = "02:00";
        timeValues[3] = "03:00";
        timeValues[4] = "04:00";
        timeValues[5] = "05:00";
        timeValues[6] = "06:00";
        timeValues[7] = "07:00";
        timeValues[8] = "08:00";
        timeValues[9] = "09:00";
        timeValues[10] = "10:00";
        timeValues[11] = "11:00";
        timeValues[12] = "12:00";
        timeValues[13] = "13:00";
        timeValues[14] = "14:00";
        timeValues[15] = "15:00";
        timeValues[16] = "16:00";
        timeValues[17] = "17:00";
        timeValues[18] = "18:00";
        timeValues[19] = "19:00";
        timeValues[20] = "20:00";
        timeValues[21] = "21:00";
        timeValues[22] = "22:00";
        timeValues[23] = "23:00";
        ArrayAdapter<String> adapterChooseTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeValues);
        adapterChooseTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChooseTime.setAdapter(adapterChooseTime);
        spinChooseTime.setSelection(0);
        //END OF SPIN1

        //SPIN2
        //END OF SPIN2
        String[] howLong = new String[5];
        howLong[0] = "2";
        howLong[1] = "4";
        howLong[2] = "6";
        howLong[3] = "8";
        howLong[4] = "10";
        ArrayAdapter<String> adapterChooseHowLong = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, howLong);
        adapterChooseHowLong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChooseHowLong.setAdapter(adapterChooseHowLong);
        spinChooseHowLong.setSelection(0);
        //END OF SPINERS

        //LIST VIEW
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, listViewItems);
        manualIrrigationListView.setAdapter(adapter);
        //END OF LIST VIEW

        //ON CLICKS
        backButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                offC.start();
                startActivity(new Intent(ManualIrrigationActivity.this, MainActivity.class));
                finish();
                onLeaveThisActivity();
            }
        });

        addToListViewButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onC.start();
                String chooseTimeString = spinChooseTime.getSelectedItem().toString();
                String chooseHowLongString = spinChooseHowLong.getSelectedItem().toString();
                Log.e("TEST", chooseTimeString + " // " + chooseHowLongString);
                listViewItems.add("• Start at " + chooseTimeString + " for " + chooseHowLongString + " sec");
                adapter.notifyDataSetChanged();
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                offC.start();
                if (listViewItems.toString().trim().equals("[]"))
                {
                    Log.e("TUKA SME", "EVALATA");
                    if (p != null)
                    {
                        popPopup(ManualIrrigationActivity.this, p, "ListView is empty", 110, 270);
                    }
                }
                else
                {
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });


            uploadToScript.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fancyC.start();
                if(listViewItems.toString().trim().equals("[]"))
                {
                    Log.e("TUKA SME", "EVALATA");
                    if(p != null)
                    {
                        popPopup(ManualIrrigationActivity.this, p, "ListView is empty, select start time and duration and click the \"Add\" button.", 220, 440);
                    }
                }
                else {
                    String edit = listViewItems.toString();
                    Log.e("TEST1", edit);
                    String first = edit.replace("[", "");
                    Log.e("TEST2", first);
                    String second = first.replace("]", "");
                    Log.e("TEST3", second);
                    String third = second.replace("• Start at ", "");
                    Log.e("TEST4", third);
                    String fourth = third.replace(" for ", "/");
                    Log.e("TEST5", fourth);
                    String fifth = fourth.replace(" sec", "");
                    Log.e("TEST6", fifth);
                    String sixth = fifth.replace(", ", "\n");
                    Log.e("TEST7", sixth);
                    command[0] = "pkill -9 -f manualBasedOnPlan.py";
                    command[1] = "pkill -9 -f automaticBasedOnTemperature.py";
                    command[2] = "pkill -9 -f automaticBasedOnForecast.py";
                    command[3] = "pkill -9 -f automaticBasedOnHumidity.py";
                    command[4] = "cd Desktop;cd FinalTest1; echo \"" + sixth + "\">manualIrrigationPlan.txt";
                    command[5] = "cd Desktop;cd FinalTest1;nohup python3 manualBasedOnPlan.py &";
                    String storedInformation = getUserName(ManualIrrigationActivity.this);
                    info = storedInformation.split("/");
                    SSHConnectionFinal ssh = new SSHConnectionFinal(command, true, info);
                    listViewItems.clear();
                    adapter.notifyDataSetChanged();
                    if(p != null)
                    {
                        popPopup(ManualIrrigationActivity.this, p, "UPLOADED SUCCESSFULLY", 120, 400);
                    }
                }
            }
        });

        getPreviousButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                offC.start();
                listViewItems.clear();
                adapter.notifyDataSetChanged();
                String storedInformation = getUserName(ManualIrrigationActivity.this);
                info = storedInformation.split("/");
                SSHConnectionFinal ssh = new SSHConnectionFinal(new String[]{"cd Desktop;cd FinalTest1;cat manualIrrigationPlan.txt"}, false, info);
                ArrayList<String> splitData = ssh.parseDataToArrayList(ssh.getFileContents());
                Log.e("TEST", splitData.toString());
                if (splitData.toString().equals("[]"))
                {
                    if (p != null)
                    {
                        popPopup(ManualIrrigationActivity.this, p, "There is no previous data", 120, 420);
                    }
                }
                else
                {
                    for (int z = 0; z < splitData.size(); z++)
                    {
                        int count = 0;
                        String temp = splitData.get(z);
                        String[] tempAr = temp.split("/");
                        if (tempAr.length < 2)
                        {
                            if (p != null)
                            {
                                popPopup(ManualIrrigationActivity.this, p, "There is no previous data", 120, 420);
                            }
                        }
                        else
                        {
                            Log.e("TESTING TEMPAR: ", Arrays.deepToString(tempAr));
                            time.add(tempAr[count]);
                            duration.add(tempAr[count + 1]);
                            String a = time.toString();
                            String b = duration.toString();
                            Log.e("NUM / TIM / DUR: ", a + "////" + b);
                            listViewItems.add("• Start at " + time.get(z) + " for " + duration.get(z) + " sec");
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        });

        deleteSelection.setOnClickListener(new View.OnClickListener()
        {
            public ListView getListView()
            {
                return manualIrrigationListView;
            }

            @Override
            public void onClick(View v) {
                onC.start();
                if (listViewItems.toString().trim().equals("[]"))
                {
                    Log.e("TUKA SME", "EVALATA");
                    if (p != null)
                    {
                        popPopup(ManualIrrigationActivity.this, p, "ListView is empty, there is nothing to delete.", 120, 470);
                    }
                }
                else
                {
                    SparseBooleanArray checkedItems = getListView().getCheckedItemPositions();
                    int count = getListView().getCount();

                    for (int i = count - 1; i >= 0; i--) {
                        if (checkedItems.get(i)) {
                            adapter.remove(listViewItems.get(i));
                        }
                    }
                    checkedItems.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        //END OF ON CLICKS
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        int[] loc = new int[2];
        Button button = (Button) findViewById(R.id.buttonGetPrevious);
        button.getLocationOnScreen(loc);

        p = new Point();
        p.x = loc[0];
        p.y = loc[1];
    }

    private void popPopup(final Activity context, Point p, String s, int h, int w)
    {
        Log.e("WTF", p.toString());
        int popHeight = h;
        int popWidth =w;

        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        final PopupWindow popup = new PopupWindow(context);
        TextView text = (TextView) layout.findViewById(R.id.textViewPopup);
        text.setText(s);
        popup.setContentView(layout);
        popup.setWidth(popWidth);
        popup.setHeight(popHeight);
        popup.setFocusable(true);
        int offset_x = 0;
        int offset_y = 0;
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        offC.start();
        startActivity(new Intent(ManualIrrigationActivity.this, MainActivity.class));
        finish();
        onLeaveThisActivity();
    }

    /**
     * Gets user name.
     * This method is used to retrieve the IP, username and password required for the SSH connection.
     *
     * @param context the context
     * @return the user name
     */
    public String getUserName(Context context) {
        SharedPreferences savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        SharedPreferences savedSession2 = context.getSharedPreferences(KEY3,Activity.MODE_PRIVATE);
        SharedPreferences savedSession3 = context.getSharedPreferences(KEY4,Activity.MODE_PRIVATE);
        String all = savedSession.getString("password","")+"/"+savedSession2.getString("IP","")+"/"+ savedSession3.getString("username","");
        return all;

    }
}
