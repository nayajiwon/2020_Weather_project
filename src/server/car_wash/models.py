from django.db import models

# Create your models here.
from django.utils import timezone

class WashType(models.Model):
    tid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=10)

class Washer(models.Model):
    name = models.CharField(max_length=20)
    lat = models.FloatField()
    lon = models.FloatField()
    address = models.CharField(max_length=50, default='-')
    city = models.CharField(max_length=7, default='서울시')
    district = models.CharField(max_length=10, default='강남구')
    dong = models.CharField(max_length=10, default='도곡동')
    phone = models.CharField(max_length=20, default = '010-0000-0000')

    def __str__(self):
        return self.name



