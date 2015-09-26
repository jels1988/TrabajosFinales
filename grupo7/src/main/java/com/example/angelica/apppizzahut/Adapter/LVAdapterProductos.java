package com.example.angelica.apppizzahut.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.internal.widget.ThemeUtils;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angelica.apppizzahut.Activity.ProductoActivity;
import com.example.angelica.apppizzahut.Entity.Producto;
import com.example.angelica.apppizzahut.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bgeek05 on 19/09/2015.
 */
public class LVAdapterProductos extends ArrayAdapter<Producto>{
    Context context;
    LayoutInflater inflater;
    List<Producto> productoList;
    private SparseBooleanArray mSelectedItemsIds;
    private int imd;
    Drawable dr;

    public LVAdapterProductos(Context context, int resourceId, List<Producto> productoList) {
        super(context, resourceId, productoList);

        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.productoList = productoList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        TextView tvDescripcion, tvPrecio, tvProducto;
        ImageView icProducto;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View view, ViewGroup parent){
        final ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_productos, null);

            holder.tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcion);
            holder.tvPrecio = (TextView) view.findViewById(R.id.tvPrecio);
            holder.tvProducto = (TextView) view.findViewById(R.id.tvProducto);

            holder.icProducto = (ImageView) view.findViewById(R.id.ic_producto);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        holder.tvDescripcion.setText(productoList.get(position).getDescripcion());
        holder.tvPrecio.setText(String.valueOf(productoList.get(position).getPrecio()));
        holder.tvProducto.setText(productoList.get(position).getNombre());

        return view;
    }

    public void remove(Producto object) {
        productoList.remove(object);
        notifyDataSetChanged();
    }

    public List<Producto> getProductoList(){
        return  productoList;
    }

    public void toggleSelection(int position){
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection(){
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value){
        if(value){
            mSelectedItemsIds.put(position,value);
        }else {
            mSelectedItemsIds.delete(position);
        }
        notifyDataSetChanged();
    }

    public int getSelectedCount(){
        return  mSelectedItemsIds.size();
    }

    public SparseBooleanArray getmSelectedItemsIds(){
        return mSelectedItemsIds;
    }

}
