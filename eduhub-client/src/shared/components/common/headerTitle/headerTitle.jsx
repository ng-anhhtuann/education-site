import React from "react";
import "./headerTitle.css";

const HeaderTitle = ({ user, count }) => {
  return (
    <div className="admin-manage">
      <p className="manage-title">COURSES MANAGEMENT 💾</p>
      <div className="admin-avt">
        <img src={user.avatarUrl} alt="" className="avatar-image" />
      </div>
      <div className="manage-info-row">
        <h1 className="manage-info-key">Teacher:</h1>
        <p>{user.userName}</p>
      </div>
      <div className="manage-info-row">
        <h1 className="manage-info-key">Email:</h1>
        <p>{user.email}</p>
      </div>
      <div className="manage-info-row">
        <h1 className="manage-info-key">Number of courses published:</h1>
        <p>{count}</p>
      </div>
    </div>
  );
};
export default HeaderTitle;
