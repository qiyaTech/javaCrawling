/**
 * Created by qiyalm on 16/6/13.
 */
$(function(){
    var isOk=false;
    //提示信息关闭时
    $("#alertModal").on(' hidden.bs.modal',function(){
        $("#alertInfo").html("操作成功!")
        $("#alertCancel").hide();
    });
});



