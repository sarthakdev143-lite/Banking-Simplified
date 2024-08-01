import React from 'react';
import { Link } from 'react-router-dom';

const NoPage = () => {
  return (
    <div>
      <img src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80' alt='Background Image Not Found' />
      <section className='content'>
        <h1>404 - Page Not Found.</h1>
        <Link to='/'>Go Home</Link>
      </section>
    </div>
  );
};

export default NoPage;