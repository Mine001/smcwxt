<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>新增</title>
    <!-- bootstrap & fontawesome -->
    <script src="/static/ace/assets/js/jquery-2.1.4.min.js"></script>
    <script src="/static/ace/assets/js/jquery.form.js"></script>
    <script src="/My97DatePicker/WdatePicker.js"></script>
    <script src="/ckeditor/ckeditor.js"></script>
    <!--引入CSS-->
    <link rel="stylesheet" type="text/css" href="/webuploader/webuploader.css">
    <!--引入JS-->
    <script type="text/javascript" src="/webuploader/webuploader.js"></script>
    <link rel="stylesheet" href="\${ctx}layui/css/layui.css"/>
    <link rel="stylesheet" href="\${ctx}css/common.css"/>
    <script src="\${ctx}layui/layui.js"></script>
</head>
<body>
<div class="container">
    <form class="layui-form" action="${baseUrl}/${lowerClassName}/save" id="editForm" onsubmit="return false;" method="post">
        @for(field in fields){
            @if(field.javaField=='isDelete'){continue;}
            @if(field.isHidden==false){
        <div class="layui-form-item">
            @if(!field.notNull){
            <label class="layui-form-label" for="${field.javaField}"><font style='color:red;'>*</font>${field.name!}:</label>
            @}else{
            <label class="layui-form-label" for="${field.javaField}">${field.name!}:</label>
            @}

            <div class="layui-input-block">
                @if(field.fieldClass=="dateInput"){
                    @if(field.fieldType=="DATETIME"||field.fieldType=="TIMESTAMP"){
                <input class="layui-input date-picker" name="${field.javaField}" type="text" id="${field.javaField}" placeholder="请输入${field.name!}"
                       @if(!field.notNull){
                        required lay-verify="required"
                       @}
                       value="\${data.${field.javaField}!${field.fieldValue},dateFormat='yyyy-MM-dd HH:mm:ss'}" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    @}else{
                <input class="layui-input" name="${field.javaField}" type="text" id="${field.javaField}" placeholder="请输入${field.name!}"
                       @if(!field.notNull){
                       required lay-verify="required"
                       @}
                       value="\${data.${field.javaField}!${field.fieldValue},dateFormat='yyyy-MM-dd'}" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
                    @}
                @}else if(field.fieldClass=="editor"){
                <!--判断是否为文本编辑器-->
                <textarea class="layui-textarea"
                          @if(!field.notNull){
                          required lay-verify="required"
                          @}
                          name="${field.javaField}"   id="${field.javaField}">\${data.${field.javaField}!${field.fieldValue}}</textarea>
                <script>
                    CKEDITOR.replace( '${field.javaField}');
                </script>
                @}else if(field.fieldClass=="textarea"){
                <!--判断是否为多行文本-->
                <textarea   class="layui-textarea"
                            @if(!field.notNull){
                            required lay-verify="required"
                            @}
                            name="${field.javaField}"  id="${field.javaField}" rows="5" cols="30">\${data.${field.javaField}!${field.fieldValue}}</textarea>
                @}else if(field.fieldClass=="file"){
                <!--附件上传-->
                <div   class="wu-example">
                    <!--用来存放文件信息-->
                    <input type="hidden"
                           @if(!field.notNull){
                           required lay-verify="required"
                           @}
                           name="${field.javaField}" id="${field.javaField}" value="\${data.${field.javaField}!${field.fieldValue}}"/>
                    <div id="${field.javaField}_list" class="uploader-list">
                    </div>
                    <div class="btns">
                        <div id="${field.javaField}_picker">选择文件</div>
                    </div>
                </div>
                <script>
                    var uploader = WebUploader.create({
                        // swf文件路径
                        swf: '/webuploader/Uploader.swf',
                        // 文件接收服务端。
                        server: '/upload',
                        // 选择文件的按钮。可选。
                        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                        pick: '#${field.javaField}_picker',
                        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                        resize: false,
                        // 选完文件后，是否自动上传。
                        auto: true,
                        multiple:false
                    });
                    uploader.on( 'uploadSuccess', function( file ,response) {
                        if(response.success){
                            var data=response.data;
                            var div=$("<div>")
                            var h4=$('<h4 class="info">');
                            var a=$('<a>');
                            a.attr("href",data.fileUrl);
                            a.text(data.fileName);
                            h4.append(a);
                            div.append(h4)
                            $("#${field.javaField}_list").append(div);
                            $("#${field.javaField}").val(data.fileUrl);
                        }else{
                            alert( data.message?data.message:"上传失败");
                        }
                    });

                    uploader.on( 'uploadError', function( file ) {
                        $( '#'+file.id ).find('p.state').text('上传出错');
                    });
                </script>
                @}else if(field.fieldClass=="image"){
                <!--附件上传-->
                <div  class="wu-example">
                    <!--用来存放文件信息-->
                    <input type="hidden"
                           @if(!field.notNull){
                           required lay-verify="required"
                           @}
                           name="${field.javaField}" id="${field.javaField}" value="\${data.${field.javaField}!${field.fieldValue}}"/>
                    <div class="uploader-list">
                        <img id="${field.javaField}_prew"  style="max-width: 120px;max-height: 120px;" src="\${data.${field.javaField}!${field.fieldValue}}"></img>
                    </div>
                    <div class="btns">
                        <div id="${field.javaField}_picker">选择图片</div>
                    </div>
                </div>
                <script>
                    var uploader = WebUploader.create({
                        // swf文件路径
                        swf: '/webuploader/Uploader.swf',
                        // 文件接收服务端。
                        server: '/upload',
                        // 选择文件的按钮。可选。
                        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                        pick: '#${field.javaField}_picker',
                        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                        resize: false,
                        // 选完文件后，是否自动上传。
                        auto: true,
                        multiple:false,
                        // 只允许选择图片文件。
                        accept: {
                            title: '请选择${field.name!}',
                            extensions: 'gif,jpg,jpeg,bmp,png',
                            mimeTypes: 'image/*'
                        }
                    });
                    uploader.on( 'uploadSuccess', function( file ,response) {
                        if(response.success){
                            var data=response.data;
                            $("#${field.javaField}_prew").attr("src",data.fileUrl);
                            $("#${field.javaField}").val(data.fileUrl);
                        }else{
                            alert( data.message?data.message:"上传失败");
                        }
                    });

                    uploader.on( 'uploadError', function( file ) {
                        $( '#'+file.id ).find('p.state').text('上传出错');
                    });
                </script>
                @}else{
                <input class="layui-input"
                       @if(!field.notNull){
                       required lay-verify="required"
                       @}
                       name="${field.javaField}" type="text" id="${field.javaField}" placeholder="请输入${field.name!}"
                       value="\${data.${field.javaField}!${field.fieldValue}}" />
                @}
            </div>
        </div>
            @}else{
        <input type="hidden"
               @if(!field.notNull){
               required lay-verify="required"
               @}
               name="${field.javaField}" value="\${data.${field.javaField}!${field.fieldValue}}"/>
            @}
        @}
        <div style="text-align: center;">
            <button class="layui-btn layui-btn-normal" lay-filter="formSubmit"  lay-submit >保存</button>
            <button class="layui-btn layui-btn-primary"  onclick="top.closeDialog(false)">关闭</button>
        </div>
    </form>

</div>
<script>
    function CKupdate() {
        for (instance in CKEDITOR.instances)
            CKEDITOR.instances[instance].updateElement();
    }
    layui.use(['form'], function(){
        var form = layui.form;

        form.on('submit(formSubmit)', function(data) {
            CKupdate();
            $("#editForm").ajaxSubmit(function(data){
                if(data.success){
                    layer.msg( data.message?data.message:"操作成功");
                    top.closeDialog(true);
                }else{
                    layer.msg( data.message?data.message:"操作失败");
                }
            });

        });
        //各种基于事件的操作，下面会有进一步介绍
    });
</script>
</body>
</html>