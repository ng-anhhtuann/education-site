import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./ownCourse.css";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";
import Pagination from "@mui/material/Pagination";
import CourseService from "../../shared/service/courseService";
import { useNavigate } from "react-router-dom";
import LoadingComponent from "../../shared/components/common/loading/loading";
import UserService from "../../shared/service/userService";
import HeaderTitle from "../../shared/components/common/headerTitle/headerTitle";

const OwnCoursePage = () => {
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

  const [load, setLoad] = useState(true);
  const [search, setSearch] = useState({
    page: 1,
    pageSize: 6,
    searchType: "FIELD",
    params: {
      teacher_id: sessionStorage.getItem("ID"),
    },
  });
  const [listRes, setListRes] = useState([]);
  const [count, setCount] = useState(0);

  useEffect(() => {
    setLoad(true);
    CourseService.searchCourseByCondition(search).then((res) => {
      setListRes(JSON.parse(sessionStorage.getItem("SEARCH_RESULT_LIST")));
      setCount(JSON.parse(sessionStorage.getItem("SEARCH_RESULT_COUNT")));
    });
    setLoad(false);
  }, [search]);

  const calculateTotalPages = (totalData, pageSize) => {
    return Math.ceil(totalData / pageSize);
  };

  const callPageChange = (event, value) => {
    setSearch({ ...search, page: value });
  };

  const renderedCourseItems = listRes
    .slice(0, 6)
    .map((courseData, index) => (
      <CourseItem
        key={index}
        data={courseData}
        onClick={() => courseClick(courseData.id)}
      />
    ));

  const courseClick = (id) => {
    sessionStorage.setItem("COURSE_CLICK", id);
    sessionStorage.removeItem("SEARCH_COURSE");
    sessionStorage.removeItem("SEARCH_RESULT_LIST");
    sessionStorage.removeItem("SEARCH_RESULT_COUNT");
    navigate("/course/manage/edit");
  };

  return (
    <Layout>
      <section className="about">
        <div className="contain">
          <div className="searchBar">
            <HeaderTitle user={user} count={count}/>
          </div>
          <div className="searchRes">
            {load ? (
              <LoadingComponent />
            ) : (
              <div className="course-items-container">
                {renderedCourseItems}
              </div>
            )}
            {count !== 0 && (
              <div className="pagination-container">
                <Pagination
                  count={calculateTotalPages(count, 6)}
                  variant="outlined"
                  shape="rounded"
                  onChange={callPageChange}
                />
              </div>
            )}
          </div>
        </div>
      </section>
    </Layout>
  );
};

export default OwnCoursePage;
