package com.unesc.artesmarciaisapp.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unesc.artesmarciaisapp.R;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.services.DateService;
import com.unesc.artesmarciaisapp.services.MatriculationService;
import com.unesc.artesmarciaisapp.services.ModalityService;
import com.unesc.artesmarciaisapp.services.StudentService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardFragment extends Fragment {
    private List<StudentModel> students = new ArrayList<>();
    private List<ModalityModel> modalities = new ArrayList<>();
    private List<MatriculationModel> matriculations = new ArrayList<>();


    Date date = new Date();
    private LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    private int year = localDate.getYear();
    private int month = localDate.getMonthValue();
    private int day = localDate.getDayOfMonth();
    private int yearLast = localDate.getYear() - 1;
    private int monthLast = localDate.getMonthValue() - 1;

    // Students
    private int studentsPerYearLast = 0;
    private int studentsPerMonthLast = 0;
    private int studentsPerYear = 0;
    private int studentsPerMonth = 0;
    private Double percentStudentsYear = 0.00;
    private Double percentStudentsMonth = 0.00;
    private TextView txvDashboardTotalAlunosAno;
    private TextView txvDashboardTotalAlunosMes;
    private TextView txvTotalAlunosAno;
    private TextView txvTotalAlunosMes;

    // Modalities
    private int qntStudentsBestModality = 0;
    private int qntStudentsWorstModality = 0;
    private String topModality = "";
    private String worstModality = "";
    private TextView txvDashboardTopModalidade;
    private TextView txvDashboardWorstModalidade;
    private TextView txvTopModalityStudentQuantity;
    private TextView txvWorstModalityStudentQuantity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        StudentService studentService = new StudentService(root.getContext());
        students = studentService.getAll();
        studentsDash(root);

//        MatriculationService matriculationService = new MatriculationService(root.getContext());
//        try {
//            matriculations = matriculationService.getAll(root.getContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        ModalityService modalityService = new ModalityService(root.getContext());
//        modalities = modalityService.getAll();
//        modalitiesDash(root);

        return root;
    }

    public void modalitiesDash(View root) {
        txvDashboardTopModalidade = root.findViewById(R.id.txvDashboardTopModalidade);
        txvDashboardWorstModalidade = root.findViewById(R.id.txvDashboardWorstModalidade);
        txvTopModalityStudentQuantity = root.findViewById(R.id.txvTopModalityStudentQuantity);
        txvWorstModalityStudentQuantity = root.findViewById(R.id.txvWorstModalityStudentQuantity);

        // Todo adicionar regra quando ter a matricula pela modalidade

        txvDashboardTopModalidade.setText(topModality);
        txvDashboardWorstModalidade.setText(worstModality);
        txvTopModalityStudentQuantity.setText(qntStudentsBestModality);
        txvWorstModalityStudentQuantity.setText(qntStudentsWorstModality);
    }

    public void studentsDash(View root) {
        txvDashboardTotalAlunosAno = root.findViewById(R.id.txvDashboardTotalAlunosAno);
        txvDashboardTotalAlunosMes = root.findViewById(R.id.txvDashboardTotalAlunosMes);
        txvTotalAlunosAno = root.findViewById(R.id.txvTotalAlunosAno);
        txvTotalAlunosMes = root.findViewById(R.id.txvTotalAlunosMes);

        studentsPerYearLast = students
                .stream().filter(s ->
                        s.getCreatedDate().endsWith(String.valueOf(yearLast))
                )
                .collect(Collectors.toList())
                .size();

        studentsPerMonthLast = students
                .stream().filter(s ->
                        s.getCreatedDate().endsWith(String.valueOf(String.valueOf(monthLast) + "/" + year))
                )
                .collect(Collectors.toList())
                .size();

        studentsPerYear = students
                .stream().filter(s ->
                        s.getCreatedDate().endsWith(String.valueOf(year))
                )
                .collect(Collectors.toList())
                .size();

        studentsPerMonth = students
                .stream().filter(s ->
                        s.getCreatedDate().endsWith(String.valueOf(String.valueOf(month) + "/" + year))
                )
                .collect(Collectors.toList())
                .size();

        percentStudentsYear = Double.valueOf(((studentsPerYear - studentsPerYearLast) / (studentsPerYearLast != 0 ? studentsPerYearLast : 1)) * 100);
        percentStudentsMonth = Double.valueOf(((studentsPerMonth - studentsPerMonthLast) / (studentsPerMonthLast != 0 ? studentsPerMonthLast : 1)) * 100);

        txvDashboardTotalAlunosAno.setText(String.valueOf(studentsPerYear));
        txvDashboardTotalAlunosMes.setText(String.valueOf(studentsPerMonth));
        txvTotalAlunosAno.setText(" +" + String.valueOf(percentStudentsYear) + "%");
        txvTotalAlunosMes.setText(" +" + String.valueOf(percentStudentsMonth) + "%");
    }
}
