import React, { useEffect, useState } from "react";

const Dashboard = (props) => {
  const [user, setUser] = useState({});
  const [notification, setNotification] = useState([]);


  useEffect(() => {
      var url ="/delivery/api/v1/login";

      var xhr = new XMLHttpRequest();
      xhr.open("POST", url);

      xhr.setRequestHeader("Content-Type", "application/json");

      xhr.onreadystatechange = function() {
          if (xhr.readyState === 4) {
              var data = JSON.parse(xhr.responseText);
              console.log("testUserLogin", xhr.status, data);
              console.log("success", data);
              setUser({ ...data });
              let source = null;
              source = new EventSource(
                "/notification/api/v1/notification/delivery/" + data.id
              );
              source.addEventListener("notification", function (event) {
                var data = event.data;
                console.log("Event data : ", data);
                setNotification([...notification, data]);
              });
              source.onerror = (err) => console.log("Error", err);
          }
      };

      var data = `{
          "username":"testDelivery",
          "password":"test"
      }`;

      xhr.send(data);
  }, []);
  return (
    <React.Fragment>
      <h2>Delivery Dashboard</h2>
      <h3>Delivery data</h3>
      <p>{JSON.stringify(user)}</p>
      <h3>Notifications</h3>
      {notification.map((notification) => (
        <p>{JSON.stringify(notification)}</p>
      ))}
    </React.Fragment>
  );
};

export default Dashboard;
