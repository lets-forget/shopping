$(function(){
	/************商品筛选***************/
	$(".choice .default").click(function(){
		$(this).siblings("ul").toggle();
		$(this).toggleClass("on");
	});
	$(".choice .select li").click(function(){
		$(".choice .default").removeClass("on");
	});
	/*************鼠标悬浮*************/
	$(".proList li").on('mouseenter',function(){
		$(this).find("p").show();
		$(".quick").on('click',function(){

		});
		$(".btns .cart").click(function(){
			if($(".categ p").hasClass("on")){
				$(".proDets").hide();
				$(".mask").hide();
			}
		});
	});
	$(".proList li").on('mouseleave',function(){
		$(this).find("p").hide();
		$(this).css('border','1px solid #fff');
	});
	//关闭按钮
	$("#echoDetails").on("click",".off",function(){
		$(".mask").hide();
		$(".proDets").hide();
		$(".pleaseC").hide();
	});
/**********************************************共用*****************************************************/
/**********************商品提示************************************/

	$(".proIntro .smallImg p img").hover(function(){
		$(this).parents(".smallImg").find("span").remove();
		var lf = $(this).position().left;
		var con = $(this).attr("alt");
		$(this).parents(".smallImg").append('<span>'+con+'</span>');
		$(".smallImg").find("span").css("left",lf);
	},function(){
		$(this).parents(".smallImg").find("span").remove();
	});
	$("body").on('click','.proIntro .smallImg img',function(){
		var offset = $(this).attr("class");
		//小弹框和详情页图片大小不一样
		$(this).parents(".proCon").find('.proImg').children(".det").attr("src",offset).css({'width':'580px','height':'520px'});
		$(this).parents(".proCon").find('.proImg').children(".list").attr("src",offset).css({'width':'360px','height':'360px'});
		$(this).parents(".smallImg").find("span").remove();
		$(this).parent("p").addClass('on').siblings().removeClass('on');
		if ($(".proIntro").has("border")){
			$(".proIntro").css("border","none");
			$(".num .please").hide();
		}
		//移除鼠标离开事件
		$(this).off("mouseleave").parent('p').siblings().find('img').on('mouseleave',function(){
			$(this).parents(".smallImg").find("span").remove();
			$(this).parent("p").removeClass('on');
		})
	});

	/*************************小图切换大图*****************************/
	$("body").on("mouseover",".smallImg > img",function(){
		$(this).css({"border-left":"1px solid #c10000","border-right":"1px solid #c10000"}).siblings("img").css("border","none");
		var src = $(this).attr("class");
		$(this).parent().siblings(".det").attr("src",src).css({'width':'580px','height':'520px'});
		$(this).parent().siblings(".list").attr("src",src).css({'width':'360px','height':'360px'});
	})
	
	/**********proDetail tab***************/
	$(".msgTit a").click(function(){
		var index = $(this).index();
		$(this).addClass("on").siblings().removeClass("on");
		$(".msgAll").children("div").eq(index).show().siblings().hide();
	});
	
	/***********************order***************/
	$(".addre").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	})
	$(".way img").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	})
	$(".dis span").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
	});

	/************************ok************************/
	var seconds = $(".ok span").text();
	function time(){
		seconds--;
		$(".ok span").text(seconds);
		if(seconds==0){
			window.location.href=("/order/selectAll")
		}
	}
	setInterval(time,1000);

})
