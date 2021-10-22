package com.erha;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.settings.service.UserService;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.workbench.dao.ClueDao;
import com.erha.CRM.workbench.dao.TranDao;
import com.erha.CRM.workbench.domain.vo.TranUserActivityVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @ClassNametest
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1013:05
 * @Version 1.0
 **/
public class test {
    @Autowired
    UserService userService;


    @Test
    public void applicationContextTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("dispatcherServlet.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanDefinitionNames){
            System.out.println(name);
        }
    }

    @Test
    public void DateTimeUtilTest(){
        String dateString = DateTimeUtil.getDateString(new Date());
        String sysTime = DateTimeUtil.getSysTime();
        System.out.println(sysTime);
        System.out.println(dateString);
    }

    @Test
    public void queryActivityPageTest(){
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ClueDao clueDao = (ClueDao) applicationContext.getBean("clueDao");
        System.out.println(clueDao);
    }

    @Test
    public void sub(){
        String text = "01交易成功";
        System.out.println(text.substring(2,text.length()));
    }

    @Test
    public void text(){
        
    }
}
