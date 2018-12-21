package io.github.aguirresabino.deliverycaseiro.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.fragments.ClientePedidosFragment;
import io.github.aguirresabino.deliverycaseiro.fragments.ClientePerfilFragment;
import io.github.aguirresabino.deliverycaseiro.fragments.InitialFragment;
import io.github.aguirresabino.deliverycaseiro.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

/**
 * BaseActivity é um objeto que implementa o LifeCycle da Activity e outras lógicas
 * que serão herdadas por Activities da aplicação (reuso).
 */
public class BaseActivity extends AppCompatActivity {

    //Atributo que define o nome da MY_TAG específica utilizada por esta classe em DEBUG
    private final String MY_TAG = "DELIVERY_CASEIRO_DEBUG";

    //CONSTANTES que representam a posicao do menu na navigation drawer
    private  final int INICIO = 0;
    private  final int MEUS_PEDIDOS = 1;
    private  final int PERFIL = 2;
    private  final int SAIR = 4;

    private Drawer drawerLeft;

    //variável utilizada para verificar se o botão voltar foi pressionado duas vezes
    //caso sim, a aplicação será fechada
    protected static int count = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.MY_TAG, getClass(), " onCreate() chamado " + savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onStart() chamado ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onResume() chamado ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onPause() chamado ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onStop() chamado ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onDestroy() chamado ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyLogger.logInfo(this.MY_TAG, getClass(), " onRestart() chamado ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        MyLogger.logInfo(this.MY_TAG, getClass(), " onSaveInstanceState() chamado " + outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MyLogger.logInfo(this.MY_TAG, getClass(), "onRestoreInstanceState() chamado " + savedInstanceState);

        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Caso o drawerLeft esteja aberto e o usuário aperte o botão "voltar", o drawerLeft é fechado.
     */
    @Override
    public void onBackPressed() {
        if(drawerLeft.isDrawerOpen()) drawerLeft.closeDrawer();
        else {
            if(count == 1){
                count = 0;
                super.onBackPressed();
            }
            else{
                count++;
                Toast.makeText(this, "Aperte o botão voltar novamente para fechar o aplicativo.", Toast.LENGTH_LONG).show();
            }
        };
    }

    /**
     * Este método inicializa o menu lateral esquerdo da aplicação.
     * O menu foi implementado utilizando a biblioteca MaterialDrawer do mikepenz:
     *
     * https://github.com/mikepenz/MaterialDrawer
     *
     * @param toolbar Toolbar utilizada pela activity ou fragment
     */
    protected void setUpNavigationDrawer(Toolbar toolbar){

        drawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withDrawerGravity(Gravity.LEFT)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Início"),
                        new PrimaryDrawerItem().withName("Meus Pedidos"),
                        new PrimaryDrawerItem().withName("Perfil"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Sair")
                )
                .withSelectedItemByPosition(INICIO)
//                .withOnDrawerNavigationListener(new Drawer.OnDrawerNavigationListener() {
//                    @Override
//                    public boolean onNavigationClickListener(View clickedView) {
//                        Toast.makeText(BaseActivity.this, "Menu lateral aberto/fechado", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case INICIO:
                                replaceFragment(new InitialFragment());
                                break;
                            case MEUS_PEDIDOS:
                                replaceFragment(new ClientePedidosFragment());
                                break;
                            case PERFIL:
                                replaceFragment(new ClientePerfilFragment());
                                break;
                            case SAIR:
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }

    /**
     * Este método auxilia a troca de fragments dentro de uma Activity.
     * @param fragment Fragment que deve ser exibido na tela.
     */
    protected void replaceFragment(BaseFragment fragment){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainActivityfragmentContainer, fragment)
                .commit();

    }

    /**
     * Este método adiciona uma toolbar a activity do contexto.
     * @param toolbar O layout da toolbar
     */
    public void setUpToolbar(Toolbar toolbar){
        if(toolbar != null) {
            this.setSupportActionBar(toolbar);
        }
    }

    /**
     * Este método é utilizado por algum fragment filho para atualizar a toolbar exibida na Activity.
     * @param toolbar Nova toolbar a ser exibida
     */
    public void updateToolbarInDrawer(Toolbar toolbar){
        drawerLeft.setToolbar(this, toolbar, true);
    }

}
