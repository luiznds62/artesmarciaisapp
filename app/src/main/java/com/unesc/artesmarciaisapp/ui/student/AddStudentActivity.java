package com.unesc.artesmarciaisapp.ui.student;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.CepModel;
import com.unesc.artesmarciaisapp.models.CityModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.CepService;
import com.unesc.artesmarciaisapp.services.CityService;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    private StudentService studentService = new StudentService(AddStudentActivity.this);
    private CityService cityService = new CityService(AddStudentActivity.this);
    private CepService cepService = new CepService();

    private Button btnStudentSave;
    private TextInputEditText edtStudentNameInput;
    private TextInputEditText edtStudentPhoneInput;
    private TextInputEditText edtStudentCellphoneInput;
    private TextInputEditText edtStudentEmailInput;
    private TextInputEditText edtStudentObservationInput;
    private TextInputEditText edtStudentAddressInput;
    private TextInputEditText edtStudentNumberInput;
    private TextInputEditText edtStudentComplementInput;
    private TextInputEditText edtStudentNeighborhoodInput;
    private AutoCompleteTextView studentsDropdownCity;
    private AutoCompleteTextView studentsDropdownState;
    private TextInputEditText edtStudentCountryInput;
    private TextInputEditText edtStudentCepInput;

    private TextInputLayout edtStudentBirthdate;
    private TextInputEditText inputStudentBirthdate;
    private AutoCompleteTextView studentsDropdownSex;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    private TextInputLayout edtStudentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        List<String> statesList = new ArrayList<String>();

        // Gambiarrinha
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<CityModel> citiesModels = this.cityService.getAll();
        for(CityModel city : citiesModels){
            if(!statesList.contains(city.getEstado())){
                statesList.add(city.getEstado());
            }
        }

        studentsDropdownState = findViewById(R.id.studentsDropdownState);
        String[] states = statesList.toArray(new String[0]);
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);
        studentsDropdownState.setAdapter(adapterState);

        studentsDropdownCity = findViewById(R.id.studentsDropdownCity);
        String[] cities = new String[]{};
        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        studentsDropdownCity.setAdapter(adapterCity);

        studentsDropdownState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String country = "";
                List<CityModel> citiesModels = cityService.getByState(states[pos]);
                List<String> citiesList = new ArrayList<String>();
                for(CityModel city : citiesModels){
                    if(!citiesList.contains(city.getCidade())){
                        citiesList.add(city.getCidade());
                    }
                    if(country.equals("")){
                        country = city.getPais();
                    }
                }
                String[] citiesByState = citiesList.toArray(new String[0]);
                ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(arg1.getContext(), android.R.layout.simple_spinner_dropdown_item, citiesByState);
                studentsDropdownCity.setAdapter(adapterCity);
                studentsDropdownCity.setText("");
                edtStudentCountryInput.setText(country);
            }

        });

        studentsDropdownSex = findViewById(R.id.studentsDropdownSex);
        String[] items = new String[]{"Masculino", "Feminino"};
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        studentsDropdownSex.setAdapter(adapterSex);

        btnStudentSave = findViewById(R.id.btnStudentSave);
        edtStudentNameInput = findViewById(R.id.edtStudentNameInput);
        edtStudentPhoneInput = findViewById(R.id.edtStudentPhoneInput);
        edtStudentCellphoneInput = findViewById(R.id.edtStudentCellphoneInput);
        edtStudentEmailInput = findViewById(R.id.edtStudentEmailInput);
        edtStudentObservationInput = findViewById(R.id.edtStudentObservationInput);
        edtStudentAddressInput = findViewById(R.id.edtStudentAddressInput);
        edtStudentNumberInput = findViewById(R.id.edtStudentNumberInput);
        edtStudentComplementInput = findViewById(R.id.edtStudentComplementInput);
        edtStudentNeighborhoodInput = findViewById(R.id.edtStudentNeighborhoodInput);
        edtStudentCountryInput = findViewById(R.id.edtStudentCountryInput);
        edtStudentCepInput = findViewById(R.id.edtStudentCepInput);

        edtStudentPhoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        edtStudentCellphoneInput.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        edtStudentBirthdate = findViewById(R.id.edtStudentBirthdate);
        edtStudentBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        edtStudentCepInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CepModel cepInfo = cepService.getCep(edtStudentCepInput.getText().toString());
                    if(cepInfo != null){
                        if(!cepInfo.getBairro().equals("")){
                            edtStudentNeighborhoodInput.setText(cepInfo.getBairro());
                        }
                        if(!cepInfo.getLogradouro().equals("")){
                            edtStudentAddressInput.setText(cepInfo.getLogradouro());
                        }
                        if(!cepInfo.getUf().equals("")){
                            studentsDropdownState.setText(cepService.getStateByUf(cepInfo.getUf()));
                        }
                        if(!cepInfo.getLocalidade().equals("")){
                            studentsDropdownCity.setText(cepInfo.getLocalidade());
                            edtStudentCountryInput.setText("Brasil");
                        }
                    }
                }
            }
        });

        inputStudentBirthdate = findViewById(R.id.inputStudentBirthdate);
        inputStudentBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        btnStudentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StudentModel newStudent = save();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Aluno " + newStudent.getAluno() + " salvo com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddStudentActivity.this, StudentActivity.class);
                                    setResult(AddStudentActivity.RESULT_OK, intent);
                                    AddStudentActivity.this.finish();
                                }
                            })
                            .show();
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Ocorreu um erro")
                            .setMessage(e.getMessage())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Informe a data de nascimento",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            LocalDateTime today = LocalDateTime.now();
            DatePickerDialog dtPicker = new DatePickerDialog(this,
                    myDateListener, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
            dtPicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dtPicker;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        TextInputEditText inputBirthDate = findViewById(R.id.inputStudentBirthdate);
        inputBirthDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private StudentModel save() throws Exception {
        String aluno = edtStudentNameInput.getText().toString();
        String data_nascimento = inputStudentBirthdate.getText().toString();
        String sexo = studentsDropdownSex.getText().toString();
        String telefone = edtStudentPhoneInput.getText().toString();
        String celular = edtStudentCellphoneInput.getText().toString();
        String email = edtStudentEmailInput.getText().toString();
        String observacao = edtStudentObservationInput.getText().toString();
        String endereco = edtStudentAddressInput.getText().toString();
        String numero = edtStudentNumberInput.getText().toString();
        String complemento = edtStudentComplementInput.getText().toString();
        String bairro = edtStudentNeighborhoodInput.getText().toString();
        String cidade = studentsDropdownCity.getText().toString();
        String estado = studentsDropdownState.getText().toString();
        String pais = edtStudentCountryInput.getText().toString();
        String cep = edtStudentCepInput.getText().toString();

        if (sexo.length() != 0) {
            sexo = sexo.substring(0, 1).toUpperCase();
        }

        return this.studentService.create(new StudentModel(aluno,data_nascimento,sexo,telefone,celular,email,observacao,endereco,numero,complemento,bairro,cidade,estado,pais,cep));
    }
}
