from django.conf import settings
from django.contrib.auth.models import AbstractUser
from django.db import models


class User(models.Model):
    name = models.CharField(max_length=50)
    pw = models.CharField(max_length=20)
    is_active = models.BooleanField()
    kakao_id = models.IntegerField()

    def __str__(self):
        return self.name

    class Meta:
        db_table = 'account_profile'
        app_label = 'account'