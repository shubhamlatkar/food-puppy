import React from "react";
import {
  BrowserRouter as Router,
  NavLink,
  Route,
  Switch
} from "react-router-dom";
import "./styles.css";
import Banner from "./components/Banner";
import Dashboard from "./components/Dashboard";

function App() {
  return (
    <div className="App App-header">
      <Router>
        <NavLink to="/delivery/member/dashboard">Dashboard</NavLink>
        <NavLink to="/delivery/member/banner">Banner</NavLink>
        <h1>Test frontend for foodgrid delivery</h1>
        <Switch>
          <Route path="/" exact component={Dashboard} />
          <Route path="/delivery/member/dashboard" component={Dashboard} />
          <Route path="/delivery/member/banner" component={Banner} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
