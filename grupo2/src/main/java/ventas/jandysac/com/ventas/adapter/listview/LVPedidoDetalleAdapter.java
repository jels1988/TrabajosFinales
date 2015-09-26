package ventas.jandysac.com.ventas.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ventas.jandysac.com.ventas.R;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;

/**
 * Created by Rodolfo on 24/09/2015.
 */
public class LVPedidoDetalleAdapter extends ArrayAdapter<PedidoDetalle> {
    public LVPedidoDetalleAdapter(Context context, int resource, List<PedidoDetalle> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.detalle_pedido_items, parent, false);

        TextView txtDetalleProducto, txtDetalleCantidad, txtDetallePrecio, txtDetalleSubtotal;
        TextView txtTotalPedido, txtItemsPedido;

        txtDetalleProducto = (TextView) convertView.findViewById(R.id.txtDetalleProducto);
        txtDetalleCantidad = (TextView) convertView.findViewById(R.id.txtDetalleCantidad);
        txtDetallePrecio = (TextView) convertView.findViewById(R.id.txtDetallePrecio);
        txtDetalleSubtotal = (TextView) convertView.findViewById(R.id.txtDetalleSubtotal);

        PedidoDetalle pedidodetalle = getItem(position);

        txtDetalleProducto.setText(pedidodetalle.getDescripcion());
        txtDetalleCantidad.setText(String.valueOf(pedidodetalle.getCantidad()));
        txtDetallePrecio.setText(String.valueOf(pedidodetalle.getPrecio()));
        txtDetalleSubtotal.setText(String.valueOf(pedidodetalle.getImporte_Neto()));

        return convertView;
    }
}
