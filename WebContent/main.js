
var pictureIndex = 1;

window.onload = function loadPictures() {
    pictureIndex = 3;
    var path = document.getElementsByClassName('listing')[0].id;
    var carousel = document.getElementById("pictureCarousel");
    for (var i = 1; i <= 3; ++i) {
        var newDiv = document.createElement("div");
        newDiv.className = "picture-showcase";
        var newImg = document.createElement("img");
        newImg.src = path.substring(0,path.length-6) + "-" + i + ".jpg";
        newDiv.appendChild(newImg);
        pictureCarousel.appendChild(newDiv);
    }
    showPicture(pictureIndex);
}

function showPicture(direction) {
    pictureIndex += direction;
    var pictures = document.getElementsByClassName("picture-showcase");
    if (pictureIndex > pictures.length) {
        pictureIndex = 1
    }
    if (pictureIndex < 1) {
        pictureIndex = pictures.length
    }
    for (i = 0; i < pictures.length; ++i) {
        if (i != [pictureIndex - 1]) {
            pictures[i].style.display = "none";
        }
        else {
            pictures[pictureIndex - 1].style.display = "block";
        }
    }
}

function checkForm() {
    document.getElementById("error").innerHTML = "";
    
    var fname = document.getElementById("fname").value;
    var lname = document.getElementById("lname").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var street = document.getElementById("street").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").value;
    var postalCode = document.getElementById("postalCode").value;
    var shippingMethod = document.getElementById("shippingMethod").value;
    var ccnumber = document.getElementById("ccnumber").value;
    var ccv = document.getElementById("ccv").value;
    var errors = [];

    for (e of document.getElementById("myForm")) {
        if (e.value == "" && e.id != "submit" && e.id!="total") {
            errors.push(" " + e.id + " cannot be empty");
        }
    }
    if (errors.length > 0) {
        document.getElementById("error").innerHTML = errors;
        return false;
    }
    if (isNaN(phoneNumber) || !(phoneNumber.length == 10 || phoneNumber.length == 11)) {
        errors.push(" Invalid Phone Number");
    }
    if (isNaN(ccnumber) || ccnumber.length != 16) {
        errors.push(" Invalid Credit Card Number");
    }
    if (isNaN(ccv) || !(ccv.length == 3 || ccv.length == 4)) {
        errors.push(" Invalid CCV");
    }
    if (isNaN(postalCode) || postalCode.length != 5) {
        errors.push(" Invalid Postal Code.")
    }
    if (errors.length > 0) {
        document.getElementById("error").innerHTML = errors;
        return false;
    }
}