<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>查看</title>
    <link rel="stylesheet" href="\${ctx}layui/css/layui.css"/>
    <link rel="stylesheet" href="\${ctx}css/common.css"/>
    <script src="\${ctx}layui/layui.js"></script>
</head>
<body>
<table class="detail-table">
    <tr>
           @var ind=0;
    @for(field in fields){
    @if(!field.isHidden){
        @ind++;
        <th class="title ">${field.name!}:</th>
        <td class="cell">
            @if(field.fieldClass=="dateInput"){
            @if(field.fieldType=="DATETIME"||field.fieldType=="TIMESTAMP"){
            \${strutil.formatDate(data.${field.javaField},'yyyy-MM-dd HH:mm:ss')}
            @}else{
            \${strutil.formatDate(data.${field.javaField},'yyyy-MM-dd')}
            @}
            @}else{
            \${data.${field.javaField}!}
            @}
        </td>
        @if(ind%2==0&& !fieldLP.last){
    </tr>
    <tr>
        @}
    @}

    @}
    </tr>
</table>
<div style="text-align: center;">
    <button class="layui-btn layui-btn-normal"  onclick="top.closeDialog(false)">关闭</button>
</div>
</body>
</html>