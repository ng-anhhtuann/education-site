import "./App.css";
import { BrowserRouter as Router, Routes as Switch, Route } from "react-router-dom";
import Contact from "./pages/contact/Contact";
import Footer from "./shared/components/common/footer/Footer";
import Home from "./pages/home/Home";
import Login from "./pages/login/login";
import Register from "./pages/register/register";

function App() {
  return (
    <Router>
      <div>
        <Switch>
          <Route exact path="/" element={<Home/>} />
          <Route exact path="/contact" element={<Contact/>} />
          <Route exact path="/login" element={<Login/>} />
          <Route exact path="/register" element={<Register/>} />
        </Switch>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
