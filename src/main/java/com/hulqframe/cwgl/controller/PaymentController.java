package com.hulqframe.cwgl.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.cwgl.model.Payment;
import com.hulqframe.cwgl.model.ReceiptForm;
import com.hulqframe.cwgl.model.ReceiptPayment;
import com.hulqframe.cwgl.service.PaymentService;
import com.hulqframe.cwgl.service.ReceiptFormService;
import com.hulqframe.cwgl.service.ReceiptPaymentService;
import com.hulqframe.system.model.User;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cwgl/payment")
public class PaymentController extends BaseController<Payment,Integer> {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ReceiptPaymentService receiptPaymentService;
    @Autowired
    private ReceiptFormService receiptFormService;
    @Override
    public BaseService<Payment, Integer> getService() {
    return paymentService;
    }
    @RequestMapping("/saveNew")
    @ResponseBody
    public Result saveNew(@CurrentUser User user,Integer[] receiptFormIds, Integer id, Payment entity) {
        entity.setStatus("0");
        if(id!=null){
            entity.setUpdateTime(new Date());
            entity.setUpdateUser(user.getId());
            getService().update(entity);
            List<SearchBean> searchBeans=new ArrayList<>();
            searchBeans.add(new SearchBean(id,"SEARCH_AND_paymentId_EQ"));
            List<ReceiptPayment> receiptPayments=receiptPaymentService.listByParams(searchBeans,null);
            if(receiptPayments!=null&&receiptPayments.size()>0) {
                for (ReceiptPayment receiptPayment : receiptPayments) {
                    ReceiptForm receiptForm = receiptFormService.findById(receiptPayment.getReceiptId());
                    receiptForm.setStatus("0");
                    receiptFormService.update(receiptForm);
                }
            }
        }else{
            entity.setCreateTime(new Date());
            entity.setCreateUser(user.getId());
            entity= getService().save(entity);
        }
        //删除之前的付款信息
        receiptPaymentService.deleteByPaymentId(entity.getId());
        BigDecimal payMoney=entity.getPayMoney();
        if(receiptFormIds!=null){
            for(Integer receiptFormId:receiptFormIds){
                if(payMoney.compareTo(BigDecimal.ZERO)>0){
                    ReceiptForm receiptForm=receiptFormService.findById(receiptFormId);
////                    List<SearchBean> searchBeans=new ArrayList<>();
////                    searchBeans.add(new SearchBean(entity.getId(),"SEARCH_AND_paymentId_EQ"));
////                    searchBeans.add(new SearchBean(receiptFormId,"SEARCH_AND_receiptId_EQ"));
////                    ReceiptPayment receiptPayment=receiptPaymentService.findObjectByParams(searchBeans);
////                    if(receiptPayment==null){
//                        receiptPayment= new ReceiptPayment();
//                        receiptPayment.setCreateTime(new Date());
//                    }
                    ReceiptPayment receiptPayment= new ReceiptPayment();
                    receiptPayment.setCreateTime(new Date());
                    if(payMoney.compareTo(receiptForm.getMoney())>=0){
                        receiptPayment.setMoney(receiptForm.getMoney());
                        receiptForm.setStatus("1");
                        receiptFormService.update(receiptForm);
                    }else{
                        receiptPayment.setMoney(payMoney);
                    }
                    receiptPayment.setPaymentId(entity.getId());
                    receiptPayment.setReceiptId(receiptFormId);
                    receiptPaymentService.save(receiptPayment);
                    payMoney=payMoney.subtract(receiptPayment.getMoney());
                }else{
                    break;
                }

            }
        }
        return Result.SUCCESS;
    }

    @RequestMapping("/edit")
    public String edit(Model model, ServletRequest request, @RequestParam(required = false) Integer id  ){
        setParameters(request,model);
        if(id!=null){
            List<SearchBean> searchBeans=new ArrayList<>();
            searchBeans.add(new SearchBean(id,"SEARCH_AND_paymentId_EQ"));
            List<ReceiptPayment> receiptPayments=receiptPaymentService.listByParams(searchBeans,null);
            if(receiptPayments!=null&&receiptPayments.size()>0){
                String receiptFormIds="";
                for(ReceiptPayment receiptPayment:receiptPayments){
                    receiptFormIds=receiptFormIds+receiptPayment.getReceiptId()+",";
                }
                searchBeans=new ArrayList<>();
                searchBeans.add(new SearchBean(receiptFormIds,"SEARCH_AND_id_IN"));
                List<ReceiptForm> receiptFormList=receiptFormService.listByParams(searchBeans,null);
                model.addAttribute("receiptFormList", receiptFormList);
            }

            model.addAttribute("data", getService().findById(id));
        }
        return getTemplatePath("edit");
    }
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id){
        if(id!=null){
            List<SearchBean> searchBeans=new ArrayList<>();
            searchBeans.add(new SearchBean(id,"SEARCH_AND_paymentId_EQ"));
            List<ReceiptPayment> receiptPayments=receiptPaymentService.listByParams(searchBeans,null);
            if(receiptPayments!=null&&receiptPayments.size()>0){
                String receiptFormIds="";
                for(ReceiptPayment receiptPayment:receiptPayments){
                    receiptFormIds=receiptFormIds+receiptPayment.getReceiptId()+",";
                }
                searchBeans=new ArrayList<>();
                searchBeans.add(new SearchBean(receiptFormIds,"SEARCH_AND_id_IN"));
                List<ReceiptForm> receiptFormList=receiptFormService.listByParams(searchBeans,null);
                model.addAttribute("receiptFormList", receiptFormList);
            }

            model.addAttribute("data", getService().findById(id));
        }
        return getTemplatePath("detail");
    }
}
