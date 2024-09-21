import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import { MYAXIOS } from '../Helper';

const Withdraw = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({ amount: '', email: '', password: '' });
  const [otp, setOtp] = useState('');
  const [showOtpInput, setShowOtpInput] = useState(false);
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [isOtpLoading, setIsOtpLoading] = useState(false);

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({ ...formData, [id]: value });
    setErrors({ ...errors, [id]: '' });  // Clear error when the user types
  };

  const validateForm = () => {
    let formErrors = {};
    if (!formData.amount || formData.amount <= 0) formErrors.amount = 'Please enter a valid amount.';
    if (!formData.email || !/\S+@\S+\.\S+/.test(formData.email)) formErrors.email = 'Please enter a valid email address.';
    if (!formData.password) formErrors.password = 'Please enter your password.';
    setErrors(formErrors);
    return Object.keys(formErrors).length === 0;
  };

  const handleRequestOtp = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    setIsOtpLoading(true);

    try {
      await MYAXIOS.get('/check-user', {
        params: { email: formData.email }
      });

      let isCorrect = await MYAXIOS.get('/check-password', {
        params: { email: formData.email, password: formData.password }
      });

      if (isCorrect.data === true) {
        await MYAXIOS.post('/send-transaction-otp', { email: formData.email });
        setShowOtpInput(true);
        console.log("OTP Sent");
      } else {
        alert("Incorrect Password! Try Again");
      }
    } catch (error) {
      setIsOtpLoading(false);
      console.error('Error checking user or sending OTP:', error);
      alert("Please Create an Account First.\n\nIf The Error Continues Please Get Help By Contacting Us.");
    } finally {
      setIsOtpLoading(false);
    }
  };

  const handleOtpSubmit = async (e) => {
    e.preventDefault();
    if (!otp) {
      setErrors({ otp: 'Please enter the OTP.' });
      return;
    }

    setIsLoading(true);

    try {
      const email = formData.email;

      console.log("\nEmail : " + email + "\nOTP : " + otp);
      console.log("Verifying OTP...");
      const response = await MYAXIOS.post('/verify-trasaction-otp', {
        email: email,
        otp: otp
      });

      console.log("DONE OTP VERIFICATION");
      alert('OTP verification successful, Initiating Withdraw');

      if (response.data.success) {
        const depositResponse = await MYAXIOS.post('/withdraw', {
          email: email,
          amount: formData.amount
        });

        if (depositResponse.data.success) {
          alert("₹" + formData.amount + " has Been Withdrawn From Your Bank Account")
          alert('Redirecting to Home Page...');
          window.location.href = '/';
        } else {
          alert('Withdraw failed');
        }
      } else {
        alert('Incorrect OTP, please try again');
      }
    } catch (error) {
      console.error('Something Went Wrong! Please Try Again Later\n\nWhat went Wrong:', error);
      alert('Something Went Wrong! Please Try Again Later');
    } finally {
      setIsLoading(false);
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
    { label: 'Amount', type: 'number', id: 'amount', delay: 0.3, min: 1 },
    { label: 'Email', type: 'email', id: 'email', delay: 0.4 },
    { label: 'Password', type: showPassword ? 'text' : 'password', id: 'password', delay: 0.5 },
  ];

  return (
    <div>
      <img
        src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80'
        alt='Background Image Not Found'
      />
      <section className='content fdc faic'>
        <h1>{createSpans('Withdraw Money.')}</h1>
        {!showOtpInput ? (
          <div id='wrapper'>
            <motion.form
              id='form'
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, ease: 'backOut', delay: 0.2 }}
              onSubmit={handleRequestOtp}
            >
              {formFields.map(({ label, type, id, delay, min }) => (
                <motion.div
                  className='input-div relative'
                  key={id}
                  initial={{ opacity: 0, y: 50 }}
                  animate={{ opacity: 1, y: 0 }}
                  transition={{ duration: 1, ease: 'backOut', delay }}
                >
                  <label htmlFor={id}>{label}</label>
                  <input
                    required
                    type={type}
                    id={id}
                    name={id}
                    value={formData[id]}
                    onChange={handleChange}
                    {...(min ? { min } : {})}
                  />
                  {errors[id] && <span className="error-message">{errors[id]}</span>}
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
                <button type='submit' disabled={isOtpLoading}>
                  {isOtpLoading ? 'Sending OTP...' : 'Request OTP'}
                </button>
              </motion.div>
            </motion.form>
            {isOtpLoading && (
              <div className='loading-overlay'>
                <div className='loading-spinner'></div>
                <p>Sending OTP...</p>
              </div>
            )}
          </div>
        ) : (
          <>
            <motion.form
              id='otp-form'
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 1, ease: 'backOut', delay: 0.2 }}
              onSubmit={handleOtpSubmit}
            >
              <motion.div
                className='input-div relative fdc'
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3, ease: 'backOut', delay: 0.3 }}
              >
                <label htmlFor='otp'>Please Enter The OTP You Have Received at <i style={{ color: "lightgreen" }}>{formData.email}</i></label>
                <div className='center1 m-gap'>
                  <label htmlFor='otp'>OTP</label>
                  <label htmlFor='otp'>:</label>
                  <input required type='text' id='otp' name='otp' value={otp} onChange={(e) => setOtp(e.target.value)} />
                  {errors.otp && <span className="error-message">{errors.otp}</span>}
                </div>
              </motion.div>
              <motion.div
                initial={{ opacity: 0, y: 50 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.4, ease: 'backOut', delay: 0.4 }}
              >
                <button type='submit' disabled={isLoading}>
                  {isLoading ? 'Verifying OTP...' : 'Verify OTP'}
                </button>
              </motion.div>
            </motion.form>
            <div className="container">
              <span id="money-deposited">Money Withdrawn Successfully</span>
            </div>
          </>
        )}
      </section>
    </div>
  );
};

export default Withdraw;
