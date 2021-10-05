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

public class RegistrarActivity extends AppCompatActivity {

    //  criar variáveis para editar e visualizar texto com,
    //  autenticação do firebase, botão e barra de progresso.
    private TextInputEditText userNameEdt, passwordEdt, confirmPwdEdt;
    private TextView loginTV;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

    // inicializando todas as nossas variáveis.
        userNameEdt = findViewById(R.id.idEdtUserName);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPwdEdt = findViewById(R.id.idEdtConfirmPassword);
        loginTV = findViewById(R.id.idTVLoginUser);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();

        // adicionando um clique para fazer login na tela.
        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // abrir activity de login ao clicar no texto de login.
                Intent i = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        // adicionando listener de clique para o botão de registro.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            // escondendo nossa barra de progresso.
                loadingPB.setVisibility(View.VISIBLE);
            // obter dados de texto de edição.
                String userName = userNameEdt.getText().toString();
                String pwd = passwordEdt.getText().toString();
                String cnfPwd = confirmPwdEdt.getText().toString();

            // verificando se a senha e a confirme a senha são iguais ou não.
                if (!pwd.equals(cnfPwd)) {
                    Toast.makeText(RegistrarActivity.this,"Por favor, verifique se ambos têm a mesma senha..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {
                    // verificando se os campos de texto estão vazios ou não.
                    Toast.makeText(RegistrarActivity.this, "Por favor, insira suas credenciais ..", Toast.LENGTH_SHORT).show();
                } else {
                    // na linha abaixo estamos criando um novo usuário passando e-mail e senha.
                    mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // na linha abaixo estamos verificando se a tarefa é bem-sucedida ou não.
                            if (task.isSuccessful()) {

                                 // no método de sucesso, estamos escondendo nossa barra de progresso e abrindo uma atividade de login.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrarActivity.this, "Usuário Registrado..", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrarActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                // se for outra condição, estamos exibindo uma mensagem de falha no sistema.
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(RegistrarActivity.this, "Falha ao registrar usuário..", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}