import axios from 'axios';

// Create an instance of axios with the desired configuration
const API = axios.create({
    baseURL: 'https://eduhub.fly.dev/',
    headers: {
        Accept: 'application/json',
    },
});

// Add a response interceptor
API.interceptors.response.use(
    (res) => {
        return res;
    },
    (err) => {
        throw err;
    },
);

export default API;