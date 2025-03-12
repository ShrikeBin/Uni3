# PATTERN DESING DESCRIPTION

---

## Abstract Factory
### Provides an interface for creating families of related or dependent objects without specifying their concrete classes. This promotes consistency among products in the same family and makes it easier to switch between different families.

---

## Adapter
### Acts as a bridge between two incompatible interfaces by converting the interface of one class into another that the client expects. It enables existing classes to work together without modifying their code.

---

## Builder
### Constructs complex objects step-by-step, separating the construction process from its representation. It allows you to create different types of objects using the same construction process.

---

## Composite
### Composes objects into tree-like structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions of objects uniformly, making it easier to work with recursive structures.

---

## Decorator
### Dynamically adds or modifies the behavior of objects by wrapping them in decorator classes. This avoids creating subclasses for every possible combination of behaviors.

---

## Facade
### Provides a simplified interface to a complex subsystem by hiding the underlying complexity. It allows clients to interact with the system more easily and promotes loose coupling between subsystems.

---

## Factory Method
### Defines a method in a base class for creating objects but lets subclasses override the method to specify the type of objects that will be created. It helps adhere to the principle of programming to an interface rather than an implementation.

---

## Singleton
### Ensures that a class has only one instance and provides a global point of access to it. This is useful for managing shared resources or global configurations.

---

## State
### Allows an object to change its behavior when its internal state changes. It delegates state-specific behavior to different state classes, making the code easier to maintain and extend.

---

## Mediator
### Centralizes communication between objects in a system, ensuring that objects do not communicate directly with each other. This reduces dependencies and simplifies the system by promoting loose coupling.

---

## Observer
### Defines a one-to-many dependency between objects, so that when one object (subject) changes state, all its dependent objects (observers) are notified and updated automatically. It’s often used to implement event systems.

---

## Prototype
### Creates new objects by copying existing ones rather than creating them from scratch. This is useful when object creation is expensive, and cloning is more efficient.
