from django.conf import settings
from django.contrib.auth.models import AbstractUser
from django.db import models


class User(AbstractUser):
    created_on = models.DateTimeField("등록일자", auto_now_add=True)
    phone = models.CharField(
        max_length=100
    )

    class Meta:
        db_table = 'account_profile'
        app_label = 'account'