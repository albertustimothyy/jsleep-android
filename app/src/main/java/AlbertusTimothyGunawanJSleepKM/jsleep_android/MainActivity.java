package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static Account loginAccount;
    public static List<Room> getRoom;
    public static Payment payment;
    public static Room room;
    public static int roomPosition;
    BaseApiService mApiService;
    Context mContext;
    EditText page;
    ListView data;
    Button prev, next, go;
    int curPage = 1, pageSize = 10;
    MenuItem addBox;


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

        getAllRoom();

        pageButtonResponses();

        data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                roomPosition = i + ((curPage - 1) * pageSize);
                Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
                startActivity(move);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        addBox = menu.findItem(R.id.add_box_button);
        if(loginAccount.renter == null) {
            addBox.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person_button:
                Intent aboutMe = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(aboutMe);
                Toast.makeText(getApplicationContext(), "Account Details", Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_box_button:
                Intent createRoom = new Intent(MainActivity.this, CreateRoomActivity.class);
                startActivity(createRoom);
                Toast.makeText(getApplicationContext(), "Create Room", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Logout_Button:
                Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logout);
                Toast.makeText(getApplicationContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    protected void getAllRoom() {
        mApiService.getAllRoom (
                curPage - 1,
                pageSize
        ).enqueue(new Callback<ArrayList<Room>>() {
            @Override
            public void onResponse(Call<ArrayList<Room>> call, Response<ArrayList<Room>> response) {
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
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    protected void pageButtonResponses() {
        prev.setOnClickListener(view -> {
            if(curPage > 1) {
                curPage--;
                getAllRoom();
                page.setText(String.valueOf(curPage));
            }
        });
        next.setOnClickListener(view -> {
            curPage++;
            getAllRoom();
            page.setText(String.valueOf(curPage));
        });
        go.setOnClickListener(view -> {
            if(curPage >= 1) {
                curPage = Integer.parseInt(page.getText().toString());
                getAllRoom();
            }
        });
    }

}
