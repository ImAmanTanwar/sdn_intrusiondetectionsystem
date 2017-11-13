# Intrusion Detection System with SDN
Implementing a flexible Network-based Intrusion Detection and Prevention System on Software-defined Networks.

# Intrusion Detection System
An intrusion detection system (IDS) is a device or software application that monitors a network or systems for malicious activity or policy violations. We, in particular, will be addressing only DDoS attack.

# Implementation Overview
* The packets are captured at the Open flow switches.
* These packets are then sent to the Feature Extractor Module which extracts relevant features and forwards them to the Classifier Module on which we run some machine learning algorithms and train the model.
* The Classifier module( after learning ) sends the information to the Controller, which then pushes appropriate rules the Open flow switches.

# Project Delegation
* Feature Extractor Module and communication between Open flow switches to this module: Aman Tanwar.
* Classification Module and communication between this module and Controller: Ritika Gera.
