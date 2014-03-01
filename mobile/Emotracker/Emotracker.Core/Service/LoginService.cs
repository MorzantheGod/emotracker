using System;

namespace Emotracker.Core
{
	public class LoginService
	{
		public LoginService ()
		{
		}

		public static OperationResult login(LoginDTO dto)
		{
			return login (dto.Email, dto.Password);
		}

		public static OperationResult login(string email, string password)
		{
			return new OperationResult (true, "OK");
		}
	}
}

