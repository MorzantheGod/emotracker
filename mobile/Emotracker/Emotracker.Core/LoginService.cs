using System;

namespace Emotracker.Core
{
	public class LoginService
	{
		public LoginService ()
		{
		}

		public static Boolean login(LoginDTO dto)
		{
			return login (dto.Email, dto.Password);
		}

		public static Boolean login(string email, string password)
		{
			return true;
		}
	}
}

