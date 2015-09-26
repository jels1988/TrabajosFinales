package grupo4.histoclici;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;

import grupo4.histoclici.adaptadores.spinner.ASPaciente;
import grupo4.histoclici.dao.CitaDAO;
import grupo4.histoclici.entidad.Cita;
import grupo4.histoclici.entidad.Paciente;


public class ActivityCita extends AppCompatActivity {

    private ASPaciente asPaciente;

    private Spinner sPaciente;
    private TextView tvFechaCita, tvInicio, tvFin;
    private ImageButton ibFechaCita, ibInicio, ibFin;
    private CheckBox chDomicilio;
    private Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cita);

        sPaciente = (Spinner) findViewById(R.id.sPaciente);
        ibFechaCita = (ImageButton) findViewById(R.id.ibFechaCita);
        tvFechaCita = (TextView) findViewById(R.id.tvFechaCita);
        ibInicio = (ImageButton) findViewById(R.id.ibInicio);
        tvInicio = (TextView) findViewById(R.id.tvInicio);
        ibFin = (ImageButton) findViewById(R.id.ibFin);
        tvFin = (TextView) findViewById(R.id.tvFin);
        chDomicilio = (CheckBox) findViewById(R.id.chDomicilio);

        tvFechaCita.setOnClickListener(mostrarDialogoFecha);
        ibFechaCita.setOnClickListener(mostrarDialogoFecha);

        tvInicio.setOnClickListener(mostrarDialogoHoraInicio);
        ibInicio.setOnClickListener(mostrarDialogoHoraInicio);
        tvFin.setOnClickListener(mostrarDialogoHoraFin);
        ibFin.setOnClickListener(mostrarDialogoHoraFin);

        asPaciente = new ASPaciente(ActivityCita.this, new CitaDAO().listarPaciente());
        sPaciente.setAdapter(asPaciente);
    }

    View.OnClickListener mostrarDialogoFecha = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog dpd =  new DatePickerDialog(ActivityCita.this, ponFecha, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
            dpd.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-1000);
            dpd.show();
        }
    };

    DatePickerDialog.OnDateSetListener ponFecha = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int ano, int mes, int dia) {
            tvFechaCita.setText(String.format("%02d/%02d/%d", dia, mes+1, ano));
        }
    };

    View.OnClickListener mostrarDialogoHoraInicio = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(ActivityCita.this, ponHoraInicio, calendario.get(calendario.HOUR), 0, true).show();
        }
    };

    TimePickerDialog.OnTimeSetListener ponHoraInicio = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            tvInicio.setText(String.format("%02d:%02d", hora, minuto));
            tvFin.setText(String.format("%02d:%02d", hora + 1, minuto));
        }
    };

    View.OnClickListener mostrarDialogoHoraFin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new TimePickerDialog(ActivityCita.this, ponHoraFin, calendario.get(calendario.HOUR), 0, true).show();
        }
    };

    TimePickerDialog.OnTimeSetListener ponHoraFin = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hora, int minuto) {
            tvInicio.setText(String.format("%02d:%02d", hora - 1, minuto));
            tvFin.setText(String.format("%02d:%02d", hora, minuto));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cita, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.a_GuardaCita) {
            if ( sPaciente.getSelectedItemId() == -1 || sPaciente.getSelectedItemPosition() == -1  ) {
                Toast.makeText(ActivityCita.this, R.string.error_paciente_cita, Toast.LENGTH_LONG).show();
                return false;
            }
            else if (tvFechaCita.getText().toString().trim().length() == 0) {
                tvFechaCita.setHint(R.string.error_fecha);
                return false;
            } else if (tvInicio.getText().toString().trim().length() == 0) {
                tvInicio.setHint(R.string.error_hora);
                return false;
            }

            Cita cita = new Cita();
            cita.setIdPaciente(((Paciente) sPaciente.getSelectedItem()).getidPaciente());
            cita.setFechaCita(tvFechaCita.getText().toString().trim());
            cita.setInicio(tvInicio.getText().toString().trim());
            cita.setFin(tvFin.getText().toString().trim());
            if (!chDomicilio.isChecked())
                cita.setPregunta("N");
            else
                cita.setPregunta("D");

            new CitaDAO().insertarCita(cita);
            Intent intent = new Intent();
            intent.putExtra(ActivityListaCita.ARG_FECHA, tvFechaCita.getText().toString().trim());
            setResult(RESULT_OK, intent);
        }
        finish();
        return true;
    }


}