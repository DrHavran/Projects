function goTo(){
  fetch("http://localhost:8080/getInfo")
    .then(response => response.json())
    .then(data => {
      window.location.replace(`http://127.0.0.1:5500/OAUTH/oauth.html?response_type=code&client_id=${data.id}&redirect_uri=${data.redirect}&scope=${data.scope}&state=${data.state}`);
  })
}