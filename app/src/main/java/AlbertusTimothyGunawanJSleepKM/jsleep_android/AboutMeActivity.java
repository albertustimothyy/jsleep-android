package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Renter;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Room;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeActivity extends AppCompatActivity {
    BaseApiService mApiService;
    TextView name, email, balance, usernameData, addressData, phoneNumberData;
    LinearLayout renterButtonLayout, registerLayout, dataLayout;
    EditText username, address, phoneNumber;
    Button renterButton, registerButton, cancelButton;
    Context mContext;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mApiService = UtilsApi.getApiService();
        mContext = this;


        name = findViewById(R.id.AboutMeInputName);
        email = findViewById(R.id.aboutMeInputEmail);
        balance = findViewById(R.id.aboutMeInputBalance);
        renterButtonLayout = (LinearLayout) findViewById(R.id.AboutMeRegisterRenterButtonLayout);
        registerLayout = (LinearLayout) findViewById(R.id.AboutMeRegisterRenterLayout);
        dataLayout = (LinearLayout) findViewById(R.id.AboutMeSecondaryLayout);
        renterButton = findViewById(R.id.AboutMeRegisterRenterButton);
        registerButton =  findViewById(R.id.RegisterRenterRegisterButton);
        cancelButton =  findViewById(R.id.RegisterRenterCancelButton);
        usernameData = findViewById(R.id.RegisterRenterNameData);
        addressData = findViewById(R.id.RegisterRenterAddressData);
        phoneNumberData = findViewById(R.id.RegisterRenterPhoneData);
        username = findViewById(R.id.RegisterRenterNameInput);
        address = findViewById(R.id.RegisterRenterAddressInput);
        phoneNumber = findViewById(R.id.RegisterRenterPhoneInput);

        name.setText(MainActivity.loginAccount.name);
        email.setText(MainActivity.loginAccount.email);
        balance.setText(String.valueOf(MainActivity.loginAccount.balance));
        if (MainActivity.loginAccount.renter == null) {
            renterButtonLayout.setVisibility(LinearLayout.VISIBLE);
            renterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    renterButtonLayout.setVisibility(LinearLayout.INVISIBLE);
                    registerLayout.setVisibility(LinearLayout.VISIBLE);
                    dataLayout.setVisibility(LinearLayout.INVISIBLE);


                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Account registerRenterAccount = requestRenter();

                        }
                    });
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            registerLayout.setVisibility(LinearLayout.INVISIBLE);
                            dataLayout.setVisibility(LinearLayout.INVISIBLE);
                            renterButtonLayout.setVisibility(LinearLayout.VISIBLE);
                        }
                    });
                }
            });
        }
        if (MainActivity.loginAccount.renter != null) {
            registerLayout.setVisibility(LinearLayout.INVISIBLE);
            renterButtonLayout.setVisibility(LinearLayout.INVISIBLE);
            dataLayout.setVisibility(LinearLayout.VISIBLE);

            usernameData.setText(MainActivity.loginAccount.renter.username);
            addressData.setText(MainActivity.loginAccount.renter.address);
            phoneNumberData.setText(String.valueOf(MainActivity.loginAccount.renter.phoneNumber));
        }
    }
    protected Account requestRenter() {
        mApiService.registerRenterRequest (
                MainActivity.loginAccount.id,
                username.getText().toString(),
                address.getText().toString(),
                phoneNumber.getText().toString()
        ).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                if(response.isSuccessful()){
                    MainActivity.loginAccount.renter = response.body();
                    Toast.makeText(mContext, "Register Renter Successful!", Toast.LENGTH_SHORT).show();
                    registerLayout.setVisibility(LinearLayout.INVISIBLE);
                    renterButtonLayout.setVisibility(LinearLayout.INVISIBLE);
                    dataLayout.setVisibility(LinearLayout.VISIBLE);

                    usernameData.setText(MainActivity.loginAccount.renter.username);
                    addressData.setText(MainActivity.loginAccount.renter.address);
                    phoneNumberData.setText(String.valueOf(MainActivity.loginAccount.renter.phoneNumber));
                }
            }

            @Override
            public void onFailure(Call<Renter> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Register Renter Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }

}