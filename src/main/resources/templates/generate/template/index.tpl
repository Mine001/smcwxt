<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${dataTable.name}</title>
    <meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <script src="/static/js/jquery.form.js"></script>
    <script src="/static/js/tableStyle.js"></script>
    <script>
        layui.use(['table','form'], function(){
            var table = layui.table;
            var form = layui.form;
            form.render(); //更新全部
            //第一个实例
            var tableIns=table.render({
                    elem: '#${lowerClassName}PageContent'
                    ,url: '${baseUrl}/${lowerClassName}/list' //数据接口
                    ,where:$("#${lowerClassName}PageForm").serializeJson()
                    ,request:{
                        pageName: 'pageNum', //页码的参数名称，默认：page
                        limitName: 'pageSize' //每页数据量的参数名，默认：limit
                    }
                    ,page: true //开启分页
                    ,cols: [[ //表头
                        {title: '序号', width:80, templet: function (d) {
                            return d.LAY_TABLE_INDEX+1
                        }}
                         @for(field in fields){
                            @if(field.isDisplay){
                            ,{field: '${field.javaField}', title: '${field.name}'}
                            @}
                        @}
                    ]]
        });
        //监听行单击事件
        table.on('row(${lowerClassName}PageTable)', function(obj){
            $(".selected").removeClass("selected");
            $(obj.tr).addClass("selected");
            selectId=obj.data.id;
        });
        init=function(){
            getContent(1);
        };
        getContent=function(pageNum){
            selectId=undefined;
            tableIns.reload({
                where:$("#${lowerClassName}PageForm").serializeJson()
        });
        };
        rest=function(){
            @for(condition in tableFieldCondition){
            $("#SEARCH_${condition.andOr}_${condition.javaField}_${condition.matchType}").val("");
            @}
            form.render(); //更新全部
            getContent(1);
        };
        add=function(title,url,width,height){
            top.openDialog(title,url,width,height)
        };


        edit=function(title,url,width,height){
            if(!selectId){
                layer.msg("请选择需要修改的记录");
                return;
            }
            top.openDialog(title,url+"?id="+selectId,width,height)
        };
        detail=function(title,url,width,height){
            if(!selectId){
                layer.msg("请选择需要查看的记录");
                return;
            }
            top.openDialog(title,url+"/"+selectId,width,height)
        };
        del=function(title,url,width,height) {
            if(!selectId){
                layer.msg("请选择需要删除的记录");
                return;
            }
            layer.confirm('您确定要删除所选信息吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                //按钮【按钮一】的回调
                $.ajax({
                    url:url+"?id="+selectId,
                    success:function(data){
                        if(data.success){
                            layer.msg( "删除成功");
                            getContent(1);
                        }else{
                            layer.msg(data.message?data.message:"操作失败");
                        }
                    }
                });
            }, function(index){
                //按钮【按钮二】的回调
            });
        };
        });
    </script>
</head>
<body>
<div class="layui-row">
    ${"@include("}"../../include/action.html"){};
    <form class="layui-form" action="${baseUrl}/${lowerClassName}/list"  method="post"  id="${lowerClassName}PageForm"
          onsubmit="return false;">
        <input type="hidden" id="orderBy" name="orderBy" value=" id_desc"/>
        <div class="test-table-reload-btn">
            <div class="layui-inline">
                @for(condition in tableFieldCondition){
               ${condition.name}:
                <div class="layui-inline" >
                    <input type="search" class="layui-input"  id="SEARCH_${condition.andOr}_${condition.javaField}_${condition.matchType}"  name="SEARCH_${condition.andOr}_${condition.javaField}_${condition.matchType}"  placeholder="请输入${condition.name}"  >
                </div>
                @}
                ${"<!-- "}           <label>名称:<input type="search" id="SEARCH_AND_name_LIKE" name="SEARCH_AND_name_LIKE"  placeholder=""  ></label>${"-->"}
                    <button class="layui-btn"  onclick="getContent()">搜索</button>
                    <button  class="layui-btn layui-btn-primary" onclick="rest()">重置</button>
            </div>
        </div>
    </form>
</div>
<div id="${lowerClassName}PageContent" style="padding-top: 10px;" lay-filter="${lowerClassName}PageTable"></div>
</body>
</html>