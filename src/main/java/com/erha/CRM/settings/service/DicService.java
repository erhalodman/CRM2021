package com.erha.CRM.settings.service;

import com.erha.CRM.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String, List<DicValue>> getAllDic();

}
