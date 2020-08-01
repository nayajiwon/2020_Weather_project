from django.conf import settings
from django.db import models


class User(models.Model):
    name = models.CharField(max_length=50, null=True)
    pw = models.CharField(max_length=20)
    kakao_id = models.IntegerField()

    def __str__(self):
        return self.name

    class Meta:
        app_label = 'account'