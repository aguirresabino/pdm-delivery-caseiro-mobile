package io.github.aguirresabino.deliverycaseiro.view.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

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
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.ClientePedidosFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.ClientePerfilFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.InitialFragment;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

/**
 * BaseActivity é um objeto que implementa o LifeCycle da Activity e outras lógicas
 * que serão herdadas por Activities da aplicação (reuso).
 */
public class BaseActivity extends AppCompatActivity {

    //CONSTANTES que representam a posicao do menu na navigation drawer
    private  final int INICIO = 0;
    private  final int MEUS_PEDIDOS = 1;
    private  final int PERFIL = 2;
    private  final int SAIR = 4;

    private Drawer drawerLeft;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onCreate() chamado " + savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onStart() chamado ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onResume() chamado ");
    }

    @Override
    protected void onPause() {
        super.onPause();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onPause() chamado ");
    }

    @Override
    protected void onStop() {
        super.onStop();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onStop() chamado ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onDestroy() chamado ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onRestart() chamado ");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), " onSaveInstanceState() chamado " + outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MyLogger.logInfo(DeliveryApplication.MY_TAG, getClass(), "onRestoreInstanceState() chamado " + savedInstanceState);

        super.onRestoreInstanceState(savedInstanceState);
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
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position){
                            case INICIO:
                                drawerLeft.closeDrawer();
                                replaceFragment(new InitialFragment());
                                break;
                            case MEUS_PEDIDOS:
                                drawerLeft.closeDrawer();
                                replaceFragment(new ClientePedidosFragment());
                                break;
                            case PERFIL:
                                drawerLeft.closeDrawer();
                                replaceFragment(new ClientePerfilFragment());
                                break;
                            case SAIR:
                                drawerLeft.closeDrawer();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                        return true;
                    }
                })
                .build();
    }

    /**
     * Caso o drawerLeft esteja aberto e o usuário aperte o botão "voltar", o drawerLeft é fechado.
     */
    @Override
    public void onBackPressed() {
        if(drawerLeft != null){
            if(drawerLeft.isDrawerOpen()) drawerLeft.closeDrawer();
        }
        else super.onBackPressed();
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
