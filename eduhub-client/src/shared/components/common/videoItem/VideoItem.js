import React, { useState, useMemo, Fragment } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import "./video.css";
import thumbnail from "./player.webp";
import Toast from "../../Toast";
import VideoService from "../../../service/videoService";
import { useNavigate } from "react-router-dom";
import SubscriptionService from "../../../service/subscriptionService";

const VideoItem = ({ video }) => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);

  const [isNotify, setIsNotify] = useState(false);
  const [textNotify, setTextNotify] = useState("");
  const [typeNotify, setTypeNotify] = useState("");
  const search = {
    page: 1,
    pageSize: 0,
    searchType: "FIELD",
    params: {
      course_id: sessionStorage.getItem("COURSE_CLICK"),
    },
  };

  const onClick = (e) => {
    const course = JSON.parse(sessionStorage.getItem("CURRENT_COURSE"));
    const studentId = sessionStorage.getItem("ID");
    const courseId = course.id;
    const teacherId = course.teacherId;

    SubscriptionService.checkSubscription({ studentId, courseId }).then(
      (res) => {
        if (!res.data.data && studentId !== teacherId) {
          setOpen(true);
          return;
        } else {
          VideoService.searchVideoByCondition(search)
            .then((res) => {
              if (res.data.status !== 200) {
                setTypeNotify("error");
                setTextNotify(res.data.errors || "Something went wrong");
                setIsNotify(true);
                return;
              }
            })
            .catch((err) => {
              setTypeNotify("error");
              setTextNotify("Something went wrong");
              setIsNotify(true);
            });
          VideoService.getVideoById(video.id);
          navigate("/video");
        }
      }
    );
  };

  const handleConfirm = () => {
    const course = JSON.parse(sessionStorage.getItem("CURRENT_COURSE"));
    const studentId = sessionStorage.getItem("ID");
    const courseId = course.id;

    SubscriptionService.createSubscription({ studentId, courseId }).then(
      (res) => {
        if (res.data.status !== 200) {
          setTypeNotify("error");
          setTextNotify(res.data.errors || "Something went wrong");
          setIsNotify(true);
          return;
        } else {
          setTypeNotify("success");
          setTextNotify("Purchase successfully!");
          setIsNotify(true);

          const data = JSON.parse(sessionStorage.getItem("USER_DATA"));
          data.balance = res.data.data.balance;
          sessionStorage.setItem("USER_DATA", JSON.stringify(data));

          VideoService.searchVideoByCondition(search)
            .then((res) => {
              if (res.data.status !== 200) {
                setTypeNotify("error");
                setTextNotify(res.data.errors || "Something went wrong");
                setIsNotify(true);
                return;
              }
            })
            .catch((err) => {
              setTypeNotify("error");
              setTextNotify("Something went wrong");
              setIsNotify(true);
            });
          VideoService.getVideoById(video.id);
          navigate("/video");
        }
      }
    );
    setOpen(false);
  };

  if (isNotify) {
    setTimeout(() => {
      if (typeNotify !== "info") {
        setIsNotify(false);
        setTextNotify("");
      }
    }, 2000);
  }

  const handleClose = (event) => {
    event.stopPropagation(); // Prevent event propagation
    setOpen(false);
  };  

  const toast = useMemo(() => {
    return <Toast isNotify={isNotify} text={textNotify} type={typeNotify} />;
  }, [isNotify, textNotify, typeNotify]);

  return (
    <div className="video-container" onClick={onClick}>
      <Fragment>
        <Dialog
          open={open}
          onClose={handleClose}
          aria-labelledby="alert-dialog-title"
          aria-describedby="alert-dialog-description"
        >
          <DialogTitle id="alert-dialog-title">
            {"Course need chargement~ 💎"}
          </DialogTitle>
          <DialogContent>
            <DialogContentText id="alert-dialog-description">
              Buy the course to access the resource. Do you want to buy the
              course?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>CANCEL ❌</Button>
            <Button onClick={handleConfirm}>BUY 💸</Button>
          </DialogActions>
        </Dialog>
      </Fragment>
      {toast}
      <div className="video-parent">
        <img src={thumbnail} alt="" />
      </div>
      <div className="video-text-container">
        <h1>{video.title}</h1>
        <h2>{video.description}</h2>
      </div>
    </div>
  );
};

export default VideoItem;
