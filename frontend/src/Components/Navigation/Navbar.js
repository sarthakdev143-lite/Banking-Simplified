import React from 'react';
import { NavLink } from 'react-router-dom'; // Import NavLink
import './Navbar.css';
import '../littles.css';

const Navbar = () => {
    return (
        <header className='absolute w-full'>
            <nav className='flex w-full center1'>
                <ul className='gap center1 h-20'>
                    <li className='faic'>
                        <NavLink id='link' to="/create" className='faic s-gap hover-target' activeClassName="active">
                            <span>Create</span>
                            <i className="ri-user-add-line"></i>
                        </NavLink>
                    </li>
                    <li className='faic'>
                        <NavLink id='link' to="/deposit" className='hover-target' activeClassName="active">
                            <span>Deposit</span>
                        </NavLink>
                    </li>
                    <li className='faic'>
                        <NavLink id='link' to="/" className='faic s-gap hover-target' activeClassName="active" exact>
                            <span>Home</span>
                            <i className="ri-home-4-line"></i>
                        </NavLink>
                    </li>
                    <li className='faic'>
                        <NavLink id='link' to="/withdraw" className='hover-target' activeClassName="active">
                            <span>Withdraw</span>
                        </NavLink>
                    </li>
                    <li className='faic'>
                        <NavLink id='link' to="/account" className='faic s-gap hover-target' activeClassName="active">
                            <span>My Account</span>
                            <i className="ri-user-3-line"></i>
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </header>
    );
}

export default Navbar;