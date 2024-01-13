import { API } from "./api";

const SubscriptionService = {
  searchCourseByCondition: (e) => {
    return API.post("subscription/list/course", e)
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

  createSubscription: (e) => {
    return API.post("subscription/edit", e)
      .then((res) => {
        if (res.data.status === 200) {
          const id = res.data.data.id;
          sessionStorage.setItem("COURSE_CLICK", id);
        }
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  checkSubscription: (e) => {
    return API.get("subscription/check/" + e.studentId + "/" + e.courseId)
      .then((res) => {
        return res;
      })
      .catch((err) => {
        console.log(err);
        throw err;
      });
  },

  searchUserByCondition: (e) => {
    return API.post("subscription/list/user", e)
      .then((res) => {
        if (res.data.status === 200) {
          sessionStorage.setItem("SEARCH_USER_SUBSCRIPTION", JSON.stringify(e));
          sessionStorage.setItem(
            "SEARCH_USER_RESULT_LIST",
            JSON.stringify(res.data.data.datas) || []
          );
          sessionStorage.setItem(
            "SEARCH_USER_RESULT_COUNT",
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
};

export default SubscriptionService;
