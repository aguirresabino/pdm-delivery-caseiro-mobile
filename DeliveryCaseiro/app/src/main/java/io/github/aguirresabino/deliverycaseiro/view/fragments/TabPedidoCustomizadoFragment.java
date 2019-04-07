package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.logs.MyLogger;
import io.github.aguirresabino.deliverycaseiro.model.entities.ItemPedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.enums.ValuesApplicationEnum;
import io.github.aguirresabino.deliverycaseiro.view.activity.ClientePerfilActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.PratoPedidoActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabPedidoCustomizadoFragment extends BaseFragment {

    @BindView(R.id.fragmentTabPedidoCustomizadoEditTextNomePrato) EditText nomePrato;
    @BindView(R.id.fragmentTabPedidoCustomizadoEditTextDescricaoPrato) EditText descricaoPrato;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_pedido_customizado, container, false);
        // ButterKnife
        ButterKnife.bind(this, view);
        // O fragment define o menu da toolbar
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_tab_pedido_customizado, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_post:
//                create();
                break;
            case R.id.action_perfil:
                getActivity().startActivity(new Intent(getContext(), ClientePerfilActivity.class));
                break;
            case R.id.action_sair:
                DeliveryApplication.usuarioLogado = null;
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    private void create() {
//        ItemPedido itemPedido = new ItemPedido();
//        itemPedido.setDescricao(descricaoPrato.getText().toString());
//        itemPedido.setNome(nomePrato.getText().toString());
//        //TODO Colocar quantidade no formulário
//        itemPedido.setQuantidade(2);
//        itemPedido.setValor("");
//
//        Pedido pedido = new Pedido();
//        pedido.setEndereco(DeliveryApplication.usuarioLogado.getEndereco());
//        pedido.setIdUsuario(DeliveryApplication.usuarioLogado.getId());
//        pedido.setEntregar(true);
//        pedido.setIdFornecedor(getIntent().getStringExtra("idChefe"));
//        pedido.setStatus(true);
//        pedido.setItens(Arrays.asList(itemPedido));
//
//        showDialog(pedido);
//    }
//
//    private void showDialog(Pedido pedido) {
//        new AlertDialog.Builder(this)
//                .setTitle(getResources().getString(R.string.app_name))
//                .setMessage("Deseja finalizar o pedido?")
//                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
//                .setPositiveButton("Sim",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Call<Pedido> call = apiDeliveryCaseiroUsuario.create(pedido);
//
//                                call.enqueue(new Callback<Pedido>() {
//                                    @Override
//                                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {
//                                        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PratoPedidoActivity.class, "Pedido realizado: " + pedido.toString());
//                                        ToastHelper.toastShort(getBaseContext(), "O pedido foi realizado!");
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Pedido> call, Throwable t) {
//                                        MyLogger.logInfo(ValuesApplicationEnum.MY_TAG.getValue(), PratoPedidoActivity.class, "Pedido não foi realizado!");
//                                    }
//                                });
//                            }
//                        })
//                .setNegativeButton("Não", null).show();
//    }
}
