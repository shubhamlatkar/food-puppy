import React from "react";

const Dashboard = ({ notification, user }) => {
  return (
    <React.Fragment>
      <h2>Dashboard for user</h2>
      <h3>User data</h3>
      <p>{JSON.stringify(user)}</p>
      <h3>Notifications</h3>
      {notification.map((notification) => (
        <p>{JSON.stringify(notification)}</p>
      ))}
    </React.Fragment>
  );
};

export default Dashboard;
