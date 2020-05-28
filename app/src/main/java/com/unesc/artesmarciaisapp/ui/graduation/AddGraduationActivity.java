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

public class AddGraduationActivity extends AppCompatActivity {
    private ModalityService modalityService = new ModalityService(this);
    private GraduationService graduationService = new GraduationService(this);
    private Button btnGraduationSave;
    private TextInputEditText edtGraduationName;
    private ModalityModel modalityEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_graduation);

        try {
            modalityEdit = modalityService.getByModalidade(getIntent().getStringExtra("modalidade"));
        } catch (Exception e) {
            new AlertDialog.Builder(AddGraduationActivity.this)
                    .setTitle(R.string.dialog_error)
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AddGraduationActivity.this.finish();
                        }
                    })
                    .show();
        }

        edtGraduationName = findViewById(R.id.edtGraduationName);
        btnGraduationSave = findViewById(R.id.btnGraduationSave);
        btnGraduationSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GraduationModel newEntity = save();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Graduação salva com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddGraduationActivity.this, GraduationActivity.class);
                                    setResult(AddGraduationActivity.RESULT_OK, intent);
                                    AddGraduationActivity.this.finish();
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

    private GraduationModel save() throws Exception {
        String name = edtGraduationName.getText().toString();
        return this.graduationService.create(new GraduationModel(modalityEdit.getModalidade(),name));
    }
}
