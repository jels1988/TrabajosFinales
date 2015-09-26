package pe.lindley.tomapedidos.dao;

import android.database.Cursor;

import java.util.List;

/**
 * Created by jlama on 19/09/2015.
 */
public class UsuarioDAO {
    private static UsuarioDAO INSTANCE = null;

    private UsuarioDAO() {
    }

    public static UsuarioDAO getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UsuarioDAO();
        }
    }

    public boolean isLogin(String usuario, String password) {
        boolean login = false;
        Cursor cursor = null;
        try {
            cursor = DataBaseHelper.myDataBase.query("TB_Usuario", null,
                    "Usuario_Id = ? and Usuario_Password = ?", new String[]{usuario, password}, null, null, null);

            if (cursor.moveToFirst()) {
                login = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return login;
    }
}
