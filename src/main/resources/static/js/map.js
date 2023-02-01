window.onload = async () => {
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(36.6330082963269, 128.02220498426198), //지도의 중심좌표.
		level: 7 //지도의 레벨(확대, 축소 정도)
	};
	
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	
	let hospitals = await getHospitals();
	hospitals.map((hospital, _) => {
		var coords = new kakao.maps.LatLng(hospital.y, hospital.x);
		
		// 결과값으로 받은 위치를 마커로 표시합니다
        var marker = new kakao.maps.Marker({
            map: map,
            position: coords
        });
        
        
        var content = `<div class="wrap"> 
                <div class="info"> 
                    <div class="title"> 
                        ${hospital.name}
                        <div class="close" id="closeBtn" title="닫기"></div> 
                    </div>
                    <div class="body"> 
                        <div class="img">
                            <img src="" width="73" height="70">
                       </div>
                        <div class="desc"> 
                            <div class="ellipsis">${hospital.address}</div> 
                            <div class="ellipsis">${hospital.tel}</div> 
                        </div>
                    </div>
                </div>   
            </div>`;
        
	    // 마커 위에 커스텀오버레이를 표시합니다
		// 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
		var overlay = new kakao.maps.CustomOverlay({
		    content: content,
		    position: marker.getPosition(),
		    xAnchor: 0.5,
    		yAnchor: 0.1
		});
		
		// 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
		kakao.maps.event.addListener(marker, 'click', function() {
		    if(currOverLay) currOverLay.setMap(null);
		    
		    currOverLay = overlay;
		    overlay.setMap(map);
		    
		    // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다
			$('#closeBtn').on('click', () => {
				currOverLay.setMap(null);	
			});	
		    
		});
	});
	
	// 지도가 확대 또는 축소되면 마지막 파라미터로 넘어온 함수를 호출하도록 이벤트를 등록합니다
//	kakao.maps.event.addListener(map, 'zoom_changed', function() {        
//	    
//	    // 지도의 현재 레벨을 얻어옵니다
//	    var level = map.getLevel();
//	    
//	    var message = '현재 지도 레벨은 ' + level + ' 입니다';
//	    console.log(message);
//	});
	
	
	// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
	if (navigator.geolocation) {
	    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
	    navigator.geolocation.getCurrentPosition(function(position) {
	        let lat = position.coords.latitude; // 위도
	        let lon = position.coords.longitude; // 경도
	        
	        let locPosition = new kakao.maps.LatLng(lat, lon); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
	        map.setCenter(locPosition);
	      });
	    
	} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
	}
	
};

let currOverLay = null;

let getHospitals = async () => {
	let res = await fetch(`http://localhost:8080/api/hospital?size=10000`);
	return await res.json();
};