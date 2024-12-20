# Physics Simulation App

This project is built by me to learn more about physics, and to mash all the tools that I accumulated over the years developing in Java. 
This app is designed to simulate 2D physics interactions, most notably rigid bodies.

## Features

- **Rigid Body Dynamics**: Simulates objects that can be dynamic or static with linear and angular motion.
- **Collision Detection**: Uses an optimized Separating Axis Theorem (SAT).
- **Versatile Object Types**: Supports polygons, circles, and point particles.

## Challenges Faced

1. **Simultaneous Collisions**:
    - Inaccuracies when one object collides with 2 or more other objects simultaneously.
    - This is most prominent when a circle collides at the corner of the screen, since the screen boundaries consists of 4 static rigid bodies.

2. **Class Management**:
    - To ensure good code maintainability, I used Model View Controller approach. 
    - I tried my best to decouple my code as much as possible, and it paid off when trying to switch to Vaadin and TeaVM. 
    - It was also confusing to try to incorporate the visitor pattern for rendering.

## Known Issues

- **Tunneling Effect**: High-speed objects may pass through thin obstacles without detecting collisions.
- **Simultaneous Collisions**: Circle collides at the corner of the screen
- **Instability**: Too many objects may cause jittering and shaking

## Future Improvements

- **Enhanced Collision Detection**:
    - Implement Continuous Collision Detection (CCD) to address the tunneling effect.

- **Physics Accuracy**:
    - Change to a more advanced solver for improved stability.
    - Implement friction and rolling resistance.

- **Performance**:
    - Integrate spatial partitioning techniques like Quadtrees or just bounding box for faster broad phase collision checks.
    - Perhaps look into multi-threading.

- **Visualization & Debugging**:
    - Add tools for step-by-step simulation playback.
    - Display collision normals, penetration vectors, and other useful debug information.

## How to Use

1. Clone the repository and run the main method in Launcher.java
2. Or use the [web version](https://bairul.github.io/Physics-Sim/)

## Reference

- Formulas used for collision resolution is from articles written by [Chris Hecker](https://www.chrishecker.com/Rigid_Body_Dynamics)
- Narrow phase collision detection is from [Dirk Gregorius](https://gdcvault.com/play/1017646/Physics-for-Game-Programmers-The)

---