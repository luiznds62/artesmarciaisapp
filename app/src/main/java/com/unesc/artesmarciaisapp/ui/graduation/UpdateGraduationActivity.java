package com.unesc.artesmarciaisapp.ui.graduation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.GraduationModel;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.services.GraduationService;
import com.unesc.artesmarciaisapp.services.ModalityService;

public class UpdateGraduationActivity extends AppCompatActivity {
    private GraduationService graduationService = new GraduationService(this);
    private Button btnGraduationUpdate;
    private TextInputEditText edtGraduationName;
    private GraduationModel graduationEdit;
    private String modality;
    private String graduation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_graduation);
        edtGraduationName = findViewById(R.id.edtGraduationName);
        btnGraduationUpdate = findViewById(R.id.btnGraduationUpdate);

        try {
            String params = getIntent().getStringExtra("modalidade_graduacao");
            modality = params.split(";")[0];
            graduation = params.split(";")[1];
            graduationEdit = graduationService.getByModalityGraduation(modality, graduation);
            edtGraduationName.setText(graduationEdit.getGraduacao());
        } catch (Exception e) {
            new AlertDialog.Builder(UpdateGraduationActivity.this)
                    .setTitle(R.string.dialog_error)
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateGraduationActivity.this.finish();
                        }
                    })
                    .show();
        }

        btnGraduationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GraduationModel newEntity = update();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Graduação atualizada com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(UpdateGraduationActivity.this, GraduationActivity.class);
                                    setResult(UpdateGraduationActivity.RESULT_OK, intent);
                                    UpdateGraduationActivity.this.finish();
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

    private GraduationModel update() throws Exception {
        String name = edtGraduationName.getText().toString();
        graduationEdit.setGraduacao(name);
        return this.graduationService.update(graduationEdit, modality,graduation);
    }
}
