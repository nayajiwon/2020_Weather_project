from django.conf import settings
from django.db import models

class User(models.Model):
    name = models.CharField(
            max_length=20,
            default='BLANK'
            )
    id = models.IntegerField(primary_key=True)
    account_id = models.CharField(max_length=20, null=False,
            default='ID')
    account_pw = models.CharField(
        max_length=100,
        null=False,
        default='PW'
    )
