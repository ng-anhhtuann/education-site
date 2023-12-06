import API from './api';

const CourseService = {
    searchCourseByCondition: (e) => {
        return API.post('course/list', e)
            .then((res) => {
                console.log({res});
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err.response.data;
            });
    },

    createOrUpdateCourse: (e) => {
        return API.post('course/edit', e)
            .then(({ res }) => {
                console.log({res});
                setHeadersAndStorage(res.data.id);
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err.response.data;
            });
    },

    getCourseById: (id) => {
        return API.get(`course/detail/${id}`)
            .then(({ res }) => {
                console.log({res});
                return res;
            })
            .catch((err) => {
                console.log(err);
                throw err.response.data;
            });
    },

    
};

const setHeadersAndStorage = (id) => {
    sessionStorage.setItem('CURRENT_COURSE', id); // Set current course in-state in session storage
};

export default CourseService;