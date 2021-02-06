package ${packageName}.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

${"@"}Table(name = "${dataTable.tableName}")
public class ${className} implements Serializable {

@for(field in fields){
    @if(field.isPrimary){
        /**
        * ${field.name}
        */
        ${"@"}Id
        ${"@"}Column(name = "${field.fieldName}")
        ${"@"}Setter ${"@"}Getter
        ${"@"}GeneratedValue(strategy = GenerationType.IDENTITY)
        private ${field.javaType} ${field.javaField};
    @}else{
        /**
        * ${field.name}
        */
        ${"@"}Column(name = "${field.fieldName}")
        ${"@"}Setter ${"@"}Getter
        @if(field.fieldClass=="dateInput"){
        @if(field.fieldType=="DATETIME"||field.fieldType=="TIMESTAMP"){
        ${"@"}JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @}else{
        ${"@"}JsonFormat(pattern = "yyyy-MM-dd")
        @}
        @}
        private ${field.javaType} ${field.javaField};
    @}

@}

}