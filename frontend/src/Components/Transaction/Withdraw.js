import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { FaEye, FaEyeSlash } from 'react-icons/fa'; // Import eye and eye-slash icons

const Withdraw = () => {
  const [showPassword, setShowPassword] = useState(false);

  const createSpans = (text) =>
    text.split(' ').map((word, index) => (
      <motion.span
        key={index}
        initial={{ opacity: 0, y: 50 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1, ease: 'backOut', delay: index * 0.1 }}
      >
        {word}
      </motion.span>
    ));

  const formFields = [
    {
      label: 'Amount',
      type: 'text',
      id: 'amount',
      delay: 0.3,
    },
    { label: 'Email', type: 'email', id: 'email', delay: 0.4 },
    {
      label: 'Password',
      type: showPassword ? 'text' : 'password',
      id: 'password',
      delay: 0.5,
    },
  ];

  return (
    <div>
      <img
        src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80'
        alt='Background Image Not Found'
      />
      <section className='content fdc faic'>
        <h1>{createSpans('Withdraw Money.')}</h1>
        <motion.form
          id='form'
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 1, ease: 'backOut', delay: 0.2 }}
        >
          {formFields.map(({ label, type, id, delay }) => (
            <motion.div
              className='input-div relative'
              key={id}
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, ease: 'backOut', delay }}
            >
              <label htmlFor={id}>{label}</label>
              <input required type={type} id={id} name={id} />
              {id === 'password' && (
                <button
                  type='button'
                  onClick={() => setShowPassword(!showPassword)}
                  className='password-toggle absolute'
                >
                  {showPassword ? <FaEyeSlash /> : <FaEye />}
                </button>
              )}
            </motion.div>
          ))}
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 1, ease: 'backOut', delay: 0.6 }}
          >
            <button type='submit'>Withdraw</button>
          </motion.div>
        </motion.form>
      </section>
    </div>
  );
};

export default Withdraw;
