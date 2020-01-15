//真实姓名
function checkName(name){
    var check_name =$(name).val();
    if(check_name!=""){
        $(name).css("border","1px solid green");
        return true;
    }else {
        $(name).css("border","1px solid red");
        return false;
    }
}
//手机号
function checkTelephone(phone){
    var telephone = $(phone).val();

    //使用正则表达式验证 2055569134@qq.com
    var flag=/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(telephone);
    //根据正则表达表达式，返回结果
    if(flag){
        $(phone).css("border","1px solid green");
        flag=true;
    }else {
        $(phone).css("border","1px solid red");
        flag=false;
    }
    return flag
}
//省
function checkProvince(province) {
    var value=$(province).val();
    if (value==""){
        $(province).css("border","1px solid red");
        return false;
    } else{
        $(province).css("border","1px solid green");
        return true;
    }
}
//市
function checkCity(city) {
    var value=$(city).val();
    if (value==""){
        $(city).css("border","1px solid red");
        return false;
    } else{
        $(city).css("border","1px solid green");
        return true;
    }
}
//县
function checkCounty(county) {
    var value=$(county).val();
    if (value==""){
        $(county).css("border","1px solid red");
        return false;
    } else{
        $(county).css("border","1px solid green");
        return true;
    }
}
//乡镇
function checkParticulars(particulars) {
    var value=$(particulars).val();
    if (value==""){
        $(particulars).css("border","1px solid red");
        return false;
    } else if(value.length>=255){
        $(particulars).css("border","1px solid red");
        return false;
    }else{
        $(particulars).css("border","1px solid green");
        return true;
    }
}