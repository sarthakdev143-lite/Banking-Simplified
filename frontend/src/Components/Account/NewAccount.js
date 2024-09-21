import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { MYAXIOS } from '../Helper';
import '../Home/Home.css';

const NewAccount = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    account_holder_name: '',
    email: '',
    password: '',
  });
  const [otp, setOtp] = useState('');
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [isLoading] = useState(false);
  const [isOtpLoading, setIsOtpLoading] = useState(false);

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleOtpRequest = async (e) => {
    e.preventDefault();
    try {
      console.log("Verifying OTP..");
      const response = await MYAXIOS.post('/verify-otp', {
        user: formData,
        otp: otp
      });
      if (response.status === 200) {
        document.querySelector('#account-created').classList.add('animate');
        setErrorMessage('');
        setFormData({ account_holder_name: '', email: '', password: '' });
        setOtp('');
        document.querySelectorAll('input').forEach(input => {
          input.value = '';
        });
        setShowOtpInput(false);
      }
    } catch (error) {
      console.error('Error handling OTP request: ', error);
      if (error.response && error.response.status === 409) {
        setErrorMessage('User already exists.');
      } else {
        setErrorMessage('Error verifying OTP.');
        setTimeout(() => {
          setErrorMessage('');
        }, 7500);
      }
    }
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsOtpLoading(true);
    try {
      const response = await MYAXIOS.post('/create', { email: formData.email });
      if (response.status === 200) {
        setShowOtpInput(true);
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setErrorMessage('Email is already registered.');
      } else {
        setErrorMessage('Error creating account.');
      }
    } finally {
      setIsOtpLoading(false);
    }
  };

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
      label: 'Account-Holder Name',
      type: 'text',
      id: 'account_holder_name',
      delay: 0.6,
    },
    { label: 'Email', type: 'email', id: 'email', delay: 0.7 },
    {
      label: 'Password',
      type: showPassword ? 'text' : 'password',
      id: 'password',
      delay: 0.8,
    },
  ];

  return (
    <div>
      <img
        src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80'
        alt='Background Not Found'
      />
      <section className='content'>
        <h1>{createSpans('Create New Account.')}</h1>
        <motion.form
          id='form'
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 1, ease: 'backOut', delay: 0.5 }}
          onSubmit={showOtpInput ? handleOtpRequest : handleSubmit}
          className='absolute'
        >
          {
            showOtpInput ? null :
              formFields.map(({ label, type, id, delay }) => (
                <motion.div
                  className='input-div relative'
                  key={id}
                  initial={{ opacity: 0, y: 50 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 1, ease: 'backOut', delay }}
                >
                  <label htmlFor={id}>{label}</label>
                  <input required type={type} id={id} name={id} onChange={handleInputChange} />
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
              ))
          }
          {showOtpInput && (
            <motion.div
              className='input-div relative'
            >
              <div className="flex-column">
                <label htmlFor='otp' id='otp-statement'>Please Enter The OTP You Have Received at <i className='highlight'>{formData.email}</i></label>
                <div className="flex">
                  <label htmlFor='otp'>OTP</label>
                  <input
                    required
                    type='text'
                    id='otp'
                    name='otp'
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                  />
                </div>
              </div>
            </motion.div>
          )}
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 1, ease: 'backOut', delay: 0.9 }}
          >
            <button id='submit-button' type='submit' disabled={isLoading || isOtpLoading}>
              {showOtpInput ? 'Create Account' : 'Request OTP'}
            </button>
          </motion.div>
        </motion.form>
        {errorMessage && (
          <motion.div
            className={`error-message ${errorMessage ? 'show' : ''}`}
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.2, ease: 'linear', delay: 0 }}
          >
            {errorMessage}
          </motion.div>
        )}
        <motion.a
          href='/login'
          initial={{ opacity: 0, y: 25 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.2, ease: 'backOut', delay: 1 }}
          id='login'
        >
          Already Have an Account? <span style={{ color: 'aquamarine' }}>Login!</span>
        </motion.a>
        {isOtpLoading && (
          <div className='loading-overlay'>
            <div className='loading-spinner'></div>
            <p>Sending OTP...</p>
          </div>
        )}
        <motion.a
          href='/login'
          initial={{ opacity: 0, y: 25 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.2, ease: 'backOut', delay: 1 }}
          id='account-created'
          className='faic s-gap'
        >
          Account Created
          <span className='relative'><i className='center2 ri-check-double-fill'></i></span>
        </motion.a>
      </section>
    </div >
  );
};

export default NewAccount;
