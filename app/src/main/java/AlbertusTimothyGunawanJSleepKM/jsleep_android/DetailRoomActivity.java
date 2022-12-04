package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Facility;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class DetailRoomActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    private Room room;
    TextView name, bedType, size, price, address, city;
    CheckBox ac, refrigerator, wifi, bathUb, balcony, restaurant, swimmingPool, fitnessCenter;
    EditText dateFrom, dateTo;
    Button makeBookButton, bookButton, cancelButton;
    LinearLayout bookLayout, buttonLayout;
    DatePickerDialog datePickerDialog;
    private Payment makePayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        room = MainActivity.getRoom.get(MainActivity.roomPosition);

        name = findViewById(R.id.DetailRoomNameInput);
        bedType = findViewById(R.id.DetailRoomBedTypeInput);
        size = findViewById(R.id.DetailRoomSizeInput);
        price = findViewById(R.id.DetailRoomPriceInput);
        address = findViewById(R.id.DetailRoomAddressInput);
        city = findViewById(R.id.DetailRoomCityInput);

        ac = findViewById(R.id.DetailRoomCheckBoxAC);
        refrigerator = findViewById(R.id.DetailRoomCheckBoxRefrigerator);
        wifi = findViewById(R.id.DetailRoomCheckBoxWifi);
        bathUb = findViewById(R.id.DetailRoomCheckBoxBathub);
        balcony = findViewById(R.id.DetailRoomCheckBoxBalcony);
        restaurant = findViewById(R.id.DetailRoomCheckBoxRestaurant);
        swimmingPool= findViewById(R.id.DetailRoomCheckBoxSwimmingPool);
        fitnessCenter= findViewById(R.id.DetailRoomCheckBoxFitnessCenter);

        dateFrom = findViewById(R.id.DetailRoomBookingFromInput);
        dateTo = findViewById(R.id.DetailRoomBookingToInput);

        makeBookButton = findViewById(R.id.DetailRoomMakeBookingButton);
        bookButton = findViewById(R.id.DetailRoomBookButton);
        cancelButton = findViewById(R.id.DetailRoomCancelButton);

        bookLayout = findViewById(R.id.DetailRoomBookingLayout);
        buttonLayout = findViewById(R.id.DetailRoomMakeBooking);

        name.setText(room.name);
        bedType.setText(room.bedType.toString().substring(0, 1) + room.bedType.toString().substring(1).toLowerCase());
        size.setText(room.size + "m\u00B2");
        price.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(room.price.price));
        address.setText(room.address);
        city.setText(room.city.toString().substring(0, 1) + room.city.toString().substring(1).toLowerCase());

        for (int i = 0; i < room.facility.size(); i++) {
            if (room.facility.get(i).equals(Facility.AC ))
                ac.setChecked(true);
            else if (room.facility.get(i).equals(Facility.Refrigerator))
                refrigerator.setChecked(true);
            else if (room.facility.get(i).equals(Facility.WiFi))
                wifi.setChecked(true);
            else if (room.facility.get(i).equals(Facility.Bathtub))
                bathUb.setChecked(true);
            else if (room.facility.get(i).equals(Facility.Balcony))
                balcony.setChecked(true);
            else if (room.facility.get(i).equals(Facility.Restaurant))
                restaurant.setChecked(true);
            else if (room.facility.get(i).equals(Facility.SwimmingPool))
                swimmingPool.setChecked(true);
            else if (room.facility.get(i).equals(Facility.FitnessCenter))
                fitnessCenter.setChecked(true);
        }
        buttonLayout.setVisibility(LinearLayout.VISIBLE);
        bookLayout.setVisibility(LinearLayout.INVISIBLE);

        makeBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonLayout.setVisibility(LinearLayout.INVISIBLE);
                bookLayout.setVisibility(LinearLayout.VISIBLE);

                dateFrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(DetailRoomActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        dateFrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

                dateTo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(DetailRoomActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        dateTo.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

            bookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makePayment = makeBooking();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Cancel Booking!", Toast.LENGTH_SHORT).show();
                    buttonLayout.setVisibility(LinearLayout.VISIBLE);
                    bookLayout.setVisibility(LinearLayout.INVISIBLE);
                }
            });
            }
        });
    }

    protected Payment makeBooking() {
        mApiService.createBookingRequest (
                MainActivity.loginAccount.id,
                MainActivity.loginAccount.renter.id,
                room.id,
                dateFrom.getText().toString(),
                dateTo.getText().toString()
        ).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {

                if(response.isSuccessful()){
                    MainActivity.payment = response.body();
                    Toast.makeText(mContext, "Make Booking Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(DetailRoomActivity.this, PaymentActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Make Booking Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}