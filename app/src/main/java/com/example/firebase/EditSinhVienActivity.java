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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditSinhVienActivity extends AppCompatActivity {

    private TextInputEditText svNameEdt, phoneEdt, emailEdt;
    private Button updateBtn, deleteBtn;
    private ProgressBar loadingPB;
    private RadioButton sexRB;
    private RadioGroup gioiTinhRB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    String svID;
    private SinhVienRVModal sinhVienRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sinh_vien);
        svNameEdt = findViewById(R.id.idEdtName);
        gioiTinhRB = findViewById(R.id.idRBGioiTinh);
        phoneEdt = findViewById(R.id.idEdtPhone);
        emailEdt = findViewById(R.id.idEdtEmail);
        updateBtn = findViewById(R.id.idBtnUpdateSinhVien);
        deleteBtn = findViewById(R.id.idBtnDeleteSinhVien);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        sinhVienRVModal = getIntent().getParcelableExtra("sinh vien");
        if (sinhVienRVModal != null){
            svNameEdt.setText(sinhVienRVModal.getSvName());
            if(sinhVienRVModal.getSvName().equals("Nam")){
                gioiTinhRB.check(R.id.idRBNam);
            }else{
                gioiTinhRB.check(R.id.idRBNu);
            }
            phoneEdt.setText(sinhVienRVModal.getPhone());
            emailEdt.setText(sinhVienRVModal.getEmail());
        }

        databaseReference = firebaseDatabase.getReference("Sinh Vien").child(svID);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String svName = svNameEdt.getText().toString();

                int selected = gioiTinhRB.getCheckedRadioButtonId();
                sexRB = findViewById(selected);
                String svGioiTinh = sexRB.getText().toString();

                String svPhone = phoneEdt.getText().toString();
                String svEmail = emailEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("svName", svName);
                map.put("gioiTinh", svGioiTinh);
                map.put("phone", svPhone);
                map.put("email", svEmail);
                map.put("svID", svID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditSinhVienActivity.this, "Sinh Vien Updated..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditSinhVienActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditSinhVienActivity.this, "Failed to updated..", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSinhVien();
            }
        });
    }
    private void deleteSinhVien(){
        databaseReference.removeValue();
        Toast.makeText(this, "Sinh Vien Deleted..", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditSinhVienActivity.this, MainActivity.class));
    }
}