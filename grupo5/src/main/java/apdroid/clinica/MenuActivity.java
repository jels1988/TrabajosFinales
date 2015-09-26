package apdroid.clinica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/** ok
 * Created by AngeloPaulo on 28/agosto/2015.
 */
public class MenuActivity extends AppCompatActivity {

    Button btNuevaCita;
    TextView tvUser;
    View.OnClickListener btNuevaCitaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i2 = new Intent(MenuActivity.this, NuevaCitaActivity.class);
            startActivity(i2);

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUser = (TextView) findViewById(R.id.tvUser);

        int usuario = this.getIntent().getIntExtra("user", 0);


        //String usuario = this.getIntent().getStringExtra();
        /*
        switch (usuario) {
            case "angelo":
                tvUser.setText("Ángelo Paulo Verástegui Ponce");
                break;
            case "edinson":
                tvUser.setText("Edinson EFG");
                break;
            case "antonio":
                tvUser.setText("Antonio ABC");
                break;
        }
        */


        btNuevaCita = (Button) findViewById(R.id.btNuevaCita);
        btNuevaCita.setOnClickListener(btNuevaCitaOnClickListener);


}

}
