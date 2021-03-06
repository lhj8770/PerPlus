<%@ page contentType="text/html;charset=UTF-8"%>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWDGjKV2YFKGM5q6gtx-J5GcJTa2wLDQU"
	type="text/javascript"></script>
<script src="/Perplus/js/map.js"></script>
<script type="text/javascript">
	function getMarkerLocation() {
		var url = "/Perplus/house/threeStep.do?houseSerial=${requestScope.houseSerial}&lat="
				+ markerArray[0].getPosition().lat().toString()
				+ "&lng="
				+ markerArray[0].getPosition().lng().toString();

		//다음 url로 링크
		location.href = url;
	}
</script>
<div class="basicheader">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-3 col-xs-3"></div>

			<div class="col-md-offset-5 col-md-3 col-xs-offset-5">
				<div class="col-md-8 col-xs-8 page_moveBtn">
					<div class="form-group ">
						<select class="form-control house_step">
							<option value="${initParam.rootPath}/house/oneStepBefore.do">1
								단계</option>
							<option
								value="${initParam.rootPath}/house/twoStepBefore.do?houseSerial=${param.houseSerial}">2
								단계</option>
							<option selected
								value="${initParam.rootPath}/house/threeStepBefore.do?houseSerial=${param.houseSerial}">3
								단계</option>
							<option
								value="${initParam.rootPath}/house/fourStepBefore.do?houseSerial=${param.houseSerial}">4
								단계</option>
							<option
								value="${initParam.rootPath}/house/fiveStepBefore.do?houseSerial=${param.houseSerial}">5
								단계</option>
							<option
								value="${initParam.rootPath}/house/sixStepBefore.do?houseSerial=${param.houseSerial}">6
								단계</option>
							<option
								value="${initParam.rootPath}/house/sevenStepBefore.do?houseSerial=${param.houseSerial}">7
								단계</option>
							<option
								value="${initParam.rootPath}/house/eightStepBefore.do?houseSerial=${param.houseSerial}">8
								단계</option>
						</select>
					</div>
				</div>
				<div class="col-md-4 page_moveBtn hidden-xs hidden-sm "
					style="margin-top: 15px;">
					<span>(3/8)</span>
				</div>
			</div>
		</div>
	</div>
</div>
<a class="navbar-img" href="${initParam.rootPath}/main.do"> <img
	src="/Perplus/css/image/logos.PNG" alt="logo" />
</a>

<!-- body -->
<div class="basicbody">
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8 housesection">

				<div class="row row-condensed space-4" style="margin-bottom: 40px;">
					<div class="col-md-offset-2 col-md-8">
						<div class="locationspan">
							<span>숙소 의 위치를 알려주세요</span>
						</div>
						<div class="locationspan">
							<span> ${requestScope.location}</span>
							<input type="hidden" id="stage" value="register"> <input
								type="hidden" id="location" value="${requestScope.location}">
							<!-- 예시... 위로 바꾸세요 -->
							<!--<input type="hidden" id="location" value="경기도 용인시 수지구"> -->
						</div>
						<div id="map-canvas" style="width: 100%; height: 400px"></div>
					</div>
				</div>
			</div>
		</div>

		<div class="row row-condensed">
			<div
				class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-1 col-sm-1 col-xs-1 previousBtn">
				<a href="${initParam.rootPath}/housetypeandlocation.do">
					<button type="button" class="btn btn-default">이전</button>
				</a>
			</div>
			<div
				class="col-md-offset-6 col-sm-offset-6 col-xs-offset-4 col-md-1 col-sm-1 col-xs-1 nextBtn leftform">
				<button class="btn btn-default" onclick="getMarkerLocation()">다음</button>
			</div>
		</div>
	</div>
</div>



