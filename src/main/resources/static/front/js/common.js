window.addEventListener("DOMContentLoaded", function() {

    // 체크박스 모두 선택하기
    const checkAlls = document.getElementsByClassName("checkAll");
    for(const el of checkAlls){
        el.addEventListener("click", function(){
            const targetName = el.dataset.targetName;
            const chks = document.getElementsByName(targetName);

            for(const el of chks) {
                el.checked = this.checked;
            }
        });
    }

    // Get the button:
    let mybutton = document.getElementById("myBtn");

    // When the user scrolls down 20px from the top of the document, show the button
    window.onscroll = function() {scrollFunction()};
});

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}