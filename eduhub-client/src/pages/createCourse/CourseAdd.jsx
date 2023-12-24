import React, { useState, useEffect, useMemo } from "react";
import Layout from "../Layout";
import "./courseAdd.css";
import Toast from "../../shared/components/Toast";

const CourseAddPage = () => {
    const [isNotify, setIsNotify] = useState(false);
    const [textNotify, setTextNotify] = useState("");
    const [typeNotify, setTypeNotify] = useState("");

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [showTagInput, setShowTagInput] = useState(false);
  const [price, setPrice] = useState(59);

  const handleTagInput = (e) => {
    setTagInput(e.target.value);
  };

  const handleTagSubmit = (e) => {
    e.preventDefault();
    setTags([...tags, tagInput]);
    setTagInput("");
    setShowTagInput(false);
  };

  const handleTagDelete = (index) => {
    const updatedTags = tags.filter((_, i) => i !== index);
    setTags(updatedTags);
  };

  const handleConfirm = (e) => {
    e.preventDefault();
    
    console.log("confirm")

  };

  if (isNotify) {
    setTimeout(() => {
      setIsNotify(false);
    }, 2000);
  }

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <Layout>
      <div className="course-add-container">
        {toast}
        <div className="add-container">
          <p>Create a course ✨</p>
          <div className="course-title">
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Title..."
            />
          </div>
          <div className="course-price">
            <p>Price</p>
            <input
              type="range"
              price={0}
              max={1000}
              value={price}
              onChange={(e) => setPrice(e.target.value)}
            />
            <p>{price}</p>
          </div>
          <div className="course-tags">
            <p>Tags related to course</p>
            {tags.map((tag, index) => (
              <button
                key={index}
                className="tag-button"
                onClick={() => handleTagDelete(index)}
              >
                {tag}
              </button>
            ))}
            {showTagInput ? (
              <form onSubmit={handleTagSubmit}>
                <input
                  type="text"
                  value={tagInput}
                  onChange={handleTagInput}
                  onBlur={() => setShowTagInput(false)}
                  autoFocus
                />
              </form>
            ) : (
              <button
                className="tag-button-add"
                onClick={() => setShowTagInput(true)}
              >
                +
              </button>
            )}
          </div>
          <div className="course-title">
            <input
              type="text"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="Description here..."
            />
          </div>
          <div className="input-row">
            <button className="confirm" onClick={handleConfirm}>
              Confirm
            </button>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default CourseAddPage;
