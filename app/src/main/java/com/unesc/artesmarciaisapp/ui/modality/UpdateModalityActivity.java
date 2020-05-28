package com.unesc.artesmarciaisapp.ui.modality;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.services.ModalityService;

public class UpdateModalityActivity extends AppCompatActivity {
    private ModalityService modalityService = new ModalityService(this);
    private Button btnModalityUpdate;
    private TextInputEditText edtModalityName;
    private ModalityModel modalityEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_modality);

        edtModalityName = findViewById(R.id.edtModalityName);
        btnModalityUpdate = findViewById(R.id.btnModalityUpdate);

        try {
            modalityEdit = modalityService.getByModalidade(getIntent().getStringExtra("modalidade"));
            load(modalityEdit);
        } catch (Exception e) {
            new AlertDialog.Builder(UpdateModalityActivity.this)
                    .setTitle("Erro")
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            UpdateModalityActivity.this.finish();
                        }
                    })
                    .show();
        }

        btnModalityUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ModalityModel newEntity = update();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Modalidade atualizada com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(UpdateModalityActivity.this, ModalityActivity.class);
                                    setResult(UpdateModalityActivity.RESULT_OK, intent);
                                    UpdateModalityActivity.this.finish();
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

    private ModalityModel update() throws Exception {
        String name = edtModalityName.getText().toString();
        return this.modalityService.update(new ModalityModel(name),modalityEdit.getModalidade());
    }

    private void load(ModalityModel model){
        edtModalityName.setText(model.getModalidade());
    }
}
