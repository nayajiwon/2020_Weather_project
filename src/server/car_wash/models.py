from django.db import models

# Create your models here.
from django.utils import timezone

class WashType(models.Model):
    tid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=10)

class Washer(models.Model):
    name = models.CharField(max_length=10)
    lat = models.FloatField()
    lon = models.FloatField()
    address = models.CharField(max_length=50)
    phone = models.CharField(max_length=20)
    open_time = models.CharField(max_length=5)
    close_time = models.CharField(max_length=5)
    wash_type = models.ManyToManyField(WashType)

    def __str__(self):
        return self.name



