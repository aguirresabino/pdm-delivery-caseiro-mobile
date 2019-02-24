package io.github.aguirresabino.deliverycaseiro.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.view.activity.base.BaseActivity;
import io.github.aguirresabino.deliverycaseiro.view.adapter.ListCardAdapter;

public class ChefeActivity extends BaseActivity {

    @BindView(R.id.activityChefeRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activityChefeCollapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefe);
        //
        ButterKnife.bind(this);
        //
        setUpToolbar(toolbar);
        //
        collapsingToolbarLayout.setTitle("Nome do Chefe");
        //collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        //utilizando botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //configurando recyclerview
        setUpRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //se clicar no botão voltar, a activity atual é finalizada
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ListCardAdapter(getResources().getStringArray(R.array.teste), this.onClickPrato()));
    }

    private ListCardAdapter.CardOnClickListener onClickPrato(){
        return new ListCardAdapter.CardOnClickListener() {
            @Override
            public void onClickCard(View view, int idx) {
                //String item = getResources().getStringArray(R.array.teste)[idx];
                //Toast.makeText(getBaseContext(), "Clicou em " + item, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), PratoPedidoActivity.class);
                startActivity(intent);
            }
        };
    }
}
