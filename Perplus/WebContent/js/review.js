$(function(){
	$("#addImg").on("click", function(){
		$(this).siblings("ul").append(
				$("<li/>").append(
						$("<input/>").attr({name:"imgs[]", type:"file"}).addClass("imgfile")
				)
		)
	})
})