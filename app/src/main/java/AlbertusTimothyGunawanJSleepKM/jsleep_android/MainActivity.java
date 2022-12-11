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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;

import android.widget.Spinner;
import android.widget.Toast;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;


import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.City;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    public static Account loginAccount;
    public static List<Room> getRoom;
    public static Payment payment;
    public static Room room;
    public static int roomPosition;
    BaseApiService mApiService;
    Context mContext;
    EditText page, priceFrom, priceTo;
    ListView data;
    Spinner city;
    Button prev, next, go, filterByCityButton, filterByPriceButton, cityFilterButton, priceFilterButton;
    ImageView filterDown, filterUp;
    LinearLayout filterByButtonLayout;
    CardView filterByCity, filterByPrice;
    int curPage = 1, pageSize = 10;
    MenuItem addBox, search;
    SearchView searchView;
    ArrayAdapter<String> adapter;
    ArrayAdapter adapterCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = UtilsApi.getApiService();
        mContext = this;

        page = findViewById(R.id.MainPage);
        priceFrom = findViewById(R.id.FilterByPriceInputFrom);
        priceTo = findViewById(R.id.FilterByPriceInputTo);

        data = findViewById(R.id.MainListView);
        prev = findViewById(R.id.MainPrevButton);
        next = findViewById(R.id.MainNextButton);
        go = findViewById(R.id.MainGoButton);
        filterByPriceButton = findViewById(R.id.FilterByPriceButton);
        filterByCityButton = findViewById(R.id.FilterByCityButton);
        cityFilterButton = findViewById(R.id.FilterByCitySubmitButton);
        priceFilterButton = findViewById(R.id.FilterByPriceSubmitButton);

        filterDown = findViewById(R.id.MainToFilterByImg);
        filterUp = findViewById(R.id.FilterToMainByImg);

        filterByButtonLayout = findViewById(R.id.FilterByButtonLayout);

        filterByCity = findViewById(R.id.FilterByCityCardViewLayout);
        filterByPrice = findViewById(R.id.FilterByPriceCardViewLayout);

        city = findViewById(R.id.FilterByCityInput);

        adapterCity = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, City.values());
        adapterCity.setDropDownViewResource(R.layout.dropdown_item);
        city.setAdapter(adapterCity);

        getAllRoom();

        pageButtonResponses();

        filterDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByButtonLayout.setVisibility(LinearLayout.VISIBLE);
                filterUp.setVisibility(ImageView.VISIBLE);
                filterDown.setVisibility(ImageView.GONE);
                filterByPriceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterByPrice.setVisibility(CardView.VISIBLE);
                        filterByButtonLayout.setVisibility(LinearLayout.GONE);
                        priceFilterButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getRoomByPrice();
                            }
                        });
                    }
                });
                filterByCityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterByCity.setVisibility(CardView.VISIBLE);
                        filterByButtonLayout.setVisibility(LinearLayout.GONE);
                        cityFilterButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getRoomByCity();
                            }
                        });
                    }
                });
            }
        });

        filterUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterByButtonLayout.setVisibility(LinearLayout.GONE);
                filterByCity.setVisibility(CardView.GONE);
                filterByPrice.setVisibility(CardView.GONE);
                filterUp.setVisibility(ImageView.GONE);
                filterDown.setVisibility(ImageView.VISIBLE);
                getAllRoom();
            }
        });
        if (loginAccount.renter != null) {
            data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int j = 0;

                    for (Room room : getRoom){
                        if( room.name.equals(adapter.getItem(i)))
                            break;
                        j++;
                    }

                    roomPosition = j;
                    System.out.println(roomPosition);

                    Intent move = new Intent(MainActivity.this, DetailRoomActivity.class);
                    startActivity(move);
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.top_menu, menu);
        addBox = menu.findItem(R.id.add_box_button);

        search = menu.findItem(R.id.search_button);
        searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
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
                    adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, list);
                    data.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }
    protected void getRoomByCity() {
        mApiService.getRoomByCity (
                curPage - 1,
                pageSize,
                city.getSelectedItem().toString()
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
                    adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, list);
                    data.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Room>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    protected void getRoomByPrice() {
        mApiService.getRoomByPrice (
                curPage - 1,
                pageSize,
                Double.parseDouble(priceFrom.getText().toString()),
                Double.parseDouble(priceTo.getText().toString())
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
                    adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, list);
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
