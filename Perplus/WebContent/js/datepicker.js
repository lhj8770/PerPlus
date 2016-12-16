

$(function() {
	var datepicker = $('#datepicker1,#datepicker2,#datepicker3').datepicker({
		format : 'yyyy-mm-dd'
	}).on('focus', function(ev) {

		$('#datepicker1,#datepicker2,#datepicker3').val("");

	}).on('changeDate', function(ev) {
		
		$("#datepicker1,#datepicker2,#datepicker3").datepicker("hide");
		
	}).data('datepicker');
	
		datepicker.dates = {
			days: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"],
			daysShort: ["일", "월", "화", "수", "목", "금", "토", "일"],
			daysMin: ["일", "월", "화", "수", "목", "금", "토", "일"],
			months: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
			monthsShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]
		};	
	
		
	
		
});		




		
