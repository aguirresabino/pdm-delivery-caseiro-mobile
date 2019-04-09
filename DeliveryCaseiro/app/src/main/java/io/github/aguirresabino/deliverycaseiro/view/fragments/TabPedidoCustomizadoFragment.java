package io.github.aguirresabino.deliverycaseiro.view.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.aguirresabino.deliverycaseiro.R;
import io.github.aguirresabino.deliverycaseiro.application.DeliveryApplication;
import io.github.aguirresabino.deliverycaseiro.model.entities.ItemPedido;
import io.github.aguirresabino.deliverycaseiro.model.entities.Pedido;
import io.github.aguirresabino.deliverycaseiro.model.enums.StatusPedidoEnum;
import io.github.aguirresabino.deliverycaseiro.model.services.PedidoService;
import io.github.aguirresabino.deliverycaseiro.view.activity.LoginActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.MainActivity;
import io.github.aguirresabino.deliverycaseiro.view.activity.UsuarioPerfilActivity;
import io.github.aguirresabino.deliverycaseiro.view.fragments.base.BaseFragment;
import io.github.aguirresabino.deliverycaseiro.view.helpers.ToastHelper;

public class TabPedidoCustomizadoFragment extends BaseFragment {

    @BindView(R.id.fragmentTabPedidoCustomizadoEditTextNomePrato) EditText nomePrato;
    @BindView(R.id.fragmentTabPedidoCustomizadoEditTextDescricaoPrato) EditText descricaoPrato;
    @BindView(R.id.fragmentTabPedidoCustomizadoEditTextQuantidade) EditText quantidade;

    private PedidoService pedidoService;
    private LocalBroadcastReceiver localBroadcastReceiver;

    @Override
    public void onAttach(@NonNull Context context) {
        pedidoService = new PedidoService(this.getActivity());
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(localBroadcastReceiver, new IntentFilter(LocalBroadcastReceiver.LOCAL_BROADCAST_TAB_PEDIDO_CUSTOMIZADO_FRAGMENT));
        super.onViewCreated(view, savedInstanceState);
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
                post();
                break;
            case R.id.action_perfil:
                getActivity().startActivity(new Intent(getContext(), UsuarioPerfilActivity.class));
                break;
            case R.id.action_sair:
                DeliveryApplication.usuarioLogado = null;
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(localBroadcastReceiver);
    }

    private void post() {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescricao(descricaoPrato.getText().toString());
        itemPedido.setNome(nomePrato.getText().toString());
        itemPedido.setQuantidade(Integer.valueOf(quantidade.getText().toString()));
        itemPedido.setValor(null);

        Pedido pedido = new Pedido();
        pedido.setEndereco(DeliveryApplication.usuarioLogado.getEndereco());
        pedido.setIdUsuario(DeliveryApplication.usuarioLogado.getId());
        pedido.setPedidoCustomizado(true);
        pedido.setImagem(DeliveryApplication.usuarioLogado.getImagem());
        pedido.setIdFornecedor(null);
        pedido.setValor(null);
        pedido.setStatus(StatusPedidoEnum.PEDIDO_POSTADO.getValue());
        pedido.setItens(Arrays.asList(itemPedido));
        pedido.setChefesDoPedidoCustomizado(Collections.emptyList());

        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Deseja finalizar o pedido?")
//                .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                .setPositiveButton(R.string.sim,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pedidoService.create(pedido);
                            }
                        })
                .setNegativeButton(R.string.nao, null).show();
    }

    public class LocalBroadcastReceiver extends BroadcastReceiver {

        public static final String LOCAL_BROADCAST_TAB_PEDIDO_CUSTOMIZADO_FRAGMENT = "local.broadcast.tab.pedido.customizado.fragment";

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successMessage = (boolean) intent.getBooleanExtra("pedido.service.create", false);

            if(successMessage) {
                ToastHelper.toastShort(getActivity(), "O pedido foi postado!");
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
            else ToastHelper.toastShort(getActivity(), "Erro durante o pedido! Tente novamente mais tarde.");
        }
    }
}
