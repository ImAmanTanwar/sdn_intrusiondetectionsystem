from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
import datetime,traceback
import json,requests

# Create your views here.

@api_view(['POST'])
def send(request):
    if request.method == 'POST':
        data = json.loads(request.POST["details"])
        packets = data['packets']
        for packet in packets:
            srcIP = packet['srcIP']
            destIP = packet['destIP']
            srcPort = packet['srcPort']
            destPort = packet['destPort']
            if 'isSYN' in packet:
                print "SYN: "+packet["isSYN"]
            if 'isRST' in packet:
                print "RST: "+packet["isRST"]
            print srcIP+" "+destIP+" "+srcPort+" "+destPort
    return Response('{"status":"ok"}',status=status.HTTP_200_OK)
