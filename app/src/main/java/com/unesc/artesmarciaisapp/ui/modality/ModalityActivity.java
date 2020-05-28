package com.unesc.artesmarciaisapp.ui.modality;

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
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.services.ModalityService;
import com.unesc.artesmarciaisapp.ui.adapters.ModalityAdapter;

import java.util.ArrayList;
import java.util.List;

public class ModalityActivity extends AppCompatActivity {
    List<ModalityModel> lst = new ArrayList<ModalityModel>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ModalityService modalityService = new ModalityService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modality);

        recyclerView = findViewById(R.id.rcvModalityList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(itemDecor);

        lst.addAll(this.modalityService.getAll());

        mAdapter = new ModalityAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modality_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.btnModalitySearch);
        SearchView sv = (SearchView) searchItem.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ModalityAdapter mAdapter = new ModalityAdapter(lst);
                mAdapter.filterDataset(query.toLowerCase(), ModalityActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ModalityAdapter mAdapter = new ModalityAdapter(lst);
                mAdapter.filterDataset(newText.toLowerCase(),ModalityActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnModalityAdd){
            Intent intent = new Intent(this, AddModalityActivity.class);
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.lst = this.modalityService.getAll();
        mAdapter = new ModalityAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }
}
