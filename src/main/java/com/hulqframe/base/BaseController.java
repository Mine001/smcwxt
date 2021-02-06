package com.hulqframe.base;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.common.LayUIPage;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.asp.SearchBeanFilter;
import com.hulqframe.base.utils.ExcelUtil;
import com.hulqframe.base.utils.FormulaResolve;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseController <T,PK extends Serializable>{
    public abstract BaseService<T,PK> getService();
    @Autowired
    protected PermissionService permissionService;
    @Autowired
    protected FormulaResolve formulaResolve;
    @RequestMapping("/index")
    public String index(@CurrentUser User user, ServletRequest request, Model model){
        setParameters(request,model);
        String menuId=request.getParameter("menuId");
        if(menuId!=null&&!menuId.equals("")){
            List<Permission> actions= permissionService.listByUserAction(Integer.parseInt(menuId),user.getId());
            model.addAttribute("actions",actions);
        }
        return getTemplatePath("index");
    }
    public void setParameters(ServletRequest request, Model model){
        Map<String, String[]> maps =  request.getParameterMap();
        for (Map.Entry<String, String[]> entry : maps.entrySet()) {
            if(entry.getValue()!=null&&entry.getValue().length>0){
                model.addAttribute(entry.getKey().toString(), entry.getValue()[0]);
            }
        }
    }
    @RequestMapping("/list")
    @ResponseBody
    public LayUIPage list(PageInfo<T> page, @SearchBeanFilter List<SearchBean> searchBeans, Model model,
                          String orderBy){
        page = getService().page(page,searchBeans,orderBy);
        model.addAttribute("page",page);
        return LayUIPage.getLayUIPageByPageInfo(page);
//        return getTemplatePath("list");
    }
    @RequestMapping("/export")
    public void export(@SearchBeanFilter List<SearchBean> searchBeans,
                       String orderBy, HttpServletResponse response,
                       String headers,String  cols,
                       String fileName){
        List list=getService().listByParams(searchBeans,orderBy);
        if(fileName.endsWith("xlsx")){
            ExcelUtil.exportExcelX(response,list, headers.split(","),cols.split(","), fileName);
        }else{
            ExcelUtil.exportExcel(response,list, headers.split(","),cols.split(","), fileName);
        }

    }

    @RequestMapping("/edit")
    public String edit(Model model,  ServletRequest request, @RequestParam(required = false) PK id  ){
        setParameters(request,model);
        if(id!=null){
            model.addAttribute("data", getService().findById(id));
        }
        return getTemplatePath("edit");
    }
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") PK id){
        model.addAttribute("data", getService().findById(id));
        return getTemplatePath("detail");
    }
    @RequestMapping("/save")
    @ResponseBody
    public Result save(@CurrentUser User user,Model model, PK id,T entity) throws IOException {
        if(id!=null){
           try {
               // 给属性设置值
               Field f = entity.getClass().getDeclaredField("updateUser");
               if(f!=null) {
                   f.setAccessible(true);
                   f.set(entity, user.getId());
               }
               Field f1 = entity.getClass().getDeclaredField("updateTime");
               if(f1!=null){
                   f1.setAccessible(true);
                   f1.set(entity, new Date());
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            getService().update(entity);
        }else{
            try{
                // 给属性设置值
                Field f = entity.getClass().getDeclaredField("createUser");
                if(f!=null) {
                    f.setAccessible(true);
                    f.set(entity, user.getId());
                }
                Field f1 = entity.getClass().getDeclaredField("createTime");
                if(f1!=null) {
                    f1.setAccessible(true);
                    f1.set(entity, new Date());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            getService().save(entity);
        }

        return Result.SUCCESS;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Model model, PK id){

        getService().delete(id);
        return Result.SUCCESS;
    }
    public String getMappingUrl(){
        Class clazz = this.getClass();
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        RequestMapping mapping= (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        return mapping.value()[0];
    }
    public String getTemplatePath(String method){
        String className=getSuperClassGenricType(this.getClass(),0).getName();
        return getMappingUrl()+"/"+className.substring(className.lastIndexOf(".",className.length())+1).toLowerCase()+"_"+method;
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
