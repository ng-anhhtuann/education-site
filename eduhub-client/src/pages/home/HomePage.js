import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./main.css";
import SearchSpace from "../../shared/components/common/search/SearchSpace";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";
import Pagination from "@mui/material/Pagination";
import CourseService from "../../shared/service/courseService";
import eventEmitter from "../../shared/utils/emitter";
import { useNavigate } from "react-router-dom";
import LoadingComponent from "../../shared/components/common/loading/loading"
const Home = () => {
  const navigate = useNavigate();
  const [load, setLoad] = useState(true)
  const [search, setSearch] = useState({
    page: 1,
    pageSize: 6,
    searchType: "ALL",
    params: {},
  });
  const [listRes, setListRes] = useState([]);
  const [count, setCount] = useState(0);

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
    setLoad(true)
    CourseService.searchCourseByCondition(search).then((res) => {
      setListRes(JSON.parse(sessionStorage.getItem("SEARCH_RESULT_LIST")));
      setCount(JSON.parse(sessionStorage.getItem("SEARCH_RESULT_COUNT")));
    });
    setLoad(false)
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
        onClick={() => emitCourse(courseData.id)}
      />
    ));

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
            <SearchSpace />
          </div>
          <div className="searchRes">
            {load ? (
              <LoadingComponent />
            ) : (
              <div className="course-items-container">{renderedCourseItems}</div>
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

export default Home;
