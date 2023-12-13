import React from "react";
import "./course.css";

const CourseItem = ({ data, onClick }) => {
  return (
    <div className="search-container" onClick={onClick}>
      <div className="image-container">
        <img src={data.thumbnailUrl} alt="" />
      </div>
      <div className="text-info-container">
        <h1>{data.title}</h1>
        <h2>{data.description}</h2>
        <div className="flex-row">
          {data.tagList.map((tag, index) => (
            <button key={index} className="tag-show">
              {tag}
            </button>
          ))}
        </div>
        <div className="flex-row-author">
          <h3>{data.teacherName}</h3>
          <h3>{data.price} $</h3>
        </div>
      </div>
    </div>
  );
};

export default CourseItem;