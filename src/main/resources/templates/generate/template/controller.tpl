package ${packageName}.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import ${packageName}.model.${className};
import ${packageName}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

${"@"}Controller
${"@"}RequestMapping("${baseUrl}/${lowerClassName}")
public class ${className}Controller extends BaseController<${className},Integer> {
    ${"@"}Autowired
    private ${className}Service ${lowerClassName}Service;
    ${"@"}Override
    public BaseService<${className}, Integer> getService() {
    return ${lowerClassName}Service;
    }



}
