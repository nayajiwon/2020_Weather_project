# Generated by Django 3.0.7 on 2020-08-08 10:26

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('car_wash', '0006_remove_washer_phone'),
    ]

    operations = [
        migrations.AddField(
            model_name='washer',
            name='phone',
            field=models.CharField(default='010-0000-0000', max_length=15),
        ),
    ]