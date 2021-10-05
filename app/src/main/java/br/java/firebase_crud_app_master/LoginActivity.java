package br.java.firebase_crud_app_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    // criando variável para edição e visualização de texto,
    // botão, barra de progresso e autenticação do firebase.
    private TextInputEditText userNameEdt, passwordEdt;
    private Button loginBtn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // inicializando todos as variáveis
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);

        // adicionando um listener(ouvinte) de clique para nossa nova tv de usuário.
        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // na linha abaixo abrindo a activity de login.
                Intent i = new Intent(LoginActivity.this, RegistrarActivity.class);
                startActivity(i);
            }
        });

        // adicionando um listener (ouvinte) de clique para nosso botão de login.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // escondendo nossa barra de progresso.
                loadingPB.setVisibility(View.VISIBLE);

                // obtendo dados de nosso texto de edição na linha abaixo.
                String email = userNameEdt.getText().toString();
                String password = passwordEdt.getText().toString();

                // na linha abaixo, validando a entrada de texto.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Por favor entre com suas credenciais..", Toast.LENGTH_SHORT).show();
                    return;
                }

                //na linha abaixo estamos chamando um método de login e passando e-mail e senha para ele.
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // na linha abaixo estamos verificando se a tarefa foi bem sucedida ou não.
                        if (task.isSuccessful()) {
                            // na linha abaixo, estamos ocultando nossa barra de progresso.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Login bem sucedido..", Toast.LENGTH_SHORT).show();

                            // na linha abaixo estamos abrindo nossa activity principal.
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            // escondendo nossa barra de progresso e exibindo uma mensagem brinde.
                            loadingPB.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Por favor, insira credenciais de usuário válidas .", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void  onStart() {
        super.onStart();

        // no método de início verificando se o usuário já está conectado.
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {

            // se o usuário não for nulo, estamos abrindo uma atividade principal na linha abaixo.
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}
