import React, { useState, useEffect } from "react";
import { IoIosNotificationsOutline } from "react-icons/io";
import { useNavigate } from "react-router-dom";
import UserService from "../../../service/userService";
import "./navbar.css";

const Navbar = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState(null);
  const [notificationCount, setNotificationCount] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const id = sessionStorage.getItem("ID");
        const response = await UserService.getUserById(id);

        if (response.data.status !== 200) {
          sessionStorage.clear();
          navigate("/login");
        } else {
          setUserData(response.data.data);
          sessionStorage.setItem(
            "USER_DATA",
            JSON.stringify(response.data.data)
          );
        }
      } catch (error) {
        sessionStorage.clear();
        navigate("/login");
      }
    };

    if (sessionStorage.getItem("USER_DATA") === null) {
      fetchData();
    } else {
      const storedUserData = sessionStorage.getItem("USER_DATA");
      setUserData(JSON.parse(storedUserData));
    }
  }, [navigate]);

  if (!userData) {
    return null;
  }

  // Function to simulate receiving a new notification
  const receiveNotification = () => {
    setNotificationCount(notificationCount + 1);
  };

  return (
    <nav id="navbar" className="navbar">
      <div className="navbar-container">
        <h2 className="navbar-brand">EduHub</h2>
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
