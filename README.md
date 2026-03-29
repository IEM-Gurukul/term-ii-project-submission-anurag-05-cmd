[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title

ZipGo

---

## Problem Statement (max 150 words)

Commuters in cities use ride-hailing apps to find a driver near them fast, but behind the scenes, the company must work hard to handle requests for rides, availability of drivers and the price of the ride. Many of these systems handle data management of structured data and coordinate activities among many people. The new ZipGo Ride Booking System simulates this system using a console-based Java application. When users request a ride through the system, they are assigned an available driver, a price that depends on vehicle type and distance, and the status of their ride. This project is designed using an object-oriented methodology, with a structured approach to each function involving abstraction, modularity and an extensible architecture within the system.

---

## Target User

- Riders who are requesting the transportation services
- Drivers who are providing the ride services
- System operators who are monitoring the rides and users


---

## Core Features

- Rider/Driver Login
- Vehicle management (Car, Bike, Auto)
- Ride Request and Driver Assignment
- Fare Calculation (by Vehicle Type and Distance)
- The lifecycle of a ride (Requested → Accepted → Completed/Canceled)
- Storing the Ride History via file handling
- Input validation and exception handling

---

## OOP Concepts Used

- **Abstraction:** All abstract classes will create the basic properties of the class for it to have common attributes & methods of specialized sub-classes. For example, User and Vehicle are both classes that can establish a base property for subclasses which derive from them.
- **Inheritance:** The Driver and Rider are both classes that would derive from a shared User superclass and both Car, Bike, and Auto would all derive from Vehicle.
- **Polymorphism:** All Vehicle subclass will override the fare calculation method & each will implement its pricing policy.
- **Exception Handling:** We will create several custom exception classes to handle runtime errors. Examples include NoDriverAvailableException, InvalidRideStateException
- **Collections / Threads:** We will use Java Collections (ArrayList, HashMap) to store & manage Users and Rides. We may use threads to simulate a ride's progress (e.g., at least one thread would be needed to allow the Car to move).

---

## Proposed Architecture Description

This system uses a multi-tiered architecture to build objects on an object-oriented approach. The core objects are Riders, Drivers, Vehicles, and Rides, which represent the domain model for the system. All requests for rides come to a central RideManager; RideManager handles requests for rides, assigns drivers, and calculates fares. Data is stored and retrieved via a simple file management layer, so the application logic layer can be separate from the data management layer. 

*Rider Class/Driver Class/Vehicle Class → Ride Class → RideManager → File Storage Layer*

---

## How to Run

### Prerequisites
* **Java JDK 8** or higher
* **Command Line / Terminal**

### Steps

1.  **Clone or download** the project to your local machine.
2.  **Navigate** to the `src` directory:
    ```bash
    cd src
    ```
3.  **Compile** all Java files:
    ```bash
    javac zipgo/**/*.java
    ```
4.  **Run** the application:
    ```bash
    java zipgo.app.ZipGoApp
    ```

---

### Usage Flow

To test the end-to-end functionality, follow this typical workflow:

* **Start** the application.
* **Login as Driver**:
    * Set status to **Online**.
    * Select **Start Listening** for requests.
* **Login as Rider**:
    * Request a ride by entering the destination distance.
* **Real-time Processing**:
    * The Driver receives and processes the ride request immediately.
    * The Rider receives live updates throughout the ride lifecycle.
* **View Statistics**:
    * **Rider** → Trip history
    * **Driver** → Earnings
* **Exit** from the main menu to save all session data to `rides.csv`.

---

### Notes

> [!IMPORTANT]
> * **Order Matters**: Ensure the driver is **Online** and **Listening** before a rider attempts to request a ride.
> * **Concurrency**: Since the app uses multithreading, console outputs may appear asynchronously.
> * **Data Persistence**: Ride data is automatically saved upon trip completion and when the application exits gracefully.

---

## Git Discipline Notes
Minimum 10 meaningful commits required.
