package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase.R.id;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SinhVienRVAdapter.SinhVienClickInterface{

    private RecyclerView sinhvienRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<SinhVienRVModal> sinhVienRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private SinhVienRVAdapter sinhVienRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sinhvienRV = findViewById(R.id.idRVSinhVien);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idBtnAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sinh Vien");
        sinhVienRVModalArrayList = new ArrayList<>();
        //bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        sinhVienRVAdapter = new SinhVienRVAdapter(sinhVienRVModalArrayList, this, this);
        sinhvienRV.setLayoutManager(new LinearLayoutManager(this));
        sinhvienRV.setAdapter(sinhVienRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddSinhVienActivity.class));
            }
        });

        getAllSinhVien();
    }

    private void getAllSinhVien(){
        sinhVienRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                sinhVienRVModalArrayList.add(snapshot.getValue(SinhVienRVModal.class));
                sinhVienRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                sinhVienRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                sinhVienRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                sinhVienRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onSinhVienClick(int position) {
        displayBottomSheet(sinhVienRVModalArrayList.get(position));
    }

    private void displayBottomSheet(SinhVienRVModal sinhVienRVModal){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView svNameTV = layout.findViewById(R.id.idTVsvName);
        TextView svGioiTinhTV = layout.findViewById(R.id.idTVsvGioiTinh);
        TextView svDienThoaiTV = layout.findViewById(R.id.idTVsvDienThoai);
        TextView svEmailTV = layout.findViewById(R.id.idTVEmail);
        Button editBtn = layout.findViewById(id.idBtnEdit);

        svNameTV.setText(sinhVienRVModal.getSvName());
        svGioiTinhTV.setText(sinhVienRVModal.getGioiTinh());
        svDienThoaiTV.setText(sinhVienRVModal.getPhone());
        svEmailTV.setText(sinhVienRVModal.getEmail());

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditSinhVienActivity.class);
                intent.putExtra("sinh vien", sinhVienRVModal);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this,"Logged Out..",Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}