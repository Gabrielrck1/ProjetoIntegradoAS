package com.example.projetofirebase;
import android.content.Intent;
import android.graphics.Color;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Reservar2 extends AppCompatActivity {
    private EditText editTextDate;
    private EditText editTextDate3;
    private Calendar calendar;
    private FirebaseFirestore db;
    String[] mensagens = {"Reserva feita com sucesso!"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);
        IniciarComponentes();
        Button bt_voltar = findViewById(R.id.seta);
        bt_voltar.setOnClickListener(v -> finish());

        editTextDate = findViewById(R.id.editTextDate);
        editTextDate3 = findViewById(R.id.editTextDate2);
        calendar = Calendar.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextDate.setOnClickListener(this::onClick);
        editTextDate3.setOnClickListener(this::onClick);

        Button buttonConfirm = findViewById(R.id.buttonConfirm);

        buttonConfirm.setOnClickListener(v -> {
            confirmReservation();
            saveToFirestore(v); // Pass the current View when calling saveToFirestore
        });
    }

    private void confirmReservation() {
    }

    private void IniciarComponentes() {
    }

    private void onClick(View v) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Initialize the DatePickerDialog with the selected date
        DatePickerDialog datePickerDialog = new DatePickerDialog(Reservar2.this,
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    String selectedDate = dayOfMonth1 + "/" + (monthOfYear + 1) + "/" + year1;

                    // Set the selected date directly to the clicked EditText field
                    if (v == editTextDate) {
                        editTextDate.setText(selectedDate);
                    } else if (v == editTextDate3) {
                        editTextDate3.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        // Show the DatePickerDialog based on the EditText field that was clicked
        if (v == editTextDate || v == editTextDate3) {
            datePickerDialog.show();
        }
    }

    private void saveToFirestore(View v) {
        String date1 = editTextDate.getText().toString();
        String date2 = editTextDate3.getText().toString();

        // Create a new reservation object
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("date1", date1);
        reservation.put("date2", date2);

        // Add the reservation to the "reservations" collection in Firestore
        db.collection("reservations")
                .add(reservation)
                .addOnSuccessListener(documentReference -> {
                    // Data added successfully
                    // You can add any additional functionality here

                    Snackbar snackbar = Snackbar.make(v, mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.addCallback(new Snackbar.Callback() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            // Navigate to 'TelaPrincipal' after the Snackbar is dismissed
                            startActivity(new Intent(Reservar2.this, TelaPrincipal.class));
                        }
                    });
                    snackbar.show();
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                });
    }
}