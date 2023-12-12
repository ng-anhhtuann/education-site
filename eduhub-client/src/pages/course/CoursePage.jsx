import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./course.css";
import CourseService from "../../shared/service/courseService";

const CoursePage = () => {
  const [id, setId] = useState("");
  const [course, setCourse] = useState([]);

  useEffect(() => {
    const courseId = sessionStorage.getItem("COURSE_CLICK");
    setId(courseId)

    CourseService.getCourseById(id).then((res) => {
        
    })

  }, []);

  return (
    <Layout>
      <section></section>
    </Layout>
  );
};

export default CoursePage;
