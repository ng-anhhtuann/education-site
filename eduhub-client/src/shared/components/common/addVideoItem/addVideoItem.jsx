import React, { useState, useMemo } from "react";
import "./addVideo.css";
import tick from "./tick.png";
import Toast from "../../Toast";
import FileService from "../../../service/fileService";
import VideoService from "../../../service/videoService";

const AddVideoItem = () => {
  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [videoFile, setVideoFile] = useState(null);
  const [videoUrl, setVideoUrl] = useState(
    "https://firebasestorage.googleapis.com/v0/b/edu-hub-3772f.appspot.com/o/thumbnail.png?alt=media"
  );

  const handleChangeFile = (e) => {
    const reader = new FileReader();
    reader.onload = function (event) {
      if (e.target.files && e.target.files[0]) {
        setVideoFile(e.target.files[0]);
        setVideoUrl(tick);
      }
    };
    reader.readAsDataURL(e.target.files[0]);
  };

  const handleConfirm = (e) => {
    e.preventDefault();

    if (videoFile) {
      FileService.uploadVideo(videoFile)
        .then((res) => {
          if (res.data.status !== 200) {
            setTypeNotify("error");
            setTextNotify(res.data.errors || "Something went wrong");
            setIsNotify(true);
            return;
          }
          console.log(res);
          setVideoUrl(tick);
          const req = {
            title,
            name: res.data.data.fileName,
            url: res.data.data.fileUrl,
            courseId: sessionStorage.getItem("COURSE_CLICK"),
            description,
          };

          VideoService.createOrUpdateVideo(req)
            .then((res) => {
              console.log(res);
              if (res.data.status !== 200) {
                setTypeNotify("error");
                setTextNotify(res.data.errors || "Something went wrong");
                setIsNotify(true);
                return;
              }

              setTypeNotify("success");
              setTextNotify("Created successfully!");
              setIsNotify(true);
            })
            .catch((err) => {
              console.error("Error creating course:", err);
              setTypeNotify("error");
              setTextNotify("Something went wrong");
              setIsNotify(true);
            });

          console.log({ req });
        })
        .catch((err) => {
          console.error("Error uploading video:", err);
          setTypeNotify("error");
          setTextNotify("Something went wrong");
          setIsNotify(true);
        });
    } else {
      setTypeNotify("error");
      setTextNotify("Video file is required");
      setIsNotify(true);
    }
  };

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify === "success") {
        window.location.reload();
      }
      setIsNotify(false);
      setTextNotify("");
    }, 2000);
  }

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <div className="add-video-container">
      {toast}
      <div className="add-video-parent">
        <img
          src={videoUrl}
          onClick={(e) => document.getElementById("fileID").click()}
          alt=""
          className="add-video"
        />
        <input
          type="file"
          id="fileID"
          onChange={handleChangeFile}
          style={{ display: "none" }}
          accept="video/*"
        />
      </div>
      <div className="add-video-text-container">
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Title..."
        />
        <input
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Description here..."
        />
        <button className="add-video-confirm" onClick={handleConfirm}>
          Confirm
        </button>
      </div>
    </div>
  );
};

export default AddVideoItem;
