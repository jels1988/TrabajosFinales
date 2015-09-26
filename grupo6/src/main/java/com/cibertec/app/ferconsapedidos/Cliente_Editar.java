package com.cibertec.app.ferconsapedidos;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cibertec.app.ferconsapedidos.Entidad.Cliente;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente_Editar extends AppCompatActivity {

    private TextInputLayout tilcliente, tilRUC, tilDireccion, tilTelf, tilLatitud, tilLongitud;
    private EditText etcliente, etRUC, etDireccion, etTelf, etLatitud, etLongitud;
    private Button btacciones, btguardar;

    private int position = -1;
    private int idcliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente__editar);

        tilcliente = (TextInputLayout) findViewById(R.id.tilcliente);
        tilRUC = (TextInputLayout) findViewById(R.id.tilRUC);
        tilDireccion = (TextInputLayout) findViewById(R.id.tilDireccion);
        tilTelf = (TextInputLayout) findViewById(R.id.tilTelf);
        tilLatitud = (TextInputLayout) findViewById(R.id.tilLatitud);
        tilLongitud = (TextInputLayout) findViewById(R.id.tilLongitud);

        etcliente = (EditText) findViewById(R.id.etcliente);
        etRUC = (EditText) findViewById(R.id.etRUC);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etTelf = (EditText) findViewById(R.id.etTelf);
        etLatitud = (EditText) findViewById(R.id.etLatitud);
        etLongitud = (EditText) findViewById(R.id.etLongitud);

        btacciones = (Button) findViewById(R.id.btacciones);
        btguardar = (Button) findViewById(R.id.btguardar);


        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(ClientesActivity.ARG_CLIENTE)) {
            Cliente cliente = getIntent().getParcelableExtra(ClientesActivity.ARG_CLIENTE);
            etcliente.setText(cliente.getNombreCliente());
            etRUC.setText(cliente.getRUC());
            etDireccion.setText(String.valueOf(cliente.getDireccion()));
            etTelf.setText(String.valueOf(cliente.getTelefono()));
            etLatitud.setText(String.valueOf(cliente.getLatitud()));
            etLongitud.setText(String.valueOf(cliente.getLongitud()));
            idcliente = Integer.valueOf(cliente.getIdCliente());
            position = getIntent().getIntExtra(ClientesActivity.ARG_POSITION, -1);
            btacciones.setText("Cancelar");
            btguardar.setText("Actualizar");
            this.setTitle("Cliente - Editar registro");
        } else {
            this.setTitle("Cliente - Nuevo registro");
            btacciones.setVisibility(View.GONE);


        }


        btacciones.setOnClickListener(btaccionesOnClickListener);
        btguardar.setOnClickListener(btguardarOnClickListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente__editar, menu);
        if (position == -1) {
            menu.findItem(R.id.action_mapa).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //f.setVisibility(View.INVISIBLE);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mapa) {
            Cliente cliente = getIntent().getParcelableExtra(ClientesActivity.ARG_CLIENTE);

            Intent intent = new Intent(getBaseContext(), ClienteDetalle.class);
            intent.putExtra(ClientesActivity.ARG_CLIENTE, cliente);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener btaccionesOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            Cliente cliente = new Cliente();
            cliente.setIdCliente(idcliente);
            intent.putExtra(ClientesActivity.ARG_CLIENTE, cliente);
            intent.putExtra(ClientesActivity.ARG_POSITION, position);

            setResult(44, intent);
            finish();

        }
    };

    View.OnClickListener btguardarOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean isComplete = true;

            tilcliente.setErrorEnabled(false);
            tilRUC.setErrorEnabled(false);
            tilDireccion.setErrorEnabled(false);
            tilTelf.setErrorEnabled(false);
            tilLatitud.setErrorEnabled(false);
            tilLongitud.setErrorEnabled(false);


            CadenaDecimal cadenaDecimal = new CadenaDecimal();
            if ( !cadenaDecimal.esDecimal(etLatitud.getText().toString().trim()) ){
                tilLatitud.setErrorEnabled(true);
                tilLatitud.setError("Ingrese el dato de Latitud correcto");
                isComplete = false;
            }

            if ( !cadenaDecimal.esDecimal(etLongitud.getText().toString().trim()) ){
                tilLongitud.setErrorEnabled(true);
                tilLongitud.setError("Ingrese el dato de Longitud correcto");
                isComplete = false;
            }



            if (etcliente.getText().toString().trim().length() <= 0) {
                tilcliente.setErrorEnabled(true);
                tilcliente.setError("Ingrese el nombre del cliente");
                isComplete = false;
            }

            if (etRUC.getText().toString().trim().length() != 11) {
                tilRUC.setErrorEnabled(true);
                tilRUC.setError("Ingrese un RUC valido");
                isComplete = false;
            }

            if (etDireccion.getText().toString().trim().length() <= 0) {
                tilDireccion.setErrorEnabled(true);
                tilDireccion.setError("Ingrese una dirección");
                isComplete = false;
            }

            if (etTelf.getText().toString().trim().length() <= 0) {
                tilTelf.setErrorEnabled(true);
                tilTelf.setError("Ingrese un telefono");
                isComplete = false;
            }

            if (etLatitud.getText().toString().trim().length() <= 0) {
                tilTelf.setErrorEnabled(true);
                tilTelf.setError("Ingrese el dato de Latitud");
                isComplete = false;
            }
            if (etLongitud.getText().toString().trim().length() <= 0) {
                tilTelf.setErrorEnabled(true);
                tilTelf.setError("Ingrese el dato de Longitud");
                isComplete = false;
            }
            if (isComplete) {

                Cliente cliente = new Cliente();
                cliente.setIdCliente(idcliente);
                cliente.setNombreCliente(etcliente.getText().toString().trim());
                cliente.setRUC(etRUC.getText().toString().trim());
                cliente.setDireccion(etDireccion.getText().toString().trim());
                cliente.setTelefono(etTelf.getText().toString().trim());
                cliente.setLatitud(Double.valueOf(etLatitud.getText().toString().trim()));
                cliente.setLongitud(Double.valueOf(etLongitud.getText().toString().trim()));

                Intent intent = new Intent();
                intent.putExtra(ClientesActivity.ARG_CLIENTE, cliente);
                intent.putExtra(ClientesActivity.ARG_POSITION, position);
                setResult(RESULT_OK, intent);
                finish();

            }
        }
    };

    public class CadenaDecimal {

        public boolean esDecimal(String cad) {
            boolean hayPunto = false ,hayRaya=false ,hayMas=false;
            StringBuffer parteEntera = new StringBuffer();
            StringBuffer parteDecimal = new StringBuffer();
            int i = 0, posicionDelPunto ,posicionRaya,posicionMas;
            int contPunto = 0,contRaya=0,contMas=0;

            if (cad.equals(".")){return false;}

            for (i = 0; i < cad.length(); i++) {
                if (cad.charAt(i) == '.')
                {
                    hayPunto = true;
                    contPunto++;
                }
                if (cad.charAt(i) == '-')
                {
                    hayRaya = true;
                    contRaya++;
                }
                if (cad.charAt(i) == '+')
                {
                    hayMas = true;
                    contMas++;
                }
            }
            if  ( contPunto > 1){ return false; }
            if  ( contRaya  > 1){ return false; }
            if  ( contMas  > 1){ return false; }

            if (hayRaya) {
                posicionRaya = cad.indexOf('-');
                if (posicionRaya>0){
                    return false;
                }

            }
            if (hayMas) {
                posicionMas = cad.indexOf('+');
                if (posicionMas>0){
                    return false;
                }

            }
            if (hayPunto) {
                posicionDelPunto = cad.indexOf('.');
            }
            else
                return false;

            if (posicionDelPunto == cad.length() - 1 || posicionDelPunto == 0)
                return false;

             if (Double.valueOf(cad)==0){return false;}

            return true;
        }


    }
}

