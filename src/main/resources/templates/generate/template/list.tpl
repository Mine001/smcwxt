<table  class="layui-table">
    <thead>
    <tr role="row">
        <th lay-data="{field:'index', width:80}">
            序号
        </th>
        @for(field in fields){
        @if(field.isDisplay){
        <th lay-data="{field:'${field.fieldName}'}">
            ${field.name}
        </th>
        @}
        @}
    </tr>
    </thead>


    <tbody>
       ${" @for(item in page.list){"}
        <tr role="row">
            <td class="center">
                <input type="hidden" value="\${item.id}"/>
                \${itemLP.index}
            </td>
            @for(field in fields){
                @if(field.isDisplay){
            <td class="center">
                @if(field.fieldClass=="dateInput"){
                    @if(field.fieldType=="DATETIME"||field.fieldType=="TIMESTAMP"){
                \${item.${field.javaField}!,dateFormat='yyyy-MM-dd HH:mm:ss'}
                    @}else{
                \${item.${field.javaField}!,dateFormat='yyyy-MM-dd'}
                    @}
                @}else{
                \${item.${field.javaField}!}
                @}
            </td>
                @}
            @}
        </tr>
       ${" @\}"}
    </tbody>
</table>
<div class="pagination" id="Pagination" >

</div>
<script>
    layui.use(['laypage','table'], function(){
        var table = layui.table;
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'Pagination' //注意，这里的 test1 是 ID，不用加 # 号
            ,count: \${page.pages} //数据总数，从服务端得到
            ,curr: \${page.pageNum}
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                //首次不执行
                if(!first){
                    getContent(obj.curr);
                }
            }
        });
        table.init('parse-table-demo', { //转化静态表格
        });
    });
</script>