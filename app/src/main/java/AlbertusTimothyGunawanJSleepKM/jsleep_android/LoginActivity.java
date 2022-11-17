package AlbertusTimothyGunawanJSleepKM.jsleep_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView register = findViewById(R.id.LoginRegister);
        Button login = findViewById(R.id.LoginButton);



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
                Intent move = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(move);
                Toast toast = Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
}