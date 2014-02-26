// WARNING
//
// This file has been generated automatically by Xamarin Studio to store outlets and
// actions made in the UI designer. If it is removed, they will be lost.
// Manual changes to this file may not be handled correctly.
//
using MonoTouch.Foundation;
using System.CodeDom.Compiler;

namespace Emotracker.iOS
{
	[Register ("LoginViewController")]
	partial class LoginViewController
	{
		[Outlet]
		MonoTouch.UIKit.UITextField emailField { get; set; }

		[Outlet]
		MonoTouch.UIKit.UIButton loginButton { get; set; }

		[Outlet]
		MonoTouch.UIKit.UILabel loginMessageLabel { get; set; }

		[Outlet]
		MonoTouch.UIKit.UIView messageView { get; set; }

		[Outlet]
		MonoTouch.UIKit.UITextField passwordField { get; set; }

		[Outlet]
		MonoTouch.UIKit.UIButton registrationButton { get; set; }
		
		void ReleaseDesignerOutlets ()
		{
			if (emailField != null) {
				emailField.Dispose ();
				emailField = null;
			}

			if (loginButton != null) {
				loginButton.Dispose ();
				loginButton = null;
			}

			if (messageView != null) {
				messageView.Dispose ();
				messageView = null;
			}

			if (passwordField != null) {
				passwordField.Dispose ();
				passwordField = null;
			}

			if (registrationButton != null) {
				registrationButton.Dispose ();
				registrationButton = null;
			}

			if (loginMessageLabel != null) {
				loginMessageLabel.Dispose ();
				loginMessageLabel = null;
			}
		}
	}
}
