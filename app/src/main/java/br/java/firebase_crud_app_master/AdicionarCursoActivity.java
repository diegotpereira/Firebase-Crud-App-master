package br.java.firebase_crud_app_master;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdicionarCursoActivity extends AppCompatActivity {

    // criando variáveis para o nosso botão, editar texto,
    // banco de dados firebase, referência de banco de dados, barra de progresso.
    private Button addCourseBtn;
    private TextInputEditText courseNameEdt, couseDescEdt, courseDescEdt, coursePriceEdt, bestSuitedEdt, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String cursoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_curso);

        // inicializando todas as variáveis
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
        courseNameEdt = findViewById(R.id.idTILCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        // na linha abaixo criando nossa referência de banco de dados.
        databaseReference = firebaseDatabase.getReference("Cursos");

        // adicionando listener(ouvinte) de clique para nosso botão de adicionar curso.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);

                // obtendo dados de nosso texto de edição.
                String cursoNome = courseNameEdt.getText().toString();
                String cursoDesc = courseDescEdt.getText().toString();
                String cursoPreco = coursePriceEdt.getText().toString();
                String maisAdequado = bestSuitedEdt.getText().toString();
                String cursoImg = courseImgEdt.getText().toString();
                String cursoLink = courseLinkEdt.getText().toString();
                cursoID = cursoNome;

                // na linha abaixo estamos passando todos os dados para nossa classe modal.
                CursoRVModal cursoRVModal = new CursoRVModal(cursoNome, cursoDesc, cursoPreco, maisAdequado, cursoImg, cursoLink, cursoID);

                // na linha abaixo, estamos chamando um evento de valor agregado.
                // para passar dados para o banco de dados firebase.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // na linha abaixo, estamos configurando dados em nosso banco de dados Firebase.
                        databaseReference.child(cursoID).setValue(cursoRVModal);

                        // exibindo uma mensagem de brinde.
                        Toast.makeText(AdicionarCursoActivity.this, "Curso Adicionado..", Toast.LENGTH_SHORT).show();

                        // inicializando a activity principal
                        startActivity(new Intent(AdicionarCursoActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        
                        // exibindo uma mensagem de falha na linha abaixo.
                        Toast.makeText(AdicionarCursoActivity.this, "Falha ao adicionar curso..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}