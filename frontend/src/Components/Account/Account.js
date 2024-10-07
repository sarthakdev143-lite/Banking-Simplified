import React, { useEffect } from 'react'

const Account = () => {
  
  useEffect(() => {
    alert("This Part is in Under Construction.")
    window.location.href = '/';
  }, [])

  return (
    <div>
      <img src='https://images.unsplash.com/photo-1577915207354-8f266a3fc016?auto=format&fit=crop&w=1470&q=80' alt='Background Not Found' />
    </div>
  )
}

export default Account