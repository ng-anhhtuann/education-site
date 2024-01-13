import { FileAPI, API } from "./api";

const FileService = {
  uploadImage: (file) => {
    const formData = new FormData();
    formData.append("image", file);
    formData.append("file", file);

    return FileAPI.post("file/upload-image?image", formData)
      .then((res) => {
        if (res.data.status === 200) {
          const fileName = res.data.data.fileName;
          setStorage(fileName)
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  uploadVideo: (file) => {
    const formData = new FormData();
    formData.append("video", file);
    formData.append("file", file);

    return FileAPI.post("file/upload-video?video", formData)
      .then((res) => {
        if (res.data.status === 200) {
          const fileName = res.data.data.fileName;
          setStorage(fileName)
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  deleteFileByFileName: (fileName) => {
    return API.delete("file/delete-file", {
      params: {
        fileName: fileName,
      },
    })
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },
};

const setStorage = (fileName) => {
  sessionStorage.setItem("CURRENT_FILE_NAME", fileName);
};

export default FileService;
