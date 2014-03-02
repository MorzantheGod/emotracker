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
	[Activity (Label = "LoginActivity")]			
	public class LoginActivity : Activity
	{
		private LoginService loginService = new LoginService ();

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Create your application here
			SetContentView (Resource.Layout.Login);

			var loginButton = FindViewById<Button> (Resource.Id.loginButton);
			loginButton.Click += (sender, e) => {
				OperationResult res = loginService.login( getLoginDTO() );
				if( res.Result == true ) {
					StartActivity (typeof(ResultsActivity));
				}
			};
		}

		protected LoginDTO getLoginDTO() {

			LoginDTO dto = new LoginDTO ();

			var emailField = FindViewById<EditText> (Resource.Id.emailField);
			var passwordField = FindViewById<EditText> (Resource.Id.passwordField);

			dto.Email = emailField.Text;
			dto.Password = passwordField.Text;
			return dto;
		}
	}
}

