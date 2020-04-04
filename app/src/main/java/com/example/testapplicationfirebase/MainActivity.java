package com.example.testapplicationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edID,edName,edAddress,edConNo;
    Button btnSave,btnShow,btnUpdate,btnDelete;
    DatabaseReference dbRef;
    Student std;

    private void clearControls(){
        edID.setText("");
        edName.setText("");
        edAddress.setText("");
        edConNo.setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edID = findViewById(R.id.editText);
        edName = findViewById(R.id.editText2);
        edAddress = findViewById(R.id.editText3);
        edConNo = findViewById(R.id.editText4);

        btnSave = findViewById(R.id.button);
        btnShow = findViewById(R.id.button2);
        btnUpdate = findViewById(R.id.button4);
        btnDelete = findViewById(R.id.button3);

        std = new Student();

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("Student");

                try {
                    if(TextUtils.isEmpty(edID.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(edName.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(edAddress.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter an ID",Toast.LENGTH_SHORT).show();
                    else{
                        std.setId(edID.getText().toString().trim());
                        std.setName(edName.getText().toString().trim());
                        std.setAddress(edAddress.getText().toString().trim());
                        std.setConNo(edConNo.getText().toString().trim());

                        //dbRef.push().setValue(std);
                        dbRef.child("Std1").setValue(std);

                        Toast.makeText(getApplicationContext(),"Data Saved Successfully",Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();

                }

            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            edID.setText(dataSnapshot.child("id").getValue().toString());
                            edName.setText(dataSnapshot.child("name").getValue().toString());
                            edAddress.setText(dataSnapshot.child("address").getValue().toString());
                            edConNo.setText(dataSnapshot.child("conNo").getValue().toString());

                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Sourse to Display",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            try{
                                std.setId(edID.getText().toString().trim());
                                std.setName(edName.getText().toString().trim());
                                std.setAddress(edAddress.getText().toString().trim());
                                std.setConNo(edConNo.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                                dbRef.setValue(std);
                                clearControls();

                                Toast.makeText(getApplicationContext(),"DATA UPDATED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                            }
                            catch (NumberFormatException e){
                                Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to update",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("Std1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("Std1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"No source to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
