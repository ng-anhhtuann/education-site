import "./App.css"
import Header from "./shared/components/common/header/Header"
import { BrowserRouter as Router, Switch, Route } from "react-router-dom"
import Contact from "./pages/contact/Contact"
import Footer from "./shared/components/common/footer/Footer"
import Home from "./pages/home/Home"
import Login from "./pages/login/login"
import Register from "./pages/register/register"
function App() {
  return (
    <>
      <Router>
        <Header />
        <link 
          rel="stylesheet" 
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
          crossOrigin="anonymous"
        />

        <Switch>
          <Route exact path='/' component={Home} />
          <Route exact path='/contact' component={Contact} />
          <Route exact path='/login' component={Login} />
          <Route exact path='/register' component={Register} />
        </Switch>
        <Footer />
      </Router>
    </>
  )
}

export default App
