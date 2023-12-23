import React, { useState } from "react";
import { IoIosNotificationsOutline, IoIosAddCircle } from "react-icons/io";
import "./navbar.css";
import { useNavigate } from "react-router-dom"

const Navbar = ({ userData }) => {
  const navigate = useNavigate()
  const [notificationCount, setNotificationCount] = useState(0);

  // Function to simulate receiving a new notification
  const receiveNotification = () => {
    setNotificationCount(notificationCount + 1);
  };

  const toHome = () => {
    navigate("/home")
  }

  return (
    <nav id="navbar" className="navbar">
      <div className="navbar-container">
        <h2 className="navbar-brand" onClick={() => toHome()}>EduHub</h2>
        <div className="navbar-menu">
          <div className="notification-badge" onClick={receiveNotification}>
            <IoIosNotificationsOutline
              className="icon"
              size="30px"
              color="white"
            />
            {notificationCount > 0 && (
              <span className="badge">{notificationCount}</span>
            )}
          </div>
          {userData.role !== "TEACHER" ? (
            <></>
          ) : (
            <div
              className="notification-badge"
              // onClick={}
            >
              <IoIosAddCircle className="icon" size="30px" color="white" />
            </div>
          )}

          <div className="user-profile">
            <img
              className="avatar"
              width="40"
              height="40"
              src={userData.avatarUrl}
              alt="Avatar"
            />
            <p className="username">{userData.userName}</p>
          </div>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
