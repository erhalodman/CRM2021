package com.erha.CRM.settings.service.impl;

import com.erha.CRM.settings.dao.DicTypeDao;
import com.erha.CRM.settings.dao.DicValueDao;
import com.erha.CRM.settings.domain.DicType;
import com.erha.CRM.settings.domain.DicValue;
import com.erha.CRM.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameDicServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1510:39
 * @Version 1.0
 **/
@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;


    @Override
    public Map<String, List<DicValue>> getAllDic() {
        Map<String, List<DicValue>> dicMap = new HashMap<String, List<DicValue>>();
        List<DicType> dicTypesAll = dicTypeDao.getDicTypesAll();
        Iterator<DicType> iterator = dicTypesAll.iterator();
        while(iterator.hasNext()){
            DicType next = iterator.next();
            String code = next.getCode();
            dicMap.put(code,dicValueDao.selectDicValuesByTypeCode(code));
        }
        if(!dicMap.isEmpty()){
            return dicMap;
        }
        return null;
    }
}
