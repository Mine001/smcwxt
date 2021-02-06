function getRegionName(id,isParent){
    var name="";
    $.ajax({
        url:"/api/area/getRegionName?id="+id+"&isParent="+isParent,
        dataType:"json",
        async: false,//关键是这个参数 是否异步请求=>false:使用同步请求
        success:function(data){
            if(data.statusCode=="200"){
                name=data.data;
            }
        }
    });
    return name;
}