import requests
from django.conf import settings
from django.db import models


class User(models.Model):
    name = models.CharField(
            max_length=20,
            default='name'
            )
    id = models.IntegerField(primary_key=True)

    def __str__(self):
        return self.name


class UserManager:

    @staticmethod
    def get_or_create(aid):
        print('test')

    @staticmethod
    def is_login(access_token):
        profile_request = requests.get(
            "https://kapi.kakao.com/v2/user/me",
            headers={"Authorization": f"Bearer {access_token}"},
        )

        profile_json = profile_request.json()
        print(profile_json)
        status_code = profile_json.get('code', None)

        if status_code is None:
            return True

        else:
            return False

#        kakao_id = profile_json['id']
#        nickname = profile_json['properties']['nickname']

    
