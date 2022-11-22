package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;

public class MainActivity extends AppCompatActivity {
    public static Account loginAccount;
    public static Account registerAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Room> roomList = new ArrayList<Room>();
        Gson gson = new Gson();
        ListView list = new ListView(this);
        String data[] = new String[200];

        try {
            InputStream filepath = getAssets().open("randomRoomList.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));

            Room[] acc = gson.fromJson(reader, Room[].class);
            Collections.addAll(roomList, acc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < roomList.size(); i++) {
            data[i] = roomList.get(i).name;
        }

        ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        list = (ListView) findViewById(R.id.MainListView);
        list.setAdapter(array);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                Toast toast = Toast.makeText(getApplicationContext(), "Account Details", Toast.LENGTH_SHORT);
                toast.show();
        }
        return true;
    }
}
