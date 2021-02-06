var selectId;
$("table").each(function(){
    var that=this;
    $(this).find("tr").each(function(index){
        if(index>=0){
            $(that).find(".selected").removeClass("selected");
            $(this).addClass("selected");
            selectId=$(this).find("input[type='hidden']:eq(0)").val();
        }
    });
});
function list_style(){
    $("table").each(function(){
        var that=this;
        $(this).find("tr").each(function(index){
            if(index>=0){
                $(this).on("click",function(){
                    $(that).find(".selected").removeClass("selected");
                    $(this).addClass("selected");
                    selectId=$(this).find("input[type='hidden']:eq(0)").val();
                });
            }
        });
    });
}

/**
 * 初始化  serializeObject
 */
function initSerializeObject() {
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
}
$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $.each(array, function () {
        if (serializeObj[this.name] !== undefined) {
            if (!serializeObj[this.name].push) {
                serializeObj[this.name] = [serializeObj[this.name]];
            }
            serializeObj[this.name].push(this.value || '');
        } else {
            serializeObj[this.name] = this.value || '';
        }
    });
    return serializeObj;
};