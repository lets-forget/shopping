$('.mygxinimage').click(function(){
    var img=$(this)[0].src;
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '700px',
        skin: 'layui-layer-nobg', //没有背景色
        shadeClose: true,
        content: '<img src='+img+' width="100%">'
    });
});