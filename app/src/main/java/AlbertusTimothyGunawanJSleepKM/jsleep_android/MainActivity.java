package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Account loginAccount, registerAccount;
    public static List<Room> getRoom;
    BaseApiService mApiService;
    Context mContext;
    EditText page;
    ListView data;
    Button prev, next, go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        page = findViewById(R.id.MainPage);
        data = findViewById(R.id.MainListView);
        prev = findViewById(R.id.MainPrevButton);
        next = findViewById(R.id.MainNextButton);
        go = findViewById(R.id.MainGoButton);
        getAllRoom(0);
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
            case R.id.add_box_button:
                Intent intent2 = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(intent2);
                Toast toast2 = Toast.makeText(getApplicationContext(), "Account Details", Toast.LENGTH_SHORT);
                toast2.show();
        }
        return true;
    }
    protected void getAllRoom(int page) {
        mApiService.getAllRoom (
                page,
                10
        ).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if(response.isSuccessful()){
                    getRoom = (ArrayList<Room>) response.body();
                    ArrayList<String> list = new ArrayList<>();
                    assert getRoom != null;
                    for (Room room : getRoom){
                        list.add(room.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, list);
                    data.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
}
