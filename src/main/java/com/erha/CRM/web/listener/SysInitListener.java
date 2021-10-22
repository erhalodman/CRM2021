package com.erha.CRM.web.listener;

import com.erha.CRM.settings.dao.DicTypeDao;
import com.erha.CRM.settings.dao.DicValueDao;
import com.erha.CRM.settings.domain.DicType;
import com.erha.CRM.settings.domain.DicValue;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*;

/**
 * @ClassNameSysInitListener
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1511:39
 * @Version 1.0
 **/
@WebListener
public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("WebListener执行了");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Map<String, List<DicValue>> dicAll = getDicAll();
        Map<String, String> stage2Possibility = getStage2Possibility();
        servletContext.setAttribute("dicAll", dicAll);
        servletContext.setAttribute("stage2Possibility",stage2Possibility);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    /**
     * @Author 二哈老头子
     * @Description //TODO 获取所有的数组字典已Map方式保存
     * @Date 15:00 2021/10/15
     * @Param []
     * @return java.util.Map<java.lang.String,java.util.List<com.erha.CRM.settings.domain.DicValue>>
     **/
    private Map<String,List<DicValue>> getDicAll(){
        Map<String,List<DicValue>> dicAll = new HashMap<>();
        //反向控制
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        DicTypeDao dicTypeDao = (DicTypeDao)applicationContext.getBean("dicTypeDao");//注意获取类名时要用小写
        DicValueDao dicValueDao = (DicValueDao)applicationContext.getBean("dicValueDao");
        List<DicType> dicTypesAll = dicTypeDao.getDicTypesAll();
        Iterator<DicType> iterator = dicTypesAll.iterator();
        while(iterator.hasNext()){
            DicType next = iterator.next();
            String code = next.getCode();
            dicAll.put(code,dicValueDao.selectDicValuesByTypeCode(code));
        }
        return dicAll;
    }

    /**
     * @Author 二哈老头子
     * @Description //TODO 获取Stage2Possibility.properties配置文件信息并用map封装
     * @Date 10:59 2021/10/18
     * @Param []
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    private Map<String,String> getStage2Possibility(){
        HashMap<String, String> possibilityMap = new HashMap<>();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        Enumeration<String> keys = resourceBundle.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = resourceBundle.getString(key);
            System.out.println(key+" : "+value);
            possibilityMap.put(key,value);
        }
        return possibilityMap;
    }
}
