package com.unesc.artesmarciaisapp.ui.graduation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.GraduationModel;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.services.GraduationService;
import com.unesc.artesmarciaisapp.services.ModalityService;
import com.unesc.artesmarciaisapp.ui.adapters.GraduationAdapter;
import com.unesc.artesmarciaisapp.ui.modality.UpdateModalityActivity;

import java.util.ArrayList;
import java.util.List;

public class GraduationActivity extends AppCompatActivity {
    List<GraduationModel> lst = new ArrayList<GraduationModel>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GraduationService graduationService = new GraduationService(this);
    private ModalityService modalityService = new ModalityService(this);
    private ModalityModel modalityEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduation);

        try {
            modalityEdit = modalityService.getByModalidade(getIntent().getStringExtra("modalidade"));
        } catch (Exception e) {
            new AlertDialog.Builder(GraduationActivity.this)
                    .setTitle(R.string.dialog_error)
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GraduationActivity.this.finish();
                        }
                    })
                    .show();
        }

        recyclerView = findViewById(R.id.rcvGraduationList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(itemDecor);

        try {
            lst.addAll(this.graduationService.getByModalidade(modalityEdit.getModalidade()));
        } catch (Exception e) {
            new AlertDialog.Builder(GraduationActivity.this)
                    .setTitle(R.string.dialog_error)
                    .setMessage(e.getMessage())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            GraduationActivity.this.finish();
                        }
                    })
                    .show();
        }

        mAdapter = new GraduationAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graduation_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.btnGraduationSearch);
        SearchView sv = (SearchView) searchItem.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GraduationAdapter mAdapter = new GraduationAdapter(lst);
                mAdapter.filterDataset(query.toLowerCase(), GraduationActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GraduationAdapter mAdapter = new GraduationAdapter(lst);
                mAdapter.filterDataset(newText.toLowerCase(), GraduationActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btnGraduationAdd) {
            Intent intent = new Intent(GraduationActivity.this, AddGraduationActivity.class);
            intent.putExtra("modalidade", modalityEdit.getModalidade());
            ((Activity) GraduationActivity.this).startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.lst = this.graduationService.getAll();
        mAdapter = new GraduationAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }
}
