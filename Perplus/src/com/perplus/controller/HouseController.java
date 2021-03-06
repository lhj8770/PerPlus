package com.perplus.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.perplus.house.service.HouseService;
import com.perplus.house.vo.CheckListVo;
import com.perplus.house.vo.CodetableVo;
import com.perplus.house.vo.HouseFilterVo;
import com.perplus.house.vo.HousePictureVo;
import com.perplus.house.vo.HouseVo;
import com.perplus.house.vo.ShutdownVo;
import com.perplus.member.service.MemberService;
import com.perplus.member.vo.HouseCommentVo;
import com.perplus.member.vo.HouseZzimVo;
import com.perplus.member.vo.MemberVo;
import com.perplus.util.Constants;
import com.perplus.util.TextUtil;
@Controller
@RequestMapping("/house")
public class HouseController {
	
	@Autowired
	private HouseService service;
	@Autowired
	private MemberService memberService;

	/****************** 하우스 상세 페이지 조회 *****************/
	@RequestMapping("/houseDetail")
	public String getHouse(@RequestParam int houseSerial, ModelMap map, @RequestParam(defaultValue = "1") int page,
			HttpServletRequest request, HttpSession session) {
		String houseRating = request.getParameter("houseRating");
		HouseVo house = service.selectHouseForDetailPage(houseSerial);
		List<HousePictureVo> picture = service.selectHousePictureForDetailPage(houseSerial);
		Map<String, Object> comment = memberService.selectHouseCommentBySerial(houseSerial, page);
		if (houseRating != null) {
			int rating = Integer.parseInt(houseRating);
			house.setHouseRating(rating);
			service.modifyHouse(house);
		}
		MemberVo member = (MemberVo) session.getAttribute("login_info");
		if (member != null) {
			HouseZzimVo zzim = memberService.selectHouseZzimByEmailAndHouseSerial(member.getMemberEmail(), houseSerial);
			if (zzim != null) {
				map.put("zzim", zzim);
			}
		}
		String shutdownDate = null;
		if (house.getHouseFilter()!=null&&house.getHouseFilter().getCheckList() != null) {
			List<ShutdownVo> checkList = house.getHouseFilter().getShutdownList();
			for (int i = 0; i < checkList.size(); i++) {
				Date date = checkList.get(i).getShutdownDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if (i == 0) {
					shutdownDate = "'" + sdf.format(date) + "'";
				} else {
					shutdownDate = shutdownDate + ", " + "'" + sdf.format(date) + "'";
				}
			}
			map.put("shutdownDate", shutdownDate);
		}

		map.put("house", house);
		map.put("picture", picture);
		map.put("comment", comment);

		return "housedetailspage.housetiles";
	}

	/****************** 하우스 상세 페이지 삭제 *****************/
	@RequestMapping("/removeHouse")
	public String removeHouse(@RequestParam int houseSerial) {
		service.removeHouse(houseSerial);
		return "redirect:/main.do";
	}

	/****************** 하우스 코멘트 등록 *****************/
	@RequestMapping("/registerHouseComment")
	public String registerHouseComment(@ModelAttribute HouseCommentVo houseComment, ModelMap map) {
		houseComment.setCommentTime(new Date());
		houseComment.setCommentSerial(1);
		houseComment.setCommentContent(TextUtil.textToHtml(houseComment.getCommentContent()));
		int houseRating = memberService.insertHouseComment(houseComment);

		return "redirect:/house/houseDetail.do?houseSerial=" + houseComment.getHouseSerial() + "&houseRating="
				+ houseRating;
	}

	/****************** 하우스 코멘트 삭제 *****************/
	@RequestMapping("/removeHouseComment")
	public String removeHouseComment(@RequestParam int commentSerial, @RequestParam int houseSerial) {
		int houseRating = memberService.deleteHouseComment(commentSerial);
		return "redirect:/house/houseDetail.do?houseSerial=" + houseSerial + "&houseRating=" + houseRating;
	}

