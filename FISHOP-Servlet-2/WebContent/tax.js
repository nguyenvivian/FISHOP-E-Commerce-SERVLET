function calcTax(ZipCode, price) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var obj = JSON.parse(this.response);
            document.getElementById("total").innerHTML = ((parseFloat(obj.CombinedRate) + 1) * price).toFixed(2);
            document.getElementById("taxrate").innerHTML = obj.CombinedRate;
            document.getElementById("state").value = obj.State;
        }
    };

    xhttp.open("GET", "get-tax?zip=" + ZipCode, true);
    xhttp.send();
}

function addShipping(shipping) {
    var xhttp = new XMLHttpRequest();
    var x = document.getElementById("total").innerHTML;
	shipping = shipping.slice(-2)    
	
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("total").innerHTML = (parseFloat(x) + parseFloat(shipping)).toFixed(2);
        }
    }
	
    console.log(shipping);
    xhttp.open("GET", "add-shipping?ship=" + shipping.slice(-2), true);
    xhttp.send();
}