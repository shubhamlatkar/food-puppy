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
    fetch( "/user/api/v1/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ username: "testUser", password: "test" })
    })
      .then((res) => res.json())
      .then((res) => {
            console.log("success", res.data);
           setUser({ ...res.data });
           let source = null;
           source = new EventSource(
             "/notification/api/v1/notification/user/" + res.data.id
           );
           source.addEventListener("notification", function (event) {
             var data = event.data;
             console.log("Event data : ", data);
             setNotification([...notification, data]);
           });
           source.onerror = (err) => console.log("Error", err);
           setLoading(false);
      })
      .catch((err) => setLoading(false));
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
