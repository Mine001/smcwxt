package com.hulqframe.generate.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.base.common.Constants;
import com.hulqframe.generate.db.MysqlDbUtil;
import com.hulqframe.generate.mapper.DataTableFieldMapper;
import com.hulqframe.generate.model.DataTable;
import com.hulqframe.generate.model.DataTableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataTableFieldService extends BaseService<DataTableField,Integer> {
    @Autowired
    private DataTableFieldMapper dataTableFieldMapper;
    @Autowired
    private DataTableService dataTableService;
    @Autowired
    private MysqlDbUtil dbUtil;
    @Override
    public BaseMapper<DataTableField, Integer> getBaseMapper() {
        return dataTableFieldMapper;
    }
    public DataTableField save(DataTableField entity){
        super.save(entity);
        DataTable dataTable=dataTableService.findById(entity.getTableId());
        String cplumnType;
        if(entity.getFieldLength()!=null&&!entity.getFieldLength().equals("")){
             cplumnType=entity.getFieldType()+"("+entity.getFieldLength()+")";
        }else{
             cplumnType=entity.getFieldType();
        }
        dbUtil.addField(dataTable.getTableName(),entity.getFieldName(),cplumnType,
                entity.getName(),entity.getNotNull(),entity.getFieldValue());
        return entity;
    }
    public void saveNew(DataTableField entity){
        super.save(entity);

    }
    public void update(DataTableField entity){
        DataTableField old=findById(entity.getId());
        super.update(entity);
        DataTable dataTable=dataTableService.findById(entity.getTableId());
        String cplumnType;
        if(entity.getFieldLength()!=null&&!entity.getFieldLength().equals("")){
            cplumnType=entity.getFieldType()+"("+entity.getFieldLength()+")";
        }else{
            cplumnType=entity.getFieldType();
        }
        if(old.getFieldName().equals(entity.getFieldName())){
            dbUtil.alterField(dataTable.getTableName(),entity.getFieldName(),cplumnType,
                    entity.getName(),entity.getNotNull(),entity.getFieldValue());
        }else{
            dbUtil.alterFieldName(dataTable.getTableName(),old.getFieldName(),entity.getFieldName(),cplumnType,
                    entity.getName(),entity.getNotNull(),entity.getFieldValue());
        }

    }
    public void deleteByTableId(Integer tableId){
        dataTableFieldMapper.deleteByTableId(tableId);
    }

    @Override
    public void delete(Integer id){
        DataTableField dataTableField=findById(id);
        DataTable dataTable=dataTableService.findById(dataTableField.getTableId());
        super.delete(id);
        dbUtil.delField(dataTable.getTableName(),dataTableField.getFieldName());
    }
    public  List<DataTableField> listByTableId(Integer tableId){
        return dataTableFieldMapper.listByTableId(tableId);
    }
    public void saveDefaultId(Integer tableId){
        DataTableField entity=new DataTableField();
        entity.setTableId(tableId);
        entity.setFieldClass(Constants.MODEL_FIELD_CLASS.INPUT.getValue());
        entity.setFieldLength(11);
        entity.setFieldName("id");
        entity.setFieldType("INT");
        entity.setIsDisplay(false);
        entity.setIsHidden(true);
        entity.setIsPrimary(true);
        entity.setName("主键");
        entity.setNotNull(true);
        entity.setSetting("");
        super.save(entity);

        DataTableField entity2=new DataTableField();
        entity2.setTableId(tableId);
        entity2.setFieldClass(Constants.MODEL_FIELD_CLASS.INPUT.getValue());
        entity2.setFieldLength(1);
        entity2.setFieldName("is_delete");
        entity2.setFieldType("BIT");
        entity2.setIsDisplay(false);
        entity2.setIsHidden(true);
        entity2.setIsPrimary(false);
        entity2.setName("是否删除");
        entity2.setFieldValue("b'0'");
        entity2.setNotNull(true);
        entity2.setSetting("");
        super.save(entity2);
    }

}
