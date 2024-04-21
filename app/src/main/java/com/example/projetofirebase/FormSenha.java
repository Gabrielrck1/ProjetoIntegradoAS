package com.example.projetofirebase;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FormSenha extends AppCompatActivity {

    private static final String TAG = "FormSenha"; // Defina sua tag de log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        // Enviar email de redefinição de senha
        enviarEmailRedefinicaoSenha();
    }

    private void enviarEmailRedefinicaoSenha() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String email = "exemplo@email.com";

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Email de redefinição de senha enviado com sucesso
                            Log.d(TAG, "Email enviado.");
                            // Exibir mensagem de sucesso para o usuário
                        } else {
                            // Falha ao enviar o email de redefinição de senha
                            Log.w(TAG, "Falha ao enviar email.", task.getException());
                            // Exibir mensagem de erro para o usuário
                        }
                    }
                });
    }
}
