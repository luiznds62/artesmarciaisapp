package com.unesc.artesmarciaisapp.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.unesc.artesmarciaisapp.models.CityModel;
import com.unesc.artesmarciaisapp.models.GraduationModel;
import com.unesc.artesmarciaisapp.models.MatriculationModel;
import com.unesc.artesmarciaisapp.models.ModalityModel;
import com.unesc.artesmarciaisapp.models.PlanModel;
import com.unesc.artesmarciaisapp.models.StudentModel;
import com.unesc.artesmarciaisapp.models.FaturaMatriculaModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NOME = "banco.db";
    private static final int DATABASE_VERSAO = 11;

    /**
     * Construtor padrão da classe.
     * @param context
     *          Activity responsável por montar o banco.
     */
    public DBOpenHelper(Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CityModel.CREATE_TABLE);

        for(String query: CityModel.INSERT_CITIES){
            db.execSQL(query);
        }

        db.execSQL(StudentModel.CREATE_TABLE);
        db.execSQL(FaturaMatriculaModel.CREATE_TABLE);
        db.execSQL(MatriculationModel.CREATE_TABLE);
        db.execSQL(ModalityModel.CREATE_TABLE);
        db.execSQL(GraduationModel.CREATE_TABLE);
        db.execSQL(PlanModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cidade
        db.execSQL(CityModel.DROP_TABLE);
        db.execSQL(CityModel.CREATE_TABLE);

        for(String query: CityModel.INSERT_CITIES){
            db.execSQL(query);
        }

        // Aluno
        db.execSQL(StudentModel.DROP_TABLE);
        db.execSQL(StudentModel.CREATE_TABLE);

        // Fatura Matricula
        db.execSQL(FaturaMatriculaModel.DROP_TABLE);
        db.execSQL(FaturaMatriculaModel.CREATE_TABLE);

        // Matricula
        db.execSQL(MatriculationModel.DROP_TABLE);
        db.execSQL(MatriculationModel.CREATE_TABLE);

        // Modalidade
        db.execSQL(ModalityModel.DROP_TABLE);
        db.execSQL(ModalityModel.CREATE_TABLE);

        // Graduação
        db.execSQL(GraduationModel.DROP_TABLE);
        db.execSQL(GraduationModel.CREATE_TABLE);

        // Plano
        db.execSQL(PlanModel.DROP_TABLE);
        db.execSQL(PlanModel.CREATE_TABLE);
    }
}
