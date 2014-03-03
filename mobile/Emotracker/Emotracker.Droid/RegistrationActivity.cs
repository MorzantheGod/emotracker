using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Emotracker.Core;

namespace Emotracker.Droid
{
	[Activity (Label = "RegistrationActivity")]	
	[Service]
	public class RegistrationActivity : Activity
	{
		private RegistrationService regService = new RegistrationService();

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Create your application here
			SetContentView (Resource.Layout.Registration);

			var regButton = FindViewById<Button> (Resource.Id.registrationButton);
			regButton.Click += (sender, e) => {
				OperationResult res = regService.registerNewUser( getRegistrationDTO() );
				if( res.Result == true ) {
					StartActivity (typeof(ResultsActivity));
				}
			};
		}

		protected RegistrationDTO getRegistrationDTO() {
			RegistrationDTO dto = new RegistrationDTO ();

			var fullName = FindViewById<EditText> (Resource.Id.fullName);
			var userName = FindViewById<EditText> (Resource.Id.userName);
			var emailField = FindViewById<EditText> (Resource.Id.email);
			var passwordField = FindViewById<EditText> (Resource.Id.password);

			dto.FullName = fullName.Text;
			dto.UserName = userName.Text;
			dto.Email = emailField.Text;
			dto.Password = passwordField.Text;

			return dto;
		}
	}
}

