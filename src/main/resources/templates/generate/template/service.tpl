package ${packageName}.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.model.${className};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

${"@"}Service
public class ${className}Service extends BaseService<${className},Integer> {
${"@"}Autowired
private ${className}Mapper ${lowerClassName}Mapper;
${"@"}Override
public BaseMapper<${className}, Integer> getBaseMapper() {
return ${lowerClassName}Mapper;
}
}
