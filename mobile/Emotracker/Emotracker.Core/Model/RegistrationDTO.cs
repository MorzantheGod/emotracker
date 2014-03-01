﻿using System;

namespace Emotracker.Core
{
	[Serializable]
	public class RegistrationDTO
	{
		public string FullName { get; set; }
		public string UserName { get; set; }
		public string Password { get; set; }
		public string Email { get; set; }
		public string TokenId { get; set; }
		public string Key { get; set;}
		public string Token { get; set; }

		public RegistrationDTO (string FullName, string UserName, string Password, string Email)
		{
			this.Email = Email;
			this.FullName = FullName;
			this.Password = Password;
			this.UserName = UserName;
		}

	}
}

