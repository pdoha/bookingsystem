window.addEventListener("DOMContentLoaded", function() {
    const jsonData = document.querySelector("input[name='jsonData']").value;
    const items = jsonData ? JSON.parse(jsonData) : [];

    const tpl = document.getElementById("tpl").innerHTML;

    const positions = [];
    for (const item of items) {
        const { ccode, cname, telNum, address, operHour } = item;
        let html = tpl;
        html = html.replace(/\[cName\]/g, cname)
                    .replace(/\[telNum\]/g, telNum)
                    .replace(/\[address\]/g, address)
                    .replace(/\[operHour\]/g, operHour)
                    .replace(/\[url\]/g, `/center/${ccode}`);

        positions.push({
          content: html,
          address,
        });
    }
    const { map } = commonLib;
    map.load("center_map", "100%", 400, {
      positions,
    });
});