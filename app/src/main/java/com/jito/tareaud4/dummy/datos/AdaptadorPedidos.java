package com.jito.tareaud4.dummy.datos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jito.tareaud4.R;

import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.PedidosViewHolder> implements View.OnClickListener {


    private List<Pedido> pedidoList;
    private View.OnClickListener listener;

    public AdaptadorPedidos(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }


    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        //contruimos ViewHolder para el return
        Context context = parent.getContext();
        int layoutId = R.layout.row_pedidos;
        LayoutInflater infater = LayoutInflater.from(context);
        boolean velocidad = false;
        View view = infater.inflate(layoutId, parent, velocidad);

        // evento
        view.setOnClickListener(this);


        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position) {

        holder.asignarDatos(pedidoList.get(position));

      //  String texto = pedidoList.get(position).producto + " " + pedidoList.get(position).categoria;
     //   holder.textViewPedidos.setText(texto);

    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }


    public void evento(View.OnClickListener listener) {
        this.listener = listener;
    }



    public class PedidosViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPedidos;
        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPedidos = itemView.findViewById(R.id.textViewRow);
        }


        void asignarDatos(Pedido pedido) {
            String mensajePedido = "Pedido:\n" + pedido.producto + " (" + pedido.categoria + ")\n" + pedido.cantidade + "Pcs\nDireccion:\n" + pedido.cidade + "\n" + pedido.rua;
            textViewPedidos.setText(mensajePedido);
        }


    }


}
