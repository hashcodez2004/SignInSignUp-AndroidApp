package com.hashdroid.loginsignup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    EditText email_pno, pass;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this, SystemBarStyle.auto(Color.WHITE, Color.WHITE));
        setContentView(R.layout.activity_main2);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        Window window = getWindow();
//        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            WindowInsetsController insetsController = window.getInsetsController();
//            if (insetsController != null) {
//                insetsController.setSystemBarsAppearance(
//                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
//                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
//                );
//            }
//        } else {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

        TextView signUp = findViewById(R.id.login_txt_view4); // Make sure this ID exists in your XML layout
        SpannableString spannableString = new SpannableString("New here? Sign up");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };
        // Set the clickable span for the text "Sign up"
        spannableString.setSpan(clickableSpan, 10, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setText(spannableString);
        signUp.setMovementMethod(LinkMovementMethod.getInstance());


        //API task
        email_pno = findViewById(R.id.email_pno);
        pass = findViewById(R.id.pass);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(view -> {
            if(email_pno.getText().toString().isEmpty() && pass.getText().toString().isEmpty()){
                Toast.makeText(MainActivity2.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                return;
            }
            postData(email_pno.getText().toString(), pass.getText().toString());
        });
    };


    private void postData(String email, String password) {
        Log.d("ashu", email);
        Log.d("ashu", password);
        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://reqres.in/api/")
        // as we are sending data in json format so
        // we have to add Gson converter factory
        .addConverterFactory(GsonConverterFactory.create())
        // at last we are building our retrofit builder.
        // below line is to create an instance for our retrofit api class.
        .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataModal modal = new DataModal(email, password);

        // calling a method to create a post and passing our modal class.
        Call<ResponseModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<ResponseModal>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModal> call, @NonNull Response<ResponseModal> response) {
                // this method is called when we get response from our api.
                if(response.code()==200) {
                    Toast.makeText(MainActivity2.this, "Data added to API", Toast.LENGTH_SHORT).show();

                    // on below line we are setting empty text
                    // to our both edit text.

                    email_pno.setText("");
                    pass.setText("");

                    // we are getting response from our body
                    // and passing it to our modal class.
                    ResponseModal responseFromAPI = response.body();

                    assert responseFromAPI != null;
                    Toast.makeText(MainActivity2.this, "token: " + responseFromAPI.getToken(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity2.this, "Error Code:"+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModal> call, @NonNull Throwable throwable) {
                // Displaying a Toast message for the failure
                Toast.makeText(MainActivity2.this, "Request failed", Toast.LENGTH_SHORT).show();
            }

        });
    }
}