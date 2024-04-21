package com.example.projetofirebase;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FormSenha extends AppCompatActivity {

    EditText edit_email; // Altere String para EditText

    private static final String TAG = "FormSenha"; // Defina sua tag de log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        // Configurar o botão 'btn_voltar' para voltar uma tela
        Button btnVoltar = findViewById(R.id.seta2);
        btnVoltar.setOnClickListener(v -> finish());

        // Configurar o botão 'btn_reset' para enviar o email de redefinição de senha
        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(v -> enviarEmailRedefinicaoSenha());
    }

    private void enviarEmailRedefinicaoSenha() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = edit_email.getText().toString().trim();

        // Verificar se o campo de email está vazio
        if (email.isEmpty()) {
            Toast.makeText(FormSenha.this, "Por favor, insira seu email", Toast.LENGTH_LONG).show();
            return; // Encerrar o método se o campo de email estiver vazio
        }

        auth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> Toast.makeText(FormSenha.this, "Enviamos um email de recuperação!", Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> {
                    // Tratar a falha ao enviar o email de redefinição de senha
                    Toast.makeText(FormSenha.this, "Falha ao enviar email de recuperação: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Falha ao enviar email de recuperação", e);
                });
    }}