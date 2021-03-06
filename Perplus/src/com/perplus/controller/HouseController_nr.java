//package com.perplus.controller;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.perplus.house.service.HouseService_nr;
//import com.perplus.house.vo.CheckListVo;
//import com.perplus.house.vo.HousePictureVo;
//import com.perplus.house.vo.HouseVo;
//import com.perplus.house.vo.ShutdownVo;
//import com.perplus.member.service.MemberService;
//import com.perplus.member.vo.HouseCommentVo;
//import com.perplus.member.vo.HouseZzimVo;
//import com.perplus.member.vo.MemberVo;
//import com.perplus.util.TextUtil;
//
//
//@Controller
//@RequestMapping("house")
//public class HouseController_nr {
//	
//	@Autowired
//	private HouseService_nr service;
//	@Autowired
//	private MemberService memberService;
//
//	/******************하우스 상세 페이지 조회*****************/
//	@RequestMapping("/houseDetail")
//	public String getHouse(@RequestParam int houseSerial, ModelMap map,@RequestParam(defaultValue="1") int page,HttpServletRequest request,HttpSession session){
//		String houseRating = request.getParameter("houseRating");
//		HouseVo house = service.selectHouseForDetailPage(houseSerial);
//		List<HousePictureVo> picture= service.selectHousePictureForDetailPage(houseSerial);
//		Map<String,Object> comment = memberService.selectHouseCommentBySerial(houseSerial,page);
//		if(houseRating!=null){
//			int rating = Integer.parseInt(houseRating);
//			house.setHouseRating(rating);
//			service.modifyHouse(house);
//		}
//		MemberVo member = (MemberVo)session.getAttribute("login_info");
//		if(member!=null){
//			HouseZzimVo zzim = memberService.selectHouseZzimByEmailAndHouseSerial(member.getMemberEmail(),houseSerial);
//			if(zzim != null){
//				map.put("zzim", zzim);
//			}
//		}
//		String shutdownDate = null;
//		if(house.getHouseFilter().getCheckList()!=null){
//			List<ShutdownVo> checkList = house.getHouseFilter().getShutdownList();
//			for(int i = 0 ; i<checkList.size();i++){
//				Date date = checkList.get(i).getShutdownDate();
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				if(i==0){
//					shutdownDate = "'"+sdf.format(date)+"'";
//				}else{
//					shutdownDate = shutdownDate +", "+ "'"+sdf.format(date)+"'";
//				}
//			}
//			map.put("shutdownDate", shutdownDate);
//		}
//		
//		
//		System.out.println(shutdownDate);
//		map.put("house", house);
//		map.put("picture",picture);
//		map.put("comment", comment);
//
//		return "housedetailspage.housetiles";
//	}
//	
//	/******************하우스 상세 페이지 삭제*****************/
//	@RequestMapping("/removeHouse")
//	public String removeHouse(@RequestParam int houseSerial){
//		System.out.println(houseSerial);
//		service.removeHouse(houseSerial);
//		return "redirect:/main.do";
//	}
//	/******************하우스 코멘트 등록*****************/
//	@RequestMapping("/registerHouseComment")
//	public String registerHouseComment(@ModelAttribute HouseCommentVo houseComment,ModelMap map){
//		houseComment.setCommentTime(new Date());
//		houseComment.setCommentSerial(1);
//		houseComment.setCommentContent(TextUtil.textToHtml(houseComment.getCommentContent()));
//		int houseRating = memberService.insertHouseComment(houseComment);
//		
//		return "redirect:/house/houseDetail.do?houseSerial="+houseComment.getHouseSerial()+"&houseRating="+houseRating;
//	}
//	/******************하우스 코멘트 삭제*****************/
//	@RequestMapping("/removeHouseComment")
//	public String removeHouseComment(@RequestParam int commentSerial,@RequestParam int houseSerial){
//		System.out.println(commentSerial);
//		int houseRating = memberService.deleteHouseComment(commentSerial);	
//		return "redirect:/house/houseDetail.do?houseSerial="+houseSerial+"&houseRating="+houseRating;
//	}
//
//	/************하우스 목록 조회**********************/
//	@RequestMapping("/myHouse")
//	public String getMyHouse(@RequestParam String memberEmail,ModelMap map){
//		List<HouseVo> houseList = service.selectMyHouse(memberEmail);
//		System.out.println(houseList.get(3));
//		map.put("houseList", houseList);
//		return "rooms/rooms/roomlist.tiles1";
//	}
//}	
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
