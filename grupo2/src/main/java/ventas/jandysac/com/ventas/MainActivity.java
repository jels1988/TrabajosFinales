package ventas.jandysac.com.ventas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ventas.jandysac.com.ventas.dao.DataBaseHelper;
import ventas.jandysac.com.ventas.dao.ParametroDAO;
import ventas.jandysac.com.ventas.dao.UsuarioDAO;
import ventas.jandysac.com.ventas.entities.Parametro;
import ventas.jandysac.com.ventas.entities.Usuario;
import ventas.jandysac.com.ventas.util.AesCbcWithIntegrity;

import static ventas.jandysac.com.ventas.util.AesCbcWithIntegrity.decryptString;
import static ventas.jandysac.com.ventas.util.AesCbcWithIntegrity.encrypt;
import static ventas.jandysac.com.ventas.util.AesCbcWithIntegrity.generateKeyFromPassword;
import static ventas.jandysac.com.ventas.util.AesCbcWithIntegrity.keyString;


public class MainActivity extends AppCompatActivity {
    private ImageButton btnLogin;
    private EditText etUsuario, etPassword;
    private final static int REQUEST_CODE = 1;

    private DataBaseHelper dataBaseHelper;
    private AesCbcWithIntegrity.SecretKeys key;
    private SharedPreferences sp;

    private final String ARG_USER = "arg_user";
    private final String ARG_PASS = "arg_pass";
    public final static String ARG_NOMAPE = "arg_nomape";
    public final static String ARG_CODIGO = "arg_codigo";

    private String password, salt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        //getActionBar().hide();

        try {
            dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        password = (new ParametroDAO().findPametro("password")).getValor();
        salt = (new ParametroDAO().findPametro("salt")).getValor();

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        if (sp.contains(ARG_USER) && sp.contains(ARG_PASS) &&
                !sp.getString(ARG_USER, "").isEmpty() && !sp.getString(ARG_PASS, "").isEmpty()) {

            Log.i("MainActivity", "password recuperado: " + sp.getString(ARG_PASS, ""));
            try {

                key = generateKeyFromPassword(password, salt);
                Log.i("MainActivity", "key: " + key.toString());

                Usuario usuario = new UsuarioDAO().findUsuario(sp.getString(ARG_USER, ""));

                AesCbcWithIntegrity.CipherTextIvMac civ = new AesCbcWithIntegrity.CipherTextIvMac(sp.getString(ARG_PASS, ""));

                String decryptedText = decryptString(civ, key);
                Log.i("MainActivity", "password desencriptado: " + decryptedText);

                if (decryptedText.equals(usuario.getContrasenia())) {
                    Intent intent = new Intent(MainActivity.this, BusquedaCliente.class);
                    startActivity(intent);
                    finish();
                }

            } catch (GeneralSecurityException e) {
                Log.e("MainActivity", "GeneralSecurityException", e);
            } catch (UnsupportedEncodingException e) {
                Log.e("MainActivity", "UnsupportedEncodingException", e);
            }

        }

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (ImageButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLoginOnClickListener);
    }

    View.OnClickListener btnLoginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences.Editor spe = sp.edit();
            String u = etUsuario.getText().toString().trim();
            String p = etPassword.getText().toString().trim();

            Usuario usuario = new UsuarioDAO().findUsuario(u);

            String md5p = md5(p);
            Log.i("MainActivity", "password md5: " + md5p);

            if (usuario.getContrasenia() != null && usuario.getContrasenia().equals(md5p)) {
                try {

                    Log.i("MainActivity", "password: " + password);
                    Log.i("MainActivity", "salt: " + salt);
                    key = generateKeyFromPassword(password, salt);

                    Log.i("MainActivity", "key: " + key.toString());
                    AesCbcWithIntegrity.CipherTextIvMac civp = encrypt(md5p, key);

                    Log.i("MainActivity", "usuario: " + u);
                    spe.putString(ARG_USER, u);
                    Log.i("MainActivity", "password encriptado: " + civp.toString());
                    spe.putString(ARG_PASS, civp.toString());

                    spe.putString(ARG_NOMAPE, usuario.getNombres() + " " + usuario.getApellidos());
                    spe.putString(ARG_CODIGO, usuario.getCodigo());

                    spe.commit();

                } catch (GeneralSecurityException e) {
                    Log.e("MainActivity", "GeneralSecurityException", e);
                } catch (UnsupportedEncodingException e) {
                    Log.e("MainActivity", "UnsupportedEncodingException", e);
                }

                Intent intent = new Intent(MainActivity.this, BusquedaCliente.class);
                //Intent intent = new Intent(MainActivity.this, BusquedaProducto.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), R.string.msg_error_usuario_contrasenia,
                        Toast.LENGTH_SHORT).show();
            }
        }

    };

    /**
     * Clase para la encriptacion en MD5.
     */
    public static final String md5(final String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
