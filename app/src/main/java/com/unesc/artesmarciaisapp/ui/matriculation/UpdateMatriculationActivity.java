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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.DateService;
import com.unesc.artesmarciaisapp.services.MatriculationService;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class UpdateMatriculationActivity extends AppCompatActivity {
    private MatriculationService matriculationService = new MatriculationService(this);

    private Button btnMatriculationUpdate;
    private TextInputEditText edtMatriculationStudent;
    private TextInputEditText inputMatriculationDate;
    private TextInputEditText edtMatriculationPayDateInput;
    private CheckBox chkEncerrarConta;

    private MatriculationModel matriculationEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_matriculation);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        chkEncerrarConta = findViewById(R.id.chkEncerrarConta);
        edtMatriculationPayDateInput = findViewById(R.id.edtMatriculationPayDateInput);
        edtMatriculationStudent = findViewById(R.id.edtMatriculationStudent);
        inputMatriculationDate = findViewById(R.id.inputMatriculationDate);
        btnMatriculationUpdate = findViewById(R.id.btnMatriculationUpdate);

        try {
            matriculationEdit = matriculationService.getByCodigoAluno(getIntent().getIntExtra("codigo_aluno", 0),UpdateMatriculationActivity.this);
            load(matriculationEdit);
        } catch (Exception e) {
            new AlertDialog.Builder(UpdateMatriculationActivity.this)
                    .setTitle("Erro")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateMatriculationActivity.this.finish();
                        }
                    })
                    .show();
        }

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

        btnMatriculationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String payDate = edtMatriculationPayDateInput.getText().toString();
                    if(chkEncerrarConta.isChecked()){
                        String todayDate = DateService.dateToStringFormated(new Date());
                        matriculationEdit.setData_encerramento(todayDate);
                    }
                    matriculationEdit.setDia_vencimento(payDate);
                    matriculationService.update(matriculationEdit);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Matrícula atualizada com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(UpdateMatriculationActivity.this, MatriculationActivity.class);
                                    setResult(UpdateMatriculationActivity.RESULT_OK, intent);
                                    UpdateMatriculationActivity.this.finish();
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

    private void load(MatriculationModel matriculation){
        edtMatriculationStudent.setText(matriculation.getStudent().getAluno());
        inputMatriculationDate.setText(matriculation.getData_matricula());
        edtMatriculationPayDateInput.setText(matriculation.getDia_vencimento());
        if(matriculation.getData_encerramento() != null){
            chkEncerrarConta.setChecked(true);
        }
    }
}
