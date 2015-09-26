package ventas.jandysac.com.ventas.adapter.recyclerview;

import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ventas.jandysac.com.ventas.MainActivity;
import ventas.jandysac.com.ventas.R;
import ventas.jandysac.com.ventas.dao.PedidoDAO;
import ventas.jandysac.com.ventas.entities.ConsolidarPedido;


/**
 * Created by GaryV on 24/09/2015.
 */
public class RVConsolidarPedidoAdapter extends RecyclerView.Adapter<RVConsolidarPedidoAdapter.RVConsolidarPedidoAdapterViewHolder>{
    private ArrayList<ConsolidarPedido> mLstConsolidarPedido;
    private RVConsolidarPedidoAdapterListener mRVConsolidarPedidoAdapterListener;

    DecimalFormat formato = new DecimalFormat("###.##");
    private SharedPreferences sp;

    public RVConsolidarPedidoAdapter(RVConsolidarPedidoAdapterListener rvConsolidarPedidoAdapterListener) {
        mLstConsolidarPedido = new ArrayList<>();
        this.mRVConsolidarPedidoAdapterListener = rvConsolidarPedidoAdapterListener;
    }

    public RVConsolidarPedidoAdapter(RVConsolidarPedidoAdapterListener rvConsolidarPedidoAdapterListener,String cod_vendedor) {
        mLstConsolidarPedido = new ArrayList<>();
        this.mRVConsolidarPedidoAdapterListener = rvConsolidarPedidoAdapterListener;

        mLstConsolidarPedido.addAll(new PedidoDAO().listPedidosAConsolidar(cod_vendedor));
    }

    public interface  RVConsolidarPedidoAdapterListener{
        void onSelectedItem(ConsolidarPedido cPerdido, int position);
    }

    @Override
    public RVConsolidarPedidoAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new RVConsolidarPedidoAdapterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.consolidar_pedido_items,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(RVConsolidarPedidoAdapterViewHolder rvConsolidarPedidoAdapterViewHolder, final int position) {
        ConsolidarPedido cPerdido = mLstConsolidarPedido.get(position);

        rvConsolidarPedidoAdapterViewHolder.tvPedidosNombre.setText(cPerdido.getNombre());
        rvConsolidarPedidoAdapterViewHolder.tvPedidosItems.setText(String.valueOf(cPerdido.getItems()));
        rvConsolidarPedidoAdapterViewHolder.tvPedidosTotal.setText(formato.format(cPerdido.getTotal()));

        /*
        if(cPerdido.getEstado()==1){
            rvConsolidarPedidoAdapterViewHolder.rbEstadoPedido.setVisibility(View.INVISIBLE);
        }
        */

        rvConsolidarPedidoAdapterViewHolder.itemView.setTag(position);
        rvConsolidarPedidoAdapterViewHolder.itemView.setOnClickListener(itemViewOnClickListener);

        /* CKB*/
        rvConsolidarPedidoAdapterViewHolder.ckbEstadoPedido.setChecked(mLstConsolidarPedido.get(position).isSelected());
        rvConsolidarPedidoAdapterViewHolder.ckbEstadoPedido.setTag(mLstConsolidarPedido.get(position));
        /* END CKB*/

        rvConsolidarPedidoAdapterViewHolder.ckbEstadoPedido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ConsolidarPedido ped = (ConsolidarPedido) cb.getTag();

                ped.setIsSelected(cb.isChecked());
                mLstConsolidarPedido.get(position).setIsSelected(cb.isChecked());

                /*Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
                                */
            }
        });
    }

    View.OnClickListener itemViewOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (mRVConsolidarPedidoAdapterListener != null) {
                mRVConsolidarPedidoAdapterListener.onSelectedItem(mLstConsolidarPedido.get(position), position);
            }
        }
    };

    @Override
    public int getItemCount() {
        return mLstConsolidarPedido.size();
    }
    public String getTotal(){
        double total = 0;

        for (ConsolidarPedido item: mLstConsolidarPedido) {
            total += item.getTotal();
        }
        return formato.format(total);
    }

    public void clear() {
        int count = getItemCount();
        mLstConsolidarPedido.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void updateEstadoPedido(ConsolidarPedido pedido){
        new PedidoDAO().updateEstadoPedido(pedido);
    }

    // method to access in activity after updating selection
    public ArrayList<ConsolidarPedido> lstConsolidarPedido() {
        return mLstConsolidarPedido;
    }

    static class RVConsolidarPedidoAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvPedidosNombre, tvPedidosItems, tvPedidosTotal;
        CheckBox ckbEstadoPedido;

        public RVConsolidarPedidoAdapterViewHolder(View itemView) {
            super(itemView);
            tvPedidosNombre = (TextView) itemView.findViewById(R.id.tvPedidosNombre);
            tvPedidosItems = (TextView) itemView.findViewById(R.id.tvPedidosItems);
            tvPedidosTotal = (TextView) itemView.findViewById(R.id.tvPedidosTotal);
            ckbEstadoPedido = (CheckBox)itemView.findViewById(R.id.ckbEstadoPedido);
        }
    }
}
