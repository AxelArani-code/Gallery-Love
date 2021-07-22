package com.example.gallerylove.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gallerylove.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    TextView text_UserName;
    ImageView btn_menu;
    CardView btn_UpdateImage;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        dataFireBase();


    }

    private void dataFireBase() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String userName = snapshot.child("userName").getValue().toString();
                    text_UserName.setText(userName);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void wakeUpMenu(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                MainActivity.this, R.style.BottomSheetDialogTheme
        );
        View bottomShertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_dialogo,(LinearLayout)findViewById(R.id.bottomShetContainer));

        bottomSheetDialog.setContentView(bottomShertView);
        bottomSheetDialog.show();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_menu:
                wakeUpMenu();
                break;
            case R.id.btn_Link:
                startActivity(new Intent(this,UpdateImage.class));
                break;

        }
    }
    private void initializeUI() {
        btn_menu = findViewById(R.id.btn_menu);
        btn_UpdateImage = findViewById(R.id.btn_Link);
        text_UserName = findViewById(R.id.text_UserName);
    }
}