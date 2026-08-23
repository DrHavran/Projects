let params = new URLSearchParams(window.location.search);

window.onload = function (){
    document.getElementById("check").style.visibility = "hidden"
    fetch("http://localhost:8000/allowedUsers", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({id: `${params.get("client_id")}`})
    }).then(res => res.json()).then(
        data => {
            if (data) {
              document.getElementById("context").innerHTML = `Allow ${params.get("client_id")} to access: ${params.get("scope")}`
              document.getElementById("check").style.visibility = "visible"
            } else {
              document.getElementById("context").innerHTML = `${params.get("client_id")} isn't a registered user of this OAUTH`
              document.getElementById("check").style.visibility = "hidden"
            }
        }
    )
}

function confirm(){
    fetch("http://localhost:8000/getCode", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            id: `${params.get("client_id")}`,
            redirect_uri: `${params.get("redirect_uri")}`,
            state: `${params.get("state")}`,
            scope: `${params.get("scope")}`
        })
    }).then(response => response.text())
    .then(data => {
        window.location.replace(`${params.get("redirect_uri")}?code=${data}&state=${params.get("state")}`);
    });
}