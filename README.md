# DeerootShop 🎹

A full-stack E-commerce platform for selling sheet music and MIDI files from my YouTube piano videos. Built to learn modern web development technologies and create a real-world application with production deployment.

**🔗 Live Site:** [https://shop.deeroot.dev](https://shop.deeroot.dev)

## Overview

DeerootShop bridges my passion for piano music with software development. Customers can browse and purchase sheet music and MIDI files of piano arrangements I've created for my YouTube channel, with secure payments and instant digital delivery.

## Tech Stack

**Backend:**
- Java 21 + Spring Boot + Maven
- Spring Security with JWT authentication
- Spring Data JPA and PostgreSQL database(uses RDS for production)
- RESTful API design
- MockMVC for Integration Tests

**Frontend:**
- React.js
- Uses vanilla CSS for styling
- Stripe integration for secure payments

**Infrastructure:**
- AWS EC2 for application hosting
- Amazon RDS for PostgreSQL database
- AWS S3 for storing necessary product-associated files
- Docker containerization
- Nginx reverse proxy
- GitHub Actions for CI/CD

## Deployment

The application is deployed on AWS with the following setup:

1. **EC2 Instance:** Docker containers managed with Docker Compose
2. **RDS Database:** Managed PostgreSQL instance
3. **CI/CD Pipeline:** GitHub Actions automatically builds and deploys on push to main
4. **SSL/TLS:** Nginx handles HTTPS termination and reverse proxy

Deployment time: < 30 seconds from code push to live production using Watchtower

## Key Features

- **Secure Authentication:** JWT-based user registration and login
- **Product Catalog:** Browse sheet music and MIDI files with detailed descriptions
- **Shopping Cart:** Add/remove items with persistent cart state
- **Payment Processing:** Secure credit card payments via Stripe
- **User Dashboard:** View owned/purchased items
- **Admin Panel:** Product management
  
## What I Learned

- Full-stack application architecture and design patterns
- Production deployment on cloud infrastructure (AWS)
- Payment processing and security best practices
- Container orchestration with Docker and docker-compose
- CI/CD pipeline implementation with GitHub Actions
- Database design and optimization with Spring Data JPA

## Contact

- **Developer:** Daniel Emerle
- **Email:** dee22@njit.edu
- **LinkedIn:** [linkedin.com/in/danielemerle](https://linkedin.com/in/danielemerle)
- **YouTube:** [My Piano Channel](https://youtube.com/@deeroot) (where the music comes from!)
