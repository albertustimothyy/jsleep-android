package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import AlbertusTimothyGunawanJSleepKM.jsleep_android.model.Account;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.BaseApiService;
import AlbertusTimothyGunawanJSleepKM.jsleep_android.request.UtilsApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;
    Context mContext;
    EditText username, password;
    TextView register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        username = findViewById(R.id.LoginUsername);
        password = findViewById(R.id.LoginPassword);
        username.setText("altap@gmail.com");
        password.setText("altap123");
        register = findViewById(R.id.LoginRegister);

        login = findViewById(R.id.LoginButton);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Register Page", Toast.LENGTH_SHORT);
                toast.show();
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account loginAccount = requestLogin();

            }
        });
    }

    protected Account requestLogin(){
        mApiService.loginRequest(
                username.getText().toString(),
                password.getText().toString()
        ).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(response.isSuccessful()){
                    MainActivity.loginAccount = response.body();
                    Toast.makeText(mContext, "Login Successful!", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(move);
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.toString());
                Toast.makeText(mContext, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}