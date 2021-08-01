var build = true;
var token = "";
var id = "";
var itemId = "";
var addressId = "";
/**
----------------------RESTAURANT START-----------------------
**/
// Restaurant api for signup
const testRestaurantSignUp = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/signup" : "http://localhost:8082/api/v1/signup";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantSignUp", xhr.status, data);
                resolve("testRestaurantSignUp successful");

            }
        };

        var data = `{
            "username":"rosewood",
            "password":"rosewood@shu.com",
            "email":"rosewood@shu.com",
            "phone":"1234567890",
            "roles":["ROLE_RESTAURANT"]
        }`;

        xhr.send(data);
    });

// Restaurant api for login
const testRestaurantLogin = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/login" : "http://localhost:8082/api/v1/login";

        var xhr = new XMLHttpRequest();
        xhr.open("POST", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                token = data.token;
                id = data.id;
                console.log("testRestaurantLogin", xhr.status, data);
                resolve("testRestaurantLogin successful");
            }
        };

        var data = `{
            "username":"rosewood",
            "password":"rosewood@shu.com"
        }`;

        xhr.send(data);
    });

// Restaurant api for logout
const testRestaurantLogout = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/logmeout" : "http://localhost:8082/api/v1/logmeout";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantLogout", xhr.status, data);
                resolve("testRestaurantLogout successful");
            }
        };

        xhr.send();
    });

// Restaurant api for logout
const testRestaurantLogoutAll = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/user/api/v1/logoutall" : "http://localhost:8082/api/v1/logoutall";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantLogoutAll", xhr.status, data);
                resolve("testRestaurantLogoutAll successful");
            }
        };

        xhr.send();
    });

// Restaurant api for logout
const testRestaurantDelete = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/user/api/v1/delete" : "http://localhost:8082/api/v1/delete";

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testRestaurantDelete", xhr.status, data);
                resolve("testRestaurantDelete successful");
            }
        };

        xhr.send();
    });


/**
----------------------MENU START-----------------------
**/
const testDeleteItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/user/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testDeleteItem", xhr.status, data);
                resolve("testDeleteItem successful");
            }
        };

        xhr.send();

    });

const testAddItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/user/api/v1/menu/item/" : "http://localhost:8082/api/v1/menu/item";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                itemId = data.id;
                console.log("testAddItem", xhr.status, data);
                resolve("testAddItem successful");
            }
        };

        var data = `{
        	 "name":"test1",
             "value":123.11,
             "ingredient":"test1",
             "comment":"test1",
             "startFirst":11,
             "endFirst":25,
             "startSecond":11,
            "endSecond":22
        }`;

        xhr.send(data);
    });

const testPatchItem = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/user/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("PATCH", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testPatchItem", xhr.status, data);
                resolve("testPatchItem successful");
            }
        };

        var data = `{
        	 "name":"test",
             "value":123.1,
             "ingredient":"test",
             "comment":"test",
             "startFirst":1,
             "endFirst":2,
             "startSecond":1,
            "endSecond":2
        }`;

        xhr.send(data);
    });

const testGetItemByRestaurantId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu" : "http://localhost:8082/api/v1/menu";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetItemByRestaurantId", xhr.status, data);
                resolve("testGetItemByRestaurantId successful");
            }
        };

        xhr.send();
    });

const testGetItemByItemId = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/restaurant/api/v1/menu/item/" + itemId : "http://localhost:8082/api/v1/menu/item/" + itemId;

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testGetItemByItemId", xhr.status, data);
                resolve("testGetItemByItemId successful");
            }
        };

        xhr.send();
    });

/**
----------------------USER START-----------------------
**/
// User api for signup
const testUserSignUp = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/signup" : "http://localhost:8081/api/v1/signup";

        var xhr = new XMLHttpRequest();
        xhr.open("PUT", url);

        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserSignUp", xhr.status, data);
                resolve("testUserSignUp successful");
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
const testUserLogin = () =>
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
                console.log("testUserLogin", xhr.status, data);
                resolve("testUserLogin successful");
            }
        };

        var data = `{
            "username":"skl",
            "password":"email@shu.com"
        }`;

        xhr.send(data);
    });

// User api for logout
const testUserLogout = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/api/v1/logmeout" : "http://localhost:8081/api/v1/logmeout";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserLogout", xhr.status, data);
                resolve("testUserLogout successful");
            }
        };

        xhr.send();
    });

// User api for logout
const testUserLogoutAll = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/user/api/v1/logoutall" : "http://localhost:8081/api/v1/logoutall";

        var xhr = new XMLHttpRequest();
        xhr.open("GET", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserLogoutAll", xhr.status, data);
                resolve("testUserLogoutAll successful");
            }
        };

        xhr.send();
    });

// User api for logout
const testUserDelete = () =>
    new Promise(function(resolve, reject) {
        var url = build ? "/user/user/api/v1/delete" : "http://localhost:8081/api/v1/delete";

        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", url);

        xhr.setRequestHeader("Authorization", "Bearer " + token);

        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                var data = JSON.parse(xhr.responseText);
                console.log("testUserDelete", xhr.status, data);
                resolve("testUserDelete successful");
            }
        };

        xhr.send();
    });

/**
----------------------ADDRESS START-----------------------
**/
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
    var url = build ? "/user/api/v1/address" + addressId : "http://localhost:8081/api/v1/address/" + addressId;
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


/**
----------------------ADDRESS TESTER FUNCTION START-----------------------
**/
const addressTester = () => {

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
        document.getElementById("current-test-heading").innerHTML = "Address app test suit";
        content.innerHTML = "";
    }

    showLoader();
    testUserSignUp().then(res => {
        addContent(res);
        testUserLogin().then((res) => {
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
//                                    testUserLogoutAll().then(res => {
//                                        addContent(res)
//                                        hideLoader();
//                                    })
                                    testUserDelete().then(res => {
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
    });
}

/**
----------------------MENU TESTER FUNCTION START-----------------------
**/

const menuTester = () => {

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
        document.getElementById("current-test-heading").innerHTML = "Menu app test suit";
        content.innerHTML = "";
    }

    showLoader();
    testRestaurantSignUp().then(res => {
        addContent(res);
        testRestaurantLogin().then((res) => {
            addContent(res);
            testAddItem().then(res => {
                addContent(res)
                testGetItemByItemId().then(res =>{
                    addContent(res)
                    testPatchItem().then(res => {
                        addContent(res)
                        testGetItemByRestaurantId().then(res => {
                            addContent(res)
                            testGetItemByItemId().then(res => {
                                addContent(res)
                                testDeleteItem().then(res => {
                                    addContent(res)
                                    testRestaurantDelete().then(res => {
                                        addContent(res)
                                        hideLoader();
                                    })
                                });
                            });
                        });
                    });
                });
            });
        })
    });
};