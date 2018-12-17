package io.github.aguirresabino.deliverycaseiro.activity.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.github.aguirresabino.deliverycaseiro.activity.MainActivity;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;

/**
 * BaseActivity é um objeto que implementa o LifeCycle da Activity e outras lógicas
 * que serão herdadas por Activities da aplicação (reuso).
 */
public class BaseActivity extends AppCompatActivity {

    //Atributo que define o nome da TAG específica utilizada por esta classe em DEBUG
    private final String TAG = getClass().getName();
    private Drawer drawerLeft;
    protected static int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onCreate() chamado " + savedInstanceState);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MyLogger.logInfo(this.TAG, getClass(), "onStart() chamado");
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLogger.logInfo(this.TAG, getClass(), "onResume() chamado");
    }

    @Override
    protected void onPause() {
        super.onPause();

        MyLogger.logInfo(this.TAG, getClass(), "onPause() chamado");
    }

    @Override
    protected void onStop() {
        super.onStop();

        MyLogger.logInfo(this.TAG, getClass(), "onStop() chamado");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyLogger.logInfo(this.TAG, getClass(), "onDestroy() chamado");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyLogger.logInfo(this.TAG, getClass(), "onRestart() chamado");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        MyLogger.logInfo(this.TAG, getClass(), "onSaveInstanceState() chamado " + outState);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MyLogger.logInfo(this.TAG, getClass(), "onRestoreInstanceState() chamado " + savedInstanceState);

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
                //.withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.LEFT)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Início"),
                        new PrimaryDrawerItem().withName("Meus Pedidos"),
                        new PrimaryDrawerItem().withName("Perfil"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Sair")
                )
                .build();
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
}
