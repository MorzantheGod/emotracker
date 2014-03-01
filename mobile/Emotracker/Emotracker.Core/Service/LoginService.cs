using System;
using Newtonsoft.Json;

namespace Emotracker.Core
{
	public class LoginService
	{
		private UserApiService userApiService = new UserApiService ();

		public OperationResult login(LoginDTO dto)
		{
			WebMessage mes = userApiService.loginUser (dto);

			UserDTO userDto;
			if (mes.Result != null) {
				userDto = JsonConvert.DeserializeObject<UserDTO> (mes.Result.ToString ());

				StorageService.saveUserData (userDto);
			}


			OperationResult res = MessageConverter.fromWebMessage (mes);
			return res;
		}
	}
}

