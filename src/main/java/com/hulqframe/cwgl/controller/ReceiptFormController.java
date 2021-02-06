package com.hulqframe.cwgl.controller;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.cwgl.model.Project;
import com.hulqframe.cwgl.model.ReceiptForm;
import com.hulqframe.cwgl.model.Supplier;
import com.hulqframe.cwgl.service.ProjectService;
import com.hulqframe.cwgl.service.ReceiptFormService;
import com.hulqframe.cwgl.service.SupplierService;
import com.hulqframe.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cwgl/receiptForm")
public class ReceiptFormController extends BaseController<ReceiptForm,Integer> {
    @Autowired
    private ReceiptFormService receiptFormService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private SupplierService supplierService;
    @Override
    public BaseService<ReceiptForm, Integer> getService() {
    return receiptFormService;
    }

    @RequestMapping("/edit")
    public String edit(Model model, ServletRequest request, @RequestParam(required = false) Integer id  ){
        setParameters(request,model);
        if(id!=null){
            ReceiptForm receiptForm=getService().findById(id);
            model.addAttribute("data", receiptForm);
            Project p=projectService.findById(receiptForm.getProjectId());
            if(p!=null){
                model.addAttribute("projectName", p.getName());
            }
            Supplier supplier=supplierService.findById(receiptForm.getSupplierId());
            if(supplier!=null){
                model.addAttribute("supplierName", supplier.getName());
            }
        }
        return getTemplatePath("edit");
    }
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        ReceiptForm receiptForm=getService().findById(id);
        model.addAttribute("data", receiptForm);
        Project p=projectService.findById(receiptForm.getProjectId());
        if(p!=null){
            model.addAttribute("projectName", p.getName());
        }
        Supplier supplier=supplierService.findById(receiptForm.getSupplierId());
        if(supplier!=null){
            model.addAttribute("supplierName", supplier.getName());
        }
        return getTemplatePath("detail");
    }
    @RequestMapping("/list")
    @ResponseBody
    public LayUIPage list(PageInfo<ReceiptForm> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                          String orderBy){
        page = getService().page(page,searchBeans,orderBy);
        model.addAttribute("page",page);
        return LayUIPage.getLayUIPageByPageInfo(page);
    }

    @RequestMapping("/listForSelect")
    public String listForSelect(@CurrentUser User user, ServletRequest request, Model model){
        setParameters(request,model);

        return getTemplatePath("listForSelect");
    }
}
