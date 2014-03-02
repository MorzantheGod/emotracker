using System;
using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;
using Emotracker.Core;

namespace Emotracker.Droid
{
	[Activity (Label = "Emotracker.Droid", MainLauncher = true)]
	public class MainActivity : Activity
	{
		private CoreStorageService storageService = new CoreStorageService();

		protected override void OnCreate (Bundle bundle)
		{
			base.OnCreate (bundle);

			// Set our view from the "main" layout resource
			SetContentView (Resource.Layout.Main);

			UserDTO user = storageService.getUserData ();
			if (user == null) {
				StartActivity (typeof(LoginActivity));
			}
		}
	}
}


