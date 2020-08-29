from django.http import HttpResponse
from django.http import JsonResponse
from django.shortcuts import render, redirect
from django.core import serializers

from .forms import PostForm
from .models import Washer
# Create your views here.


def washer_list(request):

    if request.method == "GET":
        washer_set = Washer.objects.all()
        data = list(washer_set.values())
        for i in range(len(data)):
            temp = Washer.objects.get(pk=data[i]['id'])
            wash_list = []
            for item in temp.wash_type.all().values():
                wash_list.append(item['name'])
            print(wash_list)
            data[i]['wash'] = wash_list
        return JsonResponse(data, safe=False, json_dumps_params={'ensure_ascii': False})

    elif request.method == "POST":
        print('POST')
        form = PostForm(request.POST)
        if form.is_valid():  # 폼 검증 메소드
            washer = form.save(commit=False)  # lotto 오브젝트를 form으로 부터 가져오지만, 실제로 DB반영은 하지 않는다.
            washer.save()
            return HttpResponse(washer.name)  # url의 name을 경로대신 입력한다.
        else:
            return HttpResponse('error')

def washer_detail(request, pk):
    washer = Washer.objects.get(id=pk)
    wash_type = list(washer.wash_type.all().values())

    content = {
            "name": washer.name,
            "type": wash_type
            }
    return JsonResponse(content, safe=False, json_dumps_params={'ensure_ascii': False})



