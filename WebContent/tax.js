function calcTax(ZipCode, price) {
    var xhttp = new XMLHttpRequest();
    var total = price;
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var obj = JSON.parse(this.response);
            document.getElementById("total").value = ((parseFloat(obj.CombinedRate) + 1) * price).toFixed(2);
            document.getElementById("taxrate").value = obj.CombinedRate;
            document.getElementById("state").value = obj.State;
        }
    };

    xhttp.open("GET", "get-tax?zip=" + ZipCode+"&price="+total, true);
    xhttp.send();
}

function addShipping(shipping) {
    var xhttp = new XMLHttpRequest();
    var x = document.getElementById("total").value;

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("total").value = (parseFloat(x) + parseFloat(shipping.slice(-2)  )).toFixed(2);
        }
    }
	
    xhttp.open("GET", "add-shipping?ship=" + shipping.slice(-2), true);
    xhttp.send();
}