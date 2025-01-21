import logo from './logo.svg';
import './App.css';
import Header from './header/Header';
import Login from './login/Login';
import Redirect from "./login/Redirect";
import AddInformation from './login/AddInformation';
import Home from './home/Home';
import Booklog from './booklog/Booklog';
import "react-datepicker/dist/react-datepicker.css";
import { Route, Routes, BrowserRouter } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/home" element={<Home />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/redirect" element={<Redirect />}></Route>
        <Route path="/addinformation" element={<AddInformation />}></Route>
        <Route path="/booklog" element={<Booklog />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
