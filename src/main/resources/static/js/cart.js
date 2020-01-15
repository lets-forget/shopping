$(function(){
	/**************数量加减***************/
	$("body").on('click','.num .sub',function(){
		var num = parseInt($(this).siblings("span").text());
		if(num<=1){
			$(this).attr("disabled","disabled");
		}else{
			num--;
			$(this).siblings("span").text(num);
			//获取除了货币符号以外的数字
			var price = $(this).parents(".number").prev().text().substring(1);
			//单价和数量相乘并保留两位小数
			$(this).parents(".th").find(".sAll").text('￥'+(num*price).toFixed(2));
			$.get("/cart/updateCart",{"classify":$(this).attr('value'),"type":"sub"},function (data) {
				if (data.flag){
					cartSelect();
				}
			})
			jisuan();
			zg();
		}
	});
	$("body").on('click','.num .add',function(){
		var num = parseInt($(this).siblings("span").text());
		if(num>=15){
			swal("提示信息", "您购买的商品过多！只能购买15件", "warning");
		}else{
			num++;
			$(this).siblings("span").text(num);
			var price = $(this).parents(".number").prev().text().substring(1);
			$(this).parents(".th").find(".sAll").text('￥'+(num*price).toFixed(2));
			$.get("/cart/updateCart",{"classify":$(this).attr('value'),"type":"add"},function (data) {
				if (data.flag){
					cartSelect();
				}
			})
			jisuan();
			zg();
		}
	});
	//计算总价
	function jisuan(){
		var all=0;
		var len =$(".th input[type='checkbox']:checked").length;
		if(len==0){
			 $("#all").text('￥'+parseFloat(0).toFixed(2));
		}else{
			 $(".th input[type='checkbox']:checked").each(function(){
			 	//获取小计里的数值
	        	var sAll = $(this).parents(".pro").siblings('.sAll').text().substring(1);
	        	//累加
	        	all+=parseFloat(sAll);
	        	//赋值
	        	$("#all").text('￥'+all.toFixed(2));
	        })
		}
		
	}
	//计算总共几件商品
	function zg(){
		var zsl = 0;
		var index = $(".th input[type='checkbox']:checked").parents(".th").find(".num span");
		var len =index.length;
		if(len==0){
			$("#sl").text(0);
		}else{
			index.each(function(){
				zsl+=parseInt($(this).text());
				$("#sl").text(zsl);
			})
		}
		if($("#sl").text()>0){
			$(".count").css("background","#c10000");
		}else{
			$(".count").css("background","#8e8e8e");
		}
	}
	/*****************商品全选***********************/
	$("body").on('click',"input[type='checkbox']",function(){
		var sf = $(this).is(":checked");
		var sc= $(this).hasClass("checkAll");
		if(sf){
			if(sc){
				 $("input[type='checkbox']").each(function(){
	                this.checked=true;  
	           }); 
				zg();
	           	jisuan();
			}else{
				$(this).checked=true;
	            var len = $("input[type='checkbox']:checked").length;
	            var len1 = $("input").length-1;
				if(len==len1){
					 $("input[type='checkbox']").each(function(){
		                this.checked=true;  
		            }); 
				}
				zg();
				jisuan();
			}
		}else{
			if(sc){
				 $("input[type='checkbox']").each(function(){  
	                this.checked=false;  
	           }); 
				zg();
				jisuan();
			}else{
				$(this).checked=false;
				var len = $(".th input[type='checkbox']:checked").length;
	            var len1 = $("input").length-1;
				if(len<len1){
					$('.checkAll').attr("checked",false);
				}
				zg();
				jisuan();
			}
		}
		
	});
	/****************************proDetail 加入购物车*******************************/
/*	$("body").on('click','.btns .cart',function(){
		if($(".categ p").hasClass("on")){
			var num = parseInt($(".num span").text());
			var num1 = parseInt($(".goCart span").text());
			$(".goCart span").text(num+num1);
		}
	});*/

	//删除购物车商品
	$('body').on('click','.delAll',function(){

		//选中多个一起删除
		if($(".th input[type='checkbox']:checked").length==0){
			swal("提示信息", "请选择您要删除的商品", "warning");
		} else{
			var Classifys =[];//定义一个数组
			$('.th input[type="checkbox"]:checked').each(function(){//遍历每一个名字为nodes的复选框，其中选中的执行函数
				Classifys.push({"cartClassify":$(this).val()});//将选中的值添加到数组chk_value中
			});
			swal({
				title: "您确定要删除吗？",
				text: "您确定要删除"+$(".th input[type='checkbox']:checked").length+"条商品吗？",
				type: "warning",
				showCancelButton: true,
				closeOnConfirm: false,
				confirmButtonText: "是的，我要删除",
				confirmButtonColor: "#ec6c62"
			}, function() {
				$.ajax({
					url: "/cart/remove",
					type: "POST",
					contentType : 'application/json;charset=utf-8', //设置请求头信息
					dataType:"json",
					data: JSON.stringify(Classifys),   //将Json对象序列化成Json字符串，JSON.stringify()原生态方法
					success: function(data){
						if (data.flag){
							$("#all").text('￥'+parseFloat(0).toFixed(2));
							swal("操作成功!", "已成功删除数据！", "success");
							cartSelect();
						} else{
							swals("操作失败", "删除操作失败了!", "error");
						}
					}
				});
			});
		}
	})
	//点击结算按钮
	$("div.tr p.fr").on('click','.count',function () {
		if ($(".th input[type='checkbox']:checked").length==0){
			swal({
				title: "提示信息",
				text: "请选择要结算的商品",
				imageUrl: "userImage/a.jpg"
			});
			return false;
		} else{
			//查看是否该用户有地址
			$.ajax({
				url:'/address/isByUidAddress',
				type:'post',
				dataType:'json',
				success:(data)=>{
					if (data.code===200){
						var Classifys =[];//定义一个数组
						$('.th input[type="checkbox"]:checked').each(function(){//遍历每一个名字为nodes的复选框，其中选中的执行函数
							Classifys.push($(this).val());//将选中的值添加到数组chk_value中
						});
						location.href="/order/cartinfo?classifys="+Classifys;
					}else{
						layer.confirm('抱歉，您还没有收货地址，请先添加收货地址', {
							btn: ['去添加','稍后添加'] //按钮
						}, function(){
							location.href="/address/selectAll";
						}, function(){
							layer.closeAll("confirm");
						});
					}
				},
				error:(request)=>{
					layer.confirm('您还没有登录，请先登录', {
						btn: ['去登录','稍后登录'] //按钮
					}, function(){
						location.href="/login";
					}, function(){
						layer.closeAll("confirm");
					});
				}
			});
		}
	})
	//支付
	$("body").on("click",".btns a",function(){
		if($(".categ p").hasClass("on")){
			if($(this).children().hasClass("buy")){
				//查看是否该用户有地址
				$.ajax({
					url:'/address/isByUidAddress',
					type:'post',
					dataType:'json',
					success:(data)=>{
						if (data.code===200){
							var count=$(".num span.fl").text();
							location.href="/order/info?small_id="+small_id+"&count="+count+"";
						}else{
							layer.confirm('抱歉，您还没有收货地址，请先添加收货地址', {
								btn: ['去添加','稍后添加'] //按钮
							}, function(){
								location.href="/address/selectAll";
							}, function(){
								layer.closeAll("confirm");
							});
						}
					},
					error:(request)=>{
						layer.confirm('您还没有登录，请先登录', {
							btn: ['去登录','稍后登录'] //按钮
						}, function(){
							location.href="/login";
						}, function(){
							layer.closeAll("confirm");
						});
					}
				});
			}
			$(".proIntro").css("border","none");
			$(".num .please").hide();
		}else{
			$(".proIntro").css("border","1px solid #c10000");
			$(".num .please").show();
		}
	})

	$('.cancel').click(function(){
		$(".mask").hide();
		$(".tipDel").hide();
	})

})
