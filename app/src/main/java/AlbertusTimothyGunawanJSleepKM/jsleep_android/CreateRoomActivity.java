package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.BedType;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.City;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Facility;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    ArrayAdapter adapterCity, adapterBedType;
    Spinner city, bedType;
    EditText roomName, roomAddress, roomPrice, roomSize;
    Button create, cancel;
    CheckBox ac, refrigerator, wifi, bathUb, balcony, restaurant, swimmingPool, fitnessCenter;
    private ArrayList<Facility> facilityList =  new ArrayList<>();
    City cityData;
    BedType bedTypeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        city = findViewById(R.id.CreateFacilityCitySpinner);
        bedType = findViewById(R.id.CreateFacilityBedTypeSpinner);

        roomName = findViewById(R.id.CreateRoomNameInput);
        roomAddress = findViewById(R.id.CreateRoomAddressInput);
        roomPrice = findViewById(R.id.CreateRoomPriceInput);
        roomSize = findViewById(R.id.CreateRoomSizeInput);

        create = findViewById(R.id.CreateButton);
        cancel = findViewById(R.id.CancelButton);

        ac = findViewById(R.id.checkBoxAC);
        refrigerator = findViewById(R.id.checkBoxRefrigerator);
        wifi = findViewById(R.id.checkBoxWifi);
        bathUb = findViewById(R.id.checkBoxBathub);
        balcony = findViewById(R.id.checkBoxBalcony);
        restaurant = findViewById(R.id.checkBoxRestaurant);
        swimmingPool= findViewById(R.id.checkBoxSwimmingPool);
        fitnessCenter= findViewById(R.id.checkBoxFitnessCenter);

        adapterCity = new ArrayAdapter<>(getApplicationContext(),R.layout.selected_item, City.values());
        adapterCity.setDropDownViewResource(R.layout.dropdown_item);
        city.setAdapter(adapterCity);

        adapterBedType = new ArrayAdapter<>(getApplicationContext(),R.layout.selected_item, BedType.values());
        adapterBedType.setDropDownViewResource(R.layout.dropdown_item);
        bedType.setAdapter(adapterBedType);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("click");
                if (ac.isChecked())
                    facilityList.add(Facility.AC);
                if (refrigerator.isChecked())
                    facilityList.add(Facility.Refrigerator);
                if (wifi.isChecked())
                    facilityList.add(Facility.WiFi);
                if (bathUb.isChecked())
                    facilityList.add(Facility.Bathtub);
                if (balcony.isChecked())
                    facilityList.add(Facility.Balcony);
                if (restaurant.isChecked())
                    facilityList.add(Facility.Restaurant);
                if (swimmingPool.isChecked())
                    facilityList.add(Facility.SwimmingPool);
                if (fitnessCenter.isChecked())
                    facilityList.add(Facility.FitnessCenter);
                Room createRoom = createRoom();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Cancelled!", Toast.LENGTH_SHORT).show();
                Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                startActivity(move);
            }
        });
    }

    protected Room createRoom() {
        mApiService.createRoomRequest(
                MainActivity.loginAccount.id,
                roomName.getText().toString(),
                Integer.parseInt(roomSize.getText().toString()),
                Double.parseDouble(roomPrice.getText().toString()),
                facilityList,
                City.valueOf(city.getSelectedItem().toString()),
                roomAddress.getText().toString(),
                BedType.valueOf(bedType.getSelectedItem().toString())
        ).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.isSuccessful()){
                    if(response.isSuccessful()){
                        Toast.makeText(mContext, "Create Room Successful!", Toast.LENGTH_SHORT).show();
                        Intent move = new Intent(CreateRoomActivity.this, MainActivity.class);
                        startActivity(move);
                    }
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                System.out.println("Fail");
                System.out.println(t.toString());
                Toast.makeText(mContext, "Create Room Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}