	/************ 하우스 목록 조회 **********************/
	@RequestMapping("/myHouse")
	public String getMyHouse(@RequestParam String memberEmail, ModelMap map) {
		List<HouseVo> houseList = service.selectMyHouse(memberEmail);
		map.put("houseList", houseList);
		return "rooms/rooms/roomlist.tiles1";
	}
	
	
	
	
	/*
	
		**********************하우스 등록***************************
	
	*/
	@RequestMapping("/oneStepBefore.do")//1step 들어가기전에 필요한 정보들 조회 컨트롤러.
	public String oneStepBefore(){
		return "forward:/basicinfo.do";
	}
	
	@RequestMapping("/oneStep.do")//1step commit 하는 컨트롤러
	public String oneStepHouseRegister(@ModelAttribute HouseVo houseVo, HttpServletRequest request){
		String houseNecessaryCondition = null;
		String[] houseNecessaryConditionList = (String[])request.getParameterValues("houseNecessaryConditionList");
		if(houseNecessaryConditionList!=null){
			for(int i = 0; i<houseNecessaryConditionList.length;i++){
				if(i==0){
					houseNecessaryCondition = houseNecessaryConditionList[i];
				}else{
					houseNecessaryCondition = houseNecessaryCondition + ", "+houseNecessaryConditionList[i];
				}
			}
		}else{
			houseNecessaryCondition = "";			
		}
		houseVo.setHouseNecessaryCondition(houseNecessaryCondition);
		service.insertHouse(houseVo);
		return "redirect:/house/twoStepBefore.do?houseSerial="+houseVo.getHouseSerial();
	}
	
	
	@RequestMapping("/twoStepBefore.do")//2step 들어가기전 조회하는 컨트롤러.
	public String twoStepBefore(@RequestParam int houseSerial){
		return "redirect:/housetypeandlocation.do?houseSerial="+houseSerial;
	}
	
	@RequestMapping("/twoStep.do")//2step commit하는 컨트롤러
	public String twoStepHouseRegister(@ModelAttribute HouseFilterVo houseFilterVo,HttpServletRequest request){
		String[] houseFilterLocationList = (String[])request.getParameterValues("houseFilterLocationList");
		String houseFilterLocation = "";
		for(int i = 0; i<houseFilterLocationList.length;i++){
			if(i==0){
				houseFilterLocation = houseFilterLocationList[i];
			}else{
				houseFilterLocation = houseFilterLocation +",  "+houseFilterLocationList[i];
			}
		}
		houseFilterVo.setHouseFilterLocation(houseFilterLocation);
		service.insertHouseFilter(houseFilterVo);
		return "redirect:/house/threeStepBefore.do?houseSerial="+houseFilterVo.getHouseSerial();
	}
	
	
	@RequestMapping("/threeStepBefore.do")//3step 들어가기전에 조회하는 것들...
	public String threeStepBefore(@RequestParam int houseSerial, HttpServletRequest request){
		HouseFilterVo houseFilter = service.selectHouseFilter(houseSerial);
		String location = houseFilter.getHouseFilterLocation().split(",  ")[1];
		request.setAttribute("location", location);
		request.setAttribute("houseSerial", houseSerial);
		return "forward:/houselocation.do";
	}
	
	
	@RequestMapping("/threeStep.do")//3step commit하는 컨트롤러
	public String threeStepHouseRegister(@RequestParam int houseSerial, @RequestParam String lat, @RequestParam String lng){
		HouseVo house = service.selectHouseByHouseSerial(houseSerial);
		
		double houseMarkerX = Double.parseDouble(lat);
		double houseMarkerY = Double.parseDouble(lng);
		house.setHouseMarkerX(houseMarkerX);
		house.setHouseMarkerY(houseMarkerY);
		house.setHouseMarkerConstant(Constants.MAKRER_CONSTANT_HOUSE);
		service.modifyHouse(house);
		
		return "redirect:/house/fourStepBefore.do?houseSerial="+houseSerial;
	}
	
