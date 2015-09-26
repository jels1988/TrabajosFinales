package com.cibertec.app.ferconsapedidos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.app.ferconsapedidos.Dao.ProductoDAO;

public class SincronizarActivity extends AppCompatActivity {

    Button btSincronizaProducto;
    Button btcancelar;
    ProgressBar pbarProgreso;
    AsyncTaskProducto asyncTaskProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincronizar);

        btSincronizaProducto = (Button)findViewById(R.id.btSincronizaProducto);
        btSincronizaProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pbarProgreso = (ProgressBar)findViewById(R.id.progressbar1);
                pbarProgreso.setVisibility(View.VISIBLE);


                asyncTaskProducto = new AsyncTaskProducto();
                asyncTaskProducto.execute();
            }
        });

        btcancelar = (Button)findViewById(R.id.btCancelar);
        btcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTaskProducto.cancel(true);
                btcancelar.setVisibility(View.INVISIBLE);
                btSincronizaProducto.setEnabled(true);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sincronizar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void tareaLarga()
    {
        try {
            Thread.sleep(1000);

        } catch(InterruptedException e) {}
    }

    private class AsyncTaskProducto extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            new ProductoDAO().ActualizarProducto();
            for(int i=1; i<=10; i++) {
                tareaLarga();

                publishProgress(i*10);

                if(isCancelled())
                    break;
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();

            pbarProgreso.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            pbarProgreso.setMax(100);
            pbarProgreso.setProgress(0);
            btcancelar.setVisibility(View.VISIBLE);
            btSincronizaProducto.setEnabled(false);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
                btcancelar.setVisibility(View.INVISIBLE);
                btSincronizaProducto.setEnabled(true);
                Toast.makeText(SincronizarActivity.this, "Sincronizacion Productos finalizada!",
                        Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            pbarProgreso.setProgress(0);
            Toast.makeText(SincronizarActivity.this, "Sincronizacion cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
