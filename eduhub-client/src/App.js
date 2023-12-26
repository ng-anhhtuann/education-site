import "./App.css";
import {
  BrowserRouter as Router,
  Routes as Switch,
  Route,
} from "react-router-dom";
import Footer from "./shared/components/common/footer/Footer";
import Welcome from "./pages/welcome/Welcome";
import Login from "./pages/login/login";
import Register from "./pages/register/register";
import Home from "./pages/home/Home";
import CoursePage from "./pages/course/CoursePage";
import Profile from "./pages/profile/Profile";
import VideoPage from "./pages/video/VideoPage";
import CourseAddPage from "./pages/createCourse/CourseAdd";
import AddVideoCoursePage from "./pages/uploadCourseVideo/AddVideoPage";

function App() {
  return (
    <Router>
      <div>
        <Switch>
          <Route exact path="/" element={<Welcome />} />
          <Route exact path="/home" element={<Home />} />
          <Route exact path="/login" element={<Login />} />
          <Route exact path="/register" element={<Register />} />
          <Route exact path="/course" element={<CoursePage/>} />
          <Route exact path="/profile" element={<Profile/>} />
          <Route exact path="/video" element={<VideoPage/>} />
          <Route exact path="/course/create" element={<CourseAddPage/>} />
          <Route exact path="/course/create/add-video" element={<AddVideoCoursePage/>} />
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
