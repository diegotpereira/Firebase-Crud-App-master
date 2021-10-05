package br.java.firebase_crud_app_master;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CursoRVAdapter.CursoClickInterface{

    // criando variáveis ​​para fab, firebase database,
    // barra de progresso, lista, adaptador, firebase auth,
    // visualização do reciclador e layout relativo.
    private FloatingActionButton addCourseFAB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView cursoRV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<CursoRVModal> cursoRVModalArrayList;
    private CursoRVAdapter cursoRVAdapter;
    private RelativeLayout homeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializando todas as variáveis
        cursoRV = findViewById(R.id.idRVCourses);
        homeRL = findViewById(R.id.idRLBSheet);
        loadingPB = findViewById(R.id.idPBLoading);
        addCourseFAB = findViewById(R.id.idFABAddCourse);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        cursoRVModalArrayList = new ArrayList<>();
        // na linha abaixo estamos obtendo referência de banco de dados.
        databaseReference = firebaseDatabase.getReference("Cursos");

        // na linha abaixo adicionando um ouvinte de clique para nosso botão de ação flutuante.
        addCourseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // abrindo uma nova atividade para adicionar um curso.
                Intent i = new Intent(MainActivity.this, AdicionarCursoActivity.class);
                startActivity(i);
            }
        });

        // na linha abaixo inicializando nossa classe de adaptador.
        cursoRVAdapter = new CursoRVAdapter(cursoRVModalArrayList, this, this::onCursoClick);

        // configuração do layout malinger para visualização do reciclador na linha abaixo.
        cursoRV.setLayoutManager(new LinearLayoutManager(this));

        // configuração do adaptador para a visualização do reciclador na linha abaixo.
        cursoRV.setAdapter(cursoRVAdapter);

        // na linha abaixo chamando um método para buscar cursos do banco de dados.
        getCursos();
    }

    private void getCursos() {
        // na linha abaixo limpando nossa lista.
        cursoRVModalArrayList.clear();

        // na linha abaixo, estamos chamando o método add child event listener para ler os dados.
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // na linha abaixo, estamos ocultando nossa barra de progresso.
                loadingPB.setVisibility(View.GONE);

                // adicionando instantâneo à nossa lista de array na linha abaixo.
                cursoRVModalArrayList.add(snapshot.getValue(CursoRVModal.class));

                // notificando nosso adaptador de que os dados foram alterados.
                cursoRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                // este método é chamado quando um novo filho é adicionado
                // quando estamos notificando nosso adaptador e fazendo uma barra de progresso
                // a visibilidade desapareceu.
                loadingPB.setVisibility(View.GONE);
                cursoRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                // notificando nosso adaptador quando a criança for removida.
                cursoRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                // notificando nosso adaptador quando a child é movida.
                cursoRVAdapter.notifyDataSetChanged();
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCursoClick(int position) {
        // chamando um método para exibir uma folha inferior na linha abaixo.
        displayBottonSheet(cursoRVModalArrayList.get(position));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // adicionar um ouvinte de clique para a opção selecionada na linha abaixo.
        int id = item.getItemId();
        switch (id) {
            case R.id.idLogOut:
                // exibindo uma mensagem para usuário desconectado com um clique.
                Toast.makeText(getApplicationContext(), "Usuário desconectado", Toast.LENGTH_LONG).show();

                // tradutor - Pesquisa Google.html
                mAuth.signOut();

                // na linha abaixo estamos abrindo nossa atividade de login.
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // na linha abaixo estamos inflando nosso cardápio
        // com arquivo para exibir nossas opções de menu.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayBottonSheet(CursoRVModal modal) {
        // na linha abaixo, estamos criando nossa caixa de diálogo de folha inferior.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        // na linha abaixo, estamos inflando nosso arquivo de layout para nossa folha inferior.
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_layout, homeRL);

        // na linha abaixo, estamos inflando nosso arquivo de layout para nossa folha inferior.
        bottomSheetTeachersDialog.setContentView(layout);

        // na linha abaixo estamos definindo um cancelável
        bottomSheetTeachersDialog.setCancelable(false);
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);

        // chamando um método para exibir nossa folha inferior.
        bottomSheetTeachersDialog.show();

        // na linha abaixo, estamos criando variáveis para
        // nossa visão de texto e visão de imagem dentro da folha inferior
        // and initialing them with their ids.
        TextView cursoNomeTela = layout.findViewById(R.id.idTVCourseName);
        TextView cursoDescTela = layout.findViewById(R.id.idTVCourseDesc);
        TextView adequadoParaTela = layout.findViewById(R.id.idTVSuitedFor);
        TextView precoTela = layout.findViewById(R.id.idTVCoursePrice);
        ImageView cursoIV = layout.findViewById(R.id.idIVCourse);

        // na linha abaixo, estamos configurando dados para diferentes visualizações na linha abaixo.
        cursoNomeTela.setText(modal.getCursoNome());
        cursoDescTela.setText(modal.getCursoDescricao());
        adequadoParaTela.setText("Adequado para " + modal.getMaisAdequadoPara());
        precoTela.setText("Rs." + modal.getCursoPreco());
        Picasso.get().load(modal.getCursoImg()).into(cursoIV);
        Button viewBtn = layout.findViewById(R.id.idBtnVIewDetails);
        Button editBtn = layout.findViewById(R.id.idBtnEditCourse);

        // adicionando um ouvinte de clique para nosso botão de edição.
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // na linha abaixo estamos abrindo nosso EditCourseActivity na linha abaixo.
                Intent i = new Intent(MainActivity.this, EditarCursoActivity.class);

                // na linha abaixo estamos passando nosso modal de curso
                i.putExtra("curso", String.valueOf(modal));
                startActivity(i);
            }
        });

        // adicionando ouvinte de clique para nosso botão de visualização na linha abaixo.
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // na linha abaixo, estamos navegando para o navegador
                // para exibir detalhes do curso a partir de seu url.
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modal.getCursoLink()));
                startActivity(i);
            }
        });
    }
}