package pe.lindley.tomapedidos.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.entities.Cliente;
import pe.lindley.tomapedidos.entities.Pedido;
import pe.lindley.tomapedidos.entities.Producto;

/**
 * Created by jlama on 21/09/2015.
 */
public class PedidoDAO {
    private static PedidoDAO INSTANCE = null;

    private PedidoDAO() {
    }

    public static PedidoDAO getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PedidoDAO();
        }
    }

    public List<Pedido> getListaPedido(Cliente cliente) {
        List<Pedido> lista = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.rawQuery(
                    "SELECT A.PEDIDO_CANTIDAD, A.PRODUCTO_ID, B.PRODUCTO_DESCRIPCION FROM TB_PEDIDO A INNER JOIN TB_PRODUCTO B ON A.PRODUCTO_ID = B.PRODUCTO_ID WHERE A.CLIENTE_ID = ?", new String[]{String.valueOf(cliente.getClienteId())});

            if (cursor.moveToFirst()) {
                do {
                    Pedido pedido = new Pedido();
                    pedido.setCliente(cliente);

                    pedido.setCantidad(cursor.isNull(cursor.getColumnIndex("Pedido_Cantidad")) ? 0 : cursor.getInt(cursor.getColumnIndex("Pedido_Cantidad")));

                    Producto producto = new Producto();
                    producto.setProductoId(cursor.isNull(cursor.getColumnIndex("Producto_Id")) ? 0 : cursor.getInt(cursor.getColumnIndex("Producto_Id")));
                    producto.setProductoNombre(cursor.isNull(cursor.getColumnIndex("Producto_Descripcion")) ? "" : cursor.getString(cursor.getColumnIndex("Producto_Descripcion")));
                    pedido.setProducto(producto);

                    lista.add(pedido);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return lista;
    }

    public void add(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("Cliente_Id", pedido.getCliente().getClienteId());
        values.put("Producto_Id", pedido.getProducto().getProductoId());
        values.put("Pedido_Cantidad", pedido.getCantidad());
        try {
            DataBaseHelper.myDataBase.insert("TB_PEDIDO", null, values);
        } catch (Exception ex){
        }
    }

    public void update(Pedido pedido) {
        ContentValues values = new ContentValues();
        values.put("Pedido_Cantidad", pedido.getCantidad());
        try {
            //public int update (String table, ContentValues values, String whereClause, String[] whereArgs)
            DataBaseHelper.myDataBase.update(
                    "TB_PEDIDO", values, "Cliente_Id = ? and Producto_Id = ?",
                    new String[]{String.valueOf(pedido.getCliente().getClienteId()),
                            String.valueOf(pedido.getProducto().getProductoId())});
        } catch (Exception ex){
        }
    }

    public void delete(Pedido pedido) {
        try {
            DataBaseHelper.myDataBase.delete(
                    "TB_PEDIDO", "Cliente_Id = ? and Producto_Id = ?",
                    new String[]{String.valueOf(pedido.getCliente().getClienteId()),
                            String.valueOf(pedido.getProducto().getProductoId())});
        } catch (Exception ex){
        }
    }

    public void delete(Cliente cliente) {
        try {
            DataBaseHelper.myDataBase.delete(
                    "TB_PEDIDO", "Cliente_Id = ?",
                    new String[]{String.valueOf(cliente.getClienteId())});
        } catch (Exception ex){
        }
    }

    public void deleteAll() {
        try {
            DataBaseHelper.myDataBase.execSQL(
                    "DELETE FROM TB_PEDIDO");
        } catch (Exception ex){
        }
    }
}
