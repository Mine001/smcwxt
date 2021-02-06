package com.hulqframe.system.controller;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.ApiResult;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.system.model.DataDict;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.DataDictService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/dataDict")
public class DataDictController extends BaseController<DataDict,Integer> {
    public static  final  Integer DATADICT_ROOT_ID=0;
    @Autowired
    private DataDictService dataDictService;
    @Override
    public BaseService<DataDict, Integer> getService() {
    return dataDictService;
    }
    @RequestMapping("/index")
    public String index(@CurrentUser User user, ServletRequest request, Model model){
        setParameters(request,model);
        String menuId=request.getParameter("menuId");
        if(menuId!=null&&!menuId.equals("")){
            List<Permission> actions= permissionService.listByUserAction(Integer.parseInt(menuId),user.getId());
            model.addAttribute("actions",actions);
        }
        String parentId=request.getParameter("parentId");
        if(parentId!=null){
            model.addAttribute("parentId",parentId);
        }
        return getTemplatePath("index");
    }
    @RequestMapping("/listTree")
    @ResponseBody
    public LayUIPage listTree(PageInfo<DataDict> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                              String orderBy){
        List<DataDict> dataDicts=dataDictService.listByParams(searchBeans,orderBy);
        LayUIPage layUIPage=new LayUIPage();
        layUIPage.setCode(0);
        layUIPage.setMsg("");
        layUIPage.setData(dataDicts);
        layUIPage.setCount(dataDicts.size());
        return layUIPage;
    }
    @RequestMapping("/editNew")
    public String editNew(Model model, @RequestParam(required = false) Integer id
            , @RequestParam(required = false) Integer parentId){
        DataDict dataDict;
        if(id!=null){
            dataDict=getService().findById(id);
        }else{
            dataDict=new DataDict();
            if(parentId!=null){
                dataDict.setParentId(parentId);
            }else{
                dataDict.setParentId(DATADICT_ROOT_ID);
            }
        }
        model.addAttribute("data", dataDict);
        return getTemplatePath("edit");
    }

    @RequestMapping("/saveNew")
    @ResponseBody
    public Result saveNew(Model model, DataDict dataDict){
        if(dataDict.getParentId()!=null&&!dataDict.getParentId().equals(DATADICT_ROOT_ID)){
            DataDict p=dataDictService.findById(dataDict.getParentId());
            if(p!=null){
                dataDict.setLevel(p.getLevel()==null?1:p.getLevel()+1);
            }
        }else{
            dataDict.setLevel(1);
        }
        dataDict.setIsDelete(false);
        if(dataDict.getId()!=null&&dataDict.getId()>0){
            dataDictService.update(dataDict);
        }else{
            dataDictService.save(dataDict);
        }
        return Result.SUCCESS;
    }
    /**
     * 获取树形选择框数据
     * */
    @RequestMapping("/listForTreeSelect")
    @ResponseBody
    public List listForTreeSelect( Integer pId,
                              String orderBy){
        DataDict dataDict=dataDictService.findById(pId);
        if(dataDict!=null){
            List<SearchBean> searchBeans=new ArrayList<>();
            searchBeans.add(new SearchBean(dataDict.getCode(),"SEARCH_AND_code_STARTWITH"));
            List<DataDict> dataDicts=dataDictService.listByParams(searchBeans,orderBy);
            return getTreeSelect(dataDicts,pId);
        }
        return null;
    }
    public List getTreeSelect(List<DataDict> dataDicts,Integer parentId){
        List<Map<String,Object>> res=new ArrayList<>();
        for(DataDict dict:dataDicts){
            if(dict.getParentId().equals(parentId)){
                Map<String,Object> node=new HashMap<>();
                node.put("id",dict.getId());
                node.put("name",dict.getName());
                List children=getTreeSelect(dataDicts,dict.getId());
                node.put("open",children.size()>0?true:false);
                if(children.size()>0){
                    node.put("children",children);
                }
                node.put("checked",false);
                res.add(node);
            }
        }
        return res;
    }
}
