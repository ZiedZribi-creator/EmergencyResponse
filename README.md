# Emergency Response System

This project implements an Emergency Response Management System using Java RMI (Remote Method Invocation) technology. The system facilitates communication between emergency centers and ambulances to efficiently handle emergency incidents.

## Project Structure

The project is organized as follows:

```
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── rmi/
│                       ├── DatabaseConnection.java
│                       ├── Server.java
│                       ├── Ambulance/
│                       └── Emergency/
├── images/
│   ├── diagramme de classe.png (Class Diagram)
│   ├── diagrammeUML-arch_generale.drawio.png (General Architecture)
│   ├── diagramseq.png (Sequence Diagram)
│   └── USEcaseDiagram.png (Use Case Diagram)
└── pom.xml
```

## System Architecture

### Class Diagram
<img width="326" height="729" alt="diagramme de classe" src="https://github.com/user-attachments/assets/fca5d66d-864b-42b8-914a-77230c646eec" />


The class diagram shows the relationships between key components:
- CentreUrgence (Emergency Center)
- Incident
- Ambulance
- Incident_Ambulance

### General Architecture
<img width="945" height="637" alt="diagrammeUML-arch_generale drawio (2)" src="https://github.com/user-attachments/assets/9dd45759-724c-4998-b67a-395b082bba60" />


The system consists of:
- Emergency Center Client (PC or Raspberry Pi)
- Server (Coordinates interactions via RMI)
- Database (Stores patient and ambulance information)
- Multiple Ambulance Clients (Raspberry Pi with touch screen)

### Sequence Diagram
<img width="471" height="355" alt="diagramseq" src="https://github.com/user-attachments/assets/7be2eadf-f313-434d-8343-8af96e980bbc" />


Shows the interaction flow:
1. Create Incident (position, type)
2. Confirm Incident
3. Notify Ambulance
4. Confirm Reception
5. Update GPS Position
6. Update Intervention Status

### Use Case Diagram

<img width="1373" height="332" alt="USEcaseDiagram" src="https://github.com/user-attachments/assets/2d23e130-e129-476c-b2f0-84406de308a4" />

Main functionalities:
- Create Incident
- Manage Incident
- Assign Ambulance
- Track Ambulance Position
- Receive Incident Notification
- Send GPS Position
- Confirm Mission Reception

## Technologies Used

- Java
- RMI (Remote Method Invocation)
- MySQL Database
- Maven (Build Tool)

## Getting Started

1. Ensure you have Java JDK installed
2. Set up MySQL database
3. Configure database connection in `DatabaseConnection.java`
4. Run the server: `Server.java`
5. Launch Emergency Center client: `EmergencyClientGUI.java`
6. Launch Ambulance clients: `AmbulanceClientGUI.java`
