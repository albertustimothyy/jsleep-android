package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Payment;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    TextView roomName, dateFrom, dateTo, roomPrice, totalPrice, accountBalance, voucher;
    Button acceptPayment, cancelPayment, topUpButton;
    LinearLayout topUp;
    EditText topUpBalance;
    ImageButton topUpDown, topUpUp;
    private Room room;
    Boolean paymentCheck, topUpCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        room = MainActivity.getRoom.get(MainActivity.roomPosition);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        roomName = findViewById(R.id.PaymentDetailRoomNameInput);
        dateFrom = findViewById(R.id.PaymentDetailDateFromInput);
        dateTo = findViewById(R.id.PaymentDetailDateToInput);
        roomPrice = findViewById(R.id.PaymentDetailRoomPriceInput);
        totalPrice = findViewById(R.id.PaymentDetailTotalPriceInput);
        accountBalance = findViewById(R.id.PaymentBalanceInput);
        voucher = findViewById(R.id.PaymentVoucher);

        acceptPayment = findViewById(R.id.PaymentDetailButtonAccept);
        cancelPayment = findViewById(R.id.PaymentDetailButtonCancel);
        topUpButton = findViewById(R.id.PaymentActivityTopUpButton);

        topUpDown = findViewById(R.id.PaymentBalanceDownImg);
        topUpUp = findViewById(R.id.PaymentBalanceUpImg);

        topUp = findViewById(R.id.PaymentActivityTopUpLayout);

        topUpBalance = findViewById(R.id.PaymentActivityTopUpAmount);
        roomName.setText(room.name);
        dateFrom.setText(MainActivity.payment.from.toString());
        dateTo.setText(MainActivity.payment.to.toString());
        roomPrice.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(room.price.price));
        accountBalance.setText(NumberFormat.getCurrencyInstance(new Locale("in", "ID")).format(MainActivity.loginAccount.balance));

        topUpDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topUpDown.setVisibility(ImageButton.GONE);
                topUpUp.setVisibility(ImageButton.VISIBLE);
                topUp.setVisibility(LinearLayout.VISIBLE);

                topUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        topUpAccount();
                    }
                });
            }
        });

        topUpUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topUpDown.setVisibility(ImageButton.VISIBLE);
                topUpUp.setVisibility(ImageButton.GONE);
                topUp.setVisibility(LinearLayout.GONE);
            }
        });

        acceptPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptPayment();
            }
        });

        cancelPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               cancelPayment();
            }
        });
    }

    protected void acceptPayment() {
        mApiService.acceptPaymentRequest (
                MainActivity.payment.id
        ).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.code());
                if(response.isSuccessful()){
                    paymentCheck = response.body();
                    Toast.makeText(mContext, "Payment Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Payment Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void cancelPayment() {
        mApiService.cancelPaymentRequest (
                MainActivity.payment.id
        ).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.code());
                if(response.isSuccessful()){
                    paymentCheck = response.body();
                    Toast.makeText(mContext, "Payment Cancelled!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(PaymentActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Cancelling payment failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void topUpAccount() {
        mApiService.topUpRequest(
                MainActivity.loginAccount.id,
                Double.parseDouble(topUpBalance.getText().toString())
        ).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                System.out.println(response.code());
                if (response.isSuccessful()) {
                    topUpCheck = response.body();
                    Toast.makeText(mContext, "Top Up Successful!", Toast.LENGTH_SHORT).show();
                    accountBalance.setText(String.valueOf(MainActivity.loginAccount.balance + Double.parseDouble(topUpBalance.getText().toString())));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Top Up Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}