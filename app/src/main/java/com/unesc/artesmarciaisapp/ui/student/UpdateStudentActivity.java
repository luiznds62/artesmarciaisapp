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

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class UpdateStudentActivity extends AppCompatActivity {
    private StudentService studentService = new StudentService(UpdateStudentActivity.this);
    private CityService cityService = new CityService(UpdateStudentActivity.this);
    private CepService cepService = new CepService();

    private Button btnStudentUpdate;
    private TextInputEditText edtStudentNameInput;
    private TextInputEditText edtStudentPhoneInput;
    private TextInputEditText edtStudentCellphoneInput;
    private TextInputEditText edtStudentEmailInput;
    private TextInputEditText edtStudentObservationInput;
    private TextInputEditText edtStudentAddressInput;
    private TextInputEditText edtStudentNumberInput;
    private TextInputEditText edtStudentComplementInput;
    private TextInputEditText edtStudentNeighborhoodInput;
    private TextInputEditText edtStudentCityInput;
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
        setContentView(R.layout.activity_update_student);
        List<String> statesList = new ArrayList<String>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<CityModel> citiesModels = this.cityService.getAll();
        for (CityModel city : citiesModels) {
            if (!statesList.contains(city.getEstado())) {
                statesList.add(city.getEstado());
            }
        }

        edtStudentCityInput = findViewById(R.id.edtStudentCityInput);
        studentsDropdownState = findViewById(R.id.studentsDropdownState);
        String[] states = statesList.toArray(new String[0]);
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, states);
        studentsDropdownState.setAdapter(adapterState);
        studentsDropdownState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtStudentCityInput.setText("");
            }
        });

        edtStudentCityInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = studentsDropdownState.getText().toString();
                if (state != null && !state.equals("")) {
                    List<CityModel> citiesModels = cityService.getByState(state);
                    showCityDialogSearch(citiesModels);
                } else {
                    new AlertDialog.Builder(UpdateStudentActivity.this)
                            .setTitle("Erro")
                            .setMessage("É necessário informar o estado")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            }
        });
        edtStudentCityInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    String state = studentsDropdownState.getText().toString();
                    if (state != null && !state.equals("")) {
                        List<CityModel> citiesModels = cityService.getByState(state);
                        showCityDialogSearch(citiesModels);
                    } else {
                        new AlertDialog.Builder(UpdateStudentActivity.this)
                                .setTitle("Erro")
                                .setMessage("É necessário informar o estado")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }
                }
            }
        });

        studentsDropdownSex = findViewById(R.id.studentsDropdownSex);
        String[] items = new String[]{"Masculino", "Feminino"};
        ArrayAdapter<String> adapterSex = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        studentsDropdownSex.setAdapter(adapterSex);

        btnStudentUpdate = findViewById(R.id.btnStudentUpdate);
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

        inputStudentBirthdate = findViewById(R.id.inputStudentBirthdate);
        inputStudentBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    setDate(view);
                }
            }
        });

        edtStudentCepInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    CepModel cepInfo = cepService.getCep(edtStudentCepInput.getText().toString());
                    if (cepInfo != null) {
                        if (!cepInfo.getBairro().equals("")) {
                            edtStudentNeighborhoodInput.setText(cepInfo.getBairro());
                        }
                        if (!cepInfo.getLogradouro().equals("")) {
                            edtStudentAddressInput.setText(cepInfo.getLogradouro());
                        }
                        if (!cepInfo.getUf().equals("")) {
                            studentsDropdownState.setText(cepService.getStateByUf(cepInfo.getUf()));
                        }
                        if (!cepInfo.getLocalidade().equals("")) {
                            edtStudentCityInput.setText(cepInfo.getLocalidade());
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
        StudentModel studentEdit = null;
        try {
            studentEdit = studentService.getByCodigoAluno(getIntent().getIntExtra("codigo_aluno", 0));
            load(studentEdit);
        } catch (Exception e) {
            new AlertDialog.Builder(UpdateStudentActivity.this)
                    .setTitle("Erro")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateStudentActivity.this.finish();
                        }
                    })
                    .show();
        }

        StudentModel finalStudentEdit = studentEdit;
        btnStudentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StudentModel updateStudent = update(finalStudentEdit);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Aluno " + updateStudent.getAluno() + " atualizado com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(UpdateStudentActivity.this, StudentActivity.class);
                                    setResult(UpdateStudentActivity.RESULT_OK, intent);
                                    UpdateStudentActivity.this.finish();
                                }
                            })
                            .show();
                } catch (Exception e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_error)
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

    public void showCityDialogSearch(List<CityModel> citiesModelList) {
        new SimpleSearchDialogCompat(UpdateStudentActivity.this, studentsDropdownState.getText().toString(),
                "Pesquisar", null, (ArrayList) citiesModelList,
                new SearchResultListener<CityModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           CityModel item, int position) {
                        edtStudentCityInput.setText(item.getTitle());
                        edtStudentCountryInput.setText(item.getPais());
                        dialog.dismiss();
                    }
                }).show();
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

    private void load(StudentModel student) {
        edtStudentNameInput.setText(student.getAluno());
        inputStudentBirthdate.setText(student.getData_nascimento());
        edtStudentPhoneInput.setText(student.getTelefone());
        edtStudentCellphoneInput.setText(student.getCelular());
        edtStudentEmailInput.setText(student.getEmail());
        edtStudentObservationInput.setText(student.getObservacao());
        edtStudentAddressInput.setText(student.getEndereco());
        edtStudentNumberInput.setText(student.getNumero());
        edtStudentComplementInput.setText(student.getComplemento());
        edtStudentNeighborhoodInput.setText(student.getBairro());
        edtStudentCountryInput.setText(student.getPais());
        edtStudentCepInput.setText(student.getCep());
        edtStudentCityInput.setText(student.getCidade());
        studentsDropdownState.setText(student.getEstado());
        studentsDropdownSex.setText(student.getSexo().equals("M") ? "Masculino" : "Feminino");
    }

    private StudentModel update(StudentModel studentToUpdate) throws Exception {
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
        String cidade = edtStudentCityInput.getText().toString();
        String estado = studentsDropdownState.getText().toString();
        String pais = edtStudentCountryInput.getText().toString();
        String cep = edtStudentCepInput.getText().toString();

        studentToUpdate.setAluno(aluno);
        studentToUpdate.setData_nascimento(data_nascimento);
        studentToUpdate.setTelefone(telefone);
        studentToUpdate.setCelular(celular);
        studentToUpdate.setEmail(email);
        studentToUpdate.setObservacao(observacao);
        studentToUpdate.setEndereco(endereco);
        studentToUpdate.setNumero(numero);
        studentToUpdate.setComplemento(complemento);
        studentToUpdate.setBairro(bairro);
        studentToUpdate.setCidade(cidade);
        studentToUpdate.setEndereco(estado);
        studentToUpdate.setPais(pais);
        studentToUpdate.setCep(cep);

        if (sexo.length() != 0) {
            sexo = sexo.substring(0, 1).toUpperCase();
        }
        studentToUpdate.setSexo(sexo);

        return this.studentService.update(studentToUpdate);
    }
}
