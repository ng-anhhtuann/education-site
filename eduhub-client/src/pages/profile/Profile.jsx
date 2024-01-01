import React, { useState, useMemo, useEffect } from "react";
import Layout from "../Layout";
import Toast from "../../shared/components/Toast";
import "./profile.css";
import { useNavigate } from "react-router-dom";
import UserService from "../../shared/service/userService";
import FileService from "../../shared/service/fileService";
import ImageService from "../../shared/service/imageService";

const Profile = () => {
  const navigate = useNavigate();
  const [user, setUserData] = useState({});

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

    fetchData();
  }, [navigate]);

  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");

  const [showPasswordHolder, setShowPasswordHolder] = useState(false);
  const [password, setPassword] = useState(user.password);
  const [rePassword, setRePassword] = useState(user.password);

  const [imageFile, setImageFile] = useState(null);
  const [imageUrl, setImageUrl] = useState(user.avatarUrl);

  useEffect(() => {
    setImageUrl(user.avatarUrl);
    setRePassword(user.password);
    setPassword(user.password);
  }, [user.avatarUrl, user.password]);

  const handleChangeFile = (e) => {
    const reader = new FileReader();
    reader.onload = function (event) {
      if (e.target.files && e.target.files[0]) {
        setImageFile(e.target.files[0]);
        setImageUrl(event.target.result || imageUrl);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify !== "info") {
        if (typeNotify === "success") {
          window.location.reload();
        }
        setIsNotify(false);
        setTextNotify("");
      }
    }, 2000);
  }

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  const showTooltip = () => {
    const tooltip = document.querySelector(".tooltip-text");
    tooltip.style.visibility = "visible";
  };

  const hideTooltip = () => {
    const tooltip = document.querySelector(".tooltip-text");
    tooltip.style.visibility = "hidden";
  };

  const togglePasswordHolder = () => {
    setShowPasswordHolder(!showPasswordHolder);
  };

  function formatDate(unixTime) {
    const date = new Date(unixTime);
    const options = {
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
      day: "2-digit",
      month: "short",
      year: "numeric",
    };
    return date.toLocaleDateString("en-US", options);
  }

  const handleSubmit = (e) => {
    e.preventDefault();

    setTypeNotify("info");
    setTextNotify("Wait for the server");
    setIsNotify(true);

    if (imageFile) {
      FileService.uploadImage(imageFile)
        .then((res) => {
          if (res.data.status !== 200) {
            setTypeNotify("error");
            setTextNotify(res.data.errors || "Something went wrong");
            setIsNotify(true);
            return;
          }

          setImageUrl(res.data.data.fileUrl);

          const req = {
            id: user.id,
            userName: user.userName,
            avatarUrl: res.data.data.fileUrl,
            password,
            rePassword,
            email: user.email,
            role: user.role,
          };

          const imgReq = {
            name: res.data.data.fileName,
            isAvatar: true,
            url: res.data.data.fileUrl,
            ownerId: user.id,
          };

          UserService.update(req)
            .then((response) => {
              if (response.data.status !== 200) {
                setTypeNotify("error");
                setTextNotify(response.data.errors || "Something went wrong");
                setIsNotify(true);
                return;
              }

              imgReq.ownerId = sessionStorage.getItem("ID");

              ImageService.createOrUpdateImage(imgReq).then((imgRes) => {
                if (imgRes.data.status !== 200) {
                  setTypeNotify("error");
                  setTextNotify(imgRes.data.errors || "Something went wrong");
                  setIsNotify(true);
                  return;
                } else {
                    setTypeNotify("success");
                    setTextNotify("Created successfully!");
                    setIsNotify(true);
                }
              });
            })
            .catch((err) => {
              setTypeNotify("error");
              setTextNotify("Something went wrong while creating the course");
              setIsNotify(true);
            });
        })
        .catch((err) => {
          setTypeNotify("error");
          setTextNotify("Something went wrong while uploading the image");
          setIsNotify(true);
        });
    } else {

      const req = {
        id: user.id,
        userName: user.userName,
        avatarUrl: imageUrl,
        password,
        rePassword,
        email: user.email,
        role: user.role,
      };

      UserService.update(req)
        .then((response) => {
          if (response.data.status !== 200) {
            setTypeNotify("error");
            setTextNotify(response.data.errors || "Something went wrong");
            setIsNotify(true);
          } else {
            setTypeNotify("success");
            setTextNotify("Created successfully!");
            setIsNotify(true);
          }
        })
        .catch((err) => {
          setTypeNotify("error");
          setTextNotify("Something went wrong while creating the course");
          setIsNotify(true);
        });
    }
  };

  return (
    <Layout>
      <div className="profile-container">
        {toast}
        <div className="profile-main">
          <p className="profile-header">USER PROFILE 🖥</p>
          <div className="profile-avatar">
            <div>
              <span className="tooltip-text">Click here to change avatar 📸</span>
              <img
                src={imageUrl}
                onClick={(e) => document.getElementById("fileID").click()}
                onMouseOver={showTooltip}
                onMouseOut={hideTooltip}
                alt=""
                className="avatar-image"
              />
              <input
                type="file"
                id="fileID"
                onChange={handleChangeFile}
                style={{ display: "none" }}
                accept="image/*"
              />
            </div>
          </div>
          <div className="profile-name">
            <input type="text" value={user.userName} disabled />
          </div>
          <div className="profile-name">
            <input type="text" value={user.email} disabled />
          </div>
          <div className="profile-role">
            <h2>ROLE: </h2>
            <p>{user.role}</p>
          </div>
          <div className="profile-date">
            <h2>LAST UPDATED: </h2>
            <p>{formatDate(user.updatedDate || user.createdDate)}</p>
          </div>
          <div className="profile-date">
            <h2>CREATED AT: </h2>
            <p>{formatDate(user.createdDate)}</p>
          </div>
          <div className="profile-change-pwd">
            <button className="profile-change" onClick={togglePasswordHolder}>
              CHANGE PASSWORD 🔐
            </button>
            {showPasswordHolder && (
              <div className="password-holder">
                <div className="password-jc">
                  <h2>PASSWORD</h2>
                  <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="New Password"
                    required
                  />
                </div>
                <div className="password-jc">
                  <h2>CONFIRM</h2>
                  <input
                    type="password"
                    value={rePassword}
                    onChange={(e) => setRePassword(e.target.value)}
                    placeholder="Retype New Password"
                    required
                  />
                </div>
              </div>
            )}
            <button className="profile-confirm" onClick={handleSubmit}>
              UPDATE 📝
            </button>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default Profile;
