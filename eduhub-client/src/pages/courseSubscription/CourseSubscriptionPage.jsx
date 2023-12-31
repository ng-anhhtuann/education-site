import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./courseSubscription.css";
import SearchSpace from "../../shared/components/common/search/SearchSpace";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";
import Pagination from "@mui/material/Pagination";
import SubscriptionService from "../../shared/service/subscriptionService";
import eventEmitter from "../../shared/utils/emitter";
import UserService from "../../shared/service/userService";
import { useNavigate } from "react-router-dom";
import LoadingComponent from "../../shared/components/common/loading/loading";
import HeaderTitle from "../../shared/components/common/headerTitle/headerTitle";

const CourseSubscriptionPage = () => {
  const navigate = useNavigate();
  const [user, setUserData] = useState({});
  const [load, setLoad] = useState(true);
  const [search, setSearch] = useState({
    page: 1,
    pageSize: 6,
    searchType: "FIELD",
    params: {
      student_id: sessionStorage.getItem("ID"),
    },
  });
  const [listRes, setListRes] = useState([]);
  const [count, setCount] = useState(0);

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

  useEffect(() => {
    eventEmitter.on("LIST_DATA", (data) => {
      setListRes(JSON.parse(data));
    });

    eventEmitter.on("SEARCH_BODY", (data) => {
      setSearch(JSON.parse(data));
    });

    eventEmitter.on("DATA_COUNT", (data) => {
      setCount(JSON.parse(data));
    });
  }, []);

  useEffect(() => {
    setLoad(true);
    SubscriptionService.searchCourseByCondition(search).then((res) => {
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

  const renderedCourseItems =
    listRes.length === 0 ? (
      <>
        <p>You didn't subscribe on any course before!</p>
      </>
    ) : (
      listRes
        .slice(0, 6)
        .map((courseData, index) => (
          <CourseItem
            key={index}
            data={courseData}
            onClick={() => emitCourse(courseData.id)}
          />
        ))
    );

  const emitCourse = (id) => {
    sessionStorage.setItem("COURSE_CLICK", id);
    sessionStorage.removeItem("SEARCH_COURSE");
    sessionStorage.removeItem("SEARCH_RESULT_LIST");
    sessionStorage.removeItem("SEARCH_RESULT_COUNT");
    navigate("/course");
  };

  return (
    <Layout>
      <section className="about">
        <div className="contain">
          <div className="searchBar">
            <HeaderTitle
              user={user}
              count={count}
              title={"COURSES BOUGHT 💰"}
              sumText={"Number of courses bought:"}
            />
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

export default CourseSubscriptionPage;
