package com.hulqframe.generate.controller;

import com.alibaba.fastjson.JSONArray;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.base.asp.CurrentUser;
import com.hulqframe.base.common.Constants;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.generate.model.DataTable;
import com.hulqframe.generate.model.DataTableField;
import com.hulqframe.generate.model.TableFieldCondition;
import com.hulqframe.generate.service.DataTableFieldService;
import com.hulqframe.generate.service.DataTableService;
import com.hulqframe.generate.service.TableFieldConditionService;
import com.hulqframe.generate.utils.BeetlHtmlUtil;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.model.User;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.utils.DbNameUtil;
import com.hulqframe.utils.Result;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/generate/dataTable")
public class DataTableController extends BaseController<DataTable,Integer> {
    @Autowired
    private DataTableService dataTableService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private DataTableFieldService dataTableFieldService;
    @Autowired
    private TableFieldConditionService conditionService;

    @Value("${code.java.rootPath}")
    private String rootPath;
    @Value("${code.templates.rootPath}")
    private String templatesPath;
    private final String STATIC_SUFFIX = ".html";
    private final String JAVA_SUFFIX = ".java";
    @Override
    public BaseService<DataTable, Integer> getService() {
        return dataTableService;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(@CurrentUser User user, Model model, Integer id, DataTable entity){
        if(id!=null){
            getService().update(entity);
        }else{
            getService().save(entity);
        }

        return Result.SUCCESS;
    }
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Model model, Integer id){
        getService().delete(id);
        return Result.SUCCESS;
    }

    @RequestMapping("/generateIndex")
    public String gerneateIndex(Model model, Integer id){
        DataTable dataTable=getService().findById(id);
        model.addAttribute("data",dataTable);
        model.addAttribute("className", DbNameUtil.parseToClassName(dataTable.getTableName()));

        List<Permission> permissions=permissionService.listByType(Constants.PERMISSION_TYPE.CATALOG.getValue());
        model.addAttribute("catalogs",permissions);

        model.addAttribute("codeTypes",Constants.CODE_TYPE.values());

        List<DataTableField> dataTableFields=dataTableFieldService.listByTableId(id);
        model.addAttribute("dataTableFields",dataTableFields);
        model.addAttribute("matchTypes", SearchBean.SEARCH_CRITERIA_MATCHTYPE.values());
        return getTemplatePath("generateIndex");
    }

    @RequestMapping("/generate")
    @ResponseBody
    public Result generate(Model model, Integer tableId,String className,Boolean isCreateMenu,
                           String packageName,Integer catalog,String[] createCodeTypes,String apiPackageName,String apiPrefix,
                           @RequestParam(required = false) String[] fieldNames,@RequestParam(required = false) String[] matchTypes,
                           @RequestParam(required = false) String[] andOrs,@RequestParam(required = false) String[] names){
        List<TableFieldCondition> tableFieldCondition=new ArrayList<TableFieldCondition>();
        if(fieldNames!=null){
            for(int i=0;i<fieldNames.length;i++){
                if(fieldNames[i]!=null&&!fieldNames[i].trim().equals("")
                &&matchTypes[i]!=null&&!matchTypes[i].trim().equals("")
                        &&andOrs[i]!=null&&!andOrs[i].trim().equals("")
                        &&names[i]!=null&&!names[i].trim().equals("")){
                    TableFieldCondition condition=new TableFieldCondition(matchTypes[i],andOrs[i],tableId,fieldNames[i],names[i]);
                    tableFieldCondition.add(condition);
//                    conditionService.save(condition);
                }
            }
        }
        dataTableService.generate(tableId,className,packageName,catalog,createCodeTypes,isCreateMenu,tableFieldCondition,apiPackageName,apiPrefix);
        return Result.FAILD;
    }
    @RequestMapping("/checkFiles")
    @ResponseBody
    public Result checkFiles(String className,
                           String packageName,Integer catalog,String[] createCodeTypes){
        Permission permission=permissionService.findById(catalog);
        List<Map<String,Object>> msg=new ArrayList<>();
        boolean flag=true;
        if(createCodeTypes!=null&&createCodeTypes.length>0){
            for(String s:createCodeTypes){
                Constants.CODE_TYPE code_type=Constants.CODE_TYPE.getByValue(s);
                if(code_type==null){
                    continue;
                }
                String fileUrl =null;
                String fileName=null;
                if(code_type.getType().equals("java")){
                     fileUrl = rootPath+ File.separator +packageName.replaceAll("\\.","/")+ File.separator +
                            code_type.getPackageName() + File.separator + className+code_type.getSplicing()+JAVA_SUFFIX;
                }else{
                     fileUrl = templatesPath+
                             permission.getUrl()+ File.separator +DbNameUtil.lowerCase1th(className)+File.separator+DbNameUtil.lowerCase1th(className)+"_"+ code_type.getSplicing()+STATIC_SUFFIX;

                }

                File file=new File(fileUrl);
                if(file.exists()){
                    flag=false;
                    Map<String,Object> map=new HashMap<>();
                    map.put("msg",fileUrl+"已存在，是否要覆盖此文件?");
                    map.put("type",s);
                    msg.add(map);
                }

            }
        }
        if(flag){
            return Result.SUCCESS;
        }else{
            Result result=Result.FAILD;
            result.setData(msg);
            return result;
        }

    }
    /**
     * 数据库表初始化
     * */
    @RequestMapping("/initFromDatabase")
    @ResponseBody
    public Result initFromDatabase(){
        List<Map> tables=dataTableService.getAllTable();
        if(tables!=null){
            for(Map table:tables){
                DataTable dataTable=new DataTable();
                dataTable.setDes(table.get("des")==null?"":(String) table.get("des"));
                dataTable.setIsDelete(false);
                dataTable.setName(table.get("name")==null?"":(String) table.get("name"));
                dataTable.setTableName(table.get("tableName")==null?"":(String) table.get("tableName"));
                dataTableService.saveNew(dataTable);
                List<Map> columns=dataTableService.getColumnByTableName(dataTable.getTableName());
                if(columns!=null){
                    for(Map column:columns){
                        DataTableField tableField=new DataTableField();
                        tableField.setTableId(dataTable.getId());
                        tableField.setFieldClass("input");
                        if(column.get("column_type")!=null&&!column.get("column_type").equals("")){
                            String columnType= (String) column.get("column_type");
                            columnType=columnType.replaceAll("\\D", "");
                            tableField.setFieldLength(columnType.equals("")?0:Integer.parseInt(columnType.replaceAll("\\D", "")));
                        }else{
                            tableField.setFieldLength(0);
                        }

                        tableField.setFieldName(column.get("fieldName")==null?"":(String)column.get("fieldName"));
                        tableField.setFieldType(column.get("fieldType")==null?"":((String)column.get("fieldType")).toUpperCase());
                        tableField.setIsDisplay(true);
                        tableField.setSetting("[]");
                        tableField.setIsHidden(false);
                        tableField.setName(column.get("name")==null?"":(String)column.get("name"));
                        if(column.get("column_key")!=null&&column.get("column_key").equals("PRI")){
                            tableField.setIsPrimary(true);
                        }else{
                            tableField.setIsPrimary(false);
                        }
                        if(column.get("is_nullable")!=null&&column.get("is_nullable").equals("YES")){
                            tableField.setNotNull(true);
                        }else{
                            tableField.setNotNull(false);
                        }
                        tableField.setFieldValue(column.get("fieldValue")==null?"":(String)column.get("fieldValue"));
                        dataTableFieldService.saveNew(tableField);
                    }
                }
            }
        }
        return Result.SUCCESS;
    }
}
