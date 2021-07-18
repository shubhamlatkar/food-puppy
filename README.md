[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/shubhamlatkar/foodgrid)

# food-grid

## Food Delivery App

### Sample fetch calls to test the app

### Test signup

```
var url = "http://localhost:8081/signup";

var xhr = new XMLHttpRequest();
xhr.open("PUT", url);

xhr.setRequestHeader("Content-Type", "application/json");

xhr.onreadystatechange = function () {
   if (xhr.readyState === 4) {
      console.log(xhr.status);
      console.log(xhr.responseText);
   }};

var data = `{
  "username":"skl",
  	"password":"any passwrod",
    "email":"email@shu.com",
    "phone":"1234567890",
    "roles":["ROLE_USER"]
}`;

xhr.send(data);
```

#### Test login method

```
var data = {username:"test",password:"test"}
```

```
fetch("http://localhost:8081/login", {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify(data),
})
.then(response => response.json())
.then(data => {
  console.log('Success:', data);
})
.catch((error) => {
  console.error('Error:', error);
});
```

#### Test secured endpoint

```
fetch("http://localhost:8081/user/", {
  method: 'GET',
  headers: {
    'Authorization': 'Bearer <token>',
    'Content-Type': 'application/json',
  }
})
.then(response => response.text())
.then(data => {
  console.log('Success:', data);
})
.catch((error) => {
  console.error('Error:', error);
});
```

#### Test Notifications

```
function loadNotifications () {

    this.source = null;
    
    this.start = function () {
        this.source = new EventSource("http://localhost:8083/notification/restaurant/60f292de76160655f8cb3625");
        this.source.addEventListener("notification", function (event) {
            var data = event.data;
            console.log("Event data : ", data);
            // process the data
        });
        this.source.onerror = function () {
            console.log("Error");
        };
    };
}

notifications = new loadNotifications();

notifications.start();
```

To run with docker

```
git clone http://github.com/shubhamlatkar/foodgrid.git && cd foodgrid/ && git pull origin cqrs-impl-notification && git checkout cqrs-impl-notification && docker-compose up
```

### Useful docker commands

docker stop $(docker ps -a -q)

1. Remove all images `docker image prune -a`.
2. Stop all container `docker stop $(docker ps -a -q)`.
3. Delete all stopped containers `docker rm $(docker ps -a -q)`.
4. Delete all stopped images `docker rmi $(docker images -q)`.

#### Ref

1. [Docker ref](https://www.codenotary.com/blog/extremely-useful-docker-commands/)
