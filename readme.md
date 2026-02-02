## 📌 Project Overview & Use Case

The **Event Discovery Application** is a backend system designed to manage events and allow users to book tickets for those events.  
It is suitable for platforms like **event listing portals, ticket booking systems, or internal company event management tools**.

The application supports **secure access**, **role-based operations**, and **real-time ticket management**, making it closer to a real-world production backend.

### 🔍 Use Case
- **Admins** can create, manage, and delete events
- **Users** can browse events, filter them, and book tickets (RSVP)
- The system ensures ticket availability and maintains data consistency
- All important service operations are logged for monitoring and debugging

---

## 🧩 Modules Used

- **Spring Boot** – Application framework and configuration
- **Spring Security** – Basic Authentication and role-based authorization (ADMIN / USER)
- **Spring Data JPA** – ORM and database operations
- **PostgreSQL** – Relational database
- **Spring AOP** – Logging of service-layer methods
- **Lombok** – Reduces boilerplate code

---

## 🌐 APIs Implemented

### 🎉 Event APIs

- **POST** `/api/events`  
  Create a new event (Admin only)

- **GET** `/api/events`  
  Fetch all available events

- **GET** `/api/events/search?category={category}`  
  Search events by category

- **GET** `/api/events/filterDate?start={startDate}&end={endDate}`  
  Filter events by date range

- **DELETE** `/api/events/{id}`  
  Delete an event and its related RSVPs (Admin only)

---

### 🎟 RSVP API

- **POST** `/api/events/rsvp`  
  Book tickets for an event (User / Admin)  
  - Validates ticket availability  
  - Updates remaining tickets automatically  

---

## 🏗 Application Structure

- **controller** – Exposes REST APIs
- **service** – Business logic and validations
- **repository** – Database access using JPA
- **entity** – Database models
- **dto** – API request/response models
- **config** – Security and app configuration
- **security** – Custom user authentication
- **aop** – Centralized logging

---

This project demonstrates a **clean backend architecture**, **secure API design**, and **realistic business use cases** commonly expected in production systems.
