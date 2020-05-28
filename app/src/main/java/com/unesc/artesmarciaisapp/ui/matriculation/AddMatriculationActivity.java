package com.unesc.artesmarciaisapp.ui.matriculation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.MatriculationService;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class AddMatriculationActivity extends AppCompatActivity {
    private MatriculationService matriculationService = new MatriculationService(this);
    private StudentService studentService = new StudentService(this);

    private Button btnMatriculationSave;
    private TextInputEditText edtMatriculationStudent;
    private TextInputEditText inputMatriculationDate;
    private TextInputEditText edtMatriculationPayDateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matriculation);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<MatriculationModel> matriculationList = null;
        try {
            matriculationList = this.matriculationService.getAll(AddMatriculationActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<StudentModel> studentsModelList = this.studentService.getAll();
        List<StudentModel> studentsAvaibleList = new ArrayList<StudentModel>();
        for (StudentModel student : studentsModelList) {
            Optional<MatriculationModel> studentMatriculated = matriculationList
                    .stream()
                    .filter(st -> st.getCodigo_aluno() == student.getCodigo_aluno())
                    .findAny();

            if (!studentMatriculated.isPresent()) {
                studentsAvaibleList.add(student);
            }
        }

        if (studentsAvaibleList.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddMatriculationActivity.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.dialog_warning)
                    .setMessage("Não há alunos disponíveis para matrícula.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AddMatriculationActivity.this, MatriculationActivity.class);
                            setResult(AddMatriculationActivity.RESULT_OK, intent);
                            AddMatriculationActivity.this.finish();
                        }
                    })
                    .show();
        }

        edtMatriculationStudent = findViewById(R.id.edtMatriculationStudent);
        edtMatriculationStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStudentDialogSearch(studentsAvaibleList);
            }
        });
        edtMatriculationStudent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    showStudentDialogSearch(studentsAvaibleList);
                }
            }
        });

        inputMatriculationDate = findViewById(R.id.inputMatriculationDate);
        inputMatriculationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });

        inputMatriculationDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    setDate(view);
                }
            }
        });

        edtMatriculationPayDateInput = findViewById(R.id.edtMatriculationPayDateInput);
        edtMatriculationPayDateInput.addTextChangedListener(new TextWatcher() {
            boolean textModified = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strEnteredVal = edtMatriculationPayDateInput.getText().toString();
                if (!strEnteredVal.equals("") && !(strEnteredVal.length() < 2)) {
                    int num = Integer.parseInt(strEnteredVal);
                    if (!textModified) {
                        if (num > 15) {
                            edtMatriculationPayDateInput.setText("");
                        } else {
                            textModified = true;
                            edtMatriculationPayDateInput.setText("" + num);
                        }
                    }
                }
            }
        });

        btnMatriculationSave = findViewById(R.id.btnMatriculationSave);
        btnMatriculationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Optional<StudentModel> studentCode = studentsModelList.stream().filter(st -> st.getAluno().equals(edtMatriculationStudent.getText().toString())).findAny();
                    String matriculationDate = inputMatriculationDate.getText().toString();
                    String payDate = edtMatriculationPayDateInput.getText().toString();
                    matriculationService.create(new MatriculationModel(studentCode.get().getCodigo_aluno(), matriculationDate, payDate));

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Aluno matrículado com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddMatriculationActivity.this, MatriculationActivity.class);
                                    setResult(AddMatriculationActivity.RESULT_OK, intent);
                                    AddMatriculationActivity.this.finish();
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

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Informe a data da matrícula",
                Toast.LENGTH_SHORT)
                .show();
    }

    public void showStudentDialogSearch(List<StudentModel> studentsModelList) {
        new SimpleSearchDialogCompat(AddMatriculationActivity.this, "Alunos",
                "Pesquisar", null, (ArrayList) studentsModelList,
                new SearchResultListener<StudentModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           StudentModel item, int position) {
                        edtMatriculationStudent.setText(item.getTitle());
                        dialog.dismiss();
                    }
                }).show();
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
        TextInputEditText inputMatriculationDate = findViewById(R.id.inputMatriculationDate);
        inputMatriculationDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
}
