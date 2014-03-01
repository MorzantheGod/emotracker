using System;
using System.Collections.Generic;

namespace Emotracker.Core
{
	public class UserApiService : ApiService
	{
		public static readonly String THIS_API = "users";
		public static readonly String CREATE_USER = "create";

		public WebMessage createNewUser(RegistrationDTO dto)
		{
			Dictionary<string, Object> param = new Dictionary<string, object> ();
			param.Add ("fullName", dto.FullName);
			param.Add ("userName", dto.UserName);
			param.Add ("email", dto.Email);
			param.Add ("password", dto.Password);

			WebMessage res = this.sendPostRequest (CREATE_USER, param);
			return res;
		}

		protected override string getCurrentApiUrl() 
		{
			return THIS_API;
		}
	}
}

