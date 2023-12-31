import React, { useState, useMemo } from "react";
import "./courseItemEdit.css";
import Toast from "../../Toast";
import FileService from "../../../service/fileService";
import CourseService from "../../../service/courseService";
import ImageService from "../../../service/imageService";

const CourseItemEdit = ({ data, onClick }) => {
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const [title, setTitle] = useState(data.title);
  const [description, setDescription] = useState(data.description);
  const [tags, setTags] = useState(data.tagList);
  const [tagInput, setTagInput] = useState("");
  const [showTagInput, setShowTagInput] = useState(false);
  const [price, setPrice] = useState(data.price);

  const [imageFile, setImageFile] = useState(null);
  const [imageUrl, setImageUrl] = useState(
    data.thumbnailUrl ||
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

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify !== "info") {
        setIsNotify(false);
        setTextNotify("");
        if (typeNotify === "success") {
            window.location.reload();
        }
      }
    }, 2000);
  }

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
            setTextNotify(
              res.data.errors ||
                "Something went wrong while uploading the image"
            );
            setIsNotify(true);
            return;
          }

          setImageUrl(res.data.data.fileUrl);
          const req = {
            id: data.id,
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
                setTextNotify(
                  response.data.errors ||
                    "Something went wrong when updating..."
                );
                setIsNotify(true);
              }

              imgReq.ownerId = sessionStorage.getItem("COURSE_CLICK");

              ImageService.createOrUpdateImage(imgReq).then((imgRes) => {
                if (imgRes.data.status !== 200) {
                  setTypeNotify("error");
                  setTextNotify(
                    imgRes.data.errors || "Something went wrong with image.."
                  );
                  setIsNotify(true);
                } else {
                  setTypeNotify("success");
                  setTextNotify("Updated successfully!");
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
      const req = {
        id: data.id,
        price,
        title,
        tagList: tags,
        studentCount: 0,
        teacherId: sessionStorage.getItem("ID"),
        description,
        thumbnailUrl: imageUrl,
      };
      CourseService.createOrUpdateCourse(req)
        .then((response) => {
          if (response.data.status !== 200) {
            setTypeNotify("error");
            setTextNotify(
              response.data.errors || "Something went wrong when updating..."
            );
            setIsNotify(true);
          } else {
            setTypeNotify("success");
            setTextNotify("Updated successfully!");
            setIsNotify(true);
          }
        })
        .catch((err) => {
          setTypeNotify("error");
          setTextNotify("Something went wrong while updating the course");
          setIsNotify(true);
        });
    }
  };

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <div className="course-edit-container" onClick={onClick}>
      {toast}
      <p className="course-edit-title">EDIT COURSE ✏️</p>
      <div className="image-container">
        <img
          src={imageUrl}
          onClick={(e) => document.getElementById("fileID").click()}
          alt=""
        />
        <input
          type="file"
          id="fileID"
          onChange={handleChangeFile}
          style={{ display: "none" }}
          accept="image/*"
        />
      </div>
      <div className="text-info-container">
        <div className="course-edit-text">
          <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            placeholder="Title..."
            required
          />
        </div>
        <div className="course-edit-text">
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Description here..."
            required
          />
        </div>
        <div className="course-tags">
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
        <button className="course-edit-confirm" onClick={handleConfirm}>
          UPDATE INFORMATION ⏯
        </button>
      </div>
    </div>
  );
};

export default CourseItemEdit;
