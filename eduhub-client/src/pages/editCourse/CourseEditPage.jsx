import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./courseEdit.css";
import CourseService from "../../shared/service/courseService";
import Pagination from "@mui/material/Pagination";
import VideoService from "../../shared/service/videoService";
import AddVideoItem from "../../shared/components/common/addVideoItem/addVideoItem";
import CourseItemEdit from "../../shared/components/common/courseEdit/CourseItemEdit";
import VideoEditItem from "../../shared/components/common/videoEditItem/videoEditItem";

const CourseEditPage = () => {
  const [id, setId] = useState("");
  const [course, setCourse] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const [videoList, setVideoList] = useState([]);
  const [count, setCount] = useState(0);
  const [search, setSearch] = useState({
    page: 1,
    pageSize: 5,
    searchType: "FIELD",
    params: {
      course_id: id,
    },
  });

  useEffect(() => {
    VideoService.searchVideoByCondition(search).then((res) => {
      setVideoList(
        JSON.parse(sessionStorage.getItem("VIDEO_LIST_BY_COURSE_ID"))
      );
      setCount(JSON.parse(sessionStorage.getItem("VIDEO_COUNT_BY_COURSE_ID")));
    });
  }, [search]);

  useEffect(() => {
    const fetchCourse = async () => {
      const courseId = sessionStorage.getItem("COURSE_CLICK");
      setId(courseId);

      try {
        await CourseService.getCourseById(courseId);
        setCourse(JSON.parse(sessionStorage.getItem("CURRENT_COURSE")));
        setIsLoading(false);
        search.params.course_id = courseId;
        await VideoService.searchVideoByCondition(search);
        setVideoList(
          JSON.parse(sessionStorage.getItem("VIDEO_LIST_BY_COURSE_ID"))
        );
        setCount(
          JSON.parse(sessionStorage.getItem("VIDEO_COUNT_BY_COURSE_ID"))
        );
      } catch (err) {
        console.error("Error fetching course:", err);
        setIsLoading(false);
      }
    };

    fetchCourse();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const renderedVideoItems = videoList
    .slice(0, 5)
    .map((data, index) => <VideoEditItem key={index} video={data} />);

  const calculateTotalPages = (totalData, pageSize) => {
    return Math.ceil(totalData / pageSize);
  };

  const callPageChange = (event, value) => {
    setSearch({ ...search, page: value });
  };

  return (
    <Layout>
      <section className="about">
        <div className="video-add-contain">
          {isLoading ? <p>Loading...</p> : <CourseItemEdit data={course} />}
          <div className="video-list-add-course">
            <div className="video-list-add-course-render">
              <AddVideoItem />
              {renderedVideoItems}
            </div>
            {count === 0 ? (
              <></>
            ) : (
              <div className="pagination-container">
                <Pagination
                  count={calculateTotalPages(count, 5)}
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

export default CourseEditPage;
