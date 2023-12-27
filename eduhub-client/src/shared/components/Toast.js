import React from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

function Toast(props) {
  const { position = "top-center", isNotify, text, type } = props;

  const notify = () => {
    if (type === "success") {
      return toast.success(text);
    } else if (type === "info") {
      return toast.info(text);
    }
    return toast.error(text);
  };

  if (isNotify) {
    notify();
  } else {
    return;
  }

  return (
    <div>
      <ToastContainer
        position={position}
        autoClose={1000}
        limit={1}
        hideProgressBar={false}
        rtl={false}
        theme="light"
      />
    </div>
  );
}

export default Toast;
