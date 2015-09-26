package ventas.jandysac.com.ventas.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import ventas.jandysac.com.ventas.entities.Producto;
import ventas.jandysac.com.ventas.entities.PedidoDetalle;

/**
 * Created by Rodolfo on 10/09/2015.
 */
public class ProductoDAO {
    public ArrayList<Producto> listProducto() {
        ArrayList<Producto> listProducto = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("producto", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setCodigo(cursor.isNull(cursor.getColumnIndex("codigo")) ? "" : cursor.getString(cursor.getColumnIndex("codigo")));
                    producto.setDescripcion(cursor.isNull(cursor.getColumnIndex("descripcion")) ? "" : cursor.getString(cursor.getColumnIndex("descripcion")));
                    producto.setPrecio(cursor.isNull(cursor.getColumnIndex("precio")) ? 0 : cursor.getDouble(cursor.getColumnIndex("precio")));
                    producto.setStock(cursor.isNull(cursor.getColumnIndex("stock")) ? 0 : cursor.getDouble(cursor.getColumnIndex("stock")));
                    listProducto.add(producto);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return listProducto;
    }

    public void addProducto(PedidoDetalle pedidodetalle) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("codigo_producto", pedidodetalle.getCodigo_Producto());
            cv.put("cantidad", pedidodetalle.getCantidad());
            cv.put("precio", pedidodetalle.getPrecio());
            DataBaseHelper.myDataBase.insert("movimiento_venta_detalle", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
