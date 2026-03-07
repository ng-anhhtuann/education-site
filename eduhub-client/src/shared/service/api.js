import axios from "axios";

const API_BASE_URL = process.env.REACT_APP_API_URL || "http://localhost:8080/";

const API = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    Accept: "application/json",
  },
});

const FileAPI = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    Accept: "application/json",
    "Content-Type": "multipart/form-data",
  },
});

// Add response interceptors for both instances if needed
API.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    throw err;
  }
);

FileAPI.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    throw err;
  }
);

export { API, FileAPI };
