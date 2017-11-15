# Instructions

## Feature Extractor
Feature extractor extracts features of TCP and UDP packets such as Source IP,Destination IP, SOurce Port,Destination Port and SYN,ACK,PSH,URG,FIN,RST flags. It will collect features from OVS.

### Prerequisites
For feature extractor, pcap library should be installed.
* libpcap (Linux / OSX)
* winpcap (Windows)
* Java

### Instructions
For Ubuntu/Mininet it can be installed with
```
$sudo apt install libpcap-dev
```
Feature Extrator project has an executable jar file in FeatureExtracter.project/build/libs/ directory. Which can be executed with

```
$java -jar <filename>.jar <featureCollectorAddress> <featureCollectorPort>
```

e.g.
```
$java -jar FeatureExtractor.jar 172.16.167.1 8000
```
Both address and port arguments are optional. Default address is 172.16.167.1 and port is 8000.
Then there will be a list of available interfaces. Select one from which you want to collect features from.

## Feature Collector
Feature Collector collects features from Feature Extractor with the help of REST APIs.

### Prerequisites
* python2
* pip

### Instructions
There is a setup file in FeatureCollector directory which will create virtual environment and install required packages.
```
$./setup.sh
```
Then start server with
```
$(env) python manage.py runserver 0.0.0.0:8000
```
