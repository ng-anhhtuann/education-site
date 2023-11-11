import React, { useState, useEffect } from "react";
import Heading from "../../shared/components/common/heading/Heading";
import { useNavigate } from "react-router-dom";
import SearchBar from "./SearchBar";
import Navbar from "../../shared/components/common/navbar/NavBar";
import UserService from "../../shared/service/userService";

const Home = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState(null);

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
          sessionStorage.setItem("USER_DATA", JSON.stringify(response.data.data));
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

  return (
    <>
      <Navbar userData={userData} />
      <SearchBar />
      <section className="aboutHome">
        <div className="container flexSB">
          <div className="left row">
            <img src="./images/about.webp" alt="" />
          </div>
          <div className="right row">
            <Heading
              subtitle="LEARN CODE"
              title="ONLINE CODE LEARNING AND EVERYTHING"
            />
          </div>
        </div>
      </section>
    </>
  );
};

export default Home;
