package com.unesc.artesmarciaisapp.ui.student;

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
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.StudentService;
import com.unesc.artesmarciaisapp.ui.adapters.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    List<StudentModel> lst = new ArrayList<StudentModel>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private StudentService studentService = new StudentService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        recyclerView = findViewById(R.id.rcvAlunosList);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, 1);
        recyclerView.addItemDecoration(itemDecor);

        lst.addAll(this.studentService.getAll());

        mAdapter = new StudentAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.students_search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.btnStudentsSearch);
        SearchView sv = (SearchView) searchItem.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StudentAdapter mAdapter = new StudentAdapter(lst);
                mAdapter.filterDataset(query.toLowerCase(),StudentActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                StudentAdapter mAdapter = new StudentAdapter(lst);
                mAdapter.filterDataset(newText.toLowerCase(),StudentActivity.this);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnStudentsAdd){
            Intent intent = new Intent(this, AddStudentActivity.class);
            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.lst = this.studentService.getAll();
        mAdapter = new StudentAdapter(lst);
        recyclerView.setAdapter(mAdapter);
    }
}
