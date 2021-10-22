package com.erha.CRM.workbench.web.controller;

import com.erha.CRM.settings.domain.User;
import com.erha.CRM.util.DateTimeUtil;
import com.erha.CRM.workbench.domain.Tran;
import com.erha.CRM.workbench.domain.vo.TranUserActivityVo;
import com.erha.CRM.workbench.service.TranService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassNameTranController
 * @Description TODO
 * @Author DELL
 * @Date 2021/10/1813:32
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = {"/tran"})
public class TranController {
    @Autowired
    private TranService tranService;

    @RequestMapping(value = {"/queryTran.action"})
    @ResponseBody
    public PageInfo<TranUserActivityVo> queryTranPageInfo(String pageNo,String pageSize,HttpServletRequest request){
        ServletContext servletContext = request.getServletContext();
        Map<String,String> stage2Possibility = (Map<String, String>) servletContext.getAttribute("stage2Possibility");
        PageInfo<TranUserActivityVo> tranUserActivityVoPageInfo = tranService.queryTranUserActivityVo(stage2Possibility,pageNo, pageSize);
        return tranUserActivityVoPageInfo;
    }

    @RequestMapping(value = {"/queryTranByid.action"})
    public ModelAndView queryTranById(String id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        ServletContext servletContext = request.getServletContext();
        Map<String,String> stage2Possibility = (Map<String, String>) servletContext.getAttribute("stage2Possibility");
        TranUserActivityVo tranUserActivityVo = tranService.queryTranUserActivityVoById(stage2Possibility,id);
        modelAndView.addObject("tran",tranUserActivityVo);
        modelAndView.setViewName("forward:/workbench/transaction/detail.jsp");
        return modelAndView;
    }

    @RequestMapping(value = {"/updateStage.action"},method = RequestMethod.POST)
    @ResponseBody
    public TranUserActivityVo updateStage(Tran tran,HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        tran.setEditBy(user.getName());
        tran.setEditTime(DateTimeUtil.getSysTime());
        TranUserActivityVo tranUserActivityVo = tranService.updateTranStage(tran, user);
        //修改可能性
        Map<String,String> stage2Possibility = (Map<String, String>) request.getServletContext().getAttribute("stage2Possibility");
        tranUserActivityVo.setPossibility(stage2Possibility.get(tranUserActivityVo.getStage()));
        return tranUserActivityVo;
    }


    @RequestMapping(value = {"/queryStageCount.action"})
    @ResponseBody
    public List<Map<String,Object>> queryStageCount(){
        List<Map<String, Object>> stageCount = tranService.queryStageCount();
        return stageCount;
    }

}
