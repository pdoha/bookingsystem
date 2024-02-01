window.addEventListener("DOMContentLoaded", function(){
    /* 헌혈의 집 지도 노출 처리 S */
    const {map} = commonLib;
    const addressE1 = document.querySelector("input[name='address']");
    if(addressE1 && addressE1.value.trim()){

        map.load("center_map", 500, 400, {
            zoom: 5,
            address: addressE1.value.trim(),
            title: addressE1.dataset.cName,
            selectable: false
        });
    }
    /* 헌혈의 집 지도 노출 처리 E */
});