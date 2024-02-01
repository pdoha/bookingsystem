var commonLib = commonLib || {};

commonLib.area = {
    getSigugun(sido) {
        const { ajaxLoad } = commonLib;
        return new Promise((resolve, reject) => {
            ajaxLoad("GET", `/api/area/sigugun/${sido}`, null, 'json')
                .then(res => resolve(res))
                .catch(err => reject(err));
        });
    }
};

window.addEventListener("DOMContentLoaded", function() {
    const selectAreas = document.getElementsByClassName("select_area");
    const { getSigugun } = commonLib.area;
    for (const el of selectAreas) {
        el.addEventListener("change", function() {
            const sido = this.value;
            const targetId = this.dataset.targetId;
            const targetEl = document.getElementById(targetId);
            targetEl.innerHTML = '<option value="">시/구/군</option>';
            getSigugun(sido)
                .then(items => {
                    for (const item of items) {
                        const option = document.createElement("option");
                        const optionText = document.createTextNode(item);
                        option.value = item;
                        option.appendChild(optionText);

                        targetEl.appendChild(option);

                    }
                })
                .catch (err => console.error(err));

        });
    }
});