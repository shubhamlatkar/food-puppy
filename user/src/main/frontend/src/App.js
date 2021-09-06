import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  NavLink,
  Route,
  Switch
} from "react-router-dom";
import "./styles.scss";
import Banner from "./components/Banner";
import Dashboard from "./components/Dashboard";
import Loader from "./components/Loader";

const App = () => {
  const [user, setUser] = useState({});
  const [notification, setNotification] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);

    var url ="/user/api/v1/login";

    var xhr = new XMLHttpRequest();
    xhr.open("POST", url);

    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            var data = JSON.parse(xhr.responseText);
            console.log("testRestaurantLogin", xhr.status, data);
            setUser({ ...data });
            let source = null;
            source = new EventSource("/notification/api/v1/notification/user/" + data.id);
            source.addEventListener("notification", function (event) {
                var data = event.data;
                console.log("Event data : ", data);
                setNotification([...notification, data]);
            });
            source.onerror = (err) => console.log("Error", err);
            setLoading(false);
        }
    };
    var data = `{
        "username":"testUser",
        "password":"test"
    }`;
    xhr.send(data);

  }, []);

  return (
    <div className="App App-header">
      {loading ? (
        <Loader />
      ) : (
        <Router>
          <NavLink to="/user/member/dashboard">Dashboard</NavLink>
          <NavLink to="/user/member/banner">Banner</NavLink>
          <h2>Test frontend for foodgrid user</h2>
          <Switch>
            <Route
              path="/"
              exact
              component={() => (
                <Dashboard user={user} notification={notification} />
              )}
            />
            <Route
              path="/user/member/dashboard"
              component={() => (
                <Dashboard user={user} notification={notification} />
              )}
            />
            <Route path="/user/member/banner" component={Banner} />
          </Switch>
        </Router>
      )}
    </div>
  );
};

export default App;
