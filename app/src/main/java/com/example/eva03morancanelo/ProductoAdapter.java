package com.example.eva03morancanelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductoAdapter extends BaseAdapter {

    private Context context;
    private List<Productos> itemList;

    public ProductoAdapter(Context context, List<Productos> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Productos getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Puedes cambiarlo si tienes un ID único
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Productos item = itemList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }

        ImageView imgItem = convertView.findViewById(R.id.imgProducto);
        TextView txtNombre = convertView.findViewById(R.id.txtNombre);
        TextView txtPrecio = convertView.findViewById(R.id.txtPrecio);

        txtNombre.setText(item.getNombre());
        txtPrecio.setText("$" + item.getPrecio());

        // Configurar RequestOptions
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder) // Asegúrate de que esta imagen existe
                .error(R.drawable.error); // Asegúrate de que esta imagen existe

        Glide.with(context)
                .load(item.getImgUrl())
                .apply(requestOptions)
                .into(imgItem);

        return convertView;
    }
}
