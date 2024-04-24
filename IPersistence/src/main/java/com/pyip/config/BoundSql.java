package com.pyip.config;

import com.pyip.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {
    private String sqlText;
    private List<ParameterMapping> parameterMappingList = new ArrayList<>();

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
