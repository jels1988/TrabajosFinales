package pe.lindley.tomapedidos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import pe.lindley.tomapedidos.dao.ClienteDAO;
import pe.lindley.tomapedidos.dao.DataBaseHelper;
import pe.lindley.tomapedidos.dao.UsuarioDAO;
import pe.lindley.tomapedidos.interfaces.Logeable;

public class Login extends AppCompatActivity implements Logeable {

    private Button login_btingresar;
    private EditText login_etusuario;
    private EditText login_etpassword;
    private TextView login_tvmensajeerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(Login.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        login_btingresar = (Button) findViewById(R.id.login_btingresar);
        login_etusuario = (EditText) findViewById(R.id.login_etusuario);
        login_etpassword = (EditText) findViewById(R.id.login_etpassword);
        login_tvmensajeerror = (TextView) findViewById(R.id.login_tvmensajeerror);

        SharedPreferences prefs =
                getSharedPreferences(ARG_PREF, Context.MODE_PRIVATE);

        if (prefs != null && prefs.contains(ARG_USER) && prefs.contains(ARG_PASS)
                && !prefs.getString(ARG_USER, "").isEmpty()
                && !prefs.getString(ARG_PASS, "").isEmpty()) {
            login_etusuario.setText(prefs.getString(ARG_USER, ""));
            login_etpassword.setText(prefs.getString(ARG_PASS, ""));
            ingresar();
        }

        login_btingresar.setOnClickListener(login_btingresarOnClickListener);
    }

    View.OnClickListener login_btingresarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Validar ingreso
            boolean error = false;
            login_tvmensajeerror.setText("");
            StringBuilder stringBuilder = new StringBuilder();
            if (login_etusuario.getText().toString().trim().equals("")) {
                error = true;
                stringBuilder.append(getString(R.string.txt_errorusuario));
                stringBuilder.append("\n");
            }
            if (login_etpassword.getText().toString().trim().equals("")) {
                error = true;
                stringBuilder.append(getString(R.string.txt_errorpassword));
                stringBuilder.append("\n");
            }
            if (error) {
                login_tvmensajeerror.setText(stringBuilder.toString());
                return;
            }

            UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
            if (usuarioDAO.isLogin(login_etusuario.getText().toString().trim(), login_etpassword.getText().toString().trim())) {
                ingresar();
            } else {
                login_tvmensajeerror.setText(getString(R.string.txt_errorusuariopassword));
            }
        }
    };

    public void ingresar() {
        SharedPreferences prefs =
                getSharedPreferences(ARG_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = prefs.edit();

        spe.putString(ARG_USER, login_etusuario.getText().toString().trim());
        spe.putString(ARG_PASS, login_etpassword.getText().toString().trim());
        spe.commit();

        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
