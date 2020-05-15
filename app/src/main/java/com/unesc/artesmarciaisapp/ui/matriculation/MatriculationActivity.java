package com.unesc.artesmarciaisapp.ui.matriculation;

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
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.services.MatriculationService;
import com.unesc.artesmarciaisapp.ui.adapters.MatriculationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MatriculationActivity extends AppCompatActivity {
    List<MatriculationModel> lst = new ArrayList<MatriculationModel>();
    private MatriculationService matriculationService = new MatriculationService(this);
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriculation);

        recyclerView = findViewById(R.id.rcvMatriculationList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this,1);
        recyclerView.addItemDecoration(itemDecor);

        lst.addAll(this.matriculationService.getAll(this));

        mAdapter = new MatriculationAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.matriculations_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.btnMatriculationSearch);
        SearchView sv = (SearchView) searchItem.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                MatriculationAdapter mAdapter = new MatriculationAdapter(lst);
                mAdapter.filterDataset(query.toLowerCase(),MatriculationActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MatriculationAdapter mAdapter = new MatriculationAdapter(lst);
                mAdapter.filterDataset(newText.toLowerCase(),MatriculationActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnMatriculationsAdd){
            Intent intent = new Intent(this, AddMatriculationActivity.class);
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.lst = this.matriculationService.getAll(MatriculationActivity.this);
        mAdapter = new MatriculationAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }
}
