package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.CustomAdapter.PaymentRenterAdapter;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterBookingHistoryActivity extends AppCompatActivity {
    ListView bookingList;
    public static int orderIndex;
    public static ArrayList<Payment> data;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renter_booking_history);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        bookingList = findViewById(R.id.BookingHistoryListView);
        getPaymentFromRenter();
        bookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderIndex = i;

                Intent move = new Intent(RenterBookingHistoryActivity.this, PaymentActivity.class);
                startActivity(move);
            }
        });
    }



    protected void getPaymentFromRenter(){

        mApiService.getPaymentFromRenter(
                MainActivity.loginAccount.renter.id
        ).enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if(response.isSuccessful()){
                    List<Payment> list = response.body();
                    assert list != null;
                    data = new ArrayList<Payment>(list);
                    Toast.makeText(mContext, "Get Order Success", Toast.LENGTH_SHORT).show();
                    PaymentRenterAdapter adapter = new PaymentRenterAdapter(mContext,data);
                    bookingList.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
