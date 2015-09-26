package com.example.angelica.apppizzahut.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.angelica.apppizzahut.Dao.LocalesDao;
import com.example.angelica.apppizzahut.Entity.Local;
import com.example.angelica.apppizzahut.Entity.Provincia;
import com.example.angelica.apppizzahut.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bgeek05 on 14/09/2015.
 */
public class RVAdapterLocales extends RecyclerView.Adapter<RVAdapterLocales.RVAdapterLocalesViewHolder>{
    private List<Local> lstLocales;
    private RVLocalesAdapterCallBack mRVLocalesAdapterCallBack;
    private LocalesDao dao;

    public RVAdapterLocales(RVLocalesAdapterCallBack mRVLocalesAdapterCallBack) {
        this.mRVLocalesAdapterCallBack = mRVLocalesAdapterCallBack;
        dao = new LocalesDao();
        lstLocales = new ArrayList<>();
        lstLocales.addAll(lstLocales);
    }

    public interface RVLocalesAdapterCallBack {
        void onLocalClick(Local local, int position);
    }

    @Override
    public RVAdapterLocalesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_locales, viewGroup, false);

        RVAdapterLocalesViewHolder viewHolder = new RVAdapterLocalesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVAdapterLocalesViewHolder rvAdapterLocalesViewHolder, int i) {
        Local local = lstLocales.get(i);
        rvAdapterLocalesViewHolder.itemView.setTag(i);
        rvAdapterLocalesViewHolder.itemView.setOnLongClickListener(itemViewOnLongClickListener);
        rvAdapterLocalesViewHolder.itemView.setOnClickListener(itemViewOnClickListener);
        rvAdapterLocalesViewHolder.tvNombrelbl.setText(local.getNombre());
        rvAdapterLocalesViewHolder.tvDireccionlbl.setText(local.getDireccion());


    }

    View.OnLongClickListener itemViewOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            v.setSelected(true);
            return true;
        }
    };

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (mRVLocalesAdapterCallBack != null)
                mRVLocalesAdapterCallBack.onLocalClick(lstLocales.get((int) view.getTag()), (int) view.getTag());
        }
    };

    @Override
    public int getItemCount() {
        return lstLocales.size();
    }

    public void setNewSource(List<Local> lstLocales){
        this.lstLocales = lstLocales;
        this.notifyDataSetChanged();
    }

    public static class RVAdapterLocalesViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvNombrelbl,tvDireccionlbl;

        public RVAdapterLocalesViewHolder(View itemView) {
            super(itemView);
            tvNombrelbl = (TextView) itemView.findViewById(R.id.tvNombrelbl);
            tvDireccionlbl = (TextView) itemView.findViewById(R.id.tvDireccionlbl);

        }


    }



}
