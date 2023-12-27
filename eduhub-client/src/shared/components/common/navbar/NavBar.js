import React, { useState } from "react";
import { IoIosAddCircle } from "react-icons/io";
import "./navbar.css";
import { useNavigate } from "react-router-dom";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import LogoutIcon from "@mui/icons-material/Logout";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import ViewListIcon from "@mui/icons-material/ViewList";

const Navbar = ({ userData }) => {
  const navigate = useNavigate();
  const [anchorEl, setAnchorEl] = useState(null);

  const toHome = () => {
    navigate("/home");
  };

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleItemClick = (item) => {
    if (item === "profile") {
      navigate("/profile");
    } else if (item === "logout") {
      sessionStorage.clear();
      navigate("/");
    }
    setAnchorEl(null);
  };

  return (
    <nav id="navbar" className="navbar">
      <div className="navbar-container">
        <h2 className="navbar-brand" onClick={() => toHome()}>
          EduHub
        </h2>
        <div className="navbar-menu">
          <div className="notification-badge">
            <h2>{userData.balance} $</h2>
          </div>
          {userData.role !== "TEACHER" ? (
            <></>
          ) : (
            <div
              className="notification-badge"
              onClick={() => {
                navigate("/course/create");
              }}
            >
              <IoIosAddCircle className="icon" size="30px" color="white" />
            </div>
          )}

          <div className="user-profile" onClick={handleClick}>
            <img
              className="avatar"
              width="40"
              height="40"
              src={userData.avatarUrl}
              alt="Avatar"
            />
            <p className="username">{userData.userName}</p>
            <Menu
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={handleClose}
            >
              <MenuItem onClick={() => handleItemClick("profile")}>
                <AccountBoxIcon /> Profile
              </MenuItem>
              {userData.role !== "TEACHER" ? (
                <></>
              ) : (
                <MenuItem onClick={() => handleItemClick("courses")}>
                  <ViewListIcon /> Courses
                </MenuItem>
              )}
              <MenuItem onClick={() => handleItemClick("logout")}>
                <LogoutIcon /> Logout
              </MenuItem>
            </Menu>
          </div>
        </div>
      </div>
    </nav>
  );
};
export default Navbar;
