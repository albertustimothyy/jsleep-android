package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    BaseApiService mApiService;
    EditText name, email, password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.RegisterButton);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        name = findViewById(R.id.RegisterName);
        email = findViewById(R.id.RegisterUsername);
        password = findViewById(R.id.RegisterPassword);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account registerAccount = requestRegister();
            }
        });
    }
    protected Account requestRegister(){
        mApiService.registerRequest(
                name.getText().toString(),
                email.getText().toString(),
                password.getText().toString()
        ).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.registerAccount = response.body();
                    Toast.makeText(mContext, "Register Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Account Already Registered!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}