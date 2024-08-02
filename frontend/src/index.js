import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Layout from './Layout';
import Home from './Components/Home/Home';
import NewAccount from './Components/Account/NewAccount';
import Account from './Components/Account/Account.js';
import Login from './Components/Account/Login';
import NoPage from './Components/Account/NoPage';
import Deposit from './Components/Transaction/Deposit';
import Withdraw from './Components/Transaction/Withdraw';
import CursorFollower from './CursorFollower';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <CursorFollower/>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="create" element={<NewAccount />} />
          <Route path="login" element={<Login />} />
          <Route path="deposit" element={<Deposit />} />
          <Route path="withdraw" element={<Withdraw />} />
          <Route path="account" element={<Account />} />
          <Route path="*" element={<NoPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals(console.log);
