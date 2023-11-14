import React, { useState, useEffect } from "react";
import Navbar  from "../shared/components/common/navbar/NavBar"
import { useNavigate } from "react-router-dom";
import UserService from "../shared/service/userService";

const Layout = ({ children }) => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState({});

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
  
  return (
    <>
      <Navbar userData={userData} />
      {children}
    </>
  );
};

export default Layout;
