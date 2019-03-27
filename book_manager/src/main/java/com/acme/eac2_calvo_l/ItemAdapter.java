package com.acme.eac2_calvo_l;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements View.OnClickListener {

    public ArrayList<Llibre> mData;
    public View.OnClickListener listener;
    private Context context;

    public ItemAdapter(Context context, ArrayList<Llibre> data) {
        this.context = context;
        this.mData = data;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Este método provee el layout a inflar para usar en la vista
        //Esto siempre es igual para cualquier recycler view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
        itemView.setOnClickListener(this);
        ItemViewHolder ivh = new ItemViewHolder(itemView);
        return ivh;

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        Llibre item = mData.get(position);
        holder.bindEntry(item);
        holder.mAutor.setText(item.getAutors());
        holder.mTitle.setText(item.getTitol());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public Llibre getItemAt(int pos) {
        return mData.get(pos);
    }

    public void onItemDismiss(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    /*La clase interna ItemViewHolder utilizada por el adaptador se encarga de hacer uso
    del patrón ViewHolder, que mantiene la relación entre los elementos gráficos de cada linea y los
    valores a mostrar en cada momento
    Permite acceder por ejemplo a elementos en concreto de la lista con los metodos:
    getAdapterPosition.
    */
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAutor;
        //En el constructor tenemos referencias a los elementos de layout a mostrar
        public ItemViewHolder(View itemView) {
            super(itemView);
            //Esto se basa en el layout de fila.
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mAutor = (TextView) itemView.findViewById(R.id.autor);
        }

        //Con el método BindEntry asignamos los valores a mostrar en los itemsView.
        public void bindEntry(Llibre llibre) {
            mTitle.setText(llibre.getTitol());
            mAutor.setText(llibre.getAutors());
        }

    }
}
