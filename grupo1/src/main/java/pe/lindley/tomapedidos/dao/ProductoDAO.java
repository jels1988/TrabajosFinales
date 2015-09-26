package pe.lindley.tomapedidos.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import pe.lindley.tomapedidos.entities.Producto;

/**
 * Created by jlama on 22/09/2015.
 */
public class ProductoDAO {

    private static ProductoDAO INSTANCE = null;

    private ProductoDAO() {
    }

    public static ProductoDAO getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductoDAO();
        }
    }

    public List<Producto> getListaCliente() {
        List<Producto> lista = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DataBaseHelper.myDataBase.query("TB_Producto", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Producto producto = new Producto();
                    producto.setProductoId(cursor.isNull(cursor.getColumnIndex("Producto_Id")) ? 0 : cursor.getInt(cursor.getColumnIndex("Producto_Id")));
                    producto.setProductoNombre(cursor.isNull(cursor.getColumnIndex("Producto_Descripcion")) ? "" : cursor.getString(cursor.getColumnIndex("Producto_Descripcion")));
                    producto.setProductoUnidadMedida(cursor.isNull(cursor.getColumnIndex("Producto_UnidadMedida")) ? "" : cursor.getString(cursor.getColumnIndex("Producto_UnidadMedida")));
                    producto.setProductoSubUnidad(cursor.isNull(cursor.getColumnIndex("Producto_SubUnidades")) ? 0 : cursor.getInt(cursor.getColumnIndex("Producto_SubUnidades")));
                    lista.add(producto);
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

    public void deleteAll() {
        try {
            DataBaseHelper.myDataBase.execSQL(
                    "DELETE FROM TB_Producto");
        } catch (Exception ex){
        }
    }

    public void poblarBaseDatos() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto(102, "IK 296 VRE CJX24", "CJ", 24));
        lista.add(new Producto(103, "IK 1.0 VRE CJX12", "CJ", 12));
        lista.add(new Producto(106, "IK 2.0REF PET CJX8", "CJ", 8));
        lista.add(new Producto(137, "IK 410 ML PET*12", "PQ", 12));
        lista.add(new Producto(114, "IK 625 VRE CJX12", "CJ", 12));
        lista.add(new Producto(158, "IK 3 LT PFM PQ*4", "PQ", 4));
        lista.add(new Producto(118, "IK 2.25 PET PQX6", "PQ", 6));
        lista.add(new Producto(123, "IK 192 VRE CJX24", "CJ", 24));
        lista.add(new Producto(129, "IK 500 PFM PQX12", "PQ", 12));
        lista.add(new Producto(193, "IK 330 PFM*12", "PQ", 12));
        lista.add(new Producto(139, "IK 1.5 PFM PQX6", "PQ", 6));
        lista.add(new Producto(155, "IK 1.5 VRE CJX8", "CJ", 8));
        lista.add(new Producto(518, "IK ZERO 410 ML *12", "PQ", 12));
        lista.add(new Producto(582, "IK ZERO 500 ML *12", "PQ", 12));
        lista.add(new Producto(509, "IK ZERO 1500 ML *6", "PQ", 6));
        lista.add(new Producto(1025, "CC 1.0 VRE CJX12", "CJ", 12));
        lista.add(new Producto(1036, "CC PR 2LX8", "CJ", 8));
        lista.add(new Producto(1231, "CC LATA 350 ML X6", "PQ", 6));
        lista.add(new Producto(1023, "CC 295 VRE CJX24", "CJ", 24));
        lista.add(new Producto(1291, "CC ZERO 410 PET*12", "PQ", 12));
        lista.add(new Producto(1075, "CC PFM 3LT PQX4", "PQ", 4));
        lista.add(new Producto(1041, "CC 625 ML VRE CJ12", "CJ", 12));
        lista.add(new Producto(1179, "CC 2.5 PET X 6", "PQ", 6));
        lista.add(new Producto(1022, "CC 192 VRE CJX24", "CJ", 24));
        lista.add(new Producto(1026, "CC 500 PET PQX12", "PQ", 12));
        lista.add(new Producto(1283, "CC ZERO 500 PET*12", "PQ", 12));
        lista.add(new Producto(1193, "CC 330 PFM*12", "PQ", 12));
        lista.add(new Producto(1027, "CC 1.5 PET PQX6", "PQ", 6));
        lista.add(new Producto(1294, "CC ZERO 1.5 PET *6", "PQ", 6));
        lista.add(new Producto(1392, "CC 400 ML VR CJ*20", "CJ", 20));
        lista.add(new Producto(1038, "CC VR 1.5 LT X 8", "CJ", 8));
        lista.add(new Producto(1039, "CC PR 2.5 LT X 8", "CJ", 8));
        lista.add(new Producto(1423, "FT 1.0 VRE CJX12", "CJ", 12));
        lista.add(new Producto(1459, "FT VR 10 ONZ*24", "CJ", 24));
        lista.add(new Producto(1116, "FT 3.00 ML PETX4", "PQ", 4));
        lista.add(new Producto(1424, "FT 500 PET PQX12", "PQ", 12));
        lista.add(new Producto(1420, "FT 192 VRECJX24", "CJ", 24));
        lista.add(new Producto(1478, "FT KI 192 VRE *24", "CJ", 24));
        lista.add(new Producto(1569, "SP ZERO 1750 ML*6", "PQ", 6));
        lista.add(new Producto(1520, "SP 295 VRE CJX24", "CJ", 24));
        lista.add(new Producto(1527, "SP 3.0L PET PQX4", "PQ", 4));
        lista.add(new Producto(1523, "SP 500 PET PQX12", "PQ", 12));
        lista.add(new Producto(1553, "SP ZE 500 PET PX12", "PQ", 12));
        lista.add(new Producto(1519, "SP 192 VRE CJX24", "CJ", 24));
        lista.add(new Producto(159, "IK 1750 ML PET*6", "PQ", 6));
        lista.add(new Producto(113, "IK 3.00 PET PQX4", "PQ", 4));
        lista.add(new Producto(133, "IK 3.0 PET PQX2", "PQ", 2));
        lista.add(new Producto(179, "IK 2.5 PET X 6", "CJ", 6));
        lista.add(new Producto(183, "IK 192 ML VRE*12", "PQ", 12));
        lista.add(new Producto(175, "IK 300 ML VNR*12", "PQ", 12));
        lista.add(new Producto(181, "IK 300 ML VNR*12", "PQ", 12));
        lista.add(new Producto(182, "IK ZERO 300ML VNR", "PQ", 12));
        lista.add(new Producto(584, "CC 300 PET*12", "PQ", 12));
        lista.add(new Producto(585, "IK 300 PET*12", "PQ", 12));
        lista.add(new Producto(152, "IK 1.0 PFM PQX6", "PQ", 6));
        lista.add(new Producto(517, "IK LIGHT 410ML P12", "PQ", 12));
        lista.add(new Producto(515, "IK LIGHT 1.5 L PFM", "PQ", 6));
        lista.add(new Producto(1299, "CC 1750 ML PET*6", "PQ", 6));
        lista.add(new Producto(1138, "CC 2.0LT RP PQ*6", "PQ", 6));
        lista.add(new Producto(1061, "CC LTA 12 OZX24", "CJ", 24));
        lista.add(new Producto(1228, "CC LATA 350 X24", "CJ", 24));
        lista.add(new Producto(1221, "CC ZERO LATA 355 M", "CJ", 24));
        lista.add(new Producto(1180, "CC 410 ML PET*12", "PQ", 12));
        lista.add(new Producto(1077, "CC PFM 3 LT PQX 2", "PQ", 2));
        lista.add(new Producto(1030, "CC 2.25 PETX6", "PQ", 6));
        lista.add(new Producto(1190, "CC 2500 PET*2", "PQ", 2));
        lista.add(new Producto(1187, "CC 192 ML VRE*12", "PQ", 12));
        lista.add(new Producto(1173, "CC VNR 237 *24 ICO", "CJ", 24));
        lista.add(new Producto(1067, "CC 3L PET X 4", "PQ", 4));
        lista.add(new Producto(1145, "CC 500ML PFM*12 C/", "PQ", 12));
        lista.add(new Producto(1395, "CC 500 PET*12 PBOT", "PQ", 12));
        lista.add(new Producto(1386, "CC 300 ML VNR*12", "PQ", 12));
        lista.add(new Producto(1387, "CC ZERO 300 VNR*12", "PQ", 12));
        lista.add(new Producto(1274, "CC 1.0 PET PQ*6", "PQ", 6));
        lista.add(new Producto(1226, "CC LIGHT 410 P*12", "PQ", 12));
        lista.add(new Producto(1219, "CC 500ML *12 PROMO", "CJ", 12));
        lista.add(new Producto(1207, "CC LIGTH 1.5 PETX6", "PQ", 6));
        lista.add(new Producto(1474, "FT NJA 2.0 RP *6", "PQ", 6));
        lista.add(new Producto(1473, "FT NJA 2.0 RP*8", "CJ", 8));
        lista.add(new Producto(1120, "FT NJ 3LT PNRX6", "CJ", 6));
        lista.add(new Producto(1493, "FT NJA 330ML P*12", "PQ", 12));
        lista.add(new Producto(1425, "FT 1.5 PET PQX6", "PQ", 6));
        lista.add(new Producto(1526, "SP LAT.12OZX24", "CJ", 24));
        lista.add(new Producto(1537, "SP 3.0L PET X6", "PQ", 6));
        lista.add(new Producto(1593, "SP 330 ML PFMX12", "PQ", 12));
        lista.add(new Producto(1524, "SP 1.5 PET PQX6", "PQ", 6));
        lista.add(new Producto(1554, "SP ZE 1.5 PET PQX6", "PQ", 6));

        ContentValues values = null;
        for( Producto producto : lista ) {
            values = new ContentValues();
            values.put("Producto_Id", producto.getProductoId());
            values.put("Producto_Descripcion", producto.getProductoNombre());
            values.put("Producto_UnidadMedida", producto.getProductoUnidadMedida());
            values.put("Producto_SubUnidades", producto.getProductoSubUnidad());
            try {
                DataBaseHelper.myDataBase.insert("TB_Producto", null, values);
            } catch (Exception ex){
            }
        }
    }
}
