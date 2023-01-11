function openHamburgerMenu() {
    var x = document.getElementById("navMenu");
    var y = document.getElementById("navMenuLogged");
    var r = document.getElementById("icn");

    if (x != null) {

        if (x.style.display === "block") {
            x.style.display = "none";
            r.className = "fa fa-bars";
        } else {
            x.style.display = "block";
            r.className += " fa-spin";
        }
    } else {

        if (y.style.display === "block") {
            y.style.display = "none";
            r.className = "fa fa-bars";
        } else {
            y.style.display = "block";
            r.className += " fa-spin";
        }
    }
}

/*
window.onresize = function(){
location.reload();
}
*/
