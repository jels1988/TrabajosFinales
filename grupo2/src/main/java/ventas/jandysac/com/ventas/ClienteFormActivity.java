package ventas.jandysac.com.ventas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ventas.jandysac.com.ventas.adapter.spinner.SPTipoDocAdapter;
import ventas.jandysac.com.ventas.dao.ClienteDAO;
import ventas.jandysac.com.ventas.entities.Cliente;

public class ClienteFormActivity extends AppCompatActivity {

    public final static String ARG_CLIENTE = "arg_cliente";
    public final static String ARG_OPERACION = "arg_operacion";
    private final static String LATLOG_PATTERN = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

    public final static int REQUEST_CODE_INSERT = 1;
    public final static int REQUEST_CODE_UPDATE_DELETE = 2;

    private Spinner spTipoDoc;
    private SPTipoDocAdapter mSPTipoDocAdapter;
    private TextInputLayout tilCodigo, tilApellidoMaterno, tilApellidoPaterno, tilNombres, tilDireccion, tilCoordenadas;
    private int clienteID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_form);

        spTipoDoc = (Spinner) findViewById(R.id.spTipoDoc);
        tilApellidoMaterno = (TextInputLayout) findViewById(R.id.tilApellidoMaterno);
        tilApellidoPaterno = (TextInputLayout) findViewById(R.id.tilApellidoPaterno);
        tilNombres = (TextInputLayout) findViewById(R.id.tilNombres);
        tilDireccion = (TextInputLayout) findViewById(R.id.tilDireccion);
        tilCoordenadas = (TextInputLayout) findViewById(R.id.tilCoordenadas);
        tilCodigo = (TextInputLayout) findViewById(R.id.tilCodigo);

        ArrayList<String> mLstTipoDoc = new ArrayList<>();
        mLstTipoDoc.add("CONTADO");
        mLstTipoDoc.add("CREDITO");

        mSPTipoDocAdapter = new SPTipoDocAdapter(ClienteFormActivity.this, mLstTipoDoc);
        //spTipoDoc.setOnItemSelectedListener(spTipoDocOnItemSelectedListener);
        spTipoDoc.setAdapter(mSPTipoDocAdapter);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ClienteFormActivity.ARG_OPERACION)) {
            int operacion = getIntent().getIntExtra(ClienteFormActivity.ARG_OPERACION, 0);
            if (operacion == ClienteFormActivity.REQUEST_CODE_INSERT) {
                /*btEliminar.setVisibility(View.INVISIBLE);
                btGuardar.setText("Nuevo");
                btGuardar.setOnClickListener(btGuardarOnClickListener);*/
            } else {
                if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ClienteFormActivity.ARG_CLIENTE)) {
                    Cliente cliente = getIntent().getParcelableExtra(ClienteFormActivity.ARG_CLIENTE);

//                    position = getIntent().getIntExtra(ClienteFormActivity.ARG_POSITION, -1);

                    tilApellidoMaterno.getEditText().setText(cliente.getApellido_materno());
                    tilApellidoPaterno.getEditText().setText(cliente.getApellido_paterno());
                    tilNombres.getEditText().setText(cliente.getNombres());
                    tilDireccion.getEditText().setText(String.valueOf(cliente.getDireccion()));
                    tilCoordenadas.getEditText().setText(String.valueOf(cliente.getCoodenadas()));
                    tilCodigo.getEditText().setText(String.valueOf(cliente.getCodigo()));

                    int spinnerPosition = mSPTipoDocAdapter.getPosition(cliente.getTipo_doc());
                    spTipoDoc.setSelection(spinnerPosition);

                    clienteID = cliente.getClienteID();

                    /*btGuardar.setOnClickListener(btActualizarOnClickListener);
                    btEliminar.setOnClickListener(btEliminarOnClickListener);*/
                }
            }
        }

    }

    AdapterView.OnItemSelectedListener spTipoDocOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crud_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int operacion = getIntent().getIntExtra(ClienteFormActivity.ARG_OPERACION, 0);


        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_guardar:

                String am = tilApellidoMaterno.getEditText().getText().toString().trim();
                String ap = tilApellidoPaterno.getEditText().getText().toString().trim();
                String n = tilNombres.getEditText().getText().toString().trim();
                String d = tilDireccion.getEditText().getText().toString().trim();
                String c = tilCoordenadas.getEditText().getText().toString().trim();
                String co = tilCodigo.getEditText().getText().toString().trim();

                boolean error = false;

                if (co.length() < 1) {
                    tilCodigo.setError("Ingrese su codigo");
                    error = true;
                }
                if (n.length() < 1) {
                    tilNombres.setError("Ingrese su nombre");
                    error = true;
                }
                if (ap.length() < 1) {
                    tilApellidoPaterno.setError("Ingrese su apellido paterno");
                    error = true;
                }
                if (am.length() < 1) {
                    tilApellidoMaterno.setError("Ingrese su apellido materno");
                    error = true;
                }
                if (d.length() < 1) {
                    tilDireccion.setError("Ingrese su direccion");
                    error = true;
                }
                if (c.length() < 1) {
                    tilCoordenadas.setError("Ingrese sus coordenadas");
                    error = true;
                } else {
                    if (!this.isMatch(c, LATLOG_PATTERN)){
                        tilCoordenadas.setError("Coordenadas no validas");
                        error = true;
                    }
                }

                if (error == false) {

                    Intent intent = new Intent();
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(co);
                    cliente.setNombres(n);
                    cliente.setApellido_paterno(ap);
                    cliente.setApellido_materno(am);
                    cliente.setNombre_completo(n + " " + ap + " " + am);
                    cliente.setTipo_doc(spTipoDoc.getSelectedItem().toString());
                    cliente.setCoodenadas(c);
                    cliente.setDireccion(d);

                    if (operacion == ClienteFormActivity.REQUEST_CODE_INSERT) {
                        new ClienteDAO().addCliente(cliente);
                    } else if (operacion == ClienteFormActivity.REQUEST_CODE_UPDATE_DELETE) {
                        cliente.setClienteID(clienteID);

                        new ClienteDAO().updateCliente(cliente);
                    }

                    intent.putExtra(ClienteFormActivity.ARG_CLIENTE, cliente);

                    setResult(RESULT_OK, intent);
                    finish();

                } else{
                    Toast.makeText(ClienteFormActivity.this, "Validar la información ingresada", Toast.LENGTH_SHORT).show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isMatch(String s, String pattern) {
        try {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

}
