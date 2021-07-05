[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/shubhamlatkar/foodgrid)
# food-grid

## Food Delivery App

### Sample fetch calls to test the app

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
