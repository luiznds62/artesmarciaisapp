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

public class AddModalityActivity extends AppCompatActivity {
    private ModalityService modalityService = new ModalityService(this);
    private Button btnModalitySave;
    private TextInputEditText edtModalityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_modality);

        edtModalityName = findViewById(R.id.edtModalityName);
        btnModalitySave = findViewById(R.id.btnModalitySave);
        btnModalitySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ModalityModel newEntity = save();
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle(R.string.dialog_sucess)
                            .setMessage("Modalidade salva com sucesso")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(AddModalityActivity.this, ModalityActivity.class);
                                    setResult(AddModalityActivity.RESULT_OK, intent);
                                    AddModalityActivity.this.finish();
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

    private ModalityModel save() throws Exception {
        String name = edtModalityName.getText().toString();
        return this.modalityService.create(new ModalityModel(name));
    }
}
