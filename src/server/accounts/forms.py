from django import forms
from django.utils.translation import ugettext_lazy as _

from .models import User


class SignupForm(forms.Form):
    name = forms.CharField(label=_('name'),
                                 max_length=30,
                                 widget=forms.TextInput(
                                     attrs={'placeholder':
                                                _('name'), }))
    pw = forms.CharField(label=_('pass word'),
                            max_length=30,
                            widget=forms.TextInput(
                                attrs={'placeholder':
                                           _('Phone number'), }))

    def signup(self, request, user):
        user.name = self.cleaned_data['name']
        user.save()