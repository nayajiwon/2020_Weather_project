from django.db import models
from accounts.models import User
# Create your models here.
from django.utils import timezone

class WashType(models.Model):
    tid = models.AutoField(primary_key=True)
    name = models.CharField(max_length=10)

    def __str__(self):
        return self.name

class Washer(models.Model):
    name = models.CharField(max_length=20)
    lat = models.FloatField()
    lon = models.FloatField()
    address = models.CharField(max_length=50, default='-')
    city = models.CharField(max_length=7, default='서울시')
    district = models.CharField(max_length=10, default='강남구')
    dong = models.CharField(max_length=10, default='도곡동')
    phone = models.CharField(max_length=20, default = '010-0000-0000')
    wash_type = models.ManyToManyField(WashType)
    open_week = models.CharField(max_length=20, default = '09:00-18:00')
    open_sat = models.CharField(max_length=20, default = '09:00-18:00')
    open_sun = models.CharField(max_length=20, default = '99:99-99:99')

    def __str__(self):
        return self.name

class Review(models.Model):
    washer = models.ForeignKey('Washer', 
            on_delete=models.CASCADE,
            related_name='reviews'
            )
    user = models.ForeignKey(User, on_delete=models.CASCADE,
            )
    created_date = models.DateTimeField(default=timezone.now)
    content = models.CharField(max_length=100, default = '내용이 없습니다.')

    def __str__(self):
        return self.user

