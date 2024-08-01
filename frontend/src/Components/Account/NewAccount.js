import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { FaEye, FaEyeSlash } from 'react-icons/fa'; // Import eye and eye-slash icons
import { MYAXIOS } from '../Helper';
import '../Home/Home.css';

const NewAccount = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [formData, setFormData] = useState({
    id: null,
    account_holder_name: "",
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    console.log("name :" + e.target.name);
    console.log("value :" + e.target.value);
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (formData.id && formData.email) {
        // Update existing student
        // Print Message, Data Already Exists
      } else {
        // Create new student
        console.log(formData.account_holder_name);
        console.log(formData.email);
        await MYAXIOS.post("/create", formData);
        // Reset the form and fetch updated data
        setFormData({ id: null, account_holder_name: "", email: "", password: "" });
        let inputTag = document.querySelectorAll("input");
        inputTag.forEach((input) => {
          input.value = "";
        });
        document.querySelector("#account-created").classList.add("animate");
        setTimeout(() => {
          document.querySelector("#account-created").classList.remove("animate");
        }, 6500);
        setErrorMessage(""); // Clear any previous error message
      }
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setErrorMessage("Email is already registered.");
      } else {
        console.error("Error submitting form: ", error);
      }
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
        alt='Background'
      />
      <section className='content'>
        <h1>{createSpans('Create New Account.')}</h1>
        <motion.form
          id='form'
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 1, ease: 'backOut', delay: 0.5 }}
          onSubmit={handleSubmit}
          className='absolute'
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
          ))}
          <motion.div
            initial={{ opacity: 0, y: 50 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 1, ease: 'backOut', delay: 0.9 }}
          >
            <button id='submit-button' type='submit'>Create Account</button>
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
          Already Have an Account? <span style={{color: "aquamarine"}}>Login!</span>
        </motion.a>
        <motion.a
          href='/login'
          initial={{ opacity: 0, y: 25 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.2, ease: 'backOut', delay: 1 }}
          id='account-created'
          className='faic s-gap'
        >
          Account Created
          <span className='relative'><i class="center2 ri-check-double-fill"></i></span>
        </motion.a>
      </section>
    </div>
  );
};

export default NewAccount;