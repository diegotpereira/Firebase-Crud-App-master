package br.java.firebase_crud_app_master;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CursoRVAdapter extends RecyclerView.Adapter<CursoRVAdapter.ViewHolder> {

    // criando variáveis para nossa lista, contexto, interface e posição
    private ArrayList<CursoRVModal> cursoRVModalArrayList;
    private Context context;
    private CursoClickInterface cursoClickInterface;
    int ultimaPos = -1;

    // criado um construtor
    public CursoRVAdapter(ArrayList<CursoRVModal> cursoRVModalArrayList, Context context, CursoClickInterface cursoClickInterface) {
        this.cursoRVModalArrayList = cursoRVModalArrayList;
        this.context = context;
        this.cursoClickInterface = cursoClickInterface;
    }
    @NonNull
    @Override
    public CursoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // inflando nosso arquivo de layout na linha abaixo.
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CursoRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // definir dados para nosso item de exibição de reciclador na linha abaixo.
        CursoRVModal cursoRVModal = cursoRVModalArrayList.get(position);
        holder.cursoTela.setText(cursoRVModal.getCursoNome());
        holder.cursoPrecoTela.setText("Rs." + cursoRVModal.getCursoPreco());
        Picasso.get().load(cursoRVModal.getCursoImg()).into(holder.cursoIV);

        // adicionar animação ao item de visualização do reciclador na linha abaixo.
        setAnimation(holder.itemView, position);
        holder.cursoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursoClickInterface.onCursoClick(position);
            }
        });
    }

    private  void setAnimation(View itemView, int position) {
        if (position > ultimaPos) {
            // na linha abaixo estamos configurando a animação.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            ultimaPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return cursoRVModalArrayList.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        // criando variável para nossa visualização de imagem e visualização de texto na linha abaixo.
        private ImageView cursoIV;
        private TextView cursoTela, cursoPrecoTela;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cursoIV = itemView.findViewById(R.id.idIVCourse);
            cursoTela = itemView.findViewById(R.id.idTVCourseName);
            cursoPrecoTela = itemView.findViewById(R.id.idTVCousePrice);
        }
    }

    // criando uma interface para clicar
    public interface CursoClickInterface {
        void onCursoClick(int position);
    }
}
