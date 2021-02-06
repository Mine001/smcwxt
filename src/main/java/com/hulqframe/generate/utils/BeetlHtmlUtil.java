package com.hulqframe.generate.utils;



import com.hulqframe.base.common.Constants;
import com.hulqframe.generate.model.DataTable;
import com.hulqframe.generate.model.DataTableField;
import com.hulqframe.generate.model.TableFieldCondition;
import com.hulqframe.utils.DbNameUtil;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Component
public class BeetlHtmlUtil {
	@Value("${code.java.rootPath}")
	private String rootPath;
	@Value("${code.templates.rootPath}")
	private String templatesPath;
	@Value("${generate.templates}")
	String TEMPLATE_ROOT;
	@Autowired
	private GroupTemplate groupTemplate;



	private final String STATIC_SUFFIX = ".html";
	private final String JAVA_SUFFIX = ".java";

	public void  createJava(Map<String,Object> map,
							String tpl,
						 Constants.CODE_TYPE javaType) throws IOException {
		Template template = groupTemplate.getTemplate(TEMPLATE_ROOT+tpl);
		String packageName=((String)map.get("packageName"));
		if(javaType.getValue().equals("9")){
			packageName=((String)map.get("apiPackageName"));
		}
		String fileUrl = rootPath+ File.separator +packageName.replaceAll("\\.","/")+ File.separator +
				javaType.getPackageName() + File.separator + ((String)map.get("className"))+javaType.getSplicing()+JAVA_SUFFIX;
		File directory=new File(rootPath+ File.separator +packageName.replaceAll("\\.","/")+ File.separator +
				javaType.getPackageName());
		if(!directory.exists()){
			directory.mkdirs();
		}
		File file=new File(fileUrl);
		FileWriter fileWriter=new FileWriter(file);
		Iterator i= map.keySet().iterator();
		while (i.hasNext()){
			String key= (String) i.next();
			template.binding(key,map.get(key));
		}
		template.renderTo(fileWriter);
	}
	public void  createHtml(DataTable dataTable, String schema,
                            String packageName, String className, List<DataTableField> dataTableFields,
                            String tpl, String fileName, String baseUrl, List<TableFieldCondition> tableFieldCondition) throws IOException {
		Template template = groupTemplate.getTemplate(TEMPLATE_ROOT+tpl);
		String fileUrl = templatesPath+
				baseUrl+ File.separator +DbNameUtil.lowerCase1th(className)+File.separator+ DbNameUtil.lowerCase1th(className)+"_"+fileName+STATIC_SUFFIX;
		File directory=new File(templatesPath+
				baseUrl+ File.separator +DbNameUtil.lowerCase1th(className));
		if(!directory.exists()){
			directory.mkdirs();
		}
		File file=new File(fileUrl);
		FileWriter fileWriter=new FileWriter(file);
		if(fileName.equals(Constants.CODE_TYPE.INDEX.getSplicing())){
			template.binding("tableFieldCondition",tableFieldCondition);
		}
		template.binding("baseUrl",baseUrl);
		template.binding("fields",dataTableFields);
		template.binding("dataTable",dataTable);
		template.binding("schema",schema);
		template.binding("className",className);
		template.binding("packageName",packageName);
		template.binding("formField",getFormField(dataTableFields));
		template.binding("lowerClassName", DbNameUtil.lowerCase1th(className));
		template.renderTo(fileWriter);
	}

	/**
	 * 破解页面所需的字段内容
	 * */
	public static Map<String,Object> getFormField(List<DataTableField> dataTableFields){
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuilder editRule=new StringBuilder();
		StringBuilder editMessage=new StringBuilder();
		boolean isRule=false;//是否有校验规则
		for(int i=0;i<dataTableFields.size();i++){
			isRule=false;
			DataTableField field=dataTableFields.get(i);
			if(!field.getIsHidden()){
				editRule.append(field.getJavaField()).append(":{");
				editMessage.append(field.getJavaField()).append(":{");
				if(!field.getNotNull()){
					isRule=true;
					editRule.append("required:true,");
					editMessage.append("required:\"").append(field.getName()).append("不能为空\",");
				}
				if(field.getFieldLength()!=null){
					isRule=true;
					editRule.append("maxlength:").append(field.getFieldLength()).append(",");
					editMessage.append("maxlength:\"").append(field.getName()).append("的长度不能大于").
							append(field.getFieldLength()).append("\",");
				}
				if(isRule){
					editRule.deleteCharAt(editRule.lastIndexOf(","));
					editMessage.deleteCharAt(editMessage.lastIndexOf(","));
				}
				editRule.append("},");
				editMessage.append("},");
			}

		}
		editRule.deleteCharAt(editRule.lastIndexOf(","));
		editMessage.deleteCharAt(editMessage.lastIndexOf(","));
		map.put("editRule",editRule.toString());
		map.put("editMessage",editMessage.toString());
		return map;
	}
}
