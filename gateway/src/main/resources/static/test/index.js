var build = true;
var token = "";
var id = "";
var addressId = "";

/**
----------------------USER START-----------------------
**/

// User api for signup
const testSignUp = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/signup" : "http://localhost:8081/api/v1/signup";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                //                if (xhr.status === 200) {
                console.log("testSignUp", xhr.status, data);
                resolve("testSignUp successful");
                //                } else
                //                    reject(new Error("Whoops! some error"));
            }
        };

        var data = `{
            "username":"skl",
            "password":"email@shu.com",
            "email":"email@shu.com",
            "phone":"1234567890",
            "roles":["ROLE_USER"]
        }`;

        xhr.send(data);
    });

// User api for login
const testLogin = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/login" : "http://localhost:8081/api/v1/login";

        var xhr = new XMLHttpRequest();
        xhr.open("POST", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                token = data.token;
                id = data.id;
                console.log("testLogin", xhr.status, data);
                resolve("testLogin successful");
            }
        };

        var data = `{
            "username":"skl",
            "password":"email@shu.com"
        }`;

        xhr.send(data);
    });

// User api for logout
const testLogout = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/logmeout" : "http://localhost:8081/api/v1/logmeout";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testLogout", xhr.status, data);
                resolve("testLogout successful");
            }
        };

        xhr.send();
    });

// User api for logout
const testLogoutAll = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/user/api/v1/logoutall" : "http://localhost:8081/api/v1/logoutall";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testLogoutAll", xhr.status, data);
                resolve("testLogoutAll successful");
            }
        };

        xhr.send();
    });

// User api for Put address
const testPutAddress = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address" : "http://localhost:8081/api/v1/address";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                addressId = data.id;
                console.log("testPutAddress", xhr.status, data);
                resolve("testPutAddress successful");
            }
        };

        var data = `{
          "location":{
            "x":123.23,
              "y":123.543
          },
          "name":"test",
          "addressLineOne":"test",
          "addressLineTwo":"test",
          "pin":"412123",
          "city":"pune",
          "state":"mah",
          "isSelected":false
        }`;

        xhr.send(data);
    });

// User api for testGetAddressById
const testGetAddressById = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/" + addressId : "http://localhost:8081/api/v1/address/" + addressId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetAddressById", xhr.status, data);
                resolve("testGetAddressById successful");
            }
        };

        xhr.send();
    });

const testGetAddressByUserId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/user/" + id : "http://localhost:8081/api/v1/address/user/" + id;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetAddressByUserId", xhr.status, data);
                resolve("testGetAddressByUserId successful");
            }
        };

        xhr.send();
    });

const testDeleteAddress = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/address/" + addressId : "http://localhost:8081/api/v1/address/" + addressId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testDeleteAddress", xhr.status, data);
                resolve("testDeleteAddress successful");
            }
        };

        xhr.send();

    });

const testPatchAddress = () => new Promise(function(resolve, reject) {
    var url = build ? "/user/api/v1/address" + addressId "http://localhost:8081/api/v1/address/" + addressId;
    var xhr = new XMLHttpRequest();
    xhr.open("PATCH", url);

    xhr.setRequestHeader("Authorization", "Bearer " + token);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            var data = JSON.parse(xhr.responseText);
            console.log("testPatchAddress", xhr.status, data);
            resolve("testPatchAddress successful");
        }
    };

    var data = `{
            "location":{
                "x":124.23,
                  "y":124.543
              },
              "name":"testOne",
              "addressLineOne":"testOne",
              "addressLineTwo":"testOne",
              "pin":"411111",
              "city":"puneOne",
              "state":"mahOne",
              "isSelected":false
        }`;

    xhr.send(data);

});

/**
----------------------NOTIFICATION START-----------------------
**/
const loadNotifications = () => {
    var source = null;

    const start = () => {
        source = new EventSource(
            "http://localhost:8083/notification/restaurant/" + id
        );
        source.addEventListener("notification", (event) => {
            var data = event.data;
            console.log("Event data : ", data);
            // process the data
        });
        source.onerror = function() {
            console.log("Error");
        };
    };
};

//notifications = new loadNotifications();

//notifications.start();

var content = document.getElementById("content");

const addContent = (msg) => {
    var para = document.createElement("P");
    var t = document.createTextNode(msg);
    para.appendChild(t);
    content.appendChild(para);
}

const hideLoader = () => {
    document.getElementById("loader").style.display = "none";
}

const showLoader = () => {
    document.getElementById("loader").style.display = "block";
}

showLoader();
testSignUp().then(res => {
        addContent(res);
        testLogin().then((res) => {
            addContent(res);
            testPutAddress().then((res) => {
                addContent(res);
                testGetAddressById().then((res) => {
                    addContent(res);
                    testGetAddressByUserId().then((res) => {
                        addContent(res);
                        testPatchAddress().then(res => {
                            addContent(res);
                            testGetAddressById().then(res => {
                                addContent(res);
                                testDeleteAddress().then(res => {
                                    addContent(res);
                                    testLogoutAll().then(res => {
                                        addContent(res)
                                        hideLoader();
                                    })
                                })
                            })
                        })
                    })
                })
            })
        })
    })
    .catch(err => console.log(err));