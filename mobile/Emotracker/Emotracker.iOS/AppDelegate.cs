using System;
using System.Collections.Generic;
using System.Linq;
using MonoTouch.Foundation;
using MonoTouch.UIKit;
using Emotracker.Core;

namespace Emotracker.iOS
{
	// The UIApplicationDelegate for the application. This class is responsible for launching the
	// User Interface of the application, as well as listening (and optionally responding) to
	// application events from iOS.
	[Register ("AppDelegate")]
	public partial class AppDelegate : UIApplicationDelegate
	{
		// class-level declarations
		UIWindow window;
		UIViewController root;
		public static UIStoryboard Storyboard = UIStoryboard.FromName ("Main", null);
		public static UIViewController initialViewController;

		public static UIViewController mainViewController;
		private CoreStorageService storageService = new CoreStorageService ();

		//
		// This method is invoked when the application has loaded and is ready to run. In this
		// method you should instantiate the window, load the UI into it and then make the window
		// visible.
		//
		// You have 17 seconds to return from this method, or iOS will terminate your application.
		//
		public override bool FinishedLaunching (UIApplication app, NSDictionary options)
		{
			window = new UIWindow (UIScreen.MainScreen.Bounds);
			root = new UIViewController ();

			UserDTO dto = storageService.getUserData ();
			if (dto != null) {
				//initialViewController = Storyboard.InstantiateInitialViewController () as UIViewController;
				initialViewController = Storyboard.InstantiateViewController ("ResultsViewController") as UIViewController;
			} else {
				initialViewController = Storyboard.InstantiateInitialViewController () as UIViewController;
			}

			root.View.AddSubview (initialViewController.View);
			//if (initialViewController is LoginViewController) {
			//		LoginViewController loginView = initialViewController as LoginViewController;
			//		loginView.InitialActionCompleted += (object sender, EventArgs e) => {
			//			loginView.View.RemoveFromSuperview ();

			//			mainViewController = Storyboard.InstantiateViewController("ResultsViewController") as UIViewController;
			//			root.AddChildViewController (mainViewController);
			//			root.Add (mainViewController.View);
			//		};
			//
			window.RootViewController = root;
			window.MakeKeyAndVisible ();
			
			return true;
		}
	}
}