	@RequestMapping("/fourStepBefore.do")//4step 들어가기전에 조회하는 것들...
	public String fourStepBefore(@RequestParam int houseSerial, HttpServletRequest request){
		List<CodetableVo> convenientFacility = service.codetableFindByKind(201);//편의시설
		List<CodetableVo> secureFacility = service.codetableFindByKind(202);//안전시설
		List<CodetableVo> commonFacility = service.codetableFindByKind(203);//공용시설
		request.setAttribute("convenientFacility", convenientFacility);
		request.setAttribute("secureFacility", secureFacility);
		request.setAttribute("commonFacility", commonFacility);
		return "forward:/housefacility.do?houseSerial="+houseSerial;
	}
	
	
	
	
	
	
	
	@RequestMapping("/fourStep.do")//4step commit하는 컨트롤러
	public String fourStepHouseRegister(@RequestParam int houseSerial, HttpServletRequest request, @RequestParam int houseFilterBedroomNumber,@RequestParam int houseFilterBedNumber,@RequestParam int houseFilterBathroomNumber){
		String[] convenientFacilityList = request.getParameterValues("convenientFacility");//편의시설
		String[] secureFacilityList = request.getParameterValues("secureFacility");//안전시설
		String[] commonFacilityList = request.getParameterValues("commonFacility");//공용시설
		
		HouseFilterVo houseFilter = service.selectHouseFilter(houseSerial);
		
		houseFilter.setHouseFilterBedroomNumber(houseFilterBedroomNumber);
		houseFilter.setHouseFilterBedNumber(houseFilterBedNumber);
		houseFilter.setHouseFilterBathroomNumber(houseFilterBathroomNumber);
		
		
		if(convenientFacilityList!=null&&convenientFacilityList.length!=0){
			for(int i = 0; i<convenientFacilityList.length;i++){
				service.insertChecklist(new CheckListVo(0, houseSerial, Constants.CODE_KIND_CHECKLIST_COMFORT_FACILITY, convenientFacilityList[i]));
			}
		}
		if(secureFacilityList!=null&&secureFacilityList.length!=0){
			for(int i = 0; i<secureFacilityList.length;i++){
				service.insertChecklist(new CheckListVo(0, houseSerial, Constants.CODE_KIND_CHECKLIST_SAFE_FACILITY, secureFacilityList[i]));
			}
		}
		if(commonFacilityList!=null&&commonFacilityList.length!=0){
			for(int i = 0; i<commonFacilityList.length;i++){
				service.insertChecklist(new CheckListVo(0, houseSerial, Constants.CODE_KIND_CHECKLIST_COMMON_FACILITY, commonFacilityList[i]));
			}
		}
		
		
		service.updateHouseFilter(houseFilter);
		return "redirect:/house/fiveStepBefore.do?houseSerial="+houseSerial;
	}
	
	
	@RequestMapping("/fiveStepBefore.do")//5step 들어가기전에 조회하는 것들...
	public String fiveStepBefore(@RequestParam int houseSerial){
		return "redirect:/houseimageenrollment.do?houseSerial="+houseSerial;
	}
	
	
	
	
	
	
	
