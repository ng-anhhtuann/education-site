import { API } from "./api";

const CourseService = {
  searchCourseByCondition: (e) => {
    return API.post("course/list", e)
      .then((res) => {
        if (res.data.status === 200) {
          sessionStorage.setItem("SEARCH_COURSE", JSON.stringify(e));
          sessionStorage.setItem(
            "SEARCH_RESULT_LIST",
            JSON.stringify(res.data.data.datas) || []
          );
          sessionStorage.setItem(
            "SEARCH_RESULT_COUNT",
            res.data.data.totalData || 0
          );
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  createOrUpdateCourse: (e) => {
    return API.post("course/edit", e)
      .then(({ res }) => {
        console.log({ res });
        setStorage(res.data.id);
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err.response.data;
      });
  },

  getCourseById: (id) => {
    return API.get(`course/detail/${id}`)
      .then((res) => {
        console.log(res)
        if (res.data.status === 200) {
          const course = JSON.stringify(res.data.data);
          setStorage(course);
        }
        return res;
      })
      .catch((err) => {
        console.error(err);
        throw err;
      });
  },
};

const setStorage = (data) => {
  sessionStorage.setItem("CURRENT_COURSE", data); // Set current course in-state in session storage
};

export default CourseService;
