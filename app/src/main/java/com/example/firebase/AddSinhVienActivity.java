package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddSinhVienActivity extends AppCompatActivity {

    private TextInputEditText svNameEdt, phoneEdt, emailEdt;
    private Button addBtn;
    private ProgressBar loadingPB;
    private RadioButton sexRB;
    private RadioGroup gioiTinhRB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String svID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinh_vien);
        svNameEdt = findViewById(R.id.idEdtName);
        gioiTinhRB = findViewById(R.id.idRBGioiTinh);
        phoneEdt = findViewById(R.id.idEdtPhone);
        emailEdt = findViewById(R.id.idEdtEmail);
        addBtn = findViewById(R.id.idBtnAddSinhVien);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sinh Vien");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String svName = svNameEdt.getText().toString();

                int selected = gioiTinhRB.getCheckedRadioButtonId();
                sexRB = findViewById(selected);
                String svGioiTinh = sexRB.getText().toString();

                String svPhone = phoneEdt.getText().toString();
                String svEmail = emailEdt.getText().toString();
                svID = svName;
                SinhVienRVModal sinhVienRVModal = new SinhVienRVModal(svName, svGioiTinh, svPhone, svEmail, svID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(svID).setValue(sinhVienRVModal);
                        Toast.makeText(AddSinhVienActivity.this, "Sinh Vien Added..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddSinhVienActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddSinhVienActivity.this, "Error is" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}