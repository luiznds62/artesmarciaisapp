package com.unesc.artesmarciaisapp.ui.plan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.models.PlanModel;
import com.unesc.artesmarciaisapp.models.PlanModel;
import com.unesc.artesmarciaisapp.services.ModalityService;
import com.unesc.artesmarciaisapp.services.PlanService;
import com.unesc.artesmarciaisapp.ui.matriculation.AddMatriculationActivity;
import com.unesc.artesmarciaisapp.ui.modality.ModalityActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

public class AddPlanActivity extends AppCompatActivity {
    private ModalityService modalityService = new ModalityService(this);
    private PlanService planService = new PlanService(this);
    private Button btnPlanSave;
    private TextInputEditText edtPlanModality;
    private TextInputEditText edtPlanName;
    private TextInputEditText edtPlanValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        List<PlanModel> planList = this.planService.getAll();
        List<ModalityModel> allModalities = modalityService.getAll();
        List<ModalityModel> avaibleModalities = new ArrayList<>();

        for (ModalityModel modality : allModalities) {
            Optional<PlanModel> plan = planList.stream().filter(pl ->
                    pl.getModalidade().equals(modality.getModalidade()))
                    .findAny();
            if(!plan.isPresent()){
                avaibleModalities.add(modality);
            }
        }

        if(avaibleModalities.isEmpty()){
            new AlertDialog.Builder(AddPlanActivity.this)
                    .setTitle(R.string.dialog_error)
                    .setMessage("Nenhuma modalidade disponível para criação de plano")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AddPlanActivity.this.finish();
                        }
                    })
                    .show();
        }

        edtPlanValue = findViewById(R.id.edtPlanValue);
        edtPlanModality = findViewById(R.id.edtPlanModality);
        edtPlanModality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showModalityDialog(avaibleModalities);
            }
        });
        edtPlanModality.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showModalityDialog(avaibleModalities);
                }
            }
        });

        edtPlanName = findViewById(R.id.edtPlanName);
        btnPlanSave = findViewById(R.id.btnPlanSave);
        btnPlanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PlanModel newEntity = save();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Plano salvo com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddPlanActivity.this, PlanActivity.class);
                                    setResult(AddPlanActivity.RESULT_OK, intent);
                                    AddPlanActivity.this.finish();
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

    public void showModalityDialog(List<ModalityModel> studentsModelList) {
        new SimpleSearchDialogCompat(AddPlanActivity.this, "Modalidade",
                "Pesquisar", null, (ArrayList) studentsModelList,
                new SearchResultListener<ModalityModel>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ModalityModel item, int position) {
                        edtPlanModality.setText(item.getTitle());
                        dialog.dismiss();
                    }
                }).show();
    }

    private PlanModel save() throws Exception {
        String modality = edtPlanModality.getText().toString();
        String name = edtPlanName.getText().toString();
        Double value = Double.valueOf(edtPlanValue.getText().toString());
        return this.planService.create(new PlanModel(modality, name, value));
    }
}
