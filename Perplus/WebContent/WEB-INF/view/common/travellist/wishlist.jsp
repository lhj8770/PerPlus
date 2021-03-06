<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>



<div id="dashboard-content">
	<div class="panel-header">
		<span>위시 리스트</span>
	</div>
	<div class="panel-body" style="min-height:300px;">
		<div class="row nowtravel">
			<div class="col-md-12">
				<div class="slidebar">
					<div class="carousel slide" id="myCarousel">
						<div class="carousel-inner">
							<c:choose>
								<c:when test="${!empty requestScope.houseZzim}">
									<c:forEach items="${requestScope.houseZzim}" var="houseZzim">
										<c:choose>
											<c:when test="${requestScope.houseZzim[0] == houseZzim}">
												<div class="active item">
													<div
														class="maincon con1 col-md-offset-4 col-md-4
								           col-sm-offset-4 col-sm-4 col-xs-offset-3 col-xs-6"
														style="background-color: #ccc;">
														<div class="col-md-12">
															<div class="row con1">
																<div class="row con1">
																	<div class="col-md-12" style="padding: 0px;">
																		<c:choose>
																			<c:when
																				test="${houseZzim.house.housePicture[0].pictureName != null}">
																				<a href="${initParam.rootPath}/house/houseDetail.do?houseSerial=${houseZzim.house.houseSerial}"><img
																					src="${initParam.rootPath}/housePicture/${houseZzim.house.housePicture[0].pictureName}"
																					alt="" style="width: 100%; height: 200px" />
																				</a>
																			</c:when>
																			<c:otherwise>
																				<img
																					src="${initParam.rootPath}/memberPicture/no-photo.png"
																					alt="" style="width: 100%; height: 200px" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</div>

																<div class="row subcon1" style="border: 1px solid #ccc;">
																	<div class="row con2">
																		<div class="col-md-12">
																			<span class="col-md-12"
																				style="text-align: center; padding: 5px;"></span>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 이름 : ${houseZzim.house.houseTitle} </span>
																			</div>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 장소 :
																					${houseZzim.house.houseFilter.houseFilterLocation}
																				</span>
																			</div>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 평점 : ${houseZzim.house.houseRating} </span>
																			</div>
																		</div>
																	</div>

																</div>
															</div>
														</div>
													</div>
												</div>
											</c:when>

											<c:otherwise>
												<div class="item">
													<div
														class="maincon con1 col-md-offset-4 col-md-4
								           col-sm-offset-4 col-sm-4 col-xs-offset-3 col-xs-6"
														style="background-color: #ccc;">
														<div class="col-md-12">
															<div class="row con1">
																<div class="row con1">
																	<div class="col-md-12" style="padding: 0px;">
																		<c:choose>
																			<c:when
																				test="${houseZzim.house.housePicture[0].pictureName != null}">
																				<img
																					src="${initParam.rootPath}/memberPicture/no-photo.png"
																					alt="없엉!!" style="width: 100%; height: 200px" />
																			</c:when>
																			<c:otherwise>
																				<img
																					src="${initParam.rootPath}/memberPicture/no-photo.png"
																					alt="" style="width: 100%; height: 200px" />
																			</c:otherwise>
																		</c:choose>
																	</div>
																</div>

																<div class="row subcon1" style="border: 1px solid #ccc;">
																	<div class="row con2">
																		<div class="col-md-12">
																			<span class="col-md-12"
																				style="text-align: center; padding: 5px;"></span>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 이름 : ${houseZzim.house.houseTitle} </span>
																			</div>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 장소 :
																					${houseZzim.house.houseFilter.houseFilterLocation}
																				</span>
																			</div>
																		</div>
																	</div>

																	<div class="row con3">
																		<div class="col-md-12">
																			<div class="section3"
																				style="border-top: 2px solid #ccc;">
																				<span class="col-md-12"
																					style="text-align: center; padding: 5px;">
																					숙소 평점 : ${houseZzim.house.houseRating} </span>
																			</div>
																		</div>
																	</div>

																</div>
															</div>
														</div>
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<div class="active item">없을때 뭐 보여줄지정해야됨.</div>
								</c:otherwise>
							</c:choose>
						</div>
						<c:if test="${!empty requestScope.houseZzim}">
							<!--  left right button -->
							<a class="left carousel-control" href="#myCarousel" role="button"
								data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left"></span>
							</a>
							<a class="right carousel-control" href="#myCarousel"
								role="button" data-slide="next"> <span
								class="glyphicon glyphicon-chevron-right"></span>
							</a>
						</c:if>
					</div>
				</div>
				<!--/Slider-->
			</div>
		</div>
	</div>
</div>