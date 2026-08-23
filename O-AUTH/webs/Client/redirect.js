let token = ""

let params = new URLSearchParams(window.location.search);

window.onload = function(){
    document.getElementById("check").style.visibility = "hidden"
    document.getElementById("input").style.visibility = "hidden"
    fetch("http://localhost:8080/getToken", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ 
            code: params.get("code"), 
            state: params.get("state"),
        })
    }).then(response => response.text())
    .then(data => {
        token = data
        document.getElementById("check").style.visibility = "visible"
        document.getElementById("input").style.visibility = "visible"
        document.getElementById("context").innerHTML = "Token fetched!"
        document.getElementById("token").innerHTML = "Token: " + token
    });
}

function getData(){
    let scope = document.getElementById("input").value
    fetch("http://localhost:8080/getData", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": token
        },
        body: JSON.stringify({ 
            scope: scope
        })
    }).then(response => response.text())
    .then(data => {
        document.getElementById("return").innerHTML = "Data: " + data
    });
}