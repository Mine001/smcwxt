package com.hulqframe.generate.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.common.Constants;
import com.hulqframe.generate.model.DataTableField;
import com.hulqframe.generate.service.DataTableFieldService;
import com.hulqframe.system.model.User;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;

@Controller
@RequestMapping("/generate/dataTableField")
public class DataTableFieldController extends BaseController<DataTableField,Integer> {
    @Autowired
    private DataTableFieldService dataTableFieldService;
    @Override
    public BaseService<DataTableField, Integer> getService() {
        return dataTableFieldService;
    }

    @Override
    @RequestMapping("/index")
    public String index(@CurrentUser User user, ServletRequest request, Model model){
        String tableId=request.getParameter("tableId");
        model.addAttribute("tableId",tableId);
        return getTemplatePath("index");
    }

    @RequestMapping("/editNew")
    public String edit(Model model, @RequestParam(required = false) Integer id,
                       @RequestParam(required = false) Integer tableId){
        DataTableField dataTableFiled;
        if(id!=null){
            dataTableFiled=getService().findById(id);
        }else{
            dataTableFiled=new DataTableField();
            dataTableFiled.setTableId(tableId);
        }
        model.addAttribute("data", dataTableFiled);
        model.addAttribute("class", Constants.MODEL_FIELD_CLASS.values());
        model.addAttribute("types",Constants.FILED_TYPES);
        return getTemplatePath("edit");
    }
    @Override
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@CurrentUser User user, Model model, Integer id, DataTableField entity){
        if(id!=null){
            dataTableFieldService.update(entity);
        }else{
            dataTableFieldService.save(entity);
        }
        return Result.SUCCESS;
    }
    @Override
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Model model, Integer id){
        getService().delete(id);
        return Result.SUCCESS;
    }
}
