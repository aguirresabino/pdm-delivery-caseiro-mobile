package io.github.aguirresabino.deliverycaseiro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    private final String TAG = getClass().getName() + " ESPECIFICA ";

    private AppCompatButton btEntrar;
    private AppCompatButton btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //buscando botão ENTRAR na View
        this.btEntrar = findViewById(R.id.activityLoginButtonEntrar);
        //buscando botão CADASTRAR na View
        this.btCadastrar = findViewById(R.id.activityLoginButtonCrieSuaConta);
        //configurando ação de clique sobre o botão ENTRAR
        btEntrar.setOnClickListener(this.btEntrar());
    }

    /**
     * Este método implementa um listener que aguardará uma ação de clique para
     * verificar os dados de acesso do usuário e redirecioná-lo para a página principal
     * do aplicativo.
     * @return Implementação do OnClickListener
     */
    private View.OnClickListener btEntrar(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        };
    }
}
