import React, { useState, useEffect } from "react";
import Layout from "../Layout";
import "./course.css";
import CourseService from "../../shared/service/courseService";
import CourseItem from "../../shared/components/common/courseItem/CourseItem";

const CoursePage = () => {
    const [id, setId] = useState("");
    const [course, setCourse] = useState({});
    const [isLoading, setIsLoading] = useState(true);
  
    useEffect(() => {
      const fetchCourse = async () => {
        const courseId = sessionStorage.getItem("COURSE_CLICK");
        setId(courseId);
        
        try {
          const response = await CourseService.getCourseById(id);
          setCourse(JSON.parse(sessionStorage.getItem("CURRENT_COURSE")));
          setIsLoading(false);
        } catch (err) {
          console.error("Error fetching course:", err);
          setIsLoading(false);
        }
      };
  
      fetchCourse();
    }, []);
  
    return (
      <Layout>
        <section className="about">
          <div className="contain">
              {isLoading ? (
                <p>Loading...</p>
              ) : (
                <CourseItem data={course} />
              )}
            <div className="searchRes">
              <div className="course-items-container"></div>
            </div>
          </div>
        </section>
      </Layout>
    );
  };
  
  export default CoursePage;
  
