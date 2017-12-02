from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
import datetime,traceback
import json,requests
import requests,json
from requests.auth import HTTPBasicAuth


@api_view(['POST'])
def send(request):
    if request.method == 'POST':
        data = json.loads(request.POST["details"])
        packets = data['packets']
        numSYN = numFIN = numRST = numPSH = numURG = numACK = count = 0
        sameSrcIpPackets = {}
        sameSrcSYNPackets = {}
        sameSrcRSTPackets = {}
        sameDestIpPackets = {}
        sameSrcSamePort = {}
        sameSrcDiffPort = {}
        sameDestSamePort = {}
        sameDestDiffPort = {}
        sameDestSYNPackets = {}
        for packet in packets:
            srcIP = packet['srcIP']
            destIP = packet['destIP']
            srcPort = packet['srcPort']
            destPort = packet['destPort']
            isSYN = packet['isSYN']
            isRST = packet['isRST']
            isFIN = packet['isFIN']
            isACK = packet['isACK']
            isURG = packet['isURG']
            isPSH = packet['isPSH']

            srcIpPort = srcIP+" "+srcPort
            destIpPort = destIP+" "+destPort
            if srcIP in sameSrcIpPackets:
                sameSrcIpPackets[srcIP] += 1
                if isSYN=="true":
                    sameSrcSYNPackets[srcIP] += 1
                if isRST == "true":
                    sameSrcRSTPackets[srcIP] += 1
                if srcIpPort in sameSrcSamePort:
                    sameSrcSamePort[srcIpPort] += 1
                else:
                    sameSrcDiffPort[srcIP] += 1
                    sameSrcSamePort[srcIpPort] = 0
            else:
                sameSrcIpPackets[srcIP] = 0
                sameSrcSYNPackets[srcIP] = 0
                sameSrcRSTPackets[srcIP] = 0
                sameSrcDiffPort[srcIP] = 0

            if destIP in sameDestIpPackets:
                sameDestIpPackets[destIP] += 1
                if isSYN=="true":
                    sameDestSYNPackets[destIP] += 1
                if destIpPort in sameDestSamePort:
                    sameDestSamePort[destIpPort] += 1
                else:
                    sameDestDiffPort[destIP] += 1
                    sameDestSamePort[srcIpPort] = 0
            else:
                sameDestIpPackets[destIP] = 0
                sameDestSYNPackets[destIP] = 0
                sameDestDiffPort[destIP] = 0


            if isSYN=="true":
                numSYN += 1
            if isRST == "true":
                numRST += 1
            if isFIN == "true":
                numFIN += 1
            if isPSH == "true":
                numPSH += 1
            if isURG == "true":
                numURG += 1
            if isACK == "true":
                numACK += 1
    smSrcIp = smDestIp = smSrcPort = smSrcDfPort = smSrcSYN = smSrcRST = smDestSYN = smDestSmPort = smDestDfPort = 0
    try:
        smSrcIp = sameSrcIpPackets[max(sameSrcIpPackets, key = sameSrcIpPackets.get)]
    except:
        smSrcIp = 0
    try:
        smDestIp = sameDestIpPackets[max(sameDestIpPackets, key = sameDestIpPackets.get)]
    except:
        smDestIp = 0
    try:
        smSrcPort = sameSrcSamePort[max(sameSrcSamePort,key = sameSrcSamePort.get)]
    except:
        smSrcPort = 0
    try:
        smSrcDfPort = sameSrcDiffPort[max(sameSrcDiffPort,key = sameSrcDiffPort.get)]
    except:
        smSrcDfPort = 0
    try:
        smSrcSYN = sameSrcSYNPackets[max(sameSrcSYNPackets,key = sameSrcSYNPackets.get)]
    except:
        smSrcSYN = 0
    try:
        smSrcRST = sameSrcRSTPackets[max(sameSrcRSTPackets,key = sameSrcRSTPackets.get)]
    except:
        smSrcRST = 0
    try:
        smDestSYN = sameDestSYNPackets[max(sameDestSYNPackets,key = sameDestSYNPackets.get)]
    except:
        smDestSYN = 0
    try:
        smDestSmPort = sameDestSamePort[max(sameDestSamePort,key = sameDestSamePort.get)]
    except:
        smDestSmPort = 0
    try:
        smDestDfPort = sameDestDiffPort[max(sameDestDiffPort,key = sameDestDiffPort.get)]
    except:
        smDestDfPort = 0
    print str(numSYN) +","+ str(numRST) + "," +str(numACK)+","+ str(numPSH)+","+str(numURG)+","+str(numFIN) \
                                +"," +str(smSrcIp) \
                                +","+ str(smDestIp) \
                                +"," + str(smSrcPort) \
                                +","+ str(smSrcDfPort) \
                                +","+ str(smSrcSYN) \
                                +","+ str(smSrcRST) \
                                +","+ str(smDestSYN) \
                                +","+str(smDestSmPort) \
                                +","+str(smDestDfPort)
    return Response('{"status":"ok"}',status=status.HTTP_200_OK)


def block_ip_flow(ip_address):
    url = "http://localhost:8181/restconf/config/opendaylight-inventory:nodes/node/openflow:1/table/30/flow/30"
    data = {}
    flow = []
    obj = {}
    obj["id"] = "30"
    obj["table_id"] = "30"
    obj["priority"] = "65535"
    obj["hard-timeout"] = "1200"
    obj["idle-timeout"] = "1200"
    obj["cookie"] = "14"
    obj["installHw"] = "false"
    obj["strict"] = "false"
    match = {}
    match["ipv4-source"] = ip_address+"/32"
    obj["match"] = match
    instructions = {}
    instruction = []
    inst1 = {}
    inst1["order"] = 0
    actions = {}
    action = []
    act1 = {}
    act1["order"] = 0
    act1["drop-action"] = {}
    action.append(act1)
    actions["action"] = action
    inst1["apply-actions"] = actions
    instruction.append(inst1)
    instructions["instruction"] = instruction
    obj["instructions"] = instructions
    flow.append(obj)
    data["flow"]=flow
    headers = {'Content-Type':'application/json','Accept':'application/json'}
    r = requests.put(url,data = json.dumps(data),headers=headers,auth=HTTPBasicAuth('admin', 'admin'))
    if r.status_code == 200:
        print "Flow Push successful"
    else:
        print "Some error happened!!"
