import React from 'react';
import { motion } from 'framer-motion';
import './Home.css';

const Home = () => {
    const createSpans = (text) => {
        return text.split(' ').map((word, index) => (
            <motion.span
                key={index}
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 1, ease: "backOut", delay: index * 0.1 }}
            >
                {word}
            </motion.span>
        ));
    };

    return (
        <div>
            <img
                src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80'
                alt='Background Not Found'
            />
            <section className='content'>
                <h1>
                    {createSpans('Future of Banking is Here.')}
                </h1>
            </section>
        </div>
    );
}

export default Home;
