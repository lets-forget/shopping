//发送验证码时添加cookie
function addCookie(name, value, expiresHours) {
    var cookieString = name + "=" + escape(value);
    //判断是否设置过期时间,0代表关闭浏览器时失效
    if(expiresHours > 0) {
        var date = new Date();
        date.setTime(date.getTime() + expiresHours * 1000);
        cookieString = cookieString + ";expires=" + date.toUTCString();
    }
    document.cookie = cookieString;
}
//修改cookie的值
function editCookie(name, value, expiresHours) {
    var cookieString = name + "=" + escape(value);
    if(expiresHours > 0) {
        var date = new Date();
        date.setTime(date.getTime() + expiresHours * 1000); //单位是毫秒
        cookieString = cookieString + ";expires=" + date.toGMTString();
    }
    document.cookie = cookieString;
}

//根据名字获取cookie的值
function getCookieValue(name) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for(var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if(arr[0] == name) {
            return unescape(arr[1]);
            break;
        }
    }
}
//开始倒计时
var countdown;
function settime(obj) {
    countdown = getCookieValue("secondsremained");
    var tim = setInterval(function() {
        countdown--;
        obj.attr("disabled", true);
        obj.attr("value", "重新发送(" + countdown + ")");
        if(countdown <= 0 ) {
            clearInterval(tim);
            $(obj).removeAttr("disabled");
            $(obj).attr("value", "重新发送");
        }
        editCookie("secondsremained", countdown, countdown + 1);
    }, 1000) //每1000毫秒执行一次

}