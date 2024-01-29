import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./course.css";
import CourseService from "../../shared/service/courseService";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";
import VideoItem from "../../shared/components/common/videoItem/VideoItem";
import Pagination from "@mui/material/Pagination";
import VideoService from "../../shared/service/videoService";
//userEffect xử lý dữ liệu khi nó có biến đổi từ param truyền vào
const CoursePage = () => {
  const [id, setId] = useState("");
  const [course, setCourse] = useState({});
  const [isLoading, setIsLoading] = useState(true);
  const [videoList, setVideoList] = useState([]);
  const [count, setCount] = useState(0);
  // ban đầu vào trang sẽ gọi API search lấy tất cả thông tin video của khoá học dựa theo id
  const [search, setSearch] = useState({
    page: 1,
    pageSize: 6,
    searchType: "FIELD",
    params: {
      course_id: id,
    },
  });
// phụ thuộc search để phân trang
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
        // gọi API thông tin khoá học dựa theo id
        await CourseService.getCourseById(courseId);
        setCourse(JSON.parse(sessionStorage.getItem("CURRENT_COURSE")));
        setIsLoading(false);
        search.params.course_id = courseId;
        // dựa vào search bên trên để gọi API lấy tất cả video từ khoá học đó
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
    .slice(0, 6)
    .map((data, index) => <VideoItem key={index} video={data} />);

  const calculateTotalPages = (totalData, pageSize) => {
    return Math.ceil(totalData / pageSize);
  };

  const callPageChange = (event, value) => {
    setSearch({ ...search, page: value });
  };

  return (
    <Layout>
      <section className="about">
        <div className="contain">
          {isLoading ? <p>Loading...</p> : <CourseItem data={course} />}
          <div className="searchRes">
            <div className="course-items-container">{renderedVideoItems}</div>
            {count === 0 ? (
              <></>
            ) : (
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

export default CoursePage;
