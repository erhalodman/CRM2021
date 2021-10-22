package com.erha.CRM.workbench.service.Impl;

import com.erha.CRM.workbench.dao.ClueDao;
import com.erha.CRM.workbench.dao.ClueRemarkDao;
import com.erha.CRM.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassNameClueRemarkServiceImpl
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1510:23
 * @Version 1.0
 **/
@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private ClueRemarkDao clueRemarkDao;
}
