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
            srcIP = packet['src_ip']
            destIP = packet['dest_ip']
            srcPort = packet['src_port']
            destPort = packet['dest_port']
            print srcIP+" "+destIP+" "+srcPort+" "+destPort
    return Response('{"status":"ok"}',status=status.HTTP_200_OK)
