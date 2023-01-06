function openHamburgerMenu() {
    var x = document.getElementById("navMenu");
    var r = document.getElementById("icn");
    if (x.style.display === "block") {
        x.style.display = "none";
        r.className = "fa fa-bars";
    } else {
        x.style.display = "block";
        r.className += " fa-spin";
    }
}

/*
window.onresize = function(){
location.reload();
}
*/
