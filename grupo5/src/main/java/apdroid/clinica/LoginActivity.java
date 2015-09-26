package apdroid.clinica;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import apdroid.clinica.entidades.DatosCita;
import apdroid.clinica.util.Constantes;

public class LoginActivity extends AppCompatActivity {

    public SharedPreferences sp;
    private EditText etUser, etPass;
    private Button btIngresar;





    View.OnClickListener btIngresarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();
// Se tiene q cambiar por el tipo de Perfil

            if (user.equalsIgnoreCase("angelo") && pass.equalsIgnoreCase("123456")
                    ||user.equalsIgnoreCase("antonio") && pass.equalsIgnoreCase("abcde")
                    ||user.equalsIgnoreCase("edinson") && pass.equalsIgnoreCase("qwerty")) {

                //Intent i=new Intent(getApplicationContext(),MainActivity.class);
                //i.putExtra("user", 1);
                //startActivity(i);


                user=user.toLowerCase();
                String nya="";

                int nUser=0;

                if (user.equals("angelo")){
                    nUser=1;
                    nya="Angelo Verástegui";
                }
                if (user.equals("edinson")){
                    nUser=2;
                    nya="Edinson Vásquez";

                }
                if (user.equals("antonio")){
                    nUser=3;
                    nya="Antonio Merejildo";
                }



                SharedPreferences.Editor spe = sp.edit();
                spe.putString(Constantes.ARG_USER, user);
                spe.putString(Constantes.ARG_PASS, pass);
                spe.putInt(Constantes.ARG_NUSER, nUser);
                spe.putString(Constantes.ARG_NOMBRE, nya);
                spe.commit();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //DatosCita datosPersona = new DatosCita();
                //datosPersona.setNombre(sp.getString(ARG_NOMBRE,""));
                //intent.putExtra("user", nUser);
                //intent.putExtra("datosper",datosPersona);
                intent.putExtra(MainActivity.ARG_USUARIO, nya);
                startActivity(intent);
                finish();


            }else{
                Toast.makeText(getApplicationContext(), "Ups !!!", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Clave o Usuario Incorrecto", Toast.LENGTH_LONG).show();
            }



        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        btIngresar = (Button) findViewById(R.id.btIngresar);
        btIngresar.setOnClickListener(btIngresarOnClickListener);

        sp = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        if (sp.contains(Constantes.ARG_USER)
                && sp.contains(Constantes.ARG_NUSER)
                && sp.contains(Constantes.ARG_PASS)
         //       && !sp.getString(ARG_USER, "").isEmpty()
        //        && !sp.getString(ARG_PASS, "").isEmpty()
            )
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //DatosCita datosPersona = new DatosCita();
            //datosPersona.setNombre(sp.getString(ARG_NOMBRE,""));
            intent.putExtra(MainActivity.ARG_USUARIO, sp.getString(Constantes.ARG_NOMBRE,"Default"));
            //intent.putExtra("user",sp.getInt(ARG_NUSER,0));
            //intent.putExtra("datosper",datosPersona);
            startActivity(intent);
            finish();
        }



    }










}
