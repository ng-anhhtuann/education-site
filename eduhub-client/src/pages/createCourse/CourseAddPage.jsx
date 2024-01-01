import React, { useState, useMemo } from "react";
import Layout from "../Layout";
import "./courseAdd.css";
import Toast from "../../shared/components/Toast";
import FileService from "../../shared/service/fileService";
import CourseService from "../../shared/service/courseService";
import ImageService from "../../shared/service/imageService";
import { useNavigate } from "react-router-dom";

const CourseAddPage = () => {
  const navigate = useNavigate();
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState("");
  const [showTagInput, setShowTagInput] = useState(false);
  const [price, setPrice] = useState(59);

  const [imageFile, setImageFile] = useState(null);
  const [imageUrl, setImageUrl] = useState(
    "https://firebasestorage.googleapis.com/v0/b/edu-hub-3772f.appspot.com/o/thumbnail.png?alt=media"
  );

  const handleChangeFile = (e) => {
    const reader = new FileReader();
    reader.onload = function (event) {
      if (e.target.files && e.target.files[0]) {
        setImageFile(e.target.files[0]);
        setImageUrl(event.target.result || imageUrl);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

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

    setTypeNotify("info");
    setTextNotify("Wait for the server");
    setIsNotify(true);

    if (imageFile) {
      FileService.uploadImage(imageFile)
        .then((res) => {
          if (res.data.status !== 200) {
            setTypeNotify("error");
            setTextNotify(res.data.errors || "Something went wrong");
            setIsNotify(true);
            return;
          }

          setImageUrl(res.data.data.fileUrl);
          const req = {
            price,
            title,
            tagList: tags,
            studentCount: 0,
            teacherId: sessionStorage.getItem("ID"),
            description,
            thumbnailUrl: res.data.data.fileUrl,
          };

          const imgReq = {
            name: res.data.data.fileName,
            isAvatar: false,
            url: res.data.data.fileUrl,
            ownerId: sessionStorage.getItem("COURSE_CLICK"),
          };

          CourseService.createOrUpdateCourse(req)
            .then((response) => {
              if (response.data.status !== 200) {
                setTypeNotify("error");
                setTextNotify(response.data.errors || "Something went wrong");
                setIsNotify(true);
              }

              imgReq.ownerId = sessionStorage.getItem("COURSE_CLICK");

              ImageService.createOrUpdateImage(imgReq).then((imgRes) => {
                if (imgRes.data.status !== 200) {
                  setTypeNotify("error");
                  setTextNotify(imgRes.data.errors || "Something went wrong");
                  setIsNotify(true);
                } else {
                  setTypeNotify("success");
                  setTextNotify("Created successfully!");
                  setIsNotify(true);
                }

              });
            })
            .catch((err) => {
              setTypeNotify("error");
              setTextNotify("Something went wrong while creating the course");
              setIsNotify(true);
            });
        })
        .catch((err) => {
          setTypeNotify("error");
          setTextNotify("Something went wrong while uploading the image");
          setIsNotify(true);
        });
    } else {
      setTypeNotify("error");
      setTextNotify(
        "Course requires thumbnail, please upload image as course's thumbnail"
      );
      setIsNotify(true);
    }
  };

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify !== "info") {
        setIsNotify(false);
        setTextNotify("");
        if (typeNotify === "success") {
          navigate("/course/create/add-video");
        }
      }
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
          <p className="add-container-greet">CREATE A COURSE ✨</p>
          <div className="course-title">
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="Title..."
              required
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
            <p>{price} $</p>
          </div>
          <div className="course-tags">
            <p>Tags related to course 📎</p>
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
              required
            />
          </div>
          <div className="course-image">
            <p>Upload thumbnail 🏞:</p>
            <div>
              <img
                src={imageUrl}
                onClick={(e) => document.getElementById("fileID").click()}
                alt=""
                className="thumbnail-image"
              />
              <input
                type="file"
                id="fileID"
                onChange={handleChangeFile}
                style={{ display: "none" }}
                accept="image/*"
              />
            </div>
          </div>
          <div className="input-row">
            <button className="confirm" onClick={handleConfirm}>
              CONFIRM ⏯
            </button>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default CourseAddPage;
