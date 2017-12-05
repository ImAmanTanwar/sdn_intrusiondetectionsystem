# Problem Statement 1
To block access between two hosts in same VLANs and connected to two different switches

## Approach
Pushed Flows with respect to the src and dest MAC address of the hosts. "Block" flow is pushed for matching dest src MAC addresses.
URL,Method & JSON in BlockFlowPush.txt file.

# Problem Statement 2
To connect 2 hosts in different VLANs, also reduce link bandwidth between them i.e 100 kbps.

## Approach
Couldn't complete it. But interVLAN routing was not fully working.
URL,Method & JSON in interVLAN.txt file.
