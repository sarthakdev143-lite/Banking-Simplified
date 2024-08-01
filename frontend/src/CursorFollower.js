import React, { useState, useEffect, useRef } from 'react';
import { gsap } from 'gsap';
import './index.css';

const CursorFollower = () => {
    const [position, setPosition] = useState({ x: 0, y: 0 });
    const [isHovered, setIsHovered] = useState(false);
    const followerRef = useRef(null);

    const handleMouseMove = (event) => {
        setPosition({ x: event.clientX, y: event.clientY });
    };

    const follower = document.querySelector(".follower");

    window.addEventListener('mouseenter', () => {
        console.log("mouse entered");
        follower.style.scale = 1;
        follower.style.opacity = 1;
    })
    window.addEventListener('mouseleave', () => {
        console.log("mouse left");
        follower.style.scale = 0;
        follower.style.opacity = 0;
    })

    useEffect(() => {
        const handleMouseOver = () => {
            setIsHovered(true);
        };

        const handleMouseOut = () => {
            setIsHovered(false);
        };

        const follower = followerRef.current;

        const updatePosition = () => {
            gsap.to(follower, {
                x: position.x,
                y: position.y,
                duration: 0.2,
                ease: "power3.out"
            });
        };

        updatePosition();

        window.addEventListener('mousemove', handleMouseMove);

        // Add event listeners to specific elements
        const elements = document.querySelectorAll('a, button, .hover-target'); // Add classes or tags you want to target
        elements.forEach((el) => {
            el.addEventListener('mouseover', handleMouseOver);
            el.addEventListener('mouseout', handleMouseOut);
        });

        return () => {
            window.removeEventListener('mousemove', handleMouseMove);
            elements.forEach((el) => {
                el.removeEventListener('mouseover', handleMouseOver);
                el.removeEventListener('mouseout', handleMouseOut);
            });
        };
    }, [position]);

    useEffect(() => {
        const follower = followerRef.current;

        if (isHovered) {
            gsap.to(follower, {
                scale: 1.5,
                duration: 0.2,
                ease: "power3.out",
            });
        } else {
            gsap.to(follower, {
                scale: 1,
                duration: 0.2,
                ease: "power3.out",
            });
        }
    }, [isHovered]);

    return (
        <div
            className="follower"
            ref={followerRef}
        />
    );
};

export default CursorFollower;