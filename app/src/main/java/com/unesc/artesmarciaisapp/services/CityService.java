package com.unesc.artesmarciaisapp.services;

import android.content.Context;

import com.unesc.artesmarciaisapp.database.dao.CityDAO;
import com.unesc.artesmarciaisapp.models.CityModel;

import java.util.List;

public class CityService {
    private CityDAO dao;

    public CityService(Context context) {
        this.dao = new CityDAO(context);
    }

    public List<CityModel> getAll() {
        return this.dao.select();
    }

    public List<CityModel> getByState(String state){
        return this.dao.getByState(state);
    }
}
