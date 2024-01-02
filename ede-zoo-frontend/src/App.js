import { Outlet, Routes, Route, BrowserRouter, Link, NavLink } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import Food from './components/food';
import FoodDetail from './components/food-detail';
import Login from './components/login';
import Logout from './components/logout';
import { useEffect } from 'react';
import { gapi } from  'gapi-script';
import ConfigData from './config.json';

function NavBar() {
  const styles = {
    navbar: {
      color: 'blue',
      fontWeight: "bold",
    }
  }

  useEffect(() => {
    function start() {
      gapi.client.init({
        clientId: ConfigData.clientId,
        scope: ""
      }).then(() => {
        // Get the Google Auth instance
        const authInstance = gapi.auth2.getAuthInstance();
          
        // Check if the user is signed in
        if (authInstance.isSignedIn.get()) {
          // Retrieve the current user
          const currentUser = authInstance.currentUser.get();
          
          // Get the ID token
          const idToken = currentUser.getAuthResponse().id_token;
          
          // Use the ID token as needed (e.g., send it to the backend)
          console.log('ID Token:', idToken);
          
          // You can now send this ID token to your backend for verification or further use
          // For example, you might send it to your Java API Gateway for authentication
        } else {
          console.log("not signed in?");
        }
      });
    };

    gapi.load('client:auth2', start);
  }, []);

  return (
    <nav>
      <ul>
        <li><NavLink to={"/"} className={"nav-link"}>Home</NavLink></li>
        <li><NavLink to={"/food"} className={"nav-link"}>Food</NavLink></li>
        <li><NavLink to={"/animal"} className={"nav-link"}>Animals</NavLink></li>
        <li><NavLink to={"/enclosure"} className={"nav-link"}>Enclosures</NavLink></li>
        <li><Login/></li>
        <li><Logout/></li>
      </ul>
    </nav>
  )
}

function Main() {
  return (
    <div className="content">
    <Routes>
      <Route path={'/'} element={<Home/>} />
      <Route path={'/food'} element={<Outlet />}>
        <Route index element={<Food />}/>
        <Route path={':id'} element={<FoodDetail />}/>
      </Route>
    </Routes>
  </div>
  )
}

function App() {
  return (
    <BrowserRouter>
      <NavBar></NavBar>
      <Main></Main>
    </BrowserRouter>
  );
}

export default App;
