import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";
import "./video.css";

const VideoPage = () => {
    const [course, setCourse] = useState({})
    const [currentVideo, setCurrentVideo] = useState({});
    const [videoList, setVideoList] = useState([]);

    useEffect(() => {
        const fetchVideo = () => {
            setCourse(JSON.parse(sessionStorage.getItem("CURRENT_COURSE")));

            setCurrentVideo(JSON.parse(sessionStorage.getItem("CURRENT_VIDEO")));

            setVideoList(JSON.parse(sessionStorage.getItem("VIDEO_LIST_BY_COURSE_ID")));
          };
          fetchVideo();
    }, [])

  return (
    <Layout>
      <section className="about">
        <div className="videoList">
        {course === null ?  <p>Loading...</p> : <CourseItem data={course} />}
          <div className="videoPlayer">
            <video controls autoPlay width={"80%"} height={"80%"}>
              <source src={currentVideo.url} type="video/mp4" />
              Your browser does not support the video tag.
            </video>
          </div>
        </div>
      </section>
    </Layout>
  );
};

export default VideoPage;