	@RequestMapping("/fiveStep.do")//5step commit하는 컨트롤러 이미지 등록 극혐!!!!!!!!!!!!!!!!!!!!!!!!
	public String fiveStepHouseRegister(@RequestParam int houseSerial, @RequestParam(value="houseMainPicture") MultipartFile houseMainPicture ,@RequestParam(value="houseSubPicture[]") MultipartFile[] houseSubPictures, HttpServletRequest request) throws IllegalStateException, IOException{
		//houseMainPicture 메인픽쳐
		//houseSubPictures 서브픽쳐
		
		if(houseMainPicture!=null && !houseMainPicture.isEmpty()){
			//파일 uploadPhoto로 옮기기
			String fileName = UUID.randomUUID().toString().replaceAll("-","");
			File picture = new File(request.getServletContext().getRealPath("/housePicture"), fileName);
			houseMainPicture.transferTo(picture);
			HousePictureVo housePicture = new HousePictureVo(0, houseSerial, 1, fileName);
			service.insertHousePicture(housePicture);
		}
		
		
		
		int orderNumber = 2;
		if(houseSubPictures != null){
			String saveDir = request.getServletContext().getRealPath("/housePicture");
			for(int i = 0; i<houseSubPictures.length;i++){
				MultipartFile file = (MultipartFile)houseSubPictures[i];
				if(!file.isEmpty()){
					
					String fileName = UUID.randomUUID().toString().replace("-", "");//UUID를 이용해 파일명 생성(- 제거)
					File dest = new File(saveDir,fileName);
					file.transferTo(dest);
					
					service.insertHousePicture(new HousePictureVo(0, houseSerial, orderNumber, fileName));
					orderNumber++;
				}
			}
		}
		
		
		return "redirect:/house/sixStepBefore.do?houseSerial="+houseSerial;
	}
	
	
	@RequestMapping("/sixStepBefore.do")//6step 들어가기전에 조회하는 것들...
	public String sixStepBefore(@RequestParam int houseSerial){
		return "redirect:/houseschedulemanagement.do?houseSerial="+houseSerial;
	}
	
	
	
	
	
	
	@RequestMapping("/sixStep.do")//6step commit하는 컨트롤러
	public String sixStepHouseRegister(@RequestParam int houseSerial, HttpServletRequest request,
			@RequestParam int houseFilterCheckinTerm,@RequestParam int houseFilterCheckinStart,@RequestParam int houseFilterCheckinEnd,
			@RequestParam int houseFilterReservationTerm,@RequestParam int houseFilterBakMin,@RequestParam int houseFilterBakMax
			){
		
		HouseFilterVo houseFilter = service.selectHouseFilter(houseSerial);
		
		houseFilter.setHouseFilterCheckinTerm(houseFilterCheckinTerm);
		houseFilter.setHouseFilterCheckinStart(houseFilterCheckinStart);
		houseFilter.setHouseFilterCheckinEnd(houseFilterCheckinEnd);
		houseFilter.setHouseFilterReservationTerm(houseFilterReservationTerm);
		houseFilter.setHouseFilterBakMin(houseFilterBakMin);
		houseFilter.setHouseFilterBakMax(houseFilterBakMax);
		
		service.updateHouseFilter(houseFilter);
		
		return "redirect:/house/sevenStepBefore.do?houseSerial="+houseSerial;
	}
	
	
	@RequestMapping("/sevenStepBefore.do")//7step 들어가기전에 조회하는 것들...
	public String sevenStepBefore(@RequestParam int houseSerial){
		return "redirect:/houseshutdowndate.do?houseSerial="+houseSerial;
	}
	
	
	
	
	
	
	
	
	
	@RequestMapping("/sevenStep.do")//7step commit하는 컨트롤러
	public String sevenStepHouseRegister(@RequestParam int houseSerial, @RequestParam String shutdownDateList, HttpServletRequest request) throws ParseException{
		String[] shutdownDates = shutdownDateList.split(", ");
		
		Date shutdownDate = null;
		
		ShutdownVo shutdown = null;
		if(shutdownDates!=null&&shutdownDates.length!=0){
			for(int i = 0;i<shutdownDates.length;i++){
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
				shutdownDate = transFormat.parse(shutdownDates[i]);
				
				shutdown = new ShutdownVo(0, houseSerial, shutdownDate);
				service.insertShutdown(shutdown);
			}
		}
		return "redirect:/house/eightStepBefore.do?houseSerial="+houseSerial;
	}
	
	
	@RequestMapping("/eightStepBefore.do")//8step 들어가기전에 조회하는 것들...
	public String eightStepBefore(@RequestParam int houseSerial){
		return "redirect:/houseprice.do?houseSerial="+houseSerial;
	}

	
	@RequestMapping("/eightStep.do")//8step commit하는 컨트롤러
	public String eightStepHouseRegister(@RequestParam int houseSerial, @RequestParam int houseFilterPrice){
		HouseFilterVo houseFilter = service.selectHouseFilter(houseSerial);
		
		houseFilter.setHouseFilterPrice(houseFilterPrice);
		
		service.updateHouseFilter(houseFilter);
		
		return "redirect:/hostingcomplate.do?houseSerial="+houseSerial;
	}	
	
	/*
	
		********************하우스 등록 종료**********************
	
	*/
}
