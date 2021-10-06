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

import java.util.HashMap;
import java.util.Map;

public class EditarCursoActivity extends AppCompatActivity {

    // criando variáveis para nosso texto de edição, banco de dados firebase,
    // referência de banco de dados, modal rv do curso, barra de progresso.
    private TextInputEditText cursoNomeEdt, cursoDescEdt, cursoPrecoEdt, maisAdequadoEdt, cursoImgEdt, cursoLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CursoRVModal cursoRVModal;
    private ProgressBar loadingPB;

    // criando uma string para o nosso ID do curso.
    private String cursoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_curso);

        // inicializando todas as nossas variáveis ​​na linha abaixo.
        Button addCursoBtn = findViewById(R.id.idBtnAddCourse);
        cursoNomeEdt = findViewById(R.id.idEdtCourseName);
        cursoDescEdt = findViewById(R.id.idEdtCourseDescription);
        cursoPrecoEdt = findViewById(R.id.idEdtCoursePrice);
        maisAdequadoEdt = findViewById(R.id.idEdtSuitedFor);
        cursoImgEdt = findViewById(R.id.idEdtCourseImageLink);
        cursoLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // na linha abaixo estamos obtendo nossa classe modal pela qual passamos.
        cursoRVModal = getIntent().getParcelableExtra("curso");
        Button deletarCursoBtn = findViewById(R.id.idBtnDeleteCourse);

        if (cursoRVModal != null) {
            // na linha abaixo, estamos configurando dados para nosso texto de edição de nossa classe modal.
            cursoNomeEdt.setText(cursoRVModal.getCursoNome());
            cursoPrecoEdt.setText(cursoRVModal.getCursoPreco());
            maisAdequadoEdt.setText(cursoRVModal.getMaisAdequadoPara());
            cursoImgEdt.setText(cursoRVModal.getCursoImg());
            cursoLinkEdt.setText(cursoRVModal.getCursoLink());
            cursoDescEdt.setText(cursoRVModal.getCursoDescricao());
            cursoID = cursoRVModal.getCursoId();
        }

        // na linha abaixo, estamos inicializando nossa referência de banco de dados e adicionando um filho como nosso ID do curso.
        databaseReference = firebaseDatabase.getReference("Cursos").child(cursoID);

        // na linha abaixo, estamos adicionando um ouvinte de cliques para o botão Adicionar curso.
        addCursoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // na linha abaixo, estamos tornando nossa barra de progresso visível.
                loadingPB.setVisibility(View.VISIBLE);

                // na linha abaixo estamos obtendo dados de nosso texto de edição.
                String cursoNome = cursoNomeEdt.getText().toString();
                String cursoDesc = cursoDescEdt.getText().toString();
                String cursoPreco = cursoPrecoEdt.getText().toString();
                String maisAdequadoPara = maisAdequadoEdt.getText().toString();
                String cursoImg = cursoImgEdt.getText().toString();
                String cursoLink = cursoLinkEdt.getText().toString();

                // na linha abaixo, estamos criando um mapa para
                // passando dados usando par de chave e valor.
                Map<String, Object> map = new HashMap<>();
                map.put("cursoNome", cursoNome);
                map.put("cursoDescricao", cursoDesc);
                map.put("cursoPreco", cursoPreco);
                map.put("maisAdequadoPara" , maisAdequadoPara);
                map.put("cursoImg", cursoImg);
                map.put("cursoLink", cursoLink);
                map.put("cursoId", cursoID);

                // na linha abaixo, estamos chamando uma referência de banco de dados em
                // listener (ouvinte) de eventos de valor agregado e no método de alteração de dados.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // fazendo com que a visibilidade da barra de progresso desaparecesse.
                        loadingPB.setVisibility(View.GONE);

                        // adicionando um mapa ao nosso banco de dados.
                        databaseReference.updateChildren(map);

                        // na linha abaixo, estamos exibindo uma mensagem brinde.
                        Toast.makeText(EditarCursoActivity.this, "Curso Atualizado", Toast.LENGTH_SHORT).show();

                        // abrindo uma nova activity após atualizar nosso processo.
                        startActivity(new Intent(EditarCursoActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        // exibindo uma mensagem de falha.
                        Toast.makeText(EditarCursoActivity.this, "Falha ao Atualizar Curso", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adicionando um listener(ouvinte) de clique para nosso botão de exclusão de curso.
        deletarCursoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chamando um método para excluir um curso.
                deletarCurso();
            }
        });
    }

    private void deletarCurso() {

        // chamando um método para deletar o curso.
        databaseReference.removeValue();

        // exibindo uma mensagem brinde na linha abaixo.
        Toast.makeText(this, "Curso Deletado..", Toast.LENGTH_SHORT).show();

        // abrindo uma atividade principal na linha abaixo.
        startActivity(new Intent(EditarCursoActivity.this, MainActivity.class));
    }
}