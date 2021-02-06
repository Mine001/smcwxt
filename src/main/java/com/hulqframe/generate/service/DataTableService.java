package com.hulqframe.generate.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.Constants;
import com.hulqframe.base.common.SearchBean;
import com.hulqframe.generate.db.MysqlDbUtil;
import com.hulqframe.generate.mapper.DataTableMapper;
import com.hulqframe.generate.model.DataTable;
import com.hulqframe.generate.model.DataTableField;
import com.hulqframe.generate.model.TableFieldCondition;
import com.hulqframe.generate.utils.BeetlHtmlUtil;
import com.hulqframe.system.model.Permission;
import com.hulqframe.system.service.PermissionService;
import com.hulqframe.utils.DbNameUtil;
import com.hulqframe.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataTableService extends BaseService<DataTable,Integer> {
    @Autowired
    private DataTableMapper dataTableMapper;
    @Autowired
    private DataTableFieldService dataTableFieldService;
    @Override
    public BaseMapper<DataTable, Integer> getBaseMapper() {
        return dataTableMapper;
    }
    @Autowired
    private MysqlDbUtil dbUtil;
    @Autowired
    private BeetlHtmlUtil beetlHtmlUtil;

    @Autowired
    private PermissionService permissionService;




    @Override
    public  DataTable save(DataTable entity){
        super.save(entity);
        dbUtil.createTable(entity.getTableName(),entity.getName());
        dataTableFieldService.saveDefaultId(entity.getId());
        return entity;

    }
    public  void saveNew(DataTable entity){
        super.save(entity);
    }
    @Override
    public void update(DataTable entity){
        DataTable dataTable=findById(entity.getId());
        super.update(entity);
        dbUtil.alterTableComment(dataTable.getTableName(),entity.getName());
        if(!dataTable.getTableName().equals(entity.getTableName())){
            dbUtil.renameTable(dataTable.getTableName(),entity.getTableName());
        }
    }
    @Override
    public  void delete(Integer id){
        DataTable dataTable=findById(id);
        dataTableFieldService.deleteByTableId(id);
        super.delete(id);
        dbUtil.dropTable(dataTable.getTableName());
    }

    public Result generate(Integer tableId, String className, String packageName, Integer catalog,
                           String[] createCodeTypes, Boolean isCreateMenu, List<TableFieldCondition> tableFieldCondition,
                           String apiPackageName, String apiPrefix){
        DataTable dataTable=findById(tableId);
        String schema=Constants.schema;
        List<DataTableField> dataTableFields=dataTableFieldService.listByTableId(tableId);
        Permission permission=permissionService.findById(catalog);
        Map<String,Object> map=new HashMap<>();
        map.put("fields",dataTableFields);
        map.put("dataTable",dataTable);
        map.put("schema",schema);
        map.put("className",className);
        map.put("packageName",packageName);
        map.put("lowerClassName", DbNameUtil.lowerCase1th(className));

        try {
            if(createCodeTypes!=null&&createCodeTypes.length>0){
                for(String s:createCodeTypes){
                    Constants.CODE_TYPE code_type=Constants.CODE_TYPE.getByValue(s);
                    if(code_type==null){
                        continue;
                    }

                    if(code_type.getValue().equals("9")){
                        map.put("baseUrl",apiPrefix==null?"":apiPrefix);
                        map.put("apiPackageName",apiPackageName);
                        beetlHtmlUtil.createJava(map,Constants.CODE_TYPE.API.getTpl(),
                                Constants.CODE_TYPE.API);
                    }else if(code_type.getType().equals("java")){
                        //生成model
                        map.put("baseUrl",permission.getUrl());
                        beetlHtmlUtil.createJava(map,code_type.getTpl(),
                                code_type);
                    }else{
                        beetlHtmlUtil.createHtml(dataTable,schema,packageName,className,dataTableFields,code_type.getTpl(),
                                code_type.getSplicing(),permission.getUrl(),tableFieldCondition);
                    }
                }
            }
            if(isCreateMenu){
                String menuUrl=permission.getUrl()+"/"+ DbNameUtil.lowerCase1th(className)+"/index";

                if(permissionService.findByUnique("url",menuUrl)==null){
                    Permission menu=new Permission();
                    menu.setParentId(catalog);
                    menu.setIsDelete(false);
                    menu.setName(dataTable.getName());
                    menu.setSeq(0);
                    menu.setType(Constants.PERMISSION_TYPE.MENU.getValue());
                    menu.setUrl(menuUrl);
                    permissionService.save(menu);

                    for(int i=0;i<Constants.ACTION_TYPE.values().length;i++){
                        Constants.ACTION_TYPE action_type=Constants.ACTION_TYPE.values()[i];
                        Permission action=new Permission();
                        action.setParentId(menu.getId());
                        action.setIsDelete(false);
                        action.setName(action_type.getName());
                        action.setSeq(i);
                        action.setAction(action_type.getAction());
                        action.setType(Constants.PERMISSION_TYPE.ACTION.getValue());
                        action.setUrl(permission.getUrl()+"/"+ DbNameUtil.lowerCase1th(className)+action_type.getPath());
                        permissionService.save(action);
                    }
                }
            }



            return Result.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            Result result=Result.FAILD;
            result.setMessage("生成代码失败");
            return result;
        }
    }

    public List<Map> getAllTable() {
        return dataTableMapper.getAllTable(Constants.schema);
    }

    public List<Map> getColumnByTableName(String tableName) {
        return dataTableMapper.getColumnByTableName(tableName,Constants.schema);
    }
}